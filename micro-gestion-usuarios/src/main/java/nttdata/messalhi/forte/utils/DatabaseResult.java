package nttdata.messalhi.forte.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DatabaseResult {
    private boolean success;
    private String message;

    public DatabaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ResponseEntity<String> response(){
        if (this.isSuccess()){
            return ResponseEntity.ok(this.getMessage());
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{error: " +this.getMessage() + "}");
        }
    }
}
