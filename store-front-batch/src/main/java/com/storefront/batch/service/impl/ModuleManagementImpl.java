package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.CHARACTER_KEY_FORWARD_SLASH;
import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PATH_VARIABLE_MODULES;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_COURSE_ALL;

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
import com.storefront.batch.json.model.ModuleResponse;
import com.storefront.batch.model.Module;
import com.storefront.batch.service.IModuleManagement;
import com.storefront.batch.util.CommonsUtil;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class ModuleManagementImpl implements IModuleManagement {

	private static final Logger logger = LoggerFactory.getLogger(ModuleManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_COURSE_ALL)
	private String allCourseUrl;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Override
	public List<Module> getModulesByCourseId(Long courseId, String token) throws StoreFrontException {
		URIBuilder builder = null;

		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(allCourseUrl + CHARACTER_KEY_FORWARD_SLASH + courseId + CHARACTER_KEY_FORWARD_SLASH
					+ PATH_VARIABLE_MODULES);
			URL url = builder.build().toURL();
			String moduleResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(moduleResp)) {
				ModuleResponse moduleResponse = null;
				try {
					moduleResponse = (ModuleResponse) jsonMapper.fromJson(moduleResp, ModuleResponse.class);
					List<Module> modules = new ArrayList<>();
					moduleResponse.getData().forEach(data -> {
						Module module = new Module(Long.parseLong(data.getId().toString()), data.getType(),
								data.getAttributes().getDescription(), data.getAttributes().getModuleType(),
								data.getAttributes().getTitle(),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()));
						modules.add(module);
					});
					return modules;
				} catch (Exception exception) {
					logger.error("Error in parsing module list for particular course from ACP{}", exception);
					throw new StoreFrontException(
							"Error in parsing module list for particular course from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP while fetching modules data for particluar course : {}",
					exception);
			throw new StoreFrontException(
					"Error while connecting to ACP while fetching modules data for particluar course:" + exception);
		}
	}

}
