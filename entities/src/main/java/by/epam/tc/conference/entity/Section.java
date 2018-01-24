package by.epam.tc.conference.entity;

public class Section implements Identifiable {

    private static final long serialVersionUID = 42L;

    private Long id;
    private String topic;
    private Long conferenceId;

    public Section() {
    }

    public Section(String topic, Long conferenceId) {
        this.topic = topic;
        this.conferenceId = conferenceId;
    }

    public Section(Long id, String topic, Long conferenceId) {
        this.id = id;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

        Section section = (Section) o;

        if (id != null ? !id.equals(section.id) : section.id != null) return false;
        if (topic != null ? !topic.equals(section.topic) : section.topic != null) return false;
        return conferenceId != null ? conferenceId.equals(section.conferenceId) : section.conferenceId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (conferenceId != null ? conferenceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Section{");
        sb.append("id=").append(id);
        sb.append(", topic='").append(topic).append('\'');
        sb.append(", conferenceId=").append(conferenceId);
        sb.append('}');
        return sb.toString();
    }
}
