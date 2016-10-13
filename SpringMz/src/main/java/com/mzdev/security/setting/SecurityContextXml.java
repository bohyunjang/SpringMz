package com.mzdev.security.setting;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityContextXml extends WebSecurityConfigurerAdapter{
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	DataSource dataSource;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		
	/*	final String testUserName = "test@test.com";
		final String testPassword = "test";
		
		auth.inMemoryAuthentication().passwordEncoder(encoder)
			.withUser(testUserName).password(testPassword).authorities("ROLE_ADMIN");*/
		
	}
	
	@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").authenticated()
				.antMatchers("/login**").anonymous()
				.antMatchers("/register**").anonymous()
				.and()
			.formLogin()
				.loginPage("/login").permitAll(false)
				.usernameParameter("login_email").passwordParameter("login_password")
				.loginProcessingUrl("/login-request")
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY)
				.and()
			.rememberMe()
				.rememberMeParameter("login_rememberme")
				.tokenRepository(getPersistentTokenRepository()) // token 임시 저장소
				.tokenValiditySeconds(609600);
	}
	
	@Bean
	public PersistentTokenRepository getPersistentTokenRepository(){
		
		// 테이터 베이스에 저장된 토큰을 이용하여 사용자 정보를 대조함.
		JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
		repository.setDataSource(dataSource);
		
		return repository;
	}
	
}
