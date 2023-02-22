package com.researchecosystems.todoapp.repository;

import com.researchecosystems.todoapp.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, String> {

    @Query(value = "select * " +
            "from tasks o " +
            "where o.owner_id = :id",
            nativeQuery = true)
    Page<Task> findAllByUserId(Pageable pageable, String id);
}
