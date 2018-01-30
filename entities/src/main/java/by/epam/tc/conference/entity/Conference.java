package by.epam.tc.conference.entity;

import java.util.Date;

public class Conference implements Identifiable {

    private static final long serialVersionUID = 42L;

    private Long id;
    private String name;
    private String description;
    private String address;
    private Date date;
    private Long administratorId;

    public Conference() {
    }

    public Conference(String name, String description, String address, Date date, Long administratorId) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.date = date;
        this.administratorId = administratorId;
    }

    public Conference(Long id, String name, String description, String address, Date date, Long administratorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.date = date;
        this.administratorId = administratorId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Conference that = (Conference) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (address != null ? !address.equals(that.address) : that.address != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        return administratorId != null ? administratorId.equals(that.administratorId) : that.administratorId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (administratorId != null ? administratorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", administratorId=" + administratorId +
                '}';
    }
}
