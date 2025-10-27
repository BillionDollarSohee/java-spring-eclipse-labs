package DI_07_Spring;

import java.util.List;

public class ProtocolHandler {
	//여러개의 filter 사용 (여러개니까 배열 그중에서도 동적배열)
	List<MyFilter> filters;

	//getter 주소값을 가져옴
	public List<MyFilter> getFilters() {
		return filters;
	}

	//setter  주입 주소값을 줌
	public void setFilters(List<MyFilter> filters) {
		this.filters = filters;
	}
	
	//검증 함수
	public int filter_Length() {
		return this.filters.size();
	}
	
	
}
