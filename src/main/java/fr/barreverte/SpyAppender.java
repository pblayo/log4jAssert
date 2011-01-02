package fr.barreverte;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class SpyAppender implements Appender {

	List<LoggingEvent> events = new ArrayList<LoggingEvent>();

	@Override public void doAppend(LoggingEvent event) {
		events.add(event);
	}

	@Override public String getName() {	return "test_logger"; }
	@Override public void addFilter(Filter irrelevant) {}
	@Override public void clearFilters() {}
	@Override public void close() {}
	@Override public ErrorHandler getErrorHandler() {return null;}
	@Override public Filter getFilter() {return null;}
	@Override public Layout getLayout() {return null;}
	@Override public boolean requiresLayout() {return false;}
	@Override public void setErrorHandler(ErrorHandler irrelevant) {}
	@Override public void setLayout(Layout irrelevant) {}
	@Override public void setName(String irrelevant) {}

}
