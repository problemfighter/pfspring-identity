package com.problemfighter.pfspring.identity.repository;

import com.problemfighter.pfspring.identity.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
