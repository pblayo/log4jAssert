package fr.barreverte;

import static org.fest.assertions.Assertions.assertThat;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class SpyAppenderTest {
	SpyAppender spyAppender = new SpyAppender();

	@Test public void spyAppenderIsALog4jAppender() throws Exception {
		assertThat(new SpyAppender()).isInstanceOf(Appender.class);
	}

	@Test public void registerLoggingEvents() throws Exception {
		LoggingEvent event = new LoggingEvent("", new Category(""){}, null, "log message", null);
		
		spyAppender.doAppend(event);
		
		assertThat(spyAppender.events).containsOnly(event);
	}
	
}
