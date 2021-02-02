package com.problemfighter.pfspring.identity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("pf.identity")
public class IdentityConfig {

    public List<String> skipUrls = new ArrayList<>();
    public Boolean isEnableSwaggerUi = true;


    public IdentityConfig() {
        commonInit();
        allowSwagger();
    }

    public List<String> getSkipUrls() {
        return skipUrls;
    }

    public void setSkipUrls(List<String> skipUrls) {
        if (skipUrls != null && !skipUrls.isEmpty()) {
            this.skipUrls.addAll(skipUrls);
        }
        commonInit();
        allowSwagger();
    }

    public Boolean getEnableSwaggerUi() {
        return isEnableSwaggerUi;
    }

    public void setEnableSwaggerUi(Boolean enableSwaggerUi) {
        isEnableSwaggerUi = enableSwaggerUi;
        allowSwagger();
    }

    public void commonInit(){
        skipUrls.add("/favicon.ico");
    }

    private void allowSwagger(){
        if (isEnableSwaggerUi){
            skipUrls.add("/webjars/**");
            skipUrls.add("/swagger-resources/**");
            skipUrls.add("/v2/api-docs");
            skipUrls.add("/configuration/ui");
            skipUrls.add("/configuration/security");
            skipUrls.add("/swagger-ui.html");
        }
    }
}
