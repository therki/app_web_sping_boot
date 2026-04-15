package com.openwebinars.todo.tag.service;

import com.openwebinars.todo.tag.model.Tag;
import com.openwebinars.todo.tag.model.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    /* Obtener lista de tags y por cada uno:
    - Comprobar si existe y obtener
    - Si no existe, insertar y devolver
     */
    public List<Tag> saveOrGet(List<String> tags){
        List<Tag> result = new ArrayList<>();

        tags.forEach(tag ->{
            Optional<Tag> val = tagRepository.findByText(tag);
            // Si lo encuentra lo añade sino lo crea
            result.add(val.orElseGet(()-> tagRepository.save(Tag.builder().text(tag).build())));
        });
            return result;
    }

}
