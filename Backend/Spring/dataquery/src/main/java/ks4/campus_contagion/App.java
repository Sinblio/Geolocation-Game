package ks4.campus_contagion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the App class. This is the main project file where main() is located.
 * @author Maven
 */
@SpringBootApplication
//@ComponentScan(basePackages = "WebSocket")
public class App {

	
	/** 
	 * @param args : The arguements to pass into the main program.
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	

}