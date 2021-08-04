package com.perscholas.dao;

import com.perscholas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer>
{
    @Query("select u from User u where u.email = :userEmail")
    User findUserByEmail(@Param("userEmail") String userEmail);
}
