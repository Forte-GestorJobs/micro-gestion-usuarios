package nttdata.messalhi.forte.http;

import nttdata.messalhi.forte.entities.UserCredentials;
import nttdata.messalhi.forte.services.UserCredentialsRaceService;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserCredentialsRaceController {
    @Autowired
    private UserCredentialsRaceService userCredentialsRaceService;
    @PostMapping("/UserCredentials")
    public ResponseEntity<String> addUserCredential(@RequestBody UserCredentials user) {
        String username = user.getUsername();
        String password = user.getPassword();
        DatabaseResult result = this.userCredentialsRaceService.addUserCredentials(username, password);
        return result.response();
    }
    @GetMapping("/UserCredentials/{username}")
    public ResponseEntity<String> getUserCredential(@PathVariable String username) {
        DatabaseResult result = this.userCredentialsRaceService.getUserCredentials(username);
        return result.response();
    }
    @DeleteMapping("/UserCredentials/{username}")
    public ResponseEntity<String> deleteUserCredential(@PathVariable String username) {
        DatabaseResult result = this.userCredentialsRaceService.deleteUserCredentials(username);
        return result.response();
    }
    @PutMapping("/UserCredentials")
    public ResponseEntity<String> updateRace(@RequestBody UserCredentials user) {
        String username = user.getUsername();
        String password = user.getPassword();
        DatabaseResult result = this.userCredentialsRaceService.updateUserCredentials(username, password);
        return result.response();
    }
    @PostMapping("/UserCredentials/checkUser")
    public ResponseEntity<String> checkUserCredential(@RequestBody UserCredentials user) {
        String username = user.getUsername();
        String password = user.getPassword();
        DatabaseResult result = this.userCredentialsRaceService.checkUserCredentials(username, password);
        return result.response();
    }




}
