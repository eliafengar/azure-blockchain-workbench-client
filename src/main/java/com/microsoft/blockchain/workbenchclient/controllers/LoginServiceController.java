package com.microsoft.blockchain.workbenchclient.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ApplicationsApi;
import io.swagger.client.api.ContractsApi;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.ApplicationList;
import io.swagger.client.model.ContractList;
import io.swagger.client.model.Me;

@RestController
public class LoginServiceController {

	// @Autowired
	// private OAuth2AuthorizedClientService authorizedClientService;

	// @GetMapping("/userInfo")
	// public String index(OAuth2AuthenticationToken authentication) {
	// final OAuth2AuthorizedClient authorizedClient = this.authorizedClientService
	// .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),
	// authentication.getName());
	// return ((DefaultOidcUser)
	// authentication.getPrincipal()).getIdToken().getTokenValue();
	// }

	@RequestMapping(method = RequestMethod.GET, path = "me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Me me(OAuth2AuthenticationToken authentication) {
		try {
			UsersApi api = new UsersApi();
			this.configureApi(api.getApiClient(), this.getAccessToken(authentication));
			return api.meGet();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "apps", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApplicationList applications(OAuth2AuthenticationToken authentication) {
		try {
			ApplicationsApi api = new ApplicationsApi();
			this.configureApi(api.getApiClient(), this.getAccessToken(authentication));
			Integer top = 100;
			Integer skip = null;
			Boolean enabled = true;
			return api.applicationsGet(top, skip, enabled);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "contracts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ContractList contracts(OAuth2AuthenticationToken authentication) {
		try {
			ContractsApi api = new ContractsApi();
			this.configureApi(api.getApiClient(), this.getAccessToken(authentication));
			Integer top = null;
			Integer skip = null;
			Integer workflowId = null;
			return api.contractsGet(top, skip, workflowId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void configureApi(ApiClient api, String accessToken) {
		api.setBasePath("https://myworkbench-65oim4-api.azurewebsites.net");
		api.addDefaultHeader("Authorization", "Bearer " + accessToken);
	}

	private String getAccessToken(OAuth2AuthenticationToken authentication) {
		return ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
	}
}
