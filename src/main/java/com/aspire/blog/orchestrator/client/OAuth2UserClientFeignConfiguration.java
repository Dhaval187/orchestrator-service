package com.aspire.blog.orchestrator.client;

import org.springframework.context.annotation.Bean;

import com.aspire.blog.orchestrator.web.rest.errors.UserFeignErrorDecoder;

import feign.RequestInterceptor;

public class OAuth2UserClientFeignConfiguration {

	@Bean(name = "userFeignClientInterceptor")
	public RequestInterceptor getUserFeignClientInterceptor() {
		return new UserFeignClientInterceptor();
	}

	@Bean
	public UserFeignErrorDecoder errorDecoder() {
		return new UserFeignErrorDecoder();
	}
}
