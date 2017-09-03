package kr.cnkisoft.framework.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.cnkisoft.framework.filter.RequestLoggingFilter;
import kr.cnkisoft.framework.utils.HttpSupportUtils;

/**
 * Created by PureMaN on 2017-05-29.
 */
@Configuration
@ComponentScan(basePackages = { ConfigConstant.BASE_PACKAGE })
@EnableAsync
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	public static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/static/"};
	public static final String[] RESOURCE_URL_PATTERNS = {"/css/**", "/images/**", "/file/**", "/adminlte/**",  "/js/**"};

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		super.addResourceHandlers(registry);
	}

//	@Bean
//	public FilterRegistrationBean characterFilterRegistrationBean() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		registrationBean.setFilter(characterEncodingFilter);
//		registrationBean.addUrlPatterns("/*");
//		return registrationBean;
//	}

	@Bean
	public FilterRegistrationBean requestLoggingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		RequestLoggingFilter loggingFilter = new RequestLoggingFilter(HttpSupportUtils.createOrRequestMathcers(WebMvcConfig.RESOURCE_URL_PATTERNS));
		loggingFilter.setIncludeClientInfo(false);
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		
		registrationBean.setFilter(loggingFilter);
		registrationBean.setOrder(3);
		return registrationBean;
	}

}
