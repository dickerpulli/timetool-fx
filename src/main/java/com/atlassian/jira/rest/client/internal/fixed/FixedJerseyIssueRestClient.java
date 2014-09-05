package com.atlassian.jira.rest.client.internal.fixed;

import java.net.URI;
import java.util.concurrent.Callable;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.MetadataRestClient;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.SessionRestClient;
import com.atlassian.jira.rest.client.domain.input.WorklogInput;
import com.atlassian.jira.rest.client.internal.jersey.JerseyIssueRestClient;
import com.google.common.base.Strings;
import com.sun.jersey.client.apache.ApacheHttpClient;

public class FixedJerseyIssueRestClient extends JerseyIssueRestClient {

	public FixedJerseyIssueRestClient(URI baseUri, ApacheHttpClient client, SessionRestClient sessionRestClient,
			MetadataRestClient metadataRestClient) {
		super(baseUri, client, sessionRestClient, metadataRestClient);
	}

	@Override
	public void addWorklog(final URI worklogUri, final WorklogInput worklogInput, final ProgressMonitor progressMonitor) {
		final UriBuilder uriBuilder = UriBuilder.fromUri(worklogUri).queryParam("adjustEstimate",
				worklogInput.getAdjustEstimate().restValue);

		switch (worklogInput.getAdjustEstimate()) {
		case NEW:
			uriBuilder.queryParam("newEstimate", Strings.nullToEmpty(worklogInput.getAdjustEstimateValue()));
			break;
		case MANUAL:
			uriBuilder.queryParam("reduceBy", Strings.nullToEmpty(worklogInput.getAdjustEstimateValue()));
			break;
		}

		post(uriBuilder.build(), new Callable<JSONObject>() {
			@Override
			public JSONObject call() throws Exception {
				return new FixedWorklogInputJsonGenerator().generate(worklogInput);
			}
		}, progressMonitor);
	}

}
