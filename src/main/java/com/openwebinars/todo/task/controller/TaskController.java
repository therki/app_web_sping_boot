package com.openwebinars.todo.task.controller;

import com.openwebinars.todo.category.model.Category;
import com.openwebinars.todo.category.model.CategoryRepository;
import com.openwebinars.todo.category.service.CategoryService;
import com.openwebinars.todo.task.dto.CreateTaskRequest;
import com.openwebinars.todo.task.dto.EditTaskRequest;
import com.openwebinars.todo.task.model.Task;
import com.openwebinars.todo.task.service.TaskService;
import com.openwebinars.todo.user.dto.CreateUserRequest;
import com.openwebinars.todo.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping({"/", "/list", "/task"})
    public String taskList(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("taskList", taskService.findAllByUser(user));
        model.addAttribute("newTask", new CreateTaskRequest());

        return "task-list";
    }
    @GetMapping(value = {"/", "/list", "/task"}, params = "emptyListError")
    public String createTask(Model model){
        model.addAttribute("newTask", new CreateTaskRequest());

        return "task-list";
    }

    @PostMapping("/task/submit")
    public String taskSubmit(
            @Valid @ModelAttribute("newTask") CreateTaskRequest req,
            BindingResult bindingResult,
            @AuthenticationPrincipal User author,
            Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("taskList", taskService.findAllByUser(author));
            return "task-list";
        }

        taskService.createTask(req, author);

        return "redirect:/";
    }

    @GetMapping("/task/{id}")
    public String viewOrEditTask(@PathVariable Long id, Model model) {

        Task task = taskService.findById(id);
        EditTaskRequest editTask = EditTaskRequest.of(task);
        model.addAttribute("task", editTask);
        return "show-task";

    }

    @GetMapping("/task/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleComplete(id);
        return "redirect:/";
    }

    @PostMapping("/task/{id}/del")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/";
    }

}
