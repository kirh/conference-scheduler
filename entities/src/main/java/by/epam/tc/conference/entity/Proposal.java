package by.epam.tc.conference.entity;

public class Proposal implements Identifiable<Long> {

    private Long id;
    private String title;
    private String description;
    private ProposalStatus status;

    @Override
    public Long getId() {
        return null;
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

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
