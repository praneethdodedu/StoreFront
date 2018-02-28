package com.storefront.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data class LoginResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("user_role")
	private String userRole;

	@JsonProperty("account_id")
	private String accountId;

	@JsonProperty("user_id")
	private String userId;

}