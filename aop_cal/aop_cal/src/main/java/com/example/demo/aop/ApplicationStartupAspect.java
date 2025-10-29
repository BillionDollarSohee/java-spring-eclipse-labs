package com.example.demo.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationStartupAspect {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupAspect.class);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_DIR = "logs";
    private static final String STARTUP_LOG_FILE = "startup.log";
    private static final String METHODS_LOG_FILE_PREFIX = "aop-methods";

    // 애플리케이션이 완전히 시작되었을 때 실행
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        initializeLogging();
        logApplicationStartup();
        createInitialMethodLogFile();
        logSystemInfo();
    }

    // 로깅 디렉토리 및 파일 초기화
    private void initializeLogging() {
        try {
            // 로그 디렉토리 생성
            File logDir = new File(LOG_DIR);
            if (!logDir.exists()) {
                boolean created = logDir.mkdirs();
                log.info("로그 디렉토리 생성 {}: {}", created ? "성공" : "실패", LOG_DIR);
            }

            // 아카이브 디렉토리 생성
            File archiveDir = new File(LOG_DIR + "/archive");
            if (!archiveDir.exists()) {
                boolean created = archiveDir.mkdirs();
                log.info("아카이브 디렉토리 생성 {}: {}", created ? "성공" : "실패", archiveDir.getPath());
            }

        } catch (Exception e) {
            log.error("로깅 디렉토리 초기화 실패", e);
        }
    }

    // 애플리케이션 시작 로그 기록
    private void logApplicationStartup() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String startupLogPath = LOG_DIR + "/" + STARTUP_LOG_FILE;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(startupLogPath, true))) {
            writer.write("==========================================");
            writer.newLine();
            writer.write(String.format("[%s] APPLICATION STARTED", timestamp));
            writer.newLine();
            writer.write(String.format("Startup Time: %s", timestamp));
            writer.newLine();
            writer.write(String.format("Application: AopCalApplication"));
            writer.newLine();
            writer.write(String.format("Profile: %s", getActiveProfile()));
            writer.newLine();
            writer.write("==========================================");
            writer.newLine();
            writer.newLine();
            
            log.info("애플리케이션 시작 로그 저장 완료: {}", startupLogPath);
            
        } catch (IOException e) {
            log.error("애플리케이션 시작 로그 저장 실패: {}", startupLogPath, e);
        }
    }

    // 초기 메서드 로그 파일 생성
    private void createInitialMethodLogFile() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String methodLogPath = String.format("%s/%s-%s.log", LOG_DIR, METHODS_LOG_FILE_PREFIX, today);
        
        try {
            File methodLogFile = new File(methodLogPath);
            if (!methodLogFile.exists()) {
                boolean created = methodLogFile.createNewFile();
                if (created) {
                    // 헤더 정보 작성
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(methodLogFile))) {
                        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
                        writer.write(String.format("# AOP Method Execution Log - %s", today));
                        writer.newLine();
                        writer.write(String.format("# Log Created: %s", timestamp));
                        writer.newLine();
                        writer.write("# Format: [TIMESTAMP] STATUS ClassName.MethodName args/result/exception executionTime");
                        writer.newLine();
                        writer.write("# =================================================================");
                        writer.newLine();
                        writer.newLine();
                    }
                    log.info("메서드 로그 파일 생성 완료: {}", methodLogPath);
                }
            }
        } catch (IOException e) {
            log.error("메서드 로그 파일 생성 실패: {}", methodLogPath, e);
        }
    }

    // 시스템 정보 로그
    private void logSystemInfo() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String systemLogPath = LOG_DIR + "/system-info.log";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(systemLogPath, true))) {
            writer.write(String.format("=== System Information - %s ===", timestamp));
            writer.newLine();
            writer.write(String.format("Java Version: %s", System.getProperty("java.version")));
            writer.newLine();
            writer.write(String.format("Java Home: %s", System.getProperty("java.home")));
            writer.newLine();
            writer.write(String.format("OS Name: %s", System.getProperty("os.name")));
            writer.newLine();
            writer.write(String.format("OS Version: %s", System.getProperty("os.version")));
            writer.newLine();
            writer.write(String.format("User Directory: %s", System.getProperty("user.dir")));
            writer.newLine();
            writer.write(String.format("Available Processors: %d", Runtime.getRuntime().availableProcessors()));
            writer.newLine();
            writer.write(String.format("Max Memory: %d MB", Runtime.getRuntime().maxMemory() / 1024 / 1024));
            writer.newLine();
            writer.write(String.format("Total Memory: %d MB", Runtime.getRuntime().totalMemory() / 1024 / 1024));
            writer.newLine();
            writer.write("=======================================");
            writer.newLine();
            writer.newLine();
            
            log.info("시스템 정보 로그 저장 완료: {}", systemLogPath);
            
        } catch (IOException e) {
            log.error("시스템 정보 로그 저장 실패: {}", systemLogPath, e);
        }
    }

    // Program.run() 메서드 실행 후 추가 로그
    @AfterReturning("execution(* com.example.demo.Program.run(..))")
    public void afterProgramRun() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String executionLogPath = LOG_DIR + "/execution-summary.log";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(executionLogPath, true))) {
            writer.write(String.format("[%s] Program.run() 실행 완료", timestamp));
            writer.newLine();
            writer.write("계산 작업들이 모두 완료되었습니다.");
            writer.newLine();
            writer.write("상세한 메서드 실행 로그는 aop-methods 파일을 확인하세요.");
            writer.newLine();
            writer.write("---");
            writer.newLine();
            
            log.info("프로그램 실행 완료 로그 저장: {}", executionLogPath);
            
        } catch (IOException e) {
            log.error("프로그램 실행 완료 로그 저장 실패: {}", executionLogPath, e);
        }
    }

    // 활성 프로필 확인
    private String getActiveProfile() {
        String profiles = System.getProperty("spring.profiles.active");
        return (profiles != null && !profiles.isEmpty()) ? profiles : "default";
    }
}