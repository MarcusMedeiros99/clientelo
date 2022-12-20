package br.com.alura.clientelo.controller.cliente;

import br.com.alura.clientelo.controller.errors.ClienteCreationErrorDto;
import br.com.alura.clientelo.controller.form.ClienteCreateForm;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.UsuarioDAO;
import br.com.alura.clientelo.dao.UsuarioRoleDAO;
import br.com.alura.clientelo.core.entity.cliente.Cliente;
import br.com.alura.clientelo.core.entity.usuario.RoleEnum;
import br.com.alura.clientelo.core.entity.usuario.UsuarioRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    private static final int PAGE_SIZE = 5;
    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UsuarioRoleDAO roleDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getById(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteDAO.findById(id);
        if (optionalCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ClienteDto(optionalCliente.get()));
    }

    @GetMapping
    public ResponseEntity<ClienteListDto> list(@RequestParam(defaultValue = "0") Integer page) {
        Page<Cliente> clientesPage = clienteDAO.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("nome")));

        List<ClienteOnListDto> clientes = clientesPage.stream().map(ClienteOnListDto::new).collect(Collectors.toList());
        ClienteListDto clientesDto = new ClienteListDto(clientes, page, clientesPage.getTotalPages(), PAGE_SIZE);

        return ResponseEntity.ok(clientesDto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateForm form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        if (usuarioDAO.existsByEmail(form.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        UsuarioRole role = roleDAO.findByName(RoleEnum.USUARIO_ROLE)
                .orElseThrow(() -> new RuntimeException("role USUARIO_ROLE não existe"));
        Cliente cliente = form.convert(encoder, role);
        clienteDAO.save(cliente);
        URI uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        ClienteDto clienteDto = new ClienteDto(cliente);

        return ResponseEntity.created(uri).body(clienteDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ClienteCreationErrorDto handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException exception) {
        return new ClienteCreationErrorDto("Cliente já existe. Nada foi feito.");
    }
}
