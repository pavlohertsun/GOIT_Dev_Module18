package com.example.goit_dev_module18.exceptions;

public class NoteNotExistException extends Exception{
    private static final String EX_MESSAGE = "Note with id: %s not exist";
    public NoteNotExistException(String id) {
        super(String.format(EX_MESSAGE, id));
    }
}
