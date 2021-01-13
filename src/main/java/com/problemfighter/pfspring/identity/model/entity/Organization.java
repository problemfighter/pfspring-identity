package com.problemfighter.pfspring.identity.model.entity;

import com.problemfighter.pfspring.jpacommon.model.entity.EntityCommon;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Organization extends EntityCommon {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String orgIdentifier;

    public String logoUrl;
    public String type;  // CRM, POS
    public String appURL;
    public String appKey;

    @ManyToMany(mappedBy = "organizations")
    @JoinTable
    public Set<Identity> identities = new HashSet<>();

}
