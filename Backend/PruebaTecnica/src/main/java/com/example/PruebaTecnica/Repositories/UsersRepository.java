package com.example.PruebaTecnica.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.PruebaTecnica.modelos.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    @Query(value = """
        SELECT * FROM users u WHERE
        (:role IS NULL OR u.role = :role) AND
        (:status IS NULL OR u.status = :status) AND
        (:search IS NULL OR 
            LOWER(CAST(u.firstName AS text)) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(CAST(u.lastName AS text)) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(CAST(u.email AS text)) LIKE LOWER(CONCAT('%', :search, '%')))
    """, nativeQuery = true)
    Page<Users> findWithFilters(
            @Param("role")   String role,
            @Param("status") String status,
            @Param("search") String search,
            Pageable pageable
    );
}