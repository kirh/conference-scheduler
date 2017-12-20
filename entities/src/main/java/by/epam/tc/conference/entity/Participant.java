package by.epam.tc.conference.entity;

import java.util.List;

public class Participant extends User {

    private List<Proposal> proposals;

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
