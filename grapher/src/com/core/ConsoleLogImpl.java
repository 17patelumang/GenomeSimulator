package com.core;

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

public class ConsoleLogImpl implements LogListener {

	@Override
	public void logged(LogEntry log) {
		if (log.getMessage() != null)
            System.out.println("[" + log.getBundle().getSymbolicName() + "] " + log.getMessage());

	}

}
