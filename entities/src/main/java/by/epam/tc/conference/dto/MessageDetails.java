package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Message;

public class MessageDetails extends Message {

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
}
