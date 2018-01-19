package by.epam.tc.conference.entity;

public class Proposal implements Identifiable {

    private Long id;
    private String title;
    private String description;
    private Long sectionId;
    private Long participantId;
    private ProposalStatus status;

    public Proposal() {
    }

    public Proposal(String title, String description, Long sectionId, Long participantId, ProposalStatus status) {
        this.title = title;
        this.description = description;
        this.sectionId = sectionId;
        this.participantId = participantId;
        this.status = status;
    }

    public Proposal(Long id, String title, String description, Long sectionId, Long participantId, ProposalStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sectionId = sectionId;
        this.participantId = participantId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
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

        Proposal proposal = (Proposal) o;

        if (id != null ? !id.equals(proposal.id) : proposal.id != null) return false;
        if (title != null ? !title.equals(proposal.title) : proposal.title != null) return false;
        if (description != null ? !description.equals(proposal.description) : proposal.description != null)
            return false;
        if (sectionId != null ? !sectionId.equals(proposal.sectionId) : proposal.sectionId != null) return false;
        if (participantId != null ? !participantId.equals(proposal.participantId) : proposal.participantId != null)
            return false;
        return status == proposal.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (sectionId != null ? sectionId.hashCode() : 0);
        result = 31 * result + (participantId != null ? participantId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
