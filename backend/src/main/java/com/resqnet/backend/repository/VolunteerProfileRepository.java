package com.resqnet.backend.repository;

import com.resqnet.backend.entity.User;
import com.resqnet.backend.entity.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerProfileRepository extends JpaRepository<VolunteerProfile,Long> {

    Optional<VolunteerProfile> findByUser(User user);

    Optional<VolunteerProfile> findByUserId(Long userId);

}
