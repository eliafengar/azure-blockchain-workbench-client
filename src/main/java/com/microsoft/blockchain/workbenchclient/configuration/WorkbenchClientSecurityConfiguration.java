package com.microsoft.blockchain.workbenchclient.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@EnableWebSecurity
public class WorkbenchClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().oauth2Login()
				.loginPage("/oauth2/authorization/azure").userInfoEndpoint().oidcUserService(oidcUserService);
//		
//		http.authorizeRequests().anyRequest().authenticated().and().oauth2Login().userInfoEndpoint()
//		.oidcUserService(oidcUserService);
	}
}
