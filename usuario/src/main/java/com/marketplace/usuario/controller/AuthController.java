package com.marketplace.usuario.controller;
import com.marketplace.usuario.dto.AuthLoginRequestDTO;
import com.marketplace.usuario.dto.AuthResponseDTO;
import com.marketplace.usuario.model.Usuario;
import com.marketplace.usuario.repository.UsuarioRepository;
import com.marketplace.usuario.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// Import correcto de Spring Security
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // Habilita los logs con log.info(), log.error(), etc.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Genera el constructor automáticamente para los campos final
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthLoginRequestDTO request) {

        log.info("Intento de login para el email: {}", request.getEmail());

        // Le pedimos a Spring Security que verifique el email y password
        // Si las credenciales son incorrectas, lanza una excepción automáticamente
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        // Buscamos el usuario en la base de datos usando el email autenticado
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));

        log.info("Login exitoso para el usuario con email: {}", usuario.getEmail());

        // Generamos el token JWT y lo devolvemos junto con los datos del usuario
        return new AuthResponseDTO(
                jwtService.generateToken(usuario),  // token
                usuario.getEmail(),                  // email
                usuario.getRol(),                    // role
                jwtService.getExpirationMs()         // expiresIn
        );
    }
}