package com.problemfighter.pfspring.identity.config;


import com.problemfighter.pfspring.identity.filter.JwtTokenFilterInterface;
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
    private final JwtTokenFilterInterface jwtTokenFilter;
    private final IdentityConfig identityConfig;

    @Autowired
    public SecurityConfigurer(IdentityService identityService, JwtTokenFilterInterface jwtTokenFilter, IdentityConfig identityConfig) {
        this.identityService = identityService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.identityConfig = identityConfig;
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
                .antMatchers(identityConfig.skipUrls.toArray(new String[0])).permitAll()
                .anyRequest()
                .authenticated();
    }

}
