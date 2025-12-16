package com.ByteCraft.IotProject.Controller;

import com.ByteCraft.IotProject.Dto.AuthResponse;
import com.ByteCraft.IotProject.Dto.LoginRequest;
import com.ByteCraft.IotProject.Dto.SignupRequest;
import com.ByteCraft.IotProject.Entity.Role;
import com.ByteCraft.IotProject.Entity.User;
import com.ByteCraft.IotProject.Repository.UserRepository;
import com.ByteCraft.IotProject.Security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthRestController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("EMAIL_ALREADY_EXISTS");
        }

        User u = User.builder()
                .name(req.getName())
                .surname(req.getSurname())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .address(req.getAddress())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(u);
        return ResponseEntity.ok("SIGNUP_OK");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var userOpt = userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("USER_NOT_FOUND");

        var user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("INVALID_PASSWORD");
        }

        // Token üretmek için UserDetails oluştur
        var ud = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()) // USER / ADMIN
                .build();

        String token = jwtService.generateToken(ud);

        // JSON dön: token + role
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
    }
}
