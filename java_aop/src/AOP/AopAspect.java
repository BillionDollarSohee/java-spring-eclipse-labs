package AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AopAspect {

	@Around("execution(* AOP.Calcurator.*(..))")
    public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        StopWatch sw = new StopWatch();
        sw.start();

        System.out.println("[Before Advice] " + methodName + "() 시작");

        Object result = pjp.proceed(); // 실제 메서드 실행

        sw.stop();
        System.out.println("[After Advice] " + methodName + "() 종료");
        System.out.println("[수행 시간] " + sw.getTotalTimeMillis() + "ms\n");

        return result;
    }
}
