package kr.or.kosa.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelService {
	
	private final VectorStore vectorStore;
	private final JdbcClient jdbcClient;
	
	public void processUploadText(MultipartFile file) throws IOException {
		
		File tmpFile = File.createTempFile("upload", "txt");
		file.transferTo(tmpFile);
		Resource fileResource = new FileSystemResource(tmpFile);
		
		// 이전에 입력한 임베딩한 데이터 건수는 jdbc 간단한 쿼리로
        Integer count = jdbcClient.sql("select count(*) from hotel_vector")
                .query(Integer.class)
                .single();

        List<Document> documents = null;   // ★ documents를 바깥으로 빼야 한다
		
		try {                  // 해지할 자원이 있으으로 try문 사용
			if (count == 0) {  // 아까 임배딩한 자료가 있으면
				documents = Files.lines(fileResource.getFile().toPath())
						.map(Document :: new)  // 메소드 참조 풀어쓰면 map(line -> new Document(line)) 
						.collect(Collectors.toList());
			}
			
			/*		
			Files.lines(...)

			파일을 한 줄씩 읽어서 Stream<String> 으로 반환
			.map(Document::new)
			각 줄을 Document 객체로 변환
			즉, new Document("파일의 한 줄") 형태로 리스트 생성
			.collect(Collectors.toList())
			Stream → List<Document> 로 변환
			결론:		
		   */

            // ★ null 방지: 임베딩할 내용이 없으면 종료
            if (documents == null || documents.isEmpty()) {
                return;
            }

			TokenTextSplitter splitter = new TokenTextSplitter();
			List<Document> splitDocuments = splitter.apply(documents); 
			
			// 청크 사이즈를 따로 지정하지 않았는데 디폴트 값으로 지정된다.
			// chunkSize = 500, chunkOverlap = 50, minChunkSize = 100 maxChunkSize = 정수최대값 keepSeparators false
			
			// 벡터 저장소에 저장
			vectorStore.accept(splitDocuments);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			tmpFile.delete();
		}
	}
	
}
