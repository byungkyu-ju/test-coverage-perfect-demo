package me.bk.testcoverageperfectdemo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest()
				.permitAll()
			.and()
			.headers()
				.frameOptions()
				.disable()
			.and()
				.formLogin()
					.disable()
				.csrf()
					.disable();

	}
}
