package com.aspire.blog.orchestrator.web.rest.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * UnknownException is for all unexpected exceptions
 * 
 * @author aspire45
 *
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UnknownException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;

	private final String entityName;

	private final String errorKey;

	public UnknownException(String defaultMessage, String entityName, String errorKey, int statusCode) {
		this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey, statusCode);
	}

	public UnknownException(URI type, String defaultMessage, String entityName, String errorKey, int statusCode) {
		super(type, defaultMessage, getStatusFromStatusCode(statusCode), null, null, null,
				getAlertParameters(entityName, errorKey));
		this.entityName = entityName;
		this.errorKey = errorKey;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getErrorKey() {
		return errorKey;
	}

	/**
	 * returns Status object filtered by statusCode
	 * 
	 * @param statusCode
	 * @return Status object of status code
	 */
	private static Status getStatusFromStatusCode(int statusCode) {
		// default status
		Status statusObject = Status.INTERNAL_SERVER_ERROR;
		for (Status status : Status.values()) {
			if (status.getStatusCode() == statusCode) {
				statusObject = status;
			}
		}
		return statusObject;
	}

	/**
	 * create collection of parameters from entityName and errorKey
	 * 
	 * @param entityName
	 * @param errorKey
	 * @return returns collection of parameters and message
	 */
	private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("message", "error." + errorKey);
		parameters.put("params", entityName);
		return parameters;
	}
}
