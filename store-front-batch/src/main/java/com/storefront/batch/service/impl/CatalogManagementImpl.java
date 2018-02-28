package com.storefront.batch.service.impl;

import static com.storefront.batch.util.ApplicationConstants.PARAM_KEY_AUTHORIZATION;
import static com.storefront.batch.util.ApplicationConstants.PARAM_VALUE_OAUTH;
import static com.storefront.batch.util.ApplicationConstants.PROPERTY_KEY_ACP_CATALOG_ALL;

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
import com.storefront.batch.json.model.CatalogResponse;
import com.storefront.batch.model.Catalog;
import com.storefront.batch.repository.ICatalogRepository;
import com.storefront.batch.service.ICatalogManagement;
import com.storefront.batch.service.ICourseManagement;
import com.storefront.batch.util.CommonsUtil;
import com.storefront.batch.util.HTTPClientUtil;
import com.storefront.batch.util.ObjectJSONMapper;

/**
 * @author Praneeth.dodedu
 *
 */
@Service
public class CatalogManagementImpl implements ICatalogManagement {

	private static Logger logger = LoggerFactory.getLogger(CatalogManagementImpl.class);

	@Value(PROPERTY_KEY_ACP_CATALOG_ALL)
	private String cataloglisturl;

	@Autowired
	private ICatalogRepository catalogRepo;

	@Autowired
	private HTTPClientUtil httpClientUtil;

	@Autowired
	private ObjectJSONMapper jsonMapper;

	@Autowired
	private CommonsUtil commonsUtil;

	@Autowired
	private ICourseManagement iCourseManagement;

	@Override
	public List<Catalog> getAllCatalogs(String token, Long offsetId) throws StoreFrontException {
		URIBuilder builder = null;
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put(PARAM_KEY_AUTHORIZATION, PARAM_VALUE_OAUTH + token);
		try {
			builder = new URIBuilder(cataloglisturl);
			builder.addParameter("page[offset]", offsetId.toString());
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
							iCourseManagement.findCoursesByCatalogId(catalog, token);
						} catch (StoreFrontException exception) {
							logger.error("Error while getting courses by catalog ID{}", exception);
						}
						catalogs.add(catalog);
					});
					return catalogs;
				} catch (Exception exception) {
					logger.error("Error in parsing catalog response from ACP{}", exception);
					throw new StoreFrontException(
							"Error in parsing catalog response from ACP : " + exception.getMessage());
				}
			}
			return null;
		} catch (URISyntaxException | IOException exception) {
			logger.error("Error while connecting to ACP end point to get catalog list : {}", exception);
			throw new StoreFrontException("Error while connecting to ACP end point to get catalog list " + exception);
		}
	}

	@Override
	public void saveCatalogs(List<Catalog> allCatalogs) {
		allCatalogs.forEach(catalog -> catalogRepo.save(catalog));
	}

}
