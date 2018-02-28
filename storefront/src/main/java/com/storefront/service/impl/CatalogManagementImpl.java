package com.storefront.service.impl;

import static com.storefront.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.util.ApplicationConstants.PROPERTY_KEY_ACP_CATALOG_ALL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.storefront.common.StoreFrontException;
import com.storefront.json.model.CatalogResponse;
import com.storefront.model.Catalog;
import com.storefront.model.Course;
import com.storefront.model.Module;
import com.storefront.repository.ICatalogRepository;
import com.storefront.repository.ICourseRepository;
import com.storefront.repository.IModuleRepository;
import com.storefront.service.ICatalogManagement;
import com.storefront.service.ICourseManagement;
import com.storefront.util.CommonsUtil;
import com.storefront.util.HTTPClientUtil;
import com.storefront.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class CatalogManagementImpl implements ICatalogManagement {

	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_CATALOG_ALL)
	private String catalogListurl;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private ICatalogRepository catalogRepo;

	@Autowired
	private ICourseRepository courseRepo;

	@Autowired
	private IModuleRepository uModuleRepo;

	@Autowired
	private ICourseManagement iCourseManagement;

	@Override
	public List<Catalog> findAll() {
		List<Catalog> catalogs = (List<Catalog>) catalogRepo.findAll();
		Iterator<Catalog> iterable = catalogs.iterator();
		while (iterable.hasNext()) {
			if (iterable.next().courses.isEmpty()) {
				iterable.remove();
			}
		}
		return catalogs;
	}

	@Override
	public Course findCoursesByCourseId(Long courseId) {
		return courseRepo.findByCourseId(courseId);
	}

	@Override
	public Module findMoudlebyModuleId(Long moduleId) {
		return uModuleRepo.findByModuleId(moduleId);
	}

	@Override
	public Catalog findCatalogByCatalogId(String catalogId) {
		return catalogRepo.findCatalogByCatalogId(catalogId);
	}

	@Override
	public List<Catalog> searchCatalogsByCatalogName(String catalogName) {
		return catalogRepo.searchByCatalogName(catalogName);
	}

	@Override
	public List<Catalog> getAllCatalogs(String accessToken) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + accessToken);
		try {
			builder = new URIBuilder(catalogListurl);
			URL url = builder.build().toURL();
			String catalogResp = httpClientUtil.executeGetRequest(url.toString(), reqHeaders);
			if (StringUtils.isNotBlank(catalogResp)) {
				CatalogResponse catalogResponse = null;
				try {
					catalogResponse = (CatalogResponse) jsonMapper.fromJson(catalogResp, CatalogResponse.class);
					List<Catalog> catalogs = new ArrayList<Catalog>();
					catalogResponse.getData().forEach(data -> {
						Catalog catalog = new Catalog(data.getId(), data.getAttributes().getName(),
								data.getAttributes().getState(), data.getAttributes().getDescription(),
								data.getAttributes().isDefault(),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateCreated()),
								commonsUtil.parseJsonStringIntoDate(data.getAttributes().getDateUpdated()));
						try {
							iCourseManagement.findCoursesByCatalogId(catalog, accessToken);
						} catch (StoreFrontException exception) {
							LOGGER.error("Error while getting courses by catalog ID{}", exception);
						}
						catalogs.add(catalog);
					});
					return catalogs;
				} catch (Exception exception) {
					LOGGER.error("Error in parsing catalog response from ACP{}", exception);
					throw new StoreFrontException(
							"Error in parsing catalog response from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			LOGGER.error("Error while connecting to ACP end point to get catalog list : {}", exception);
			throw new StoreFrontException("Error while connecting to ACP end point to get catalog list " + exception);
		}
	}

}
