package by.epam.tc.conference.entity;

import java.util.List;

public class Admin extends User {

    private List<Conference> conferences;

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }
}
