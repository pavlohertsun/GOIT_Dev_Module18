package com.example.goit_dev_module18.utils;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.entities.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public NoteDto toNoteDto(Note note){
        return new NoteDto(note.getId(), note.getTitle(), note.getContent());
    }
    public Note toNoteEntity(NoteDto noteDto){
        return new Note(noteDto.getId(), noteDto.getTitle(), noteDto.getContent());
    }
}
