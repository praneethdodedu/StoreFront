package com.storefront;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.storefront")
public class CourseConfiguration {
	
	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}

}
