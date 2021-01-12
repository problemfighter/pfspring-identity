package com.problemfighter.pfspring.identity.model.entity;

import com.problemfighter.pfspring.jpacommon.model.entity.EntityCommon;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Identity extends EntityCommon {

    @Column(nullable = false)
    public String identifier;

    @Column(nullable = false)
    public String password;

    public String email;

    public String mobile;

    public String recoverToken;

    @ManyToMany(mappedBy = "identities")
    public Set<Organization> organizations = new HashSet<>();

}
