package com.openwebinars.todo.user.dto;

import jakarta.persistence.Entity;
import lombok.*;
import org.jspecify.annotations.Nullable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateUserRequest {
    private String username,password, verifyPassword, email, fullname;


}
