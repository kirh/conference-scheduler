package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Question;

public class QuestionDetails extends Question {
    private String username;
    private String conferenceName;

    public QuestionDetails() {
    }

    public QuestionDetails(String text, Long userId, Long conferenceId, String username, String conferenceName) {
        super(text, userId, conferenceId);
        this.username = username;
        this.conferenceName = conferenceName;
    }

    public QuestionDetails(Long id, String text, Long userId, Long conferenceId, String username, String conferenceName) {
        super(id, text, userId, conferenceId);
        this.username = username;
        this.conferenceName = conferenceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }
}
