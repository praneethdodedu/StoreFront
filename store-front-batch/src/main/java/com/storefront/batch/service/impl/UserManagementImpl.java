package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_USERS_ALL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
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

import com.storefront.batch.common.StoreFrontException;
import com.storefront.batch.json.model.ResponseData;
import com.storefront.batch.json.model.UserResponse;
import com.storefront.batch.model.User;
import com.storefront.batch.repository.IUserReository;
import com.storefront.batch.service.IUserManagement;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class UserManagementImpl implements IUserManagement {

	public static final Logger logger = LoggerFactory.getLogger(UserManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_USERS_ALL)
	private String userListUrl;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private IUserReository iUserReository;

	@Override
	public List<User> getAllUsers(String token, Long offsetId) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(userListUrl);
			builder.addParameter("page[offset]", offsetId.toString());
			URL url = builder.build().toURL();
			String userResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(userResp)) {
				UserResponse userResponse = null;
				try {
					userResponse = (UserResponse) jsonMapper.fromJson(userResp, UserResponse.class);
					List<User> users = new ArrayList<User>();
					for (ResponseData data : userResponse.getData()) {
						User user = new User(Long.parseLong(data.getId()), data.getAttributes().getName(),
								data.getAttributes().getEmail(), data.getType(), data.getAttributes().getProfile(),
								data.getAttributes().getState(), data.getAttributes().getUserType(),
								data.getAttributes().getAvatarUrl());
						user.setRoles(data.getAttributes().getRoles());
						userResponse.getData().forEach(data2 -> {
							if (data.getRelationships() != null && data.getRelationships().getManager() != null) {
								if (data.getRelationships().getManager().getData().getId().equals(data2.getId())) {
									User manager = new User(Long.parseLong(data2.getId()),
											data2.getAttributes().getName(), data2.getAttributes().getEmail(),
											data2.getType(), data2.getAttributes().getProfile(),
											data2.getAttributes().getState(), data2.getAttributes().getUserType(),
											data2.getAttributes().getAvatarUrl());
									manager.setRoles(data2.getAttributes().getRoles());
									user.setManager(manager);
								}
							}
						});
						users.add(user);
					}
					return users;
				} catch (Exception exception) {
					logger.error("Error parsing the Users list from ACP {}", exception);
					throw new StoreFrontException("Error parsing the Users list from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP while getting users list : {}", exception);
			throw new StoreFrontException("Error while connecting to ACP while getting users list: " + exception);
		}
	}

	@Override
	public void saveUsers(List<User> users) throws StoreFrontException {
		users.forEach(user -> iUserReository.save(user));
	}

	@Override
	public User fetchUserByUserId(Long userId) throws StoreFrontException {
		return iUserReository.findByUserId(userId);
	}

}
