package nttdata.messalhi.forte.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "credentials")
public class UserCredentials {
    @Id
    @Column(name = "username")
    private String username;
    private String password;
    private String salt;

    @OneToOne(optional = false)
    @MapsId
    private UserInfo userInfo;

    public UserCredentials() {
    }
    public UserCredentials(String username, String password_hash, String salt) {
        this.username = username;
        this.password = password_hash;
        this.salt = salt;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
