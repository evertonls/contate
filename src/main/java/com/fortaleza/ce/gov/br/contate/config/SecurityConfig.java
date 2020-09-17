package com.fortaleza.ce.gov.br.contate.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	 @Override
	  protected void configure(HttpSecurity http) throws Exception {
		 http
				.csrf().disable()
				.requiresChannel().antMatchers("/**").requiresSecure()
				.and()
				.headers()
				.xssProtection().disable()
				.frameOptions().disable()
				.and()
				.authorizeRequests()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/javax.faces.resource/**").permitAll()
				.antMatchers("/css/**", "/imagens/**", "/js/**", "/fontes/**", "/docs/**").permitAll()
				.antMatchers("/usuarios/**").hasAnyRole("ADMIN", "USER")
				.anyRequest()
				.authenticated()
				.and().formLogin().loginPage("/login/login.xhtml")
				.defaultSuccessUrl("/usuarios/home.xhtml")
				.failureUrl("/login/login.xhtml?error=true")
				
				.and().logout()
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login/login.xhtml")
				.and()
				.exceptionHandling();
	  }
	 
	 @Override
	  public void configure(AuthenticationManagerBuilder auth)
	      throws Exception {
	    auth.inMemoryAuthentication().withUser("john.doe")
	        .password("{noop}1234").roles("USER").and()
	        .withUser("jane.doe").password("{noop}5678").roles("ADMIN");
	  }

}