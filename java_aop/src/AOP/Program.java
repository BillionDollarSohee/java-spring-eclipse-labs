package AOP;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Program {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AopConfig.class);
		
		
		Calcurator cal = ctx.getBean("calcurator", Calcurator.class);
		System.out.println(cal.add(1, 2));
		System.out.println(cal.mul(1, 2));
	}
}
