package org.vito.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class FirstLogger {
	
	public void testLogCreate(){
		Logger logger = Logger.getLogger("MyLogger");
		logger.info("Hello World in console");
	}
	
	public void testLogAddAppender1(){
		Logger logger = Logger.getLogger("MyLogger");
		ConsoleAppender appender = new ConsoleAppender();
		logger.addAppender(appender);
		logger.info("Hello World in console");
	}
	
	public void testLogAddAppender2(){
		Logger logger = Logger.getLogger("MyLogger");
		logger.removeAllAppenders();
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout("%p %t %m%n"), ConsoleAppender.SYSTEM_OUT);
		ConsoleAppender appender1 = new ConsoleAppender(new PatternLayout("%p %t %m%n"));
		logger.addAppender(appender);
		logger.info("Hello World in console");
	}
	
	public static void main(String[] args){
		Logger logger = Logger.getLogger("MyLogger");
		logger.removeAllAppenders();
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout("%p %t %m%n"));
		logger.addAppender(appender);
		logger.info("Hello World in console");
	}
}
