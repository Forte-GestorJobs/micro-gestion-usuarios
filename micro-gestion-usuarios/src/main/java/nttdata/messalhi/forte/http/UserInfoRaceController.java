package nttdata.messalhi.forte.http;
import nttdata.messalhi.forte.entities.UserInfo;
import nttdata.messalhi.forte.services.UserInfoRaceService;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("user")
public class UserInfoRaceController {
    @Autowired
    private UserInfoRaceService userInfoRaceService;
    @PostMapping("/UserInfo")
    public ResponseEntity<String> addUserCredential(@RequestBody UserInfo user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String type = user.getType();
        DatabaseResult result = this.userInfoRaceService.addUserInfo(username, email, type);
        return result.response();
    }
    @GetMapping("/UserInfo/{username}")
    public ResponseEntity<String> getUserCredential(@PathVariable String username) {
        DatabaseResult result = this.userInfoRaceService.getUserInfo(username);
        return result.response();
    }

    @DeleteMapping("/UserInfo/{username}")
    public ResponseEntity<String> deleteUserCredential(@PathVariable String username) {
        DatabaseResult result = this.userInfoRaceService.deleteUserInfo(username);
        return result.response();
    }

    @PutMapping("/UserInfo")
    public ResponseEntity<String> updateRace(@RequestBody UserInfo user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String type = user.getType();
        DatabaseResult result = this.userInfoRaceService.updateUserInfo(username, email, type);
        return result.response();
    }

}
