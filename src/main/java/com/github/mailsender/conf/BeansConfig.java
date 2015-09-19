package com.github.mailsender.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/**
 * Class configuring additional beans for the Spring Application Context
 * @author Martin Nikolov
 *
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.SendGrid;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@Configuration
@PropertySource(value = { "classpath:authentication.properties" })
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
	public SendGrid sendGridSender(@Value("${sendgrid.username}") String username,
			@Value("${sendgrid.password}") String password) {
		return new SendGrid(username, password);
	}

	@Bean
	public WebResource mailGunSender(@Value("${mailgun.apiKey}") String apiKey,
			@Value("${mailgun.apiVersion}") String apiVersion, @Value("${mailgun.host}") String host) {
		Client client = Client.create();
		ClientFilter filter = new HTTPBasicAuthFilter("api", apiKey);
		client.addFilter(filter);
		WebResource webResource = client.resource("https://api.mailgun.net" + apiVersion + host + "/messages");
		return webResource;
	}

}
