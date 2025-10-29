package com.example.demo.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Aspect
@Component
public class FileLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(FileLoggingAspect.class);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE_PREFIX = "aop-methods";
    
    // 비동기 로그 저장을 위한 큐와 스케줄러
    private final ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        // 로그 디렉토리 생성
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs();
            log.info("로그 디렉토리 생성: {}", LOG_DIR);
        }
        
        // 스케줄러 시작 - 5초마다 파일에 저장
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::flushLogsToFile, 5, 5, TimeUnit.SECONDS);
        log.info("파일 로깅 스케줄러 시작 (5초 간격)");
    }

    @PreDestroy
    public void destroy() {
        if (scheduler != null) {
            // 마지막으로 남은 로그들 저장
            flushLogsToFile();
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("파일 로깅 스케줄러 종료");
        }
    }

    // Calc 클래스의 모든 public 메서드를 대상으로
    @Pointcut("execution(public * com.example.demo.service.Calc.*(..))")
    void calcOperations() {}

    @Around("calcOperations()")
    public Object logToFile(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringTypeName();
        Object[] args = pjp.getArgs();
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        
        long startTime = System.nanoTime();
        
        try {
            // 메서드 시작 로그
            String startLog = String.format("[%s] START %s.%s args=%s", 
                timestamp, className, methodName, Arrays.toString(args));
            addLogToQueue(startLog);
            
            // 실제 메서드 실행
            Object result = pjp.proceed();
            
            // 메서드 완료 로그
            long executionTime = System.nanoTime() - startTime;
            String endLog = String.format("[%s] END %s.%s result=%s executionTime=%.2f ms", 
                timestamp, className, methodName, result, executionTime / 1_000_000.0);
            addLogToQueue(endLog);
            
            return result;
            
        } catch (Throwable throwable) {
            // 예외 발생 로그
            long executionTime = System.nanoTime() - startTime;
            String errorLog = String.format("[%s] ERROR %s.%s exception=%s executionTime=%.2f ms", 
                timestamp, className, methodName, throwable.toString(), executionTime / 1_000_000.0);
            addLogToQueue(errorLog);
            
            throw throwable;
        }
    }

    // 큐에 로그 추가
    private void addLogToQueue(String logMessage) {
        logQueue.offer(logMessage);
        
        // 큐가 너무 커지면 즉시 플러시 (메모리 보호)
        if (logQueue.size() > 100) {
            flushLogsToFile();
        }
    }

    // 큐에 있는 로그들을 파일에 저장
    private void flushLogsToFile() {
        if (logQueue.isEmpty()) {
            return;
        }
        
        String fileName = String.format("%s/%s-%s.log", 
            LOG_DIR, 
            LOG_FILE_PREFIX, 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String logMessage;
            int count = 0;
            
            while ((logMessage = logQueue.poll()) != null) {
                writer.write(logMessage);
                writer.newLine();
                count++;
            }
            
            if (count > 0) {
                log.debug("파일에 {} 개의 로그 저장: {}", count, fileName);
            }
            
        } catch (IOException e) {
            log.error("로그 파일 저장 실패: {}", fileName, e);
        }
    }
}