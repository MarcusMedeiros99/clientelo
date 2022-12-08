package br.com.alura.clientelo.controller;

import br.com.alura.clientelo.config.security.JwtUtils;
import br.com.alura.clientelo.controller.dto.UserInfoResponse;
import br.com.alura.clientelo.controller.form.LoginForm;
import br.com.alura.clientelo.controller.form.SignupForm;
import br.com.alura.clientelo.dao.UsuarioDAO;
import br.com.alura.clientelo.dao.UsuarioRoleDAO;
import br.com.alura.clientelo.models.Usuario;
import br.com.alura.clientelo.models.RoleEnum;
import br.com.alura.clientelo.models.UsuarioRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private UsuarioRoleDAO roleDAO;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
//        UsuarioRole role = roleDAO.findByName(RoleEnum.USUARIO_ROLE)
//                .orElseThrow(() -> new RuntimeException("role USUARIO_ROLE não existe"));
        Usuario user = usuarioDAO.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        Authentication authentication = new TestingAuthenticationToken(user, loginForm.getPassword(), "USUARIO_ROLE");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario userDetails = (Usuario) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        Collection<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getEmail(),
                        roles));
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
