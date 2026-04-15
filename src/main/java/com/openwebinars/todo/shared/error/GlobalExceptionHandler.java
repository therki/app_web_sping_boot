package com.openwebinars.todo.shared.error;

import com.openwebinars.todo.task.exception.EmptyTaskListException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class GlobalExceptionHandler {
    @ExceptionHandler
    public String emptyTaskList(EmptyTaskListException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("emptyListError", true);
        return "redirect:/";
    }
}
