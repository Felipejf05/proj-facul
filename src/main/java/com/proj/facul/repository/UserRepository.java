package com.proj.facul.repository;

import com.proj.facul.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);

    Optional<User> findByEmail(String email);

    boolean existsByDocument(String document);
    boolean existsByPhone(Long phone);
    boolean existsByEmail(String email);
    boolean existsByAddress(String address);

    boolean existsByDocumentAndIdNot(String document, Long id);
    boolean existsByPhoneAndIdNot(Long phone, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByAddressAndIdNot(String address, Long id);
}
