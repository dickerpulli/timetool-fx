package de.tbosch.tools.timetool.service.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.RestClientException;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.domain.input.WorklogInput;
import com.atlassian.jira.rest.client.domain.input.WorklogInputBuilder;
import com.atlassian.jira.rest.client.internal.fixed.FixedJerseyJiraRestClient;

import de.tbosch.tools.timetool.exception.ServiceBusinessException;
import de.tbosch.tools.timetool.model.JiraSettings;
import de.tbosch.tools.timetool.service.JiraService;
import de.tbosch.tools.timetool.utils.LogUtils;

/**
 * Standard implementation of {@link JiraService}.
 * 
 * @author Thomas Bosch
 */
@Service
@Transactional
public class JiraServiceImpl implements JiraService {

	private static final Log LOG = LogFactory.getLog(JiraServiceImpl.class);

	private static final Map<String, JiraSession> CACHE = new HashMap<String, JiraServiceImpl.JiraSession>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorklogInput addWorklog(String name, DateTime startDate, int minutesSpent, String comment,
			JiraSettings settings) {
		try {
			// Establisch remote connection to Jira
			URL url = new URL(settings.getUrl());
			JiraSession jiraSession = getJiraSession(url, settings.getUser(), settings.getPass());
			JiraRestClient jiraClient = jiraSession.getJiraRestClient();
			ProgressMonitor pm = new NullProgressMonitor();

			// Create new worklog item to add to Jira
			Issue issue = jiraClient.getIssueClient().getIssue(name, pm);
			URI worklogUri = issue.getWorklogUri();
			WorklogInput worklog = new WorklogInputBuilder(worklogUri).setStartDate(startDate).setComment(comment)
					.setMinutesSpent(minutesSpent).setAdjustEstimateAuto().build();

			// Add it and return the result
			jiraClient.getIssueClient().addWorklog(worklogUri, worklog, pm);
			return worklog;
		} catch (MalformedURLException e) {
			throw new ServiceBusinessException(ServiceBusinessException.URL_MALFORMED, settings.getUrl());
		} catch (RestClientException e) {
			throw new ServiceBusinessException(ServiceBusinessException.CONNCETION_FAILURE, e, settings.getUrl());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSummary(String name, JiraSettings settings) {
		try {
			// Establisch remote connection to Jira
			URL url = new URL(settings.getUrl());
			JiraSession jiraSession = getJiraSession(url, settings.getUser(), settings.getPass());
			JiraRestClient jiraClient = jiraSession.getJiraRestClient();
			ProgressMonitor pm = new NullProgressMonitor();

			// Get description
			Issue remoteIssue = jiraClient.getIssueClient().getIssue(name, pm);
			return remoteIssue.getSummary();
		} catch (MalformedURLException e) {
			throw new ServiceBusinessException(ServiceBusinessException.URL_MALFORMED, settings.getUrl());
		} catch (RestClientException e) {
			throw new ServiceBusinessException(ServiceBusinessException.CONNCETION_FAILURE, e, settings.getUrl());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getTicketNamesBySearchText(String text, JiraSettings settings) {
		try {
			// Establisch remote connection to Jira
			URL url = new URL(settings.getUrl());
			JiraSession soapSession = getJiraSession(url, settings.getUser(), settings.getPass());
			JiraRestClient jiraClient = soapSession.getJiraRestClient();
			ProgressMonitor pm = new NullProgressMonitor();

			// Get description
			SearchResult searchResult = jiraClient.getSearchClient().searchJql("summary ~ " + text, pm);
			List<String> names = new ArrayList<String>();
			for (BasicIssue issue : searchResult.getIssues()) {
				names.add(issue.getKey());
			}
			return names;
		} catch (MalformedURLException e) {
			throw new ServiceBusinessException(ServiceBusinessException.URL_MALFORMED, settings.getUrl());
		} catch (RestClientException e) {
			throw new ServiceBusinessException(ServiceBusinessException.CONNCETION_FAILURE, e, settings.getUrl());
		}
	}

	/**
	 * Creates a new SoapSession for connections to Jira.
	 * 
	 * @param baseUrl
	 *            The url of the server
	 * @param user
	 *            The login name
	 * @param pass
	 *            The passwort
	 * @return The creates session
	 * @throws RemoteException
	 *             if connection error occurs
	 */
	private JiraSession getJiraSession(URL baseUrl, String user, String pass) {
		String key = baseUrl.toString() + user;
		JiraSession jiraSession = CACHE.get(key);
		if (jiraSession == null) {
			Timing timing = Timing.startTiming("JIRA REST client");
			try {
				// get handle to the JIRA SOAP Service from a client point of view
				jiraSession = new JiraSession();

				// connect to JIRA
				Timing loginTiming = Timing.startTiming("Login");
				try {
					jiraSession.connect(baseUrl, user, pass);
				} catch (URISyntaxException e) {
					throw new ServiceBusinessException(ServiceBusinessException.CONNCETION_FAILURE, e);
				} finally {
					loginTiming.printTiming();
				}
			} finally {
				timing.printTiming();
			}
			CACHE.put(key, jiraSession);
		}
		return jiraSession;
	}

	/**
	 * Class to log out timing things.
	 */
	private static final class Timing {

		private final String operationDesc;

		private final long then;

		/**
		 * Constructor.
		 * 
		 * @param operationDesc
		 *            The description
		 */
		private Timing(final String operationDesc) {
			this.operationDesc = operationDesc;
			this.then = System.currentTimeMillis();
		}

		/**
		 * Start the timing from now.
		 * 
		 * @param operationDesc
		 *            A description
		 * @return The timing object
		 */
		private static Timing startTiming(String operationDesc) {
			LogUtils.logInfo("\nRunning : " + operationDesc, LOG);
			return new Timing(operationDesc);
		}

		/**
		 * Print out timing in log.
		 */
		private void printTiming() {
			final long howLong = System.currentTimeMillis() - this.then;
			LogUtils.logInfo("________________________________________________________________", LOG);
			DecimalFormat decFormat = new DecimalFormat("###,##0");
			LogUtils.logInfo("\t" + this.operationDesc + " took " + decFormat.format(howLong) + " ms to run", LOG);
		}
	}

	/**
	 * A class holding the state of the JIRA connection.
	 */
	private static class JiraSession {

		private static final Log LOG = LogFactory.getLog(JiraSession.class);

		private JiraRestClient jiraRestClient;

		/**
		 * Constructor.
		 * 
		 * @param webServicePort
		 *            The URL to connect
		 */
		public JiraSession() {
		}

		/**
		 * Connect to the JIRA server with the given parameters.
		 * 
		 * @param userName
		 *            The username
		 * @param password
		 *            The passwort
		 * @throws RemoteException
		 *             if something went wrong on server
		 * @throws URISyntaxException
		 */
		public void connect(URL baseUrl, String userName, String password) throws URISyntaxException {
			LogUtils.logInfo("\tConnnecting via REST as : " + userName, LOG);
			URI jiraServerUri = baseUrl.toURI();
			jiraRestClient = new FixedJerseyJiraRestClient(jiraServerUri, new BasicHttpAuthenticationHandler(userName,
					password));
			LogUtils.logInfo("\tConnected", LOG);
		}

		/**
		 * @return the jiraSoapService
		 */
		public JiraRestClient getJiraRestClient() {
			return jiraRestClient;
		}

	}

}
