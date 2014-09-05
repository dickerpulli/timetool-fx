package com.atlassian.jira.rest.client.internal.fixed;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.atlassian.jira.rest.client.AuthenticationHandler;
import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClient;
import com.sun.jersey.client.apache.ApacheHttpClient;

public class FixedJerseyJiraRestClient extends JerseyJiraRestClient {

	private final IssueRestClient fixedIssueRestClient;

	public FixedJerseyJiraRestClient(URI serverUri, ApacheHttpClient client) {
		super(serverUri, client);
		URI baseUri = UriBuilder.fromUri(serverUri).path("/rest/api/latest").build();
		fixedIssueRestClient = new FixedJerseyIssueRestClient(baseUri, getTransportClient(), getSessionClient(),
				getMetadataClient());
	}

	public FixedJerseyJiraRestClient(URI serverUri, AuthenticationHandler authenticationHandler) {
		super(serverUri, authenticationHandler);
		URI baseUri = UriBuilder.fromUri(serverUri).path("/rest/api/latest").build();
		fixedIssueRestClient = new FixedJerseyIssueRestClient(baseUri, getTransportClient(), getSessionClient(),
				getMetadataClient());
	}

	@Override
	public IssueRestClient getIssueClient() {
		return fixedIssueRestClient;
	}

}
