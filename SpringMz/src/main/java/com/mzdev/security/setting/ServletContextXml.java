package com.mzdev.security.setting;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan("com.mzdev.security")
@EnableWebMvc
@Import({SecurityContextXml.class})
@EnableTransactionManagement
public class ServletContextXml extends WebMvcConfigurerAdapter implements TransactionManagementConfigurer {

	@Autowired
	DataSourceTransactionManager dataSourceTransactionManager;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//super.addResourceHandlers(registry);
		registry.addResourceHandler("/resources/**").addResourceLocations("/resource/");
	}
	
	// Mybatis 사용 설정 메소드
	@Bean
	public SqlSessionFactoryBean setSqlSessionFactoryBean(
			DataSource dataSource, ApplicationContext applicationContext)throws IOException{
		
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		
	/*	final String configLocation = "/src/main/resource/mybatis/mybatis-conf.xml";
		final String boardMapperLocation = "/src/main/resource/mybatis/mapper/boardSql.xml";
		final String userMapperLocation = "/src/main/resource/mybatis/mapper/userSql.xml";*/
		
		final String configLocation = "/WEB-INF/spring/mybatis/mybatis-conf.xml";
		final String boardMapperLocation = "/WEB-INF/spring/mybatis/mapper/boardSql.xml";
		final String userMapperLocation = "/WEB-INF/spring/mybatis/mapper/userSql.xml";
		
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource(configLocation));
		sqlSessionFactory.setMapperLocations(applicationContext.getResources(boardMapperLocation)); // boardMapper 등록
		sqlSessionFactory.setMapperLocations(applicationContext.getResources(userMapperLocation)); // userMapper 등록
		
		return sqlSessionFactory;
		
	}
	
	@Bean(name="sqlSession")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		
		return sqlSessionTemplate;		
	}
	
	// 데이터베이스 서버와 웹서버를 연결시켜주는 클래스.
	// 내부에선 Tomcat Connection Pool을 이용하기 때문에 데이터랑 빠른 통신이 가능
	@Bean(name="dataSource")
	public DataSource dataSource(){
		
		DataSource dataSource = new DataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mzdev" 
							+ "?useSSl=false&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("test");
		
		dataSource.setMaxActive(10);
		dataSource.setInitialSize(2);
		
		dataSource.setMinIdle(2);
		dataSource.setMaxIdle(5);
		
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTimeBetweenEvictionRunsMillis(7200000);
		dataSource.setMinEvictableIdleTimeMillis(2800000);
		
		return dataSource;
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
		
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		
		return dataSourceTransactionManager;
	}
	
	@Bean
	public InternalResourceViewResolver setInternalResourceResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		
		return resolver;
	}
	
	@Bean
	public PasswordEncoder setPasswordEncoder(){
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		//TODO change later.		
//		new ShaPasswordEncoder()
		
		
		return encoder;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(setWebContentInterceptor());
	
	}
	
	private WebContentInterceptor setWebContentInterceptor(){
		WebContentInterceptor interceptor = new WebContentInterceptor();
		interceptor.setCacheSeconds(0);
		
		return interceptor;
	}
	
	// @Transaction 어노테이션으로 트랜잭션을 관리할 수 있게 해주는 클래스
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		// TODO Auto-generated method stub
		
		return dataSourceTransactionManager;
	}

}
