package br.com.alura.clientelo.web.controller.categoria;

import br.com.alura.clientelo.web.core.usecase.categoria.CategoriaCreationDto;
import br.com.alura.clientelo.web.controller.errors.CategoriaCreationErrorDto;
import br.com.alura.clientelo.web.dao.categoria.CategoriaDAO;
import br.com.alura.clientelo.web.dao.categoria.VendasPorCategoriaVO;
import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.CategoriaStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private CategoriaFilterUseCase categoriaFilterUseCase;
    @Autowired
    private CategoriaCreationUseCase categoriaCreationUseCase;
    private Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAll(CategoriaStatus status, String nome) {
        List<CategoriaDto> categorias = findCategoriasBy(status, nome);
        return ResponseEntity.ok(categorias);
    }

    private List<CategoriaDto> findCategoriasBy(CategoriaStatus status, String nome) {
        List<Categoria> categorias = categoriaFilterUseCase.findBy(nome, status);

        return categorias.stream().map(CategoriaDto::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getById(@PathVariable Long id) {
        Optional<Categoria> optionalCategoria = categoriaDAO.findById(id);
        if (optionalCategoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CategoriaDto(optionalCategoria.get()));
    }

    @PostMapping
    @Transactional
    @CacheEvict("categoriaVendas")
    public ResponseEntity<?> create(@Valid @RequestBody CategoriaCreationDto form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Categoria categoria = categoriaCreationUseCase.create(form);
        categoriaDAO.save(categoria);
        URI uri = uriBuilder.path("/api/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        CategoriaDto categoriaDto = new CategoriaDto(categoria);

        return ResponseEntity.created(uri).body(categoriaDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CategoriaCreationErrorDto handleSQLIntegrityConstraintViolation (SQLIntegrityConstraintViolationException exception) {
        return new CategoriaCreationErrorDto("Categoria já existe. Nada foi feito.");
    }

    @GetMapping("/vendas")
    @Cacheable("categoriaVendas")
    public ResponseEntity<List<VendasPorCategoriaVO>> vendas() {
        logger.info("request at endpoint GET api/categorias/vendas");
        List<VendasPorCategoriaVO> vendasPorCategoria = categoriaDAO.agrupaPorCategoria();
        return ResponseEntity.ok().body(vendasPorCategoria);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity toggleStatus(@PathVariable Long id) {
        Optional<Categoria> optionalCategoria = categoriaDAO.findById(id);
        if (optionalCategoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = optionalCategoria.get();
        categoria.toggleStatus();
        categoriaDAO.save(categoria);

        return ResponseEntity.ok().build();
    }
}
