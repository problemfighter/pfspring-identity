package com.problemfighter.pfspring.identity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("pf.identity")
public class IdentityConfig {

    public List<String> skipUrls = new ArrayList<>();


    public IdentityConfig() {
        skipUrls.add("/favicon.ico");
        allowSwagger();
    }

    public List<String> getSkipUrls() {
        return skipUrls;
    }

    public void setSkipUrls(List<String> skipUrls) {
        if (skipUrls != null && !skipUrls.isEmpty()) {
            this.skipUrls.addAll(skipUrls);
        }
    }

    private void allowSwagger(){
        skipUrls.add("/webjars/**");
        skipUrls.add("/swagger-resources/**");
        skipUrls.add("/v2/api-docs");
        skipUrls.add("/configuration/ui");
        skipUrls.add("/configuration/security");
        skipUrls.add("/swagger-ui.html");
    }
}
