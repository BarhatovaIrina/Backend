package com.example.springlibraryproject.dto;

import com.example.springlibraryproject.model.Genre;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {

    @NotNull
    private Long id;

    @Pattern(regexp = "[A-Za-zА-Яа-яЁё .-]{3,}", message = "Invalid book's name")
    private String name;

    @NotNull
    private Genre genre;
}
