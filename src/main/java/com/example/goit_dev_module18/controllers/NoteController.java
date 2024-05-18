package com.example.goit_dev_module18.controllers;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.exceptions.NotValidNoteException;
import com.example.goit_dev_module18.exceptions.NoteNotExistException;
import com.example.goit_dev_module18.services.NoteService;
import com.example.goit_dev_module18.utils.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<NoteDto>> getAllNotes(){
        return ResponseEntity
                .ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable long id) throws NoteNotExistException {
        return ResponseEntity
                .ok(noteService.getNoteById(id));
    }

    @PostMapping("")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) throws NotValidNoteException {
        NoteDto createdNote = noteService.createNote(noteDto);
        return ResponseEntity
                .status(201)
                .body(createdNote);
    }

    @PutMapping("")
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto noteDto)
            throws NoteNotExistException, NotValidNoteException{
        return ResponseEntity
                .ok(noteService.updateNote(noteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id) throws NoteNotExistException{
        noteService.deleteNote(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
