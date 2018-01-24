package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Identifiable;
import by.epam.tc.conference.entity.ProposalStatus;

public class ProposalDetails implements Identifiable {

    private static final long serialVersionUID = 42L;

    private Long id;
    private String title;
    private String username;
    private String conferenceName;
    private String sectionName;
    private ProposalStatus status;

    public ProposalDetails(Long id, String title, String username, String conferenceName, String sectionName, ProposalStatus status) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.conferenceName = conferenceName;
        this.sectionName = sectionName;
        this.status = status;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProposalDetails that = (ProposalDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (conferenceName != null ? !conferenceName.equals(that.conferenceName) : that.conferenceName != null)
            return false;
        if (sectionName != null ? !sectionName.equals(that.sectionName) : that.sectionName != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (conferenceName != null ? conferenceName.hashCode() : 0);
        result = 31 * result + (sectionName != null ? sectionName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProposalDetails{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", conferenceName='").append(conferenceName).append('\'');
        sb.append(", sectionName='").append(sectionName).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
