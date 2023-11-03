package com.example.springlibraryproject.dto;

import com.example.springlibraryproject.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    private String name;
    private Genre genre;

}
