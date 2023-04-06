package com.frontservice.tellmemoremvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigFilter {
	
	@Bean
    public UserDetailsService userDetailsService() {
        // InMemoryUserDetailsManager (see below)
		

//        UserDetails john = User.builder()
//            .username("user1")
//            .password(passwordEncoder().encode("user1Pass"))
//            .roles("USER","SUBSCRIBED")
//            .build();
//
//        UserDetails mary = User.builder()
//                .username("user2")
//                .password(passwordEncoder().encode("user2Pass"))
//                .roles("USER")
//                .build();
//
//        UserDetails susan = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN","USER","SUBSCRIBED")
//                .build();
//        
//        return new InMemoryUserDetailsManager(john, mary, susan);
		
		return new LoginInfoUserDetailsService();
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
		
		return http.csrf().disable()
				.authorizeHttpRequests(configurer ->
					configurer
					
						.antMatchers("/subscription").hasRole("SUBSCRIBED")
						.antMatchers("/addUser","/allUsers/*").permitAll()
						.anyRequest().authenticated()
						//the more specific rules need to come first, followed by the more general ones. that is why anyRequest is last otherwise
						//it fails and application run fails as well.
						)
				.formLogin(configurer ->
					configurer
						.loginPage("/showMyLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll())
				
				.logout(configurer -> configurer.permitAll())
				
				.build();
    }
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProv = new DaoAuthenticationProvider();
		authProv.setUserDetailsService(userDetailsService());
		authProv.setPasswordEncoder(passwordEncoder());
		
		return authProv;
	}
}
