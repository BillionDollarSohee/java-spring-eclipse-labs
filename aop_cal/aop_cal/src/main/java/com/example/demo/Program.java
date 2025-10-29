package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.demo.service.Calc;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Program implements CommandLineRunner {

    private final Calc calc;

    public Program(Calc calc) {
        this.calc = calc;
    }

    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("==========================================");
        System.out.println("AOP 계산기 프로그램 시작");
        System.out.println("로그 파일들이 logs/ 디렉토리에 생성됩니다.");
        System.out.println("==========================================");
        
        // 즉시 계산 실행하여 로그 생성
        System.out.println("add = " + calc.Add(3, 5));
        System.out.println("sub = " + calc.Sub(10, 3));
        System.out.println("mul = " + calc.Mul(4, 6));
        
        // 정상 나눗셈
        System.out.println("div = " + calc.Div(8, 2));
        
        // 예외 발생 케이스
        try {
            System.out.println("div by zero = " + calc.Div(8, 0)); // 예외 로그 AOP로 확인
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        
        // 추가 계산들 (더 많은 로그 생성)
        System.out.println("더 많은 계산 실행 중...");
        for (int i = 1; i <= 5; i++) {
            System.out.println("계산 " + i + ": " + calc.Add(i, i * 2));
        }
        
        System.out.println("==========================================");
        System.out.println("프로그램 실행 완료!");
        System.out.println("로그 파일들을 확인해보세요:");
        System.out.println("- logs/startup.log");
        System.out.println("- logs/aop-methods-yyyy-MM-dd.log");
        System.out.println("- logs/system-info.log");
        System.out.println("- logs/execution-summary.log");
        System.out.println("==========================================");
    }
}