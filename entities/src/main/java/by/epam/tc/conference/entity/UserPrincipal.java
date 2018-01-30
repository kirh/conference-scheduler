package by.epam.tc.conference.entity;

import java.io.Serializable;

public class UserPrincipal implements Serializable{

    private static final long serialVersionUID = 42L;

    private Long id;
    private String username;
    private boolean isAdmin;

    public UserPrincipal() {
    }

    public UserPrincipal(Long id, String username, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPrincipal that = (UserPrincipal) o;

        if (isAdmin != that.isAdmin) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return username != null ? username.equals(that.username) : that.username == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
