package nttdata.messalhi.forte.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import nttdata.messalhi.forte.auxi.UserDTO;
import nttdata.messalhi.forte.dao.UserCredentialsDAO;
import nttdata.messalhi.forte.dao.UserInfoDAO;
import nttdata.messalhi.forte.entities.UserCredentials;
import nttdata.messalhi.forte.entities.UserInfo;
import nttdata.messalhi.forte.utils.DatabaseResult;
import nttdata.messalhi.forte.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDTORaceService {
    Logger logger = LoggerFactory.getLogger(UserDTORaceService.class);

    private UserUtils userUtils = new UserUtils();
    @Autowired
    private UserCredentialsDAO userCredentialsDAO;

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Autowired
    UserInfoRaceService userInfoRaceService = new UserInfoRaceService();

    @Autowired
    UserCredentialsRaceService userCredentialsRaceService = new UserCredentialsRaceService();

    public ResponseEntity<String> addUser(UserDTO userDTO) {
        try {
            DatabaseResult result = userInfoRaceService.addUserInfo(userDTO.getUsername(), userDTO.getEmail(), userDTO.getType());
            if (result.isSuccess()) {
                DatabaseResult result2 = userCredentialsRaceService.addUserCredentials(userDTO.getUsername(), userDTO.getPassword());
                if (result2.isSuccess()) {
                    return ResponseEntity.ok("User saved");
                }else{
                    return ResponseEntity.badRequest().body(result2.getMessage());
                }
            } else {
                return ResponseEntity.badRequest().body(result.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> getUser(String userId) {
        try{
            DatabaseResult result = userInfoRaceService.getUserInfo(userId);
            if (result.isSuccess()){
                return ResponseEntity.ok(result.getMessage());
            }else{
                return ResponseEntity.badRequest().body(result.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> listUsers(Pageable pageable) {
        try{
            Page<UserInfo> usersPage = userInfoDAO.findAll(pageable);
            ObjectMapper mapper = new ObjectMapper();
            String usersJson = mapper.writeValueAsString(usersPage.getContent());
            return ResponseEntity.ok(usersJson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteUser(String userId) {
        try{
            DatabaseResult result = userInfoRaceService.deleteUserInfo(userId);
            if (result.isSuccess()) {
                return ResponseEntity.ok("User Deleted");
            }else{
                return ResponseEntity.badRequest().body(result.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> updateUser(UserDTO userDTO) {
        try{
            DatabaseResult result = userInfoRaceService.updateUserInfo(userDTO.getUsername(), userDTO.getEmail(), userDTO.getType());
            if (result.isSuccess()) {
                DatabaseResult result2 = userCredentialsRaceService.updateUserCredentials(userDTO.getUsername(), userDTO.getPassword());
                if (result2.isSuccess()) {
                    return ResponseEntity.ok("User Updated");
                }else{
                    return ResponseEntity.badRequest().body(result2.getMessage());
                }
            } else {
                return ResponseEntity.badRequest().body(result.getMessage());
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> checkUser(UserDTO userDTO) {
        try{
            String username = userDTO.getUsername();
            logger.info("Petición checkUser para" + username);
            UserCredentials userCredentials = userCredentialsDAO.findById(username).orElse(null);
            if (userCredentials != null){
                String passwordHash = userUtils.generateHash(userDTO.getPassword(), userCredentials.getSalt());
                if (passwordHash.equals(userCredentials.getPassword())){
                    logger.info("Usuario OK");
                    return ResponseEntity.ok(username + " OK");
                }
                else{
                    logger.info("Usuario o contraseña incorrectos");
                    return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
                }
            }
            else{
                logger.info("Usuario o contraseña incorrectos");
                return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error interno: "+ e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }




}
