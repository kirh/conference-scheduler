package by.epam.tc.conference.dto;

import by.epam.tc.conference.entity.Identifiable;
import by.epam.tc.conference.entity.ProposalStatus;

public class ProposalDetails implements Identifiable {
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
}
