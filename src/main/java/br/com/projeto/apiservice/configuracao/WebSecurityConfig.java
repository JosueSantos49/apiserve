package br.com.projeto.apiservice.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig{
	
	@Autowired
    private AuthEntryPointJwt jwtAuthenticacaoEntryPoint;

    @Autowired
    private JwtRequestFilterToken jwtRequestFilterToken;
    
    @Autowired
    private UserDetailsService jwtService;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
		
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{        
		httpSecurity.csrf(csrf -> csrf.disable())
		.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticacaoEntryPoint))
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests( request -> 
        	request.requestMatchers("/api/auth/**").permitAll()
        			.requestMatchers("/registrarNovoUsuario").permitAll()        			
        			.anyRequest().authenticated()        				
        );
		
		httpSecurity.headers(h -> h.frameOptions(f -> f.sameOrigin()));
       
        httpSecurity.addFilterBefore(jwtRequestFilterToken, UsernamePasswordAuthenticationFilter.class);
        
		return httpSecurity.build();
	}		

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
