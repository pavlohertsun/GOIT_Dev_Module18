package com.example.goit_dev_module18.exceptions;

public class NotValidNoteException extends Exception{
    private static final String EX_MESSAGE = "Note is not valid. Title's length should be more than 3 and content's more than 5";
    public NotValidNoteException() {
        super(EX_MESSAGE);
    }
}
