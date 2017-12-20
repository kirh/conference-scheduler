package by.epam.tc.conference.entity;

import java.io.Serializable;

public interface Identifiable<T> extends Serializable{

    T getId();
    void setId(T id);
}
