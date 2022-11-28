package br.com.alura.clientelo.controller;

import br.com.alura.clientelo.controller.dto.CategoriaCreateForm;
import br.com.alura.clientelo.controller.dto.CategoriaDto;
import br.com.alura.clientelo.controller.dto.CategoriaCreationErrorDto;
import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaDAO categoriaDAO;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAll(CategoriaStatus status, String nome) {
        List<CategoriaDto> categorias = findCategoriasBy(status, nome);
        return ResponseEntity.ok(categorias);
    }

    private List<CategoriaDto> findCategoriasBy(CategoriaStatus status, String nome) {
        List<CategoriaDto> categorias = new ArrayList<>();

        Categoria categoriaExample = new Categoria(nome, status);
        ExampleMatcher matcher = buildCategoriaMatcher();

        categoriaDAO.findAll(Example.of(categoriaExample, matcher))
                .forEach(categoria -> categorias.add(new CategoriaDto(categoria)));
        return categorias;
    }

    private static ExampleMatcher buildCategoriaMatcher() {
        return ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("nome", match -> match.contains());
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
    public ResponseEntity<?> create(@Valid @RequestBody CategoriaCreateForm form, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        Categoria categoria = form.convert();
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
    public ResponseEntity<List<VendasPorCategoriaVO>> vendas() {
        List<VendasPorCategoriaVO> vendasPorCategoria = categoriaDAO.agrupaPorCategoria();
        return ResponseEntity.ok().body(vendasPorCategoria);
    }

    @PatchMapping("/{id}")
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
