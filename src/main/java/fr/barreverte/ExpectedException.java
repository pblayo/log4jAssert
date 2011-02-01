package fr.barreverte;


import org.junit.ComparisonFailure;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ExpectedException implements MethodRule {

	private Class<? extends Throwable> expectedException = null;
	private String expectedMessage;

	public ExpectedException(Class<? extends Throwable> expectedException) {expect(expectedException);}

	public ExpectedException expect(Class<? extends Throwable> expectedException) {
		this.expectedException = expectedException;
		return this;
	}
	
	public ExpectedException withMessage(String expectedMessage) {
		this.expectedMessage = expectedMessage;
		return this;
	}

	public ExpectedException expectMessage(String expectedMessage) {
		return withMessage(expectedMessage);
	}


	
	public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
		return new Statement() {
			@Override public void evaluate() throws Throwable {
				try {
					base.evaluate();
				} catch (Throwable actualException) {
					failIfDifferent(expectedException, expectedMessage, actualException);
					return;
				}
				if (expectedException != null)
					throw new ComparisonFailure("Expected test to throw", expectedException.getName(), "");
			}

		};
	}

	void failIfDifferent(Class<? extends Throwable> expectedException, String expectedMessage, Throwable actualException)
			throws Throwable {
		if (expectedException == null)
			throw actualException;
		if (!actualException.getClass().equals(expectedException))
			throw new ComparisonFailure("", expectedException.getName(), actualException.toString());
		if (expectedMessage == null)
			return;
		if (actualException.getMessage() == null)
			throw new ComparisonFailure("No message in exception", expectedMessage, actualException.getMessage());
		if (!actualException.getMessage().equals(expectedMessage))
			throw new ComparisonFailure("Expected exception with message", expectedMessage, actualException.getMessage());
	}

	public static ExpectedException none() {
		return new ExpectedException(null);
	}

}
