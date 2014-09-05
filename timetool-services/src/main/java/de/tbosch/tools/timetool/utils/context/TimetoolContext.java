package de.tbosch.tools.timetool.utils.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

/**
 * Holds the Spring application context and provides access to beans in this context.
 * 
 * @author Thomas Bosch
 */
public final class TimetoolContext {

	/** Load context at class load time */
	static {
		init();
	}

	/** The Spring context. */
	private static ConfigurableApplicationContext context;

	/**
	 * Default private constructor avoiding instanciation.
	 */
	private TimetoolContext() {
		// Nothing
	}

	/**
	 * Gets the Spring application context.
	 * 
	 * @return The context
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * Gets a bean from the context.
	 * 
	 * @param name The name of the bean
	 * @return The bean
	 */
	public static Object getBean(String name) {
		return context.getBean(name);
	}

	/**
	 * Gets a beans from the context.
	 * 
	 * @param type The type of the beans
	 * @param <T> The type of the beans
	 * @return The beans
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getBeansByType(Class<T> type) {
		String[] namesForType = context.getBeanNamesForType(type);
		List<T> beans = new ArrayList<T>();
		for (String name : namesForType) {
			beans.add((T) context.getBean(name));
		}
		return beans;
	}

	/**
	 * Closes the application context.
	 */
	public static void close() {
		context.close();
	}

	/**
	 * Creates the context, if it is null.
	 */
	private static void init() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	/**
	 * Creates the context from given applicationContext. Is overriden, when loaded a second time.
	 * 
	 * @param applicationContext The context to use
	 */
	public static void load(ConfigurableApplicationContext applicationContext) {
		Assert.notNull(applicationContext);
		context = applicationContext;
	}

}
