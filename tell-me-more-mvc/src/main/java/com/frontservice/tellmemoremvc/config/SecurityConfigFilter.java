package com.frontservice.tellmemoremvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigFilter {
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // InMemoryUserDetailsManager (see below)
		

        UserDetails john = User.builder()
            .username("user1")
            .password(passwordEncoder().encode("user1Pass"))
            .roles("USER","SUBSCRIBED")
            .build();

        UserDetails mary = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();

        UserDetails susan = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN","USER","SUBSCRIBED")
                .build();
        
        return new InMemoryUserDetailsManager(john, mary, susan);
    }
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    		
//        return http
//		.authorizeRequests(configurer ->
//			configurer
//				.anyRequest()
//				.authenticated())
//		
//		.formLogin(configurer ->
//			configurer
//				.loginPage("/showMyLoginPage")
//				.loginProcessingUrl("/authenticateTheUser")
//				.permitAll())
//		.logout(configurer -> configurer.permitAll())
//		
//		.build();
		
		return http
				.authorizeRequests(configurer ->
					configurer
						.antMatchers("/subscription").hasRole("SUBSCRIBED").anyRequest().authenticated())
				
				.formLogin(configurer ->
					configurer
						.loginPage("/showMyLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll())
				.logout(configurer -> configurer.permitAll())
				
				.build();
    }
}
