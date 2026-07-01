package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchableTerm();
    String getSearchableType();
    String getSearchableName();
    UUID getId();
    default void getStringRepresentation(){
        System.out.println("имя " + getSearchableName() + " - тип " + getSearchableType());
    }

}