package de.tbosch.tools.timetool.utils.context;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Helper for accessing messages from resource bundle.
 * 
 * @author Thomas Bosch
 */
public final class MessageHelper {

	/** The name of the resource wrapper in application context. */
	private static final String RESOURCE_BUNDLE_MESSAGE_SOURCE_NAME = "resourceBundleMessageSource";

	/**
	 * Utilities.
	 */
	private MessageHelper() {
		// Utilities
	}

	/**
	 * Get the default message from resource bundle.
	 * 
	 * @param messageKey The key
	 * @return The text
	 */
	public static String getMessage(String messageKey) {
		ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) TimetoolContext.getBean(RESOURCE_BUNDLE_MESSAGE_SOURCE_NAME);
		return resourceBundleMessageSource.getMessage(messageKey, null, Locale.getDefault());
	}

	/**
	 * Get a message with some parameters filled in from resource bundle.
	 * 
	 * @param messageKey The key
	 * @param args The arguments
	 * @return The text
	 */
	public static String getMessage(String messageKey, Object... args) {
		ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) TimetoolContext.getBean(RESOURCE_BUNDLE_MESSAGE_SOURCE_NAME);
		return resourceBundleMessageSource.getMessage(messageKey, args, Locale.getDefault());
	}

}
