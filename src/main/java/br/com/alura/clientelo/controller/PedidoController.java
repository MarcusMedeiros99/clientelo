package br.com.alura.clientelo.controller;

import br.com.alura.clientelo.controller.dto.*;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.ClienteNotFoundException;
import br.com.alura.clientelo.exceptions.EstoqueInsuficienteException;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private PedidoDAO pedidoDAO;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PedidoCreateForm form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Pedido pedido = form.convert(clienteDAO, produtoDAO, pedidoDAO);
        pedidoDAO.save(pedido);
        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        return ResponseEntity.created(uri).body(pedidoDTO);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public EstoqueInsuficienteErrorDto handleEstoqueInsuficiente(EstoqueInsuficienteException exception) {
        return new EstoqueInsuficienteErrorDto(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProdutoNotFoundErrorDto handleProdutoNotFound(ProdutoNotFoundException exception) {
        return new ProdutoNotFoundErrorDto(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ClienteNotFoundErrorDto handleClienteNotFound(ClienteNotFoundException exception) {
        return new ClienteNotFoundErrorDto(exception.getMessage());
    }
}
