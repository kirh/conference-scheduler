package by.epam.tc.conference.entity;

public class Question implements Identifiable {

    private static final long serialVersionUID = 42L;

    private Long id;
    private String title;
    private Long userId;
    private Long conferenceId;

    public Question() {
    }

    public Question(String title, Long userId, Long conferenceId) {
        this.title = title;
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    public Question(Long id, String title, Long userId, Long conferenceId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (userId != null ? !userId.equals(question.userId) : question.userId != null) return false;
        return conferenceId != null ? conferenceId.equals(question.conferenceId) : question.conferenceId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (conferenceId != null ? conferenceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", conferenceId=").append(conferenceId);
        sb.append('}');
        return sb.toString();
    }
}
