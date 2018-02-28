package com.storefront.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.storefront.batch.model.Catalog;

/**
 * @author Praneeth.dodedu
 *
 */
@Configuration
public class CatalogsFetchBatchConfiguration extends BatchJobConfig {

	private static final Logger logger = LoggerFactory.getLogger(CatalogsFetchBatchConfiguration.class);

	@Bean
	@StepScope
	ItemReader<Catalog> catalogDetailsReader() {
		return new CatalogDataReader();
	}

	@Bean
	@StepScope
	public CatalogDataProcessor catalogDetailsProcessor() {
		return new CatalogDataProcessor();
	}

	@Bean
	@StepScope
	public CatalogDataWriter catalogDetailsWriter() {
		return new CatalogDataWriter();
	}

	@Bean
	public Step stepCatalogsGet() {
		logger.info("Inside method stepUsersGet() step of Batch Job");
		return stepBuilderFactory.get("stepCatalogsGet").<Catalog, Catalog>chunk(100).reader(catalogDetailsReader())
				.processor(catalogDetailsProcessor()).writer(catalogDetailsWriter()).build();
	}

}
