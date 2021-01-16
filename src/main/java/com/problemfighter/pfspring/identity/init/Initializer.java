package com.problemfighter.pfspring.identity.init;


import com.problemfighter.pfspring.identity.model.dto.identity.IdentityMasterDTO;
import com.problemfighter.pfspring.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationRunner {

    @Autowired
    private IdentityService identityService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (identityService.getIdentityCount() == 0) {
            try {
                IdentityMasterDTO identityMasterDTO = new IdentityMasterDTO();
                identityMasterDTO.identifier = "admin";
                identityMasterDTO.password = "password";
                identityService.createIdentity(identityMasterDTO);
            } catch (Exception e) {
                System.out.printf("Exception.......");
            }
        }
    }

}
