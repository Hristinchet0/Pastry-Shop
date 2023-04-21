package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    @Query("select u from Users as u" +
            " join Orders as o on u.id = o.users.id" +
            " where o.keyOrderProduct = :id" +
            " group by o.keyOrderProduct")
    Optional<Users> findUserBayKey(Long id);
}
