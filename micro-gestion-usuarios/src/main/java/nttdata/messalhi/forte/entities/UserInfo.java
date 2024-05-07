package nttdata.messalhi.forte.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    private String username;
    private String email;
    private String type; // client / admin
    private Date last_access;

    //@OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    //private UserCredentials userCredentials;

    public UserInfo(){
    }
    public UserInfo(String username, String email, String type) {
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLast_access() {
        return last_access;
    }
    public void setLast_access(Date last_access) {
        this.last_access = last_access;
    }

    public String toStringJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Manejo de errores
        }
    }

}
