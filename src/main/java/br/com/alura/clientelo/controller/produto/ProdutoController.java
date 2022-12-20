package br.com.alura.clientelo.controller.produto;

import br.com.alura.clientelo.controller.errors.ProdutoCreationErrorDto;
import br.com.alura.clientelo.controller.errors.ProdutoEditErrorDto;
import br.com.alura.clientelo.core.usecase.produto.ProdutoCreationDto;
import br.com.alura.clientelo.core.usecase.produto.ProdutoEditDto;
import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.dao.specifications.ProdutoSpecifications;
import br.com.alura.clientelo.exceptions.ClienteloEntityNotFoundException;
import br.com.alura.clientelo.core.entity.produto.Produto;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private ProdutoCreationUseCase produtoCreationUseCase;
    @Autowired
    private ProdutoEditUseCase produtoEditUseCase;

    private static int pageSize = 5;

    @GetMapping
    public ResponseEntity<AllProdutosDto> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") String nomeCategoria,
            @RequestParam(required = false) BigDecimal minPreco,
            @RequestParam(required = false) BigDecimal maxPreco) {

        logger.info("request at endpoint GET api/produtos");

        Page<Produto> produtosPage = produtoDAO.findAll(
                ProdutoSpecifications.filter(nome, nomeCategoria, minPreco, maxPreco),
                        PageRequest.of(page, pageSize, Sort.Direction.ASC, "nome"));
        List<ProdutoResumeDto> produtos = produtosPage.stream().map(ProdutoResumeDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(new AllProdutosDto(produtos, page, produtosPage.getTotalPages(), produtosPage.getSize()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> getById(@PathVariable Long id) {
        Optional<Produto> optionalProduto = produtoDAO.findById(id);
        if (optionalProduto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ProdutoDto(optionalProduto.get()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody ProdutoCreationDto form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Produto produto = produtoCreationUseCase.createFrom(form);
        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        ProdutoDto produtoDto = new ProdutoDto(produto);

        return ResponseEntity.created(uri).body(produtoDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProdutoCreationErrorDto handleClienteloEntityNotFound(ClienteloEntityNotFoundException exception) {
        return new ProdutoCreationErrorDto("Categoria não encontrada. Nada foi feito.");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity edit(@PathVariable Long id, @Valid @RequestBody ProdutoEditDto form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Produto novoProduto = produtoEditUseCase.edit(id, form);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProdutoEditErrorDto handleProdutoNotFoundException(ProdutoNotFoundException exception) {
        return new ProdutoEditErrorDto("Produto não encontrado. Nada foi feito.");
    }

}
