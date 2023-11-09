package com.example.springlibraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenreUpdateDto {
    private Long id;

    @Size(min=2, max=15)
    @NotBlank(message = "Input data in genre's name")
    private String name;
}
