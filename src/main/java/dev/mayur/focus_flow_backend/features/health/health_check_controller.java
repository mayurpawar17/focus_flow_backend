package dev.mayur.focus_flow_backend.features.health;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class health_check_controller {

    @GetMapping
    public String healthCheck() {
        return "Focus Flow";
    }
}
