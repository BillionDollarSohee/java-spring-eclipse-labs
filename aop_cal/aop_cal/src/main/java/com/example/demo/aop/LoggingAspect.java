package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect // aop 관점임을 선언 
@Component // 스프링 빈으로 등록 
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Calc 클래스의 public 메서드 전체를 포인트컷으로
    // @Pointcut: 어떤 조인포인트를 가로챌지 정의 
    @Pointcut("execution(public * com.example.demo.service.Calc.*(..))")
    void calcOps() {}

    // @Around: 실행 전/후/예외를 한 번에 처리: 성능 측정 + 파라미터/결과 로그
    @Around("calcOps()")
    public Object timeAndLog(ProceedingJoinPoint pjp) throws Throwable { // ProceedingJoinPoint pjp: 대상 메서드에 접근/호출하기 위한 핸들 
        String sig = pjp.getSignature().toShortString(); // 현재 호출되는 메서드 시그니처 요약 문자열(예: Calc.add(..))을 얻음
        Object[] args = pjp.getArgs(); // 대상 메서드에 전달된 실제 인자 배열
        long start = System.nanoTime();
        try {
            log.info("▶ {} args={}", sig, Arrays.toString(args));
            Object ret = pjp.proceed();
            long took = System.nanoTime() - start;
            log.info("◀ {} result={} ({} ms)", sig, ret, took / 1_000_000.0);
            return ret;
        } catch (Throwable t) {
            long took = System.nanoTime() - start;
            log.error("✖ {} threw {} ({} ms)", sig, t.toString(), took / 1_000_000.0);
            throw t;
        }
    }
}