package br.com.alura.clientelo.web.controller.auth;

import br.com.alura.clientelo.web.config.security.JwtUtils;
import br.com.alura.clientelo.web.controller.errors.AuthenticationErrorDto;
import br.com.alura.clientelo.web.core.exceptions.UsuarioNotFoundException;
import br.com.alura.clientelo.web.core.usecase.auth.LoginDto;
import br.com.alura.clientelo.web.dao.auth.UsuarioDAO;
import br.com.alura.clientelo.web.dao.auth.UsuarioRoleDAO;
import br.com.alura.clientelo.web.core.entity.auth.Usuario;
import br.com.alura.clientelo.web.core.entity.auth.RoleEnum;
import br.com.alura.clientelo.web.core.entity.auth.UsuarioRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final String AUTH_FAILURE_MSG = "Usuário ou senha não existe";

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private UsuarioRoleDAO roleDAO;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SignInUseCase signInUseCase;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginForm) {
        Usuario userDetails = signInUseCase.signIn(loginForm);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationErrorDto handleUsuarioNotFoundException(UsuarioNotFoundException exception) {
        return new AuthenticationErrorDto(AUTH_FAILURE_MSG);
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupForm signupForm) {
        if (usuarioDAO.existsByEmail(signupForm.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        // Create new user's account
        Usuario user = new Usuario(signupForm.getEmail(),
                encoder.encode(signupForm.getPassword()));

        UsuarioRole usuarioRole = roleDAO.findByName(RoleEnum.USUARIO_ROLE)
                .orElseThrow(() -> new RuntimeException("role USUARIO_ROLE não existe"));
        user.addRole(usuarioRole);
        usuarioDAO.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
