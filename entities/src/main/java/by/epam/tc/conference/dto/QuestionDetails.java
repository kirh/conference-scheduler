package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Question;

public class QuestionDetails extends Question {

    private static final long serialVersionUID = 42L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        QuestionDetails that = (QuestionDetails) o;

        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        return conferenceName != null ? conferenceName.equals(that.conferenceName) : that.conferenceName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (conferenceName != null ? conferenceName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionDetails{");
        sb.append("username='").append(username).append('\'');
        sb.append(", conferenceName='").append(conferenceName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
