package dev.mayur.focus_flow_backend.features.health;


import dev.mayur.focus_flow_backend.core.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class health_check_controller {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Focus Flow", "Spring boot is running"));
    }
}
