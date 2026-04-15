package com.openwebinars.todo.shared.init;

import com.openwebinars.todo.category.model.Category;
import com.openwebinars.todo.category.model.CategoryRepository;
import com.openwebinars.todo.task.dto.CreateTaskRequest;
import com.openwebinars.todo.task.service.TaskService;
import com.openwebinars.todo.user.dto.CreateUserRequest;
import com.openwebinars.todo.user.model.User;
import com.openwebinars.todo.user.model.UserRole;
import com.openwebinars.todo.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {
    private  final UserService userService;
    private final TaskService taskService;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init(){
        List<User> users = inserUsers();
        inserCategories();
        insertTasks(users.get(0));
    }

    private List<User> inserUsers(){
        List<User> result = new ArrayList<>();

        CreateUserRequest req = CreateUserRequest.builder()
                .username("user")
                .email("user@user.com")
                .password("1234")
                .verifyPassword("1234")
                .fullname("The user")
                .build();
        User user = userService.registerUser(req);
        result.add(user);

        CreateUserRequest req2 = CreateUserRequest.builder()
                .username("admin")
                .email("admin@openwebinars.net")
                .password("1234")
                .verifyPassword("1234")
                .fullname("Administrador")
                .build();
        User user2 = userService.registerUser(req2);
        userService.changeRole(user2, UserRole.ADMIN);

        return result;
    }

    private void inserCategories(){
        categoryRepository.save(Category.builder().title("Main").build());
    }

    private void insertTasks(User author){
        CreateTaskRequest req1 = CreateTaskRequest.builder()
                .title("Primera task")
                .description("Primera")
                .tags("tag1, tag2, tag3")
                .build();

        taskService.createTask(req1, author);
        CreateTaskRequest req2 = CreateTaskRequest.builder()
                .title("Segunda  task")
                .description("Segunda")
                .tags("tag1, tag2, tag4")
                .build();

        taskService.createTask(req2, author);
    }

}
