package by.epam.tc.conference.entity;

import java.util.Date;

public class Message implements Identifiable<Long> {

    private Long id;
    private String text;
    private Date created;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
