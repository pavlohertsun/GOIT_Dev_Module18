package com.example.goit_dev_module18.utils;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.entities.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteMapperTest {
    private NoteMapper noteMapper = new NoteMapper();

    @Test
    void toNoteDtoTest() {
        Note note = new Note(1, "Title", "Content");

        NoteDto expectedDto = new NoteDto(1, "Title", "Content");

        NoteDto actualDto = noteMapper.toNoteDto(note);

        Assertions.assertEquals(expectedDto, actualDto);

    }

    @Test
    void toNoteEntityTest() {
        NoteDto note = new NoteDto(1, "Title", "Content");

        Note expectedNote = new Note(1, "Title", "Content");

        Note actualNote = noteMapper.toNoteEntity(note);

        Assertions.assertEquals(expectedNote, actualNote);
    }
}