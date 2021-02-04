package com.problemfighter.pfspring.identity.config;


import com.problemfighter.pfspring.identity.common.IdentityConstant;
import com.problemfighter.pfspring.identity.filter.JwtTokenFilterInterface;
import com.problemfighter.pfspring.identity.filter.IdentifierPassAuthFilter;
import com.problemfighter.pfspring.identity.service.AuthService;
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

    private final JwtTokenFilterInterface jwtTokenFilter;
    private final IdentityConfig identityConfig;
    private final AuthService authService;

    @Autowired
    public SecurityConfigurer(JwtTokenFilterInterface jwtTokenFilter, IdentityConfig identityConfig, AuthService authService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.identityConfig = identityConfig;
        this.authService = authService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new IdentifierPassAuthFilter(authenticationManager(), authService))
                .addFilterAfter(jwtTokenFilter, IdentifierPassAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/", IdentityConstant.TOKEN_RENEW_URL).permitAll()
                .antMatchers(identityConfig.skipUrls.toArray(new String[0])).permitAll()
                .anyRequest()
                .authenticated();
    }

}
