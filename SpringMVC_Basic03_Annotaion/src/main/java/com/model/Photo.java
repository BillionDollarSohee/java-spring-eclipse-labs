package com.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/*
게시판 글쓰기 + 파일 업로드 

create table photo(name, age, image)
image는 진짜 이미지 파일을 저장하는 것이 아니라 1.jpg처럼 파일명을 저장하고

파일저장하는 clob, blob가 있지만 무식하다
실제 파일은 웹서버의 disk에 저장. 이것도 무식하다
그러면 AWS > S3 (파일서버)에 저장하는 것이 바람직하다.

S3에서 뷰나 리액트와 같은 정적자원을 배포하기도 하지만 주 역할은 파일 서버이다.

파일업로드
1. 파일서버(S3)가 파일 write하기 > File, inputStream, outputStream...
2. 정보저장하기 = DB에 insert하기 -> 파일명, 용량, 확장자들을 저장한다.

Spring에서는 DTO를 통해서 파일객체를 받을거고
파일을 받을 수 있는 클래스 타입이 존재한다 : CommonsMultipartFile 타입이다.
*/

// 테이블생성했다고 가정함
public class Photo {
	private String name;
	private int age;
	private String image;
	
	
	///////////////////////////////////////////////////////////////////////
	// 파일을 담을 수 있는 객체로 업로드한 파일 정보를 받아준다.
	// 단, 맴버필드 이름과 input태그의 이름이 동일해야 한다. <input type ="file" name="file">
	// 그리고, form 태그에 enctype = "multipart/form-date"도 있어야한다.
	private CommonsMultipartFile file;
	
	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	} 
	
	// 이미지가 여러개라면 배열로
	///////////////////////////////////////////////////////////////////////////////
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
