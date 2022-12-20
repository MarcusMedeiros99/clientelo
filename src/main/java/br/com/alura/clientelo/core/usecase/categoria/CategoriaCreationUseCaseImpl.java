package br.com.alura.clientelo.core.usecase.categoria;

import br.com.alura.clientelo.controller.categoria.CategoriaCreationUseCase;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.CategoriaStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoriaCreationUseCaseImpl implements CategoriaCreationUseCase {

    @Override
    public Categoria create(CategoriaCreationDto dto) {
        return new Categoria(dto.getNome(), CategoriaStatus.ATIVA);
    }
}
