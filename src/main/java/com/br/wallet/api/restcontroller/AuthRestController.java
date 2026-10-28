package com.br.wallet.api.restcontroller;

import com.br.wallet.dto.ref.LoginRequestDTO;
import com.br.wallet.dto.request.update.ChangePasswordRequestDTO;
import com.br.wallet.security.JwtService;
import com.br.wallet.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody @Valid LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = usuarioService.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(jwtToken);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequestDTO request,
            Principal connectedUser
    ) {
        jwtService.changePassword(request, connectedUser);
        return ResponseEntity.ok().body("Senha alterada com sucesso!");
    }
}
