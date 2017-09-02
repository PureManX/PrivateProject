package kr.cnkisoft.framework.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages=ConfigConstant.BASE_PACKAGE, annotationClass=Mapper.class)
public class DatasourceConfig {
	@Autowired
	private ApplicationContext applicationContext;

//	@Autowired
//	private DataSource dataSource;

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {

//		String[] activeProfiles = env.getActiveProfiles();
		String databaseUrl = "jdbc:mysql://localhost:3306/cnkisoft";
		System.out.print(System.getenv());
		String prof = System.getenv("USERNAME");

		System.out.print(prof);
//		ArrayList<String> profileList = (ArrayList<String>) Arrays.asList(activeProfiles);
//		if (profileList.contains("local")) {
		if ("PureMaN".equals(prof)) {
			databaseUrl = "jdbc:mysql://cnkisoft.cafe24.com:3306/cnkisoft";
		}

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername("cnkisoft");
		dataSource.setPassword("dkagh0211!@@");
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestWhileIdle(true);
		dataSource.setMaxActive(4);
		dataSource.setMaxIdle(1);
		dataSource.setMinIdle(1);
		dataSource.setTestOnBorrow(true);
		dataSource.setInitialSize(1);

		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {

		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setTypeHandlers(new TypeHandler[]{
		});

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setAggressiveLazyLoading(true);
//		configuration.setUseGeneratedKeys(true);
		configuration.setDefaultExecutorType(ExecutorType.REUSE);
		configuration.setDefaultStatementTimeout(3000);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);

		sessionFactoryBean.setConfiguration(configuration);

		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
//	@Primary
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage(ConfigConstant.BASE_PACKAGE);
		configurer.setAnnotationClass(Repository.class);

		return configurer;
	}
}
