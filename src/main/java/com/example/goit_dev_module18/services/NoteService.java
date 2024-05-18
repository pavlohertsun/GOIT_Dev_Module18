package com.example.goit_dev_module18.services;

import com.example.goit_dev_module18.dtos.NoteDto;
import com.example.goit_dev_module18.entities.Note;
import com.example.goit_dev_module18.exceptions.NotValidNoteException;
import com.example.goit_dev_module18.exceptions.NoteNotExistException;
import com.example.goit_dev_module18.repositories.NoteRepository;
import com.example.goit_dev_module18.utils.NoteMapper;
import com.example.goit_dev_module18.utils.NoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    private NoteMapper noteMapper;
    private NoteValidator noteValidator;

    @Autowired
    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper, NoteValidator noteValidator) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.noteValidator = noteValidator;
    }

    public List<NoteDto> getAllNotes(){
        return noteRepository.findAll().stream()
                .map(noteMapper::toNoteDto)
                .collect(Collectors.toList());
    }

    public NoteDto getNoteById(long id) throws NoteNotExistException{
        return noteMapper.toNoteDto(
                noteRepository.findById(id)
                        .orElseThrow(() -> new NoteNotExistException(String.valueOf(id))))
                ;
    }

    public NoteDto createNote(NoteDto noteToCreate) throws NotValidNoteException {
        if(!noteValidator.titleIsValid(noteToCreate.getTitle())
                || !noteValidator.contentIsValid(noteToCreate.getContent()))
            throw new NotValidNoteException();

        Note note = new Note();

        note.setTitle(noteToCreate.getTitle());
        note.setContent(noteToCreate.getContent());

        return noteMapper.toNoteDto(noteRepository.save(note));
    }
    public NoteDto updateNote(NoteDto noteUpdate)
            throws NoteNotExistException, NotValidNoteException{
        if(!noteValidator.titleIsValid(noteUpdate.getTitle())
                || !noteValidator.contentIsValid(noteUpdate.getContent()))
            throw new NotValidNoteException();

        Note note = noteRepository.findById(noteUpdate.getId())
                .orElseThrow(() -> new NoteNotExistException(String.valueOf(noteUpdate.getId())));

        if(noteUpdate.getTitle() != null)
            note.setTitle(noteUpdate.getTitle());
        if(noteUpdate.getContent() != null)
            note.setContent(noteUpdate.getContent());

        return noteMapper.toNoteDto(noteRepository.save(note));
    }

    public boolean deleteNote(long id) throws NoteNotExistException{
        if(!noteRepository.existsById(id))
            throw new NoteNotExistException(String.valueOf(id));

        noteRepository.deleteById(id);

        return true;
    }

}
