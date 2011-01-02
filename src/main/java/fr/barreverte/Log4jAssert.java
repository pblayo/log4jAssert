package fr.barreverte;

import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jAssert {

	public static void spy() {
		Properties logProperties = new Properties();
		logProperties.setProperty("log4j.rootLogger", "TRACE, test_logger");
		logProperties.setProperty("log4j.appender.test_logger", SpyAppender.class.getName());
		PropertyConfigurator.configure(logProperties);
	}

	public static Appender getAppender() {
		SpyAppender appender = (SpyAppender) Logger.getRootLogger().getAppender("test_logger");
		if (appender == null) {
			throw new IllegalStateException("Log4jAssert appender not found.\n" +
					"Check spy() method was called before any assertion.");
		}
		return appender;
	}
	
}
