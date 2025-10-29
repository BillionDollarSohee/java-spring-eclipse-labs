package com.example.demo.aop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Aspect
@Component
public class LogFileManagerAspect {

    private static final Logger log = LoggerFactory.getLogger(LogFileManagerAspect.class);
    private static final String LOG_DIR = "logs";
    private static final String ARCHIVE_DIR = "logs/archive";
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final int MAX_ARCHIVE_DAYS = 7; // 7일간 보관
    
    private ScheduledExecutorService fileManagerScheduler;

    @PostConstruct
    public void initFileManager() {
        // 아카이브 디렉토리 생성
        File archiveDir = new File(ARCHIVE_DIR);
        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
            log.info("아카이브 디렉토리 생성: {}", ARCHIVE_DIR);
        }
        
        // 파일 관리 스케줄러 시작
        fileManagerScheduler = Executors.newSingleThreadScheduledExecutor();
        
        // 1시간마다 로그 파일 크기 체크 및 아카이브
        fileManagerScheduler.scheduleAtFixedRate(this::manageLogFiles, 
            1, 60, TimeUnit.MINUTES);
        
        // 매일 자정에 오래된 아카이브 파일 정리
        long initialDelay = calculateInitialDelayToMidnight();
        fileManagerScheduler.scheduleAtFixedRate(this::cleanupOldArchives, 
            initialDelay, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);
        
        log.info("로그 파일 관리 스케줄러 시작");
    }

    @PreDestroy
    public void destroyFileManager() {
        if (fileManagerScheduler != null) {
            fileManagerScheduler.shutdown();
            try {
                if (!fileManagerScheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    fileManagerScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                fileManagerScheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("로그 파일 관리 스케줄러 종료");
        }
    }

    // 로그 파일 관리 (크기 체크 및 아카이브)
    private void manageLogFiles() {
        try {
            File logDir = new File(LOG_DIR);
            if (!logDir.exists()) {
                return;
            }

            File[] logFiles = logDir.listFiles((dir, name) -> 
                name.startsWith("aop-methods-") && name.endsWith(".log"));

            if (logFiles != null) {
                for (File logFile : logFiles) {
                    // 파일 크기가 MAX_FILE_SIZE를 초과하면 아카이브
                    if (logFile.length() > MAX_FILE_SIZE) {
                        archiveLogFile(logFile);
                    }
                }
            }
            
            // 현재 로그 파일 상태 리포트
            reportLogFileStatus();
            
        } catch (Exception e) {
            log.error("로그 파일 관리 중 오류 발생", e);
        }
    }

    // 로그 파일 아카이브
    private void archiveLogFile(File logFile) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String archiveName = logFile.getName().replace(".log", "-" + timestamp + ".log");
            Path source = logFile.toPath();
            Path target = new File(ARCHIVE_DIR, archiveName).toPath();
            
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            log.info("로그 파일 아카이브 완료: {} -> {}", source.getFileName(), target.getFileName());
            
        } catch (IOException e) {
            log.error("로그 파일 아카이브 실패: {}", logFile.getName(), e);
        }
    }

    // 오래된 아카이브 파일 정리
    private void cleanupOldArchives() {
        try {
            File archiveDir = new File(ARCHIVE_DIR);
            if (!archiveDir.exists()) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (MAX_ARCHIVE_DAYS * 24 * 60 * 60 * 1000L);
            File[] oldArchives = archiveDir.listFiles((dir, name) -> {
                File file = new File(dir, name);
                return file.isFile() && file.lastModified() < cutoffTime;
            });

            if (oldArchives != null && oldArchives.length > 0) {
                for (File oldArchive : oldArchives) {
                    if (oldArchive.delete()) {
                        log.info("오래된 아카이브 파일 삭제: {}", oldArchive.getName());
                    }
                }
                log.info("{} 개의 오래된 아카이브 파일 정리 완료", oldArchives.length);
            }
            
        } catch (Exception e) {
            log.error("아카이브 파일 정리 중 오류 발생", e);
        }
    }

    // 로그 파일 상태 리포트
    private void reportLogFileStatus() {
        try {
            File logDir = new File(LOG_DIR);
            File archiveDir = new File(ARCHIVE_DIR);
            
            long currentLogSize = 0;
            int currentLogCount = 0;
            
            if (logDir.exists()) {
                File[] currentLogs = logDir.listFiles((dir, name) -> 
                    name.startsWith("aop-methods-") && name.endsWith(".log"));
                if (currentLogs != null) {
                    currentLogCount = currentLogs.length;
                    for (File logFile : currentLogs) {
                        currentLogSize += logFile.length();
                    }
                }
            }
            
            long archiveLogSize = 0;
            int archiveLogCount = 0;
            
            if (archiveDir.exists()) {
                File[] archiveLogs = archiveDir.listFiles((dir, name) -> name.endsWith(".log"));
                if (archiveLogs != null) {
                    archiveLogCount = archiveLogs.length;
                    for (File archiveFile : archiveLogs) {
                        archiveLogSize += archiveFile.length();
                    }
                }
            }
            
            log.info("로그 파일 상태 - 현재: {} 개 ({} KB), 아카이브: {} 개 ({} KB)", 
                currentLogCount, currentLogSize / 1024,
                archiveLogCount, archiveLogSize / 1024);
                
        } catch (Exception e) {
            log.error("로그 파일 상태 리포트 중 오류 발생", e);
        }
    }

    // 자정까지 남은 시간 계산 (분 단위)
    private long calculateInitialDelayToMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return java.time.Duration.between(now, midnight).toMinutes();
    }
}