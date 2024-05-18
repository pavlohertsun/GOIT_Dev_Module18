package com.example.goit_dev_module18.controllers;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.services.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoteService noteService;
    @Test
    void getAllNotesTest() throws Exception{
        List<NoteDto> notes = new ArrayList<>();
        notes.add(new NoteDto(1, "Note #1", "Content for note #1"));
        notes.add(new NoteDto(2, "Note #2", "Content for note #2"));

        when(noteService.getAllNotes()).thenReturn(notes);

        mockMvc.perform(get("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Note #1"))
                .andExpect(jsonPath("$[0].content").value("Content for note #1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Note #2"))
                .andExpect(jsonPath("$[1].content").value("Content for note #2"));
    }

    @Test
    void getNoteByIdTest() throws Exception {
        NoteDto note = new NoteDto(1, "Note #1", "Content for note #1");

        when(noteService.getNoteById(1L)).thenReturn(note);

        mockMvc.perform(get("/api/notes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Note #1"))
                .andExpect(jsonPath("$.content").value("Content for note #1"));
    }

    @Test
    void createNoteTest() throws Exception{
        when(noteService.createNote(new NoteDto(0, "Note #1", "Content for note #1"))).thenReturn(new NoteDto(1, "Note #1", "Content for note #1"));

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new NoteDto(0, "Note #1", "Content for note #1"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Note #1"))
                .andExpect(jsonPath("$.content").value("Content for note #1"));
    }

    @Test
    void updateNoteTest() throws Exception{
        when(noteService.updateNote(new NoteDto(1, "Updated Note", "Updated Content"))).thenReturn(new NoteDto(1, "Updated Note", "Updated Content"));

        mockMvc.perform(put("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new NoteDto(1, "Updated Note", "Updated Content"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Note"))
                .andExpect(jsonPath("$.content").value("Updated Content"));
    }

    @Test
    void deleteNoteTest() throws Exception{
        when(noteService.deleteNote(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/notes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}