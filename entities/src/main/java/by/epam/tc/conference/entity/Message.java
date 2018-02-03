package by.epam.tc.conference.entity;

import java.sql.Timestamp;

public class Message implements Identifiable {

    private static final long serialVersionUID = 42L;

    private Long id;
    private String text;
    private Long questionId;
    private Long userId;
    private Timestamp createTime;

    public Message() {
    }

    public Message(String text, Long questionId, Long userId) {
        this.text = text;
        this.questionId = questionId;
        this.userId = userId;
    }

    public Message(Long id, String text, Long questionId, Long userId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
        this.userId = userId;
    }

    public Message(Long id, String text, Long questionId, Long userId, Timestamp createTime) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
        this.userId = userId;
        this.createTime = createTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) {
            return false;
        }
        if (text != null ? !text.equals(message.text) : message.text != null) {
            return false;
        }
        if (questionId != null ? !questionId.equals(message.questionId) : message.questionId != null) {
            return false;
        }
        if (userId != null ? !userId.equals(message.userId) : message.userId != null) {
            return false;
        }
        return createTime != null ? createTime.equals(message.createTime) : message.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                '}';
    }
}
