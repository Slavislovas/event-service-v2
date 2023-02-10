package eventservice.eventservice.security;

import eventservice.eventservice.security.filter.AuthoritiesLoggingFilter;
import eventservice.eventservice.security.filter.JWTTokenGenerationFilter;
import eventservice.eventservice.security.filter.JWTTokenInvalidationFilter;
import eventservice.eventservice.security.filter.JWTTokenValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    JWTTokenValidationFilter jwtTokenValidationFilter;

    @Autowired
    JWTTokenInvalidationFilter jwtTokenInvalidationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JWTTokenGenerationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(jwtTokenValidationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtTokenInvalidationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers( "/v2/users", "/v1/events/event/**", "/error", "/error/**").permitAll()
                .antMatchers("/v2/users/**").hasAnyRole("user", "admin")
                .antMatchers("/v2/events/**").hasRole("user")
                .antMatchers("/v2/attendance/**").hasRole("user")
                .antMatchers("/v2/admin/**").hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .logout()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
