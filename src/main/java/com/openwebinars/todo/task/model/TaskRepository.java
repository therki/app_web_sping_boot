package com.openwebinars.todo.task.model;

import com.openwebinars.todo.category.model.Category;
import com.openwebinars.todo.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor(User user, Sort sort);

    List<Task> findByCategory(Category category);
}
