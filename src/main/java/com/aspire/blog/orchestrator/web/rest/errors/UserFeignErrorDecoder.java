package com.aspire.blog.orchestrator.web.rest.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * This is error decoder thrown from api called using Feign client of uaa (User
 * Account and Authentication) Implements ErrorDecoder interface and implements
 * overridden method
 * 
 * @author aspire45
 *
 */
public class UserFeignErrorDecoder implements ErrorDecoder {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Exception decode(String methodKey, Response response) {

		// gets entityName from headers of response
		String entityName = !response.headers().get("x-uaaapp-params").isEmpty()
				? response.headers().get("x-uaaapp-params").iterator().next()
				: "";

		// gets errorKey from headers of response
		String errorKey = !response.headers().get("x-uaaapp-error").isEmpty()
				? response.headers().get("x-uaaapp-error").iterator().next()
				: "";
		logger.error("length : {}", errorKey.split(ErrorConstants.PREFIX_ERROR_KEY).length);

		// splits error. from errorKey
		errorKey = errorKey.split(ErrorConstants.PREFIX_ERROR_KEY).length > 1
				? errorKey.split(ErrorConstants.PREFIX_ERROR_KEY)[1]
				: errorKey;

		// switch case checks status of response
		// based on status value returns relevent exceptions with entityName and
		// errorKey
		switch (response.status()) {
		case 400:
			return new BadRequestAlertException("", entityName, errorKey);
		case 404:
			return new ResourceNotFoundException("", entityName, errorKey);
		default:
			return new UnknownException("", "", "", response.status());
		}
	}

}
