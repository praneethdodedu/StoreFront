package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_LEARNING_PROGRAM_ALL;

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
import com.storefront.batch.json.model.LearningProgramResponse;
import com.storefront.batch.model.Course;
import com.storefront.batch.model.LearningProgram;
import com.storefront.batch.repository.ICourseRepository;
import com.storefront.batch.repository.ILearningProgramRepository;
import com.storefront.batch.service.ILearningProgramManagement;
import com.storefront.batch.util.CommonsUtil;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class LearningProgramManagementImpl implements ILearningProgramManagement {

	private static Logger logger = LoggerFactory.getLogger(CatalogManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_LEARNING_PROGRAM_ALL)
	private String learningProgramListurl;

	@Autowired
	private ILearningProgramRepository learningProgramRepo;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private ICourseRepository iCourseRepo;

	@Override
	public List<LearningProgram> getAllLearningPrograms(String token, Long offsetId) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(learningProgramListurl);
			builder.addParameter("page[offset]", offsetId.toString());
			URL url = builder.build().toURL();
			String learningProgramResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(learningProgramResp)) {
				LearningProgramResponse learningProgramResponse = null;
				try {
					learningProgramResponse = (LearningProgramResponse) jsonMapper.fromJson(learningProgramResp,
							LearningProgramResponse.class);
					List<LearningProgram> learningPrograms = new ArrayList<LearningProgram>();
					learningProgramResponse.getData().forEach(data -> {
						LearningProgram learningProgram = new LearningProgram(Long.parseLong(data.getId()),
								data.getAttributes().getName(), data.getAttributes().getState(), data.getType(),
								data.getAttributes().getCourseOrderEnforced(),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()),
								Long.parseLong(data.getAttributes().getEffectivenessIndex()),
								data.getAttributes().getEnrollmentType());
						data.getRelationships().getCourses().getData().forEach(data2 -> {
							Course course = iCourseRepo.findByCourseId(Long.parseLong(data2.getId()));
							if (learningProgram.getCourses() != null) {
								learningProgram.getCourses().add(course);
							}
						});
						learningPrograms.add(learningProgram);
					});
					return learningPrograms;
				} catch (Exception exception) {
					logger.error("Error in parsing catalog response from ACP{}", exception);
					throw new StoreFrontException(
							"Error in parsing catalog response from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP end point to get laerning program list : {}", exception);
			throw new StoreFrontException(
					"Error while connecting to ACP end point to get laerning program list " + exception);
		}
	}

	@Override
	public void saveLearningPrograms(List<LearningProgram> alLearningPrograms) {
		alLearningPrograms.forEach(learningProgram -> {
			learningProgramRepo.save(learningProgram);
		});
	}

}
