package kr.or.kosa.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbedingService {

	private final VectorStore vectorStore; // lombok 자동주입
	
	// 문서, txt를 read해서 숫자화된 배열로
	public void processUploadPdf(MultipartFile file) throws IOException {
		// 사용자가 업로드한 pdf를 바로 읽으면 성능이 떨어진다.
		// 임시파일로 만들어서 로컬 tamp 폴더 안에 uploadxxxx.pdf로 자동 생성
		File tmpFile = File.createTempFile("upload", "pdf");
		
		file.transferTo(tmpFile);
		
		Resource fileResource = new FileSystemResource(tmpFile);
		
		try {
			// PDF 문서를 읽을건데 형식(옵션)을 정의할게. ex) 제목이랑 공백은 제거하라는 환경설정
			PdfDocumentReaderConfig config = PdfDocumentReaderConfig
					.builder()
					.withPageTopMargin(0)            // PDF 페이지 상단 여백 0
					.withPageExtractedTextFormatter( // 객체들어감
							ExtractedTextFormatter   // 페이지에서 추출한 텍스트 포맷팅 방식
							.builder()
							.withNumberOfBottomTextLinesToDelete(0) //상단이나 하단에서 지울 줄 수
							.build()
					)
					.withPagesPerDocument(1) // 한번에 처리할 페이지 수
					.build();
			PagePdfDocumentReader pdfDocumentReader =
					new PagePdfDocumentReader(fileResource, config);
			
			List<Document> documents = pdfDocumentReader.get();
			
			// 벡터화(float 배열 생성)
			TokenTextSplitter splitter = new TokenTextSplitter(1000,400,10,5000,true);
			/*
			  “ 문서를 1000 토큰씩 자르되,
				다음 청크에 400 토큰을 중복 포함해서 문맥을 잇고,
				너무 작은 청크는 버리고,
				최대 5000 토큰까지 허용하며,
				개행/마침표 같은 구분자도 포함해라.” 

			    TokenTextSplitter(
		    			int chunkSize,
		    			int chunkOverlap,
		    			int minChunkSize,
		    			int maxChunkSize,
		    			boolean keepSeparators
				)
				
				1000 토큰 = 1000단어가 아니다.
				한국어 기준으로 약 500~800 단어 정도,
				영어 기준으로는 약 700~900 단어 정도 분량입니다.
				즉, A4로 치면 약 1~2페이지 분량입니다.
				
				
				chunkOverlap = 400
				✔ 앞 청크 일부(400 tokens)를 다음 청크에 중복해서 포함
				왜 사용? → 문맥 유지!!
				
				예시
				Chunk1: A B C D E F G
				Chunk2: E F G H I J K
				
				400이면 꽤 많은 중복
				→ 정확도↑
				→ 임베딩 비용↑ (청크 양 증가)
					 
					 
				keepSeparators = true
				문장 구분자(개행, 마침표 등)를 청크에 포함할지 여부
				true → "구분자 포함"
					 → 읽기 편하고 문맥 유지
				
				false → 구분자 버림
				      → 임베딩 저장 크기 줄어듦
				
				일반적으로 true 권장
				→ 자연스러운 문맥 유지에 도움.
			 
				 */
			
			List<Document> spDovuments = splitter.apply(documents);
			
			// PGvector store 저장
			vectorStore.accept(documents);
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// IO자원 해제 임시 파일 삭제
			tmpFile.delete();
		}
		
	}
}
