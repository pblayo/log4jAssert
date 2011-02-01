package fr.barreverte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.BindException;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runners.model.Statement;

public class ExpectedExceptionTest {

	public ExpectedException exception = new ExpectedException(ExceptionInTest.class);
	
	@Test public void whenTestThrowsExpectedException_thenNoExceptionTrown() throws Throwable {
		exception.apply(testThrows(new ExceptionInTest()), null, null).evaluate();
	}

	@Test public void whenTestThrowsUnexpectedExceptionType_thenComparisonFailureRaised() throws Throwable {
		when(testThrows(new BindException())).messageIs(
			"expected:<[fr.barreverte.ExceptionInTest]> " +
			"but was:<[java.net.BindException]>");
	}
	
	@Test public void whenNoExceptionExpectdAndTestThrowsOne_thenShowThisException() throws Throwable {
		exception = ExpectedException.none();
		try {
			exception.apply(testThrows(new ExceptionInTest()), null, null).evaluate();
			fail("ExceptionInTest should have been raised");
		} catch (ExceptionInTest success) {}
	}
	
	@Test public void whenNoExceptionExpectdAndTestThrowsNon_thenNoExceptionShown() throws Throwable {
		exception = ExpectedException.none();

		exception.apply(noExceptionInTest, null, null).evaluate();
	}
	
	@Test public void whenTestDontRaisedExceptionAtAll_thenSpecificMessageRaised() throws Throwable {

		when(noExceptionInTest).messageIs("Expected test to throw expected:<[fr.barreverte.ExceptionInTest]> but was:<[]>");
	}

	@Test public void whenDifferentMessage_thenComparisonFailureRaised()
		throws Throwable {
		exception.expectMessage("blayo");

		when(testThrows(new ExceptionInTest("philippe"))).
		messageIs("Expected exception with message " +
				"expected:<[blayo]> but was:<[philippe]>");
	}

	@Test public void whenNoMessageInException_thenSpecificMessageInComparisonFailure() throws Throwable {
		exception.expectMessage("blayo");
		 
		when(testThrows(new ExceptionInTest())).messageIs("No message in exception expected:<blayo> but was:<null>");
	}

	@Test public void setExpectedMessageInRuleConstruction() throws Throwable {
		exception = new ExpectedException(ExceptionInTest.class).withMessage("blayo");

		when(testThrows(new ExceptionInTest())).messageIs("No message in exception expected:<blayo> but was:<null>");
	}
	
	private Statement testThrows(final Throwable exceptionThrown) {
		return new Statement() {
			@Override public void evaluate() throws Throwable {throw exceptionThrown;}
		};
	}
	
	Context when(Statement test) {return new Context(test);}
	class Context {
		private Statement test;
		public Context(Statement test) {this.test = test;}

		void messageIs(String expectedMessage) throws Throwable {
			try {
				exception.apply(test, null, null).evaluate();
				fail("Should have thrown ComparisonFailure exception");
			} catch (ComparisonFailure success) {
				assertEquals(expectedMessage, success.getMessage());
			}
		}
	}
	
	Statement noExceptionInTest = new Statement() {@Override public void evaluate() throws Throwable {}};

}
class ExceptionInTest extends Exception {
	public ExceptionInTest(String message) {super(message);}
	public ExceptionInTest() {}
	static final long serialVersionUID = 1L;
}
