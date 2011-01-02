package fr.barreverte;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class AssertTest {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Test public void whenAppenderNotInitializedIllegalStateExceptionRaised() {
		exception.expect(IllegalStateException.class);
		exception.expectMessage("Check spy() method was called before any assertion.");
		
		Log4jAssert.getAppender();
	}
	
	@Test public void whenSpyIsStartedLog4jAssertAppenderIsKnown() throws Exception {
		Log4jAssert.spy();
		@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
		List allAppenders = Collections.list(logger.getRootLogger().getAllAppenders());
		
		Assertions.assertThat(allAppenders).containsOnly(Log4jAssert.getAppender());
	}

}
