package com.example.demo;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.timer_tasks.PeriodicIpStatusChecker;


@SpringBootApplication
public class DemoApplication {
	final static private int MINUTE_COUNT = 2;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		// start the timer after the application starts
		startPeriodicIpStatusChecker();
	}
	
	private static void startPeriodicIpStatusChecker() {
		final int SECONDS = 5*1000;
		new Timer().scheduleAtFixedRate(new PeriodicIpStatusChecker(), 0, MINUTE_COUNT*SECONDS);
	}
}