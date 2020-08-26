package com.example.approvalservice.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Generic connector between services
 * @author javadevopsmc06
 *
 */

@Component
public class ServiceConnector {
	private RestTemplate restTemplate;

	@Autowired
	public ServiceConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public <T> T post(String url, Object request, Class<T> responseType, Map<String, Object> uriVariables) {
		return restTemplate.postForObject(url, request, responseType, uriVariables);
	}
	
	public <T> T get(String url, Class<T> responseType) {
		return restTemplate.getForObject(url, responseType);
	}
	
	public <T> T patch(String url, Object request, Class<T> responseType) {
		return restTemplate.patchForObject(url, request, responseType);
	}
	
	public <T> T exchange(String url, HttpMethod method, Object request, Class<T> responseType, Map<String, String> params) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		HttpEntity<Object> entity = new HttpEntity<>(request, headers);
		ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType, params);
		return response.getBody();
	}
}
