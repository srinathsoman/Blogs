package com.blogger.blogs.config.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    public HttpSecurity configSecurity(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage());
                        })
                .and();

        http = http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/health").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        http = http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class);
        return http;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        this.configSecurity(http);
        return http.build();
    }

}
