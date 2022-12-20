package br.com.alura.clientelo.controller.pedido;

import br.com.alura.clientelo.controller.errors.ClienteNotFoundErrorDto;
import br.com.alura.clientelo.controller.errors.EstoqueInsuficienteErrorDto;
import br.com.alura.clientelo.controller.errors.ProdutoNotFoundErrorDto;
import br.com.alura.clientelo.core.usecase.pedido.PedidoCreationDto;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.ClienteNotFoundException;
import br.com.alura.clientelo.exceptions.EstoqueInsuficienteException;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.core.entity.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private PedidoDAO pedidoDAO;
    @Autowired
    private PedidoCreationUseCase pedidoCreationUseCase;

    private final static int PAGE_SIZE = 5;


    @GetMapping
    public ResponseEntity<PedidosListDto> list(@RequestParam(defaultValue = "0") Integer page) {
        Page<Pedido> pedidoPage = pedidoDAO.findAll(buildPageRequest(page));
        List<PedidoOnListDto> pedidos = getPageOfPedidosAsList(pedidoPage);

        return ResponseEntity.ok(new PedidosListDto(pedidos, page, pedidoPage.getTotalPages(), pedidoPage.getSize()));
    }

    private List<PedidoOnListDto> getPageOfPedidosAsList(Page<Pedido> page) {
        Page<Pedido> pedidoPage = pedidoDAO.findAll(buildPageRequest(page.getNumber()));

        return pedidoPage.stream().map(PedidoOnListDto::new).collect(Collectors.toList());
    }

    private static PageRequest buildPageRequest(Integer pageNumber) {
        return PageRequest.of(
                pageNumber, PAGE_SIZE,
                Sort.by("data").descending()
                        .and(Sort.by("cliente_nome").ascending()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetailsDto> details(@PathVariable Long id) {
        Optional<Pedido> optionalPedido = pedidoDAO.findById(id);
        if (optionalPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PedidoDetailsDto(optionalPedido.get()));
    }

    @PostMapping
    @Transactional
    @CacheEvict("categoriaVendas")
    public ResponseEntity<?> create(@Valid @RequestBody PedidoCreationDto form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Pedido pedido = pedidoCreationUseCase.createPedido(form);
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
