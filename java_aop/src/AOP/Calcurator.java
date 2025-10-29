package AOP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/*
간단한 사칙 연산기
주관심사(코어 컨선-Core Concern) : 사칙연산 
보조관심(크로스컷팅 컨선) = 공통관심 : 연산에 걸린 시간, 로그 출력
*/
@Component
public class Calcurator {

	public int add(int x, int y) {
        int result = x + y;
        System.out.println("add 결과 : " + result);
        return result;
    }

    public int mul(int x, int y) {
        int result = x * y;
        System.out.println("mul 결과 : " + result);
        return result;
    }
}
