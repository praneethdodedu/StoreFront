package com.storefront.service.impl;

import static com.storefront.util.ApplicationConstants.CHARACTER_KEY_FORWARD_SLASH;
import static com.storefront.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.util.ApplicationConstants.PATH_VARIABLE_MODULES;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_COURSE_ALL;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_FLUIDIC_PLAYER_URL;

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
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.ModuleResponse;
import com.storefront.model.Module;
import com.storefront.repository.ICourseRepository;
import com.storefront.repository.IModuleRepository;
import com.storefront.service.IModuleManagement;
import com.storefront.util.CommonsUtil;
import com.storefront.util.HTTPClientUtil;
import com.storefront.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class ModuleManagementImpl implements IModuleManagement {

	private static final Logger logger = LoggerFactory.getLogger(ModuleManagementImpl.class);

	@Autowired
	private IModuleRepository moduleRepo;

	@Autowired
	private ICourseRepository courseRepo;

	@Value(PROPERTY_KEY_ACP_COURSE_ALL)
	private String allCourseUrl;

	@Value(PROPERTY_KEY_ACP_FLUIDIC_PLAYER_URL)
	private String fluidicPlayerURL;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Module> findModulesByCourseId(Long courseId) {
		return null;
	}

	@Override
	public Module findModuleByModuleId(Long moduleId) {
		return moduleRepo.findByModuleId(moduleId);
	}

	@Override
	public List<Module> findAll() {
		return (List<Module>) moduleRepo.findAll();
	}

	@Override
	public List<Module> getModulesByCourseId(Long courseId, String accessToken) throws StoreFrontException {
		URIBuilder builder = null;

		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + accessToken);
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

	@Override
	public String getFluidicPlayerUrl(String courseId, String moduleId, OAuth2Authentication authentication)
			throws StoreFrontException {
		String courseInstance = courseRepo.findOne(Long.parseLong(courseId)).getCourseInstance();
		final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		String accessToken = details.getTokenValue();
		String courseModuleResourceId = courseInstance.substring(0, courseInstance.length() - 1) + moduleId
				+ courseInstance.substring(courseInstance.length() - 2);
		String fluidicPlayerUrl = fluidicPlayerURL + courseId + "&module_id=course:" + courseModuleResourceId
				+ "&access_token=" + accessToken;
		logger.info("00000000000000000000000000: " + fluidicPlayerUrl);
		return fluidicPlayerUrl;
	}

}
