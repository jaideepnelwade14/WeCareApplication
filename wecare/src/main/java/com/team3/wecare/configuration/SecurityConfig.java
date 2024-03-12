package com.team3.wecare.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.team3.wecare.service.CustomSuccessHandler;
import com.team3.wecare.serviceimpl.CustomUserDetailsImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	@Autowired
	CustomUserDetailsImpl customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Configuring Security for the Application !");
		
		http.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request
				.requestMatchers("/wecare/admin/track-complaints", "/wecare/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/wecare/user/track-complaints", "/wecare/user/**").hasAuthority("USER")
				.requestMatchers("/wecare/officer/track-complaints", "/wecare/officer/**").hasAuthority("OFFICER")
				.requestMatchers("/wecare/registration", "/css/**", "/wecare", "/wecare/generateEmailOtp",
						"/wecare/validateOtp")
				.permitAll().anyRequest().authenticated())
		
				.formLogin(form -> form.loginPage("/wecare/login").loginProcessingUrl("/wecare/login")
						.successHandler(customSuccessHandler).permitAll())

				.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/wecare/login?logout").permitAll())
				
				.sessionManagement(session -> session.invalidSessionUrl("/login?timeout"));

		logger.info("Application Security Configured Successfully !");
		return http.build();
	}
	
	@Bean
	private static PasswordEncoder passwordEncoder() {
		logger.info("Encrypting password using BCrypt Encoder !");
		
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Configuring Authentication Manager !");
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		logger.info("Authentication Manager Configured Successfully !");
	}
}
