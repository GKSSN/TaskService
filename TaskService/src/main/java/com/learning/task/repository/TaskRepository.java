package com.learning.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>  {

}
