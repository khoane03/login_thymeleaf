package com.it.repositpry;

import com.it.dto.response.UserDto;
import com.it.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users u WHERE u.username LIKE %:keyword% OR u.name LIKE %:keyword%", nativeQuery = true)
    Page<User> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
