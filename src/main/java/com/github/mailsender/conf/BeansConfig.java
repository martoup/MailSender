package com.github.mailsender.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mailsender.utils.RequestValidator;
import com.sendgrid.SendGrid;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Class configuring additional beans for the Spring Application Context
 * @author Martin Nikolov
 *
 */
@Configuration
@PropertySource(value = { "classpath:authentication.properties", "classpath:validation.properties" })
class BeansConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}

	@Bean
	public SendGrid sendgridPusher(@Value("${sendgrid.username}") String username,
			@Value("${sendgrid.password}") String password) {
		return new SendGrid(username, password);
	}

	@Bean
	public WebResource mailgunPusher(@Value("${mailgun.apiKey}") String apiKey, @Value("${mailgun.url}") String url) {
		Client client = Client.create();
		ClientFilter filter = new HTTPBasicAuthFilter("api", apiKey);
		client.addFilter(filter);
		WebResource webResource = client.resource(url);
		return webResource;
	}

	@Bean
	public WebResource mandrillPusher(@Value("${mandrill.url}") String url) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		return webResource;
	}

	@Bean
	public RequestValidator requestValidator(@Value("${maxRecipients}") int maxRecipients,
			@Value("${maxSubjectLength}") int maxSubjectLength) {
		return new RequestValidator(maxRecipients, maxSubjectLength);
	}

}
