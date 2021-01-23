package com.problemfighter.pfspring.identity.config;

import com.problemfighter.pfspring.identity.filter.JwtTokenFilter;
import com.problemfighter.pfspring.identity.filter.JwtTokenFilterInterface;
import com.problemfighter.pfspring.identity.service.IdentityCallbackInterface;
import com.problemfighter.pfspring.identity.service.IdentityCallbackService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class IdentityConfiguration {


    @Bean
    @Lazy
    public IdentityCallbackInterface identityCallbackInterface() {
        return new IdentityCallbackService();
    }

    @Bean
    @Lazy
    public JwtTokenFilterInterface jwtTokenFilterInterface(){
        return new JwtTokenFilter();
    }

}
