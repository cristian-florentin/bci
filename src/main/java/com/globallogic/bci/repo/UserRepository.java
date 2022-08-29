package com.globallogic.bci.repo;

import com.globallogic.bci.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u left join fetch u.phones phone where u.email = (:email)")
    User findByEmail(String email);

}
