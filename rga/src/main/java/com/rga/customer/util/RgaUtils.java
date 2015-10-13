package com.rga.customer.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * Contains util methods for the application.
 * 
 * @author Adrian Sacuiu
 *
 */
public class RgaUtils {

	/**
	 * Prints the error stack trace in the log file defined in the log4j.prop file
	 * 
	 * @param logger the logger defined in the application
	 * @param e the error that was thrown
	 */
	public static void printStackTraceToLogger(Logger logger, Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		logger.error(sw.toString());
	}
}
