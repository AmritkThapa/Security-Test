package com.amrit.securitytest.repo;

import com.amrit.securitytest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Integer> {
    @Query(
            nativeQuery = true,
            value = "select * from address_data where id = ?1"
    )
    User getUserByUsername(String username);
}
