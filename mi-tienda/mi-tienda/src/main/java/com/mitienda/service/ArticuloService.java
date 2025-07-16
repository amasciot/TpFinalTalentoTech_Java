package com.mitienda.service;

import com.mitienda.dto.ArticuloDTO;
import com.mitienda.entity.Articulo;
import com.mitienda.exception.ResourceNotFoundException;
import com.mitienda.repository.ArticuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ArticuloDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<ArticuloDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(articulo -> modelMapper.map(articulo, ArticuloDTO.class));
    }

    public List<ArticuloDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ArticuloDTO guardar(ArticuloDTO articuloDTO) {
        Articulo articulo = modelMapper.map(articuloDTO, Articulo.class);
        Articulo guardado = repository.save(articulo);
        return modelMapper.map(guardado, ArticuloDTO.class); // ¡Asegúrate de mapear a DTO!
    }

    public String eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Artículo no encontrado");
        }
        repository.deleteById(id);

        return "Artículo eliminado correctamente";
    }

    private ArticuloDTO convertirADTO(Articulo articulo) {
        return modelMapper.map(articulo, ArticuloDTO.class);
    }

    public ArticuloDTO actualizar(Long id, ArticuloDTO articuloDTO) {
        return repository.findById(id)
                .map(articuloExistente -> {
                    modelMapper.map(articuloDTO, articuloExistente);
                    articuloExistente.setId(id); // Asegura que no se modifique el ID
                    Articulo actualizado = repository.save(articuloExistente);
                    return modelMapper.map(actualizado, ArticuloDTO.class);
                })
                .orElseThrow(() -> new EntityNotFoundException("Artículo no encontrado"));
    }

}