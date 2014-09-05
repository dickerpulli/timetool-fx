package de.tbosch.tools.timetool.model;

/**
 * A class for the settings for JIRA.
 * 
 * @author Thomas Bosch
 */
public class JiraSettings {

	private String url;

	private String user;

	private String pass;

	/**
	 * Constructor.
	 * 
	 * @param url The URL
	 * @param user The username
	 * @param pass The password
	 */
	public JiraSettings(String url, String user, String pass) {
		super();
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

}
