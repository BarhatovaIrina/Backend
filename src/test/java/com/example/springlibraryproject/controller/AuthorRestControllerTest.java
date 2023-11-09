package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.AuthorCreateDto;
import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.AuthorUpdateDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAuthorById() throws Exception {
        Long id=1L;
        AuthorDto authorDto = new AuthorDto(id, "Александр", "Пушкин", null);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }
    @Test
    public void testCreateAuthor() throws Exception {
        JSONObject jsonObject = new JSONObject();
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(null, "Petr", "Petrov");
        jsonObject.put("name", authorCreateDto.getName());
        jsonObject.put("surname", authorCreateDto.getSurname());
        mockMvc.perform(MockMvcRequestBuilders.post("/author/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteAuthorId() throws Exception {
        Long id=12L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/author/delete/{id}",id))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateAuthor() throws Exception{
        JSONObject jsonObject = new JSONObject();
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(1L, "Petr", "Petrov", null);
        jsonObject.put("id",authorUpdateDto.getId());
        jsonObject.put("name", authorUpdateDto.getName());
        jsonObject.put("surname", authorUpdateDto.getSurname());
        mockMvc.perform(MockMvcRequestBuilders.put("/author/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAuthorBySurnameV1 () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/author/v1")
                        .param("surname", "Толстой"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void testGetAllAuthorsView() throws Exception { // для @Controller!!! not @RestController
//        mockMvc.perform(MockMvcRequestBuilders.get("/author"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("author"))
//                .andExpect(model().attributeExists("authors"));
}
}
