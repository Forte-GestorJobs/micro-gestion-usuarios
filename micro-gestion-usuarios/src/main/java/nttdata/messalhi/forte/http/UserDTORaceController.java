package nttdata.messalhi.forte.http;
import nttdata.messalhi.forte.auxi.UserDTO;
import nttdata.messalhi.forte.services.UserDTORaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserDTORaceController {
    @Autowired
    private UserDTORaceService userDTORaceService;
    @PostMapping("/User")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        return this.userDTORaceService.addUser(userDTO);
    }

    @GetMapping("/User/{id}")
    public ResponseEntity<String> getUser(@PathVariable String id) {
        return this.userDTORaceService.getUser(id);
    }

    @GetMapping("/User/list")
    public ResponseEntity<String> listTaskSchedule(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.userDTORaceService.listUsers(pageable);
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        return this.userDTORaceService.deleteUser(id);
    }

    @PutMapping("/User")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
        return this.userDTORaceService.updateUser(userDTO);
    }

    @PostMapping("/User/checkUser")
    public ResponseEntity<String> checkUser(@RequestBody UserDTO user) {
        return this.userDTORaceService.checkUser(user);
    }




}
