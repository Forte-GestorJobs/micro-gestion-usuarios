package nttdata.messalhi.forte.http;
import nttdata.messalhi.forte.auxi.UserDTO;
import nttdata.messalhi.forte.services.UserDTORaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserDTORaceController {
    @Autowired
    private UserDTORaceService userDTORaceService;
    @PostMapping("/User")
    public ResponseEntity<String> addTaskSchedule(@RequestBody UserDTO userDTO) {
        return this.userDTORaceService.addUser(userDTO);
    }

    @GetMapping("/User/{id}")
    public ResponseEntity<String> getTaskSchedule(@PathVariable String id) {
        return this.userDTORaceService.getUser(id);
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<String> deleteTaskSchedule(@PathVariable String id) {
        return this.userDTORaceService.deleteUser(id);
    }

    @PutMapping("/User")
    public ResponseEntity<String> updateTaskSchedule(@RequestBody UserDTO userDTO){
        return this.userDTORaceService.updateUser(userDTO);
    }

    @PostMapping("/User/checkUser")
    public ResponseEntity<String> checkUserCredential(@RequestBody UserDTO user) {
        return this.userDTORaceService.checkUser(user);
    }


}
