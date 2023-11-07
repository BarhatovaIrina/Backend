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
public class AuthorUpdateDto {
    private Long id;

    @Size(min=2, max=15)
    @NotBlank(message = "Input data in name")
    private String name;

    @Size(min=2, max=15)
    @NotBlank(message = "Input data in surname")
    private String surname;
}
