package com.example.goit_dev_module18.services;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.entities.Note;
import com.example.goit_dev_module18.exceptions.NotValidNoteException;
import com.example.goit_dev_module18.exceptions.NoteNotExistException;
import com.example.goit_dev_module18.repositories.NoteRepository;
import com.example.goit_dev_module18.utils.NoteMapper;
import com.example.goit_dev_module18.utils.NoteValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private NoteValidator noteValidator;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getAllNotesTest() {
        List<Note> notesList = new ArrayList<>();

        notesList.add(new Note(1, "Note #1", "Content for note #1"));
        notesList.add(new Note(2, "Note #2", "Content for note #2"));
        notesList.add(new Note(3, "Note #3", "Content for note #3"));

        when(noteRepository.findAll()).thenReturn(notesList);

        when(noteMapper.toNoteDto(notesList.get(0))).thenReturn(new NoteDto(1, "Note #1", "Content for note #1"));
        when(noteMapper.toNoteDto(notesList.get(1))).thenReturn(new NoteDto(2, "Note #2", "Content for note #2"));
        when(noteMapper.toNoteDto(notesList.get(2))).thenReturn(new NoteDto(3, "Note #3", "Content for note #3"));

        List<NoteDto> expectedList = new ArrayList<>();

        expectedList.add(new NoteDto(1, "Note #1", "Content for note #1"));
        expectedList.add(new NoteDto(2, "Note #2", "Content for note #2"));
        expectedList.add(new NoteDto(3, "Note #3", "Content for note #3"));

        List<NoteDto> actualList = noteService.getAllNotes();

        Assertions.assertIterableEquals(expectedList, actualList);

        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getNoteByIdTest() throws NoteNotExistException {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(new Note(1, "Note #1", "Content for note #1")));
        when(noteMapper.toNoteDto(new Note(1, "Note #1", "Content for note #1"))).thenReturn(new NoteDto(1, "Note #1", "Content for note #1"));

        NoteDto expectedDto = new NoteDto(1, "Note #1", "Content for note #1");

        NoteDto actualDto = noteService.getNoteById(1L);

        Assertions.assertEquals(expectedDto, actualDto);

        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void createNoteTest() throws NotValidNoteException {
        when(noteValidator.titleIsValid("Note #4")).thenReturn(true);
        when(noteValidator.contentIsValid("Content for note #4")).thenReturn(true);

        when(noteRepository.save(new Note(0, "Note #4", "Content for note #4"))).thenReturn(new Note(4, "Note #4", "Content for note #4"));

        when(noteMapper.toNoteDto(new Note(4, "Note #4", "Content for note #4"))).thenReturn(new NoteDto(4, "Note #4", "Content for note #4"));

        NoteDto expectedDto = new NoteDto(4, "Note #4", "Content for note #4");

        NoteDto actualDto = noteService.createNote(new NoteDto(0, "Note #4", "Content for note #4"));

        Assertions.assertEquals(expectedDto, actualDto);

        verify(noteRepository, times(1)).save(new Note(0, "Note #4", "Content for note #4"));
    }

    @Test
    void createNotValidNoteTest(){
        when(noteValidator.titleIsValid("Note #4")).thenReturn(true);
        when(noteValidator.contentIsValid("Con")).thenReturn(false);

        Assertions.assertThrows(NotValidNoteException.class, () -> noteService.createNote(new NoteDto(0, "Note #4", "Con")));
    }
    @Test
    void createBlankNoteTest(){
        when(noteValidator.titleIsValid("\n\t")).thenReturn(false);

        Assertions.assertThrows(NotValidNoteException.class, () -> noteService.createNote(new NoteDto(0, "\n\t", "Content for note#4")));
    }
    @Test
    void updateNoteTest() throws NoteNotExistException, NotValidNoteException {
        when(noteValidator.titleIsValid("Note #6 updated")).thenReturn(true);
        when(noteValidator.contentIsValid("Content for note #6 updated")).thenReturn(true);

        when(noteRepository.findById(6L)).thenReturn(Optional.of(new Note(6, "Note #6", "Content for note #6")));
        when(noteRepository.save(new Note(6, "Note #6 updated", "Content for note #6 updated"))).thenReturn(new Note(6, "Note #6 updated", "Content for note #6 updated"));

        when(noteMapper.toNoteDto(new Note(6, "Note #6 updated", "Content for note #6 updated"))).thenReturn(new NoteDto(6, "Note #6 updated", "Content for note #6 updated"));

        NoteDto expectedDto = new NoteDto(6, "Note #6 updated", "Content for note #6 updated");

        NoteDto actualDto = noteService.updateNote(new NoteDto(6, "Note #6 updated", "Content for note #6 updated"));

        Assertions.assertEquals(expectedDto, actualDto);

        verify(noteRepository, times(1)).findById(6L);
        verify(noteRepository, times(1)).save(new Note(6, "Note #6 updated", "Content for note #6 updated"));
    }
    @Test
    void updateNotExistingNoteTest(){
        when(noteValidator.titleIsValid("Note #6 updated")).thenReturn(true);
        when(noteValidator.contentIsValid("Content for note #6 updated")).thenReturn(true);

        when(noteRepository.findById(6L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoteNotExistException.class, () -> noteService.updateNote(new NoteDto(6, "Note #6 updated", "Content for note #6 updated")));

        verify(noteRepository, times(1)).findById(6L);
    }

    @Test
    void updateNotValidNoteTest(){
        when(noteValidator.titleIsValid("Note #6 updated")).thenReturn(true);
        when(noteValidator.contentIsValid("Con")).thenReturn(false);

        Assertions.assertThrows(NotValidNoteException.class, () -> noteService.updateNote(new NoteDto(6, "Note #6 updated", "Con")));
    }
    @Test
    void updateBlankNoteTest(){
        when(noteValidator.titleIsValid("\t\t")).thenReturn(false);

        Assertions.assertThrows(NotValidNoteException.class, () -> noteService.updateNote(new NoteDto(6, "\t\t", "Content for note #6 updated")));
    }
    @Test
    void deleteNoteTest() throws NoteNotExistException {
        when(noteRepository.existsById(6L)).thenReturn(true);
        doNothing().when(noteRepository).deleteById(6L);

        Assertions.assertEquals(true, noteService.deleteNote(6L));

        verify(noteRepository, times(1)).deleteById(6L);
    }

    @Test
    void deleteNotExistingNoteTest() {
        when(noteRepository.existsById(6L)).thenReturn(false);

        Assertions.assertThrows(NoteNotExistException.class, () -> noteService.deleteNote(6L));
    }
}