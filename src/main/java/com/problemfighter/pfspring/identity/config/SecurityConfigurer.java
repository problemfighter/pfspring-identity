package com.problemfighter.pfspring.identity.config;


import com.problemfighter.pfspring.identity.filter.JwtTokenFilter;
import com.problemfighter.pfspring.identity.filter.IdentifierPassAuthFilter;
import com.problemfighter.pfspring.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    public final IdentityService identityService;
    private final String renewUrl = "/api/v1/auth/renew";
    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfigurer(IdentityService identityService, JwtTokenFilter jwtTokenFilter) {
        this.identityService = identityService;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new IdentifierPassAuthFilter(authenticationManager(), identityService))
                .addFilterAfter(jwtTokenFilter, IdentifierPassAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/", renewUrl).permitAll()
                .anyRequest()
                .authenticated();
    }

}
