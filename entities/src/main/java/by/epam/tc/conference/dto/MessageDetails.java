package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Message;

public class MessageDetails extends Message {

    private static final long serialVersionUID = 42L;

    private String username;
    private boolean sendByAdmin;

    public MessageDetails() {
    }

    public MessageDetails(String text, Long questionId, Long userId, String username, boolean sendByAdmin) {
        super(text, questionId, userId);
        this.username = username;
        this.sendByAdmin = sendByAdmin;
    }

    public MessageDetails(Long id, String text, Long questionId, Long userId, String username, boolean sendByAdmin) {
        super(id, text, questionId, userId);
        this.username = username;
        this.sendByAdmin = sendByAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSendByAdmin() {
        return sendByAdmin;
    }

    public void setSendByAdmin(boolean sendByAdmin) {
        this.sendByAdmin = sendByAdmin;
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

        MessageDetails that = (MessageDetails) o;

        if (sendByAdmin != that.sendByAdmin) {
            return false;
        }
        return username != null ? username.equals(that.username) : that.username == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (sendByAdmin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageDetails{" +
                "username='" + username + '\'' +
                ", sendByAdmin=" + sendByAdmin +
                "} " + super.toString();
    }
}
