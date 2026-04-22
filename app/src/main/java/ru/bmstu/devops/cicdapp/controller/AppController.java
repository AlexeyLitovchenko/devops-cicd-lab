package ru.bmstu.devops.cicdapp.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Value("${app.service-name:devops-cicd-lab}")
    private String serviceName;

    @Value("${app.version:1.0.0}")
    private String version;

    @Value("${feature.new-greeting:false}")
    private boolean newGreetingEnabled;

    @GetMapping("/")
    public Map<String, String> index() throws UnknownHostException {
        return Map.of(
                "service", serviceName,
                "version", version,
                "hostname", InetAddress.getLocalHost().getHostName()
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/greeting")
    public Map<String, String> greeting() {
        if (newGreetingEnabled) {
            return Map.of("message", "Hello from the new greeting feature!");
        }
        return Map.of("message", "Hello from the classic greeting!");
    }
}
