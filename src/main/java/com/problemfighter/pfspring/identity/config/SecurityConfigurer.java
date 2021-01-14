package com.problemfighter.pfspring.identity.config;


import com.problemfighter.pfspring.identity.filter.IdentifierPassAuthFilter;
import com.problemfighter.pfspring.identity.filter.JwtTokenFilter;
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

    @Autowired
    public SecurityConfigurer(IdentityService identityService) {
        this.identityService = identityService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new IdentifierPassAuthFilter(authenticationManager(), identityService))
                .addFilterAfter(new JwtTokenFilter(), IdentifierPassAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated();
    }

}
