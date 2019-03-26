package com.microsoft.blockchain.workbenchclient.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ApplicationsApi;
import io.swagger.client.api.CapabilitiesApi;
import io.swagger.client.api.ContractsApi;
import io.swagger.client.api.LedgersApi;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.ApplicationList;
import io.swagger.client.model.Capabilities;
import io.swagger.client.model.ContractList;
import io.swagger.client.model.LedgerList;
import io.swagger.client.model.Me;

@RestController
public class WorkbenchClientController {

	@Value("${workbench.api.url}")
	private String workbenchApiUrl;

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

	@RequestMapping(method = RequestMethod.GET, path = "ledgers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LedgerList ledgers(OAuth2AuthenticationToken authentication) {
		try {
			LedgersApi api = new LedgersApi();
			this.configureApi(api.getApiClient(), this.getAccessToken(authentication));
			Integer top = 100;
			Integer skip = null;
			return api.ledgersGet(top, skip);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "capabilities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Capabilities operations(OAuth2AuthenticationToken authentication) {
		try {
			CapabilitiesApi api = new CapabilitiesApi();
			this.configureApi(api.getApiClient(), this.getAccessToken(authentication));
			return api.capabilitiesGet();
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
			Integer top = 10;
			Integer skip = null;
			Integer workflowId = 1;
			return api.contractsGet(top, skip, workflowId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void configureApi(ApiClient api, String accessToken) {
		api.setBasePath(this.workbenchApiUrl);
		api.addDefaultHeader("Authorization", "Bearer " + accessToken);
	}

	private String getAccessToken(OAuth2AuthenticationToken authentication) {
		return ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getTokenValue();
	}
}
