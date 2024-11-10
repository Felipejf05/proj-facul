package com.proj.facul.repository;

import com.proj.facul.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByDocument(String document);

    boolean existsByPhone(Long phone);

    boolean existsByEmail(String email);

    boolean existsByAddress(String address);

    // Verificação de duplicidade para atualizações, ignorando o ID do usuário
    boolean existsByDocumentAndIdNot(String document, Long id);

    boolean existsByPhoneAndIdNot(Long phone, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);
}
