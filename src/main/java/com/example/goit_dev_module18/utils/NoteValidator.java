package com.example.goit_dev_module18.utils;

import org.springframework.stereotype.Component;

@Component
public class NoteValidator {

    public boolean titleIsValid(String title){
        if(title.isBlank() ||
                title.length() < 3)
            return false;
        return true;
    }
    public boolean contentIsValid(String content){
        if(content.isBlank() ||
                content.length() < 5)
            return false;
        return true;
    }
}
