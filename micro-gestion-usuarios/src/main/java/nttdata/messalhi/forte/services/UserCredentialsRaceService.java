package nttdata.messalhi.forte.services;

import nttdata.messalhi.forte.dao.UserCredentialsDAO;
import nttdata.messalhi.forte.dao.UserInfoDAO;
import nttdata.messalhi.forte.entities.UserCredentials;
import nttdata.messalhi.forte.entities.UserInfo;
import nttdata.messalhi.forte.utils.DatabaseResult;
import nttdata.messalhi.forte.utils.UserCredentialsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsRaceService{
    String userCredentialsName = "UserCredentials ";
    @Autowired
    private UserCredentialsDAO userCredentialsDAO;
    @Autowired
    private UserInfoDAO userInfoDAO;

    private UserCredentialsUtils userCredentialsUtils = new UserCredentialsUtils();
    public boolean existsUser(String username) {
        Optional<UserCredentials> optUser = this.userCredentialsDAO.findByUsername(username);
        return optUser.isPresent();
    }
    public DatabaseResult addUserCredentials(String username, String password) {
        String ucExists = userCredentialsName + username + " already exists.";
        try {
            if (existsUser(username)) {
                return new DatabaseResult(false, ucExists);
            } else {
                // Verificar si existe un UserInfo correspondiente
                UserInfo userInfo = userInfoDAO.findById(username).orElse(null);
                if (userInfo == null) {
                    // Si no existe UserInfo correspondiente, no se puede crear UserCredentials
                    return new DatabaseResult(false, "No se puede crear UserCredentials sin UserInfo correspondiente para el usuario " + username);
                }

                // Generar salt y hash para la contraseña
                String salt = userCredentialsUtils.generateSalt();
                String passwordHash = userCredentialsUtils.generateHash(password, salt);

                // Crear UserCredentials con la información proporcionada
                UserCredentials userCredentials = new UserCredentials(username, passwordHash, salt);

                // Establecer la relación con UserInfo
                userCredentials.setUserInfo(userInfo);

                // Guardar UserCredentials en la base de datos
                this.userCredentialsDAO.save(userCredentials);

                return new DatabaseResult(true, ucExists);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult getUserCredentials(String username) {
        try {
            Optional<UserCredentials> userCredentials = this.userCredentialsDAO.getReferenceByUsername(username);
            if (userCredentials.isPresent()) {
                return new DatabaseResult(true, userCredentials.get().toStringJSON()); // Operación exitosa
            }
            else{
                return new DatabaseResult(false, userCredentialsName + username + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult deleteUserCredentials(String username) {
        try {
            if (existsUser(username)) {
                this.userCredentialsDAO.deleteById(username);
            }
            return new DatabaseResult(true, userCredentialsName + username + " deleted");
        }catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult updateUserCredentials(String username, String password) {
        String notFound = " not found";
        try {
            if (existsUser(username)) {
                UserCredentials userCredentials = userCredentialsDAO.findById(username).orElse(null);
                if (userCredentials != null) {
                    String salt = userCredentials.getSalt();
                    String passwordHash = userCredentialsUtils.generateHash(password, salt);
                    userCredentials.setPassword(passwordHash);
                    this.userCredentialsDAO.save(userCredentials);

                    return new DatabaseResult(true, userCredentialsName + username + " updated.");
                } else {
                    return new DatabaseResult(false, userCredentialsName + username + notFound);
                }
            } else {
                return new DatabaseResult(false, userCredentialsName + username + notFound);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult checkUserCredentials(String username, String password){
        try{
            UserCredentials userCredentials = userCredentialsDAO.findById(username).orElse(null);
            if (userCredentials != null){
                String passwordHash = userCredentialsUtils.generateHash(password, userCredentials.getSalt());
                if (passwordHash.equals(userCredentials.getPassword())){
                    return new DatabaseResult(true, userCredentialsName + "OK");
                }
                else{
                    return new DatabaseResult(false, "Password Error");
                }
            }
            else{
                return new DatabaseResult(false, "User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResult(false, e.getMessage());
        }
    }

}
