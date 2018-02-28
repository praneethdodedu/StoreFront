package com.storefront.service.impl;

import static com.storefront.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_CHECK_TOKEN;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_POST_USER_URL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.storefront.common.StoreFrontException;
import com.storefront.json.model.LoginResponse;
import com.storefront.json.model.RegisteredUserResponse;
import com.storefront.json.model.ResponseData;
import com.storefront.model.User;
import com.storefront.repository.IUserRepository;
import com.storefront.service.IUserManagement;
import com.storefront.util.HTTPClientUtil;
import com.storefront.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class UserManagementImpl implements IUserManagement {

	public static final Logger logger = LoggerFactory.getLogger(UserManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_CHECK_TOKEN)
	private String checkTokenUrl;

	@Value(PROPERTY_KEY_ACP_POST_USER_URL)
	private String postNewUserUrl;

	@Autowired
	private IUserRepository iUserRepository;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Override
	public List<User> findAll() throws StoreFrontException {
		return (List<User>) iUserRepository.findAll();
	}

	@Override
	public User fetchUserByUserId(Long userId) throws StoreFrontException {
		return iUserRepository.findByUserId(userId);
	}

	@Override
	public User saveUser(User user) {
		return iUserRepository.save(user);
	}

	@Override
	public User getCurrentUser(String accessToken) throws StoreFrontException {
		URIBuilder builder = null;

		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + accessToken);
		try {
			builder = new URIBuilder(checkTokenUrl);
			builder.addParameter("access_token", accessToken);
			URL url = builder.build().toURL();
			String userResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(userResp)) {
				LoginResponse loginResponse = null;
				try {
					loginResponse = (LoginResponse) jsonMapper.fromJson(userResp, LoginResponse.class);
					User user = null;
					if (loginResponse != null)
						user = iUserRepository.findByUserId(Long.parseLong(loginResponse.getUserId()));
					return user;
				} catch (Exception exception) {
					logger.error("Error in fetching access token/account info from ACP {}", exception);
					throw new StoreFrontException(
							"Error in fetching access token/account info from ACP  : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP to get the token/account info: {}", exception);
			throw new StoreFrontException("Error while connecting to ACP to get token/account info: " + exception);
		}
	}

	@Override
	public User signUpNewUser(String userName, String emailId) throws StoreFrontException {
		URIBuilder builder = null;
		String accessToken = "a2ba44c27a0a15cda6929d22c5bf865a";
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + accessToken);
		reqHeaders.put("Content-Type", "application/json");
		try {
			builder = new URIBuilder(postNewUserUrl);
			builder.addParameter("access_token", accessToken);
			URL url = builder.build().toURL();
			String userResp = httpClientUtil.executePostRequest(url.toString(), reqHeaders,
					formRequestBody(userName, emailId));
			if (StringUtils.isNotBlank(userResp)) {
				RegisteredUserResponse userResponse = null;
				try {
					userResponse = (RegisteredUserResponse) jsonMapper.fromJson(userResp, RegisteredUserResponse.class);
					ResponseData data = userResponse.getData();
					User user = new User(Long.parseLong(data.getId()), data.getAttributes().getName(),
							data.getAttributes().getEmail(), data.getType(), data.getAttributes().getProfile(),
							data.getAttributes().getState(), data.getAttributes().getUserType(),
							data.getAttributes().getAvatarUrl());
					user.setRoles(data.getAttributes().getRoles());
					if (data.getRelationships() != null && data.getRelationships().getManager() != null) {
						user.setManager(iUserRepository
								.findByUserId(Long.parseLong(data.getRelationships().getManager().getData().getId())));
					}
					iUserRepository.save(user);
					return user;
				} catch (Exception exception) {
					logger.error("Error parsing the Users list from ACP {}", exception);
					throw new StoreFrontException("Error parsing the Users list from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP to get the token/account info: {}", exception);
			throw new StoreFrontException("Error while connecting to ACP to get token/account info: " + exception);
		}
	}

	public String formRequestBody(String userName, String emailId) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		JsonNode dataNode = mapper.createObjectNode();
		((ObjectNode) dataNode).put("type", "user");

		JsonNode attributeNode = mapper.createObjectNode();
		((ObjectNode) attributeNode).put("email", emailId);
		((ObjectNode) attributeNode).put("name", userName);
		((ObjectNode) attributeNode).put("userType", "INTERNAL");
		((ObjectNode) dataNode).set("attributes", attributeNode);

		((ObjectNode) rootNode).set("data", dataNode);

		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

}
