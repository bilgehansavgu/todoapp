package com.researchecosystems.todoapp.repository;

import com.researchecosystems.todoapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findUserByEmail(String email);

    @Query(value = "select * " +
            "from users o " +
            "order by o.id",
            nativeQuery = true)
    Page<User> findAll(Pageable pageable);

}