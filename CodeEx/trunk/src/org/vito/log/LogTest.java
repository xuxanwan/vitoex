package org.vito.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.omg.CORBA.INITIALIZE;

public class LogTest {
	private Logger logger;
	
	public void initialize(){
		logger = Logger.getLogger("logger");
		logger.removeAllAppenders();
		Logger.getRootLogger().removeAllAppenders();
	}
	
	public void basicLogger(){
		BasicConfigurator.configure();
		logger.info("basicLogger");
	}
	
	public void addAppenderWithStream(){
		logger.addAppender(new ConsoleAppender(new PatternLayout("%p %t %m%n"),ConsoleAppender.SYSTEM_OUT));
		logger.info("addAppenderWithStream");
	}
	
	public void addAppenderWithoutStream(){
		logger.addAppender(new ConsoleAppender(new PatternLayout("%p %t %m%n")));
		logger.info("addAppenderWithoutStream");
	}
	
	public static void main(String[] args){
		LogTest test = new LogTest();
		test.initialize();
//		test.basicLogger();
//		test.addAppenderWithStrewam();
		test.addAppenderWithoutStream();
	}
}
