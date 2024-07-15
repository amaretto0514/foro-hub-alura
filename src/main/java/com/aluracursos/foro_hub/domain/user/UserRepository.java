package com.aluracursos.foro_hub.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);
   @Query("""
            SELECT u.id
            FROM UserEntity u
            WHERE u.name = :name
            """)
    Long findIdByName(String name);
}
