package com.atlassian.jira.rest.client.internal.fixed;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.format.DateTimeFormatter;

import com.atlassian.jira.rest.client.domain.BasicUser;
import com.atlassian.jira.rest.client.domain.Visibility;
import com.atlassian.jira.rest.client.domain.input.WorklogInput;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.atlassian.jira.rest.client.internal.json.gen.BasicUserJsonGenerator;
import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;
import com.atlassian.jira.rest.client.internal.json.gen.VisibilityJsonGenerator;
import com.atlassian.jira.rest.client.internal.json.gen.WorklogInputJsonGenerator;

/**
 * Wegen eines Bug im Jira-Java--Rest-Client (addWorklog macht bei setMinutesSpent(1) aus einer Minute eine Stunde). Fix
 * in {@link FixedWorklogInputJsonGenerator#generate(WorklogInput)}.
 * 
 * @see WorklogInputJsonGenerator
 */
public class FixedWorklogInputJsonGenerator extends WorklogInputJsonGenerator {

	private final JsonGenerator<Visibility> visibilityGenerator = new VisibilityJsonGenerator();

	private final JsonGenerator<BasicUser> basicUserJsonGenerator = new BasicUserJsonGenerator();

	private final DateTimeFormatter dateTimeFormatter;

	public FixedWorklogInputJsonGenerator() {
		this(JsonParseUtil.JIRA_DATE_TIME_FORMATTER);
	}

	public FixedWorklogInputJsonGenerator(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	@Override
	public JSONObject generate(final WorklogInput worklogInput) throws JSONException {
		final JSONObject res = new JSONObject()
				.put("self", worklogInput.getSelf())
				.put("comment", worklogInput.getComment())
				.put("started", dateTimeFormatter.print(worklogInput.getStartDate()))
				.put("timeSpent",
						worklogInput.getMinutesSpent() != null ? worklogInput.getMinutesSpent() + " m" : worklogInput
								.getTimeSpent());

		putGeneratedIfNotNull("visibility", worklogInput.getVisibility(), res, visibilityGenerator);
		putGeneratedIfNotNull("author", worklogInput.getAuthor(), res, basicUserJsonGenerator);
		putGeneratedIfNotNull("updateAuthor", worklogInput.getUpdateAuthor(), res, basicUserJsonGenerator);
		return res;
	}

	private <K> JSONObject putGeneratedIfNotNull(final String key, final K value, final JSONObject dest,
			final JsonGenerator<K> generator) throws JSONException {
		if (value != null) {
			dest.put(key, generator.generate(value));
		}
		return dest;
	}

}
