package com.problemfighter.pfspring.identity.repository;

import com.problemfighter.pfspring.identity.model.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    @Query("SELECT i FROM Identity i WHERE i.identifier = :identifier AND i.isDeleted = false")
    public Identity getActiveIdentityByIdentifier(@Param("identifier") String identifier);

    @Query("SELECT i FROM Identity i WHERE i.identifier = :identifier AND i.isDeleted = :isDeleted")
    public Identity getIdentityByIdentifier(@Param("identifier") String identifier, @Param("isDeleted") Boolean isDeleted);

    @Query("SELECT i FROM Identity i WHERE i.identifier = :identifier")
    public Identity getIdentityByIdentifier(@Param("identifier") String identifier);


    @Query("SELECT i FROM Identity i WHERE i.uuid = :uuid AND i.isDeleted = false")
    public Identity getActiveIdentityByUUID(@Param("uuid") String uuid);

    @Query("SELECT count (i) FROM Identity i")
    public Long totalIdentity();
}
