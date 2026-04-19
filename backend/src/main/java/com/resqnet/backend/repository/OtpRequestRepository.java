package com.resqnet.backend.repository;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import com.resqnet.backend.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRequestRepository extends JpaRepository<OtpRequest, Long> {
    Optional<OtpRequest> findTopByPhoneNumberAndPurposeOrderByCreatedAtDesc
            (
                    String phoneNumber, OtpPurpose purpose
            );
    Optional<OtpRequest> findTopByPhoneNumberAndPurposeAndIsVerfiedTrueOrderByCreatedAtDesc
            (
                    String phoneNumber, OtpPurpose purpose
            );
}
