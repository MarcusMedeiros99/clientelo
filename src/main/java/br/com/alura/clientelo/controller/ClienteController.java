package br.com.alura.clientelo.controller;

import br.com.alura.clientelo.controller.dto.*;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getById(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteDAO.findById(id);
        if (optionalCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ClienteDto(optionalCliente.get()));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateForm form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Cliente cliente = form.convert();
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
