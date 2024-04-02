package nttdata.messalhi.forte.services;

import nttdata.messalhi.forte.dao.UserCredentialsDAO;
import nttdata.messalhi.forte.dao.UserInfoDAO;
import nttdata.messalhi.forte.entities.UserCredentials;
import nttdata.messalhi.forte.entities.UserInfo;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoRaceService {
    @Autowired
    private UserInfoDAO userInfoDAO;

    @Autowired
    private UserCredentialsDAO userCredentialsDAO;

    public boolean existsUser(String username) {
        Optional<UserInfo> optUser = this.userInfoDAO.findByUsername(username);
        return optUser.isPresent();
    }
    public DatabaseResult addUserInfo(String username, String email, String type) {
        try {
            if (existsUser(username)) {
                return new DatabaseResult(false, "UserInfo " + username +" already exists.");
            }
            else{
                UserInfo userInfo = new UserInfo(username, email, type);
                Date last_access = new Date();
                userInfo.setLast_access(last_access);
                this.userInfoDAO.save(userInfo);
                return new DatabaseResult(true, "UserInfo " + username + " added to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult getUserInfo(String username) {
        try {
            Optional<UserInfo> userInfo = this.userInfoDAO.getReferenceByUsername(username);
            if (userInfo.isPresent()) {
                return new DatabaseResult(true, userInfo.get().toStringJSON()); // Operación exitosa
            }
            else{
                return new DatabaseResult(false, "UserInfo "+ username + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult deleteUserInfo(String username) {
        try {
            if (existsUser(username)) {
                Optional<UserCredentials> optUser = this.userCredentialsDAO.findByUsername(username);
                if (optUser.isPresent()) {
                    this.userCredentialsDAO.deleteById(username);
                }
                userInfoDAO.deleteById(username);
            }
            return new DatabaseResult(true, "UserInfo " + username + " deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }
    public DatabaseResult updateUserInfo(String username, String email, String type) {
        try {
            if (existsUser(username)) {
                Date last_access = new Date();
                UserInfo userInfo = new UserInfo(username, email, type);
                userInfo.setLast_access(last_access);
                this.userInfoDAO.save(userInfo);
                return new DatabaseResult(true, "UserInfo " + username + " updated in the database.");
            }
            else{
                return new DatabaseResult(false, "UserInfo " + username + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }
}
