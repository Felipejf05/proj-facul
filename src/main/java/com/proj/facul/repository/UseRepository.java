package com.proj.facul.repository;

import com.proj.facul.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UseRepository extends JpaRepository<User, Long> {
}
