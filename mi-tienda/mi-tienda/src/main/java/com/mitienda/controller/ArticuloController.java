package com.mitienda.controller;

import com.mitienda.dto.ArticuloDTO;
import com.mitienda.service.ArticuloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    private final ArticuloService service;

    // Inyección por constructor (mejor práctica que @Autowired)
    public ArticuloController(ArticuloService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ArticuloDTO>> listar(
            @RequestParam(required = false) String nombre) {
        try {
            List<ArticuloDTO> resultados = (nombre != null)
                    ? service.buscarPorNombre(nombre)
                    : service.listarTodos();

            if (resultados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al listar artículos",
                    e
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloDTO> buscarPorId(@PathVariable Long id) {
        ArticuloDTO articuloDTO = service.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Artículo no encontrado con ID: " + id
                ));
        return ResponseEntity.ok(articuloDTO);
    }

    @PostMapping
    public ResponseEntity<ArticuloDTO> crear(@Valid @RequestBody ArticuloDTO articuloDTO) {
        try {
            ArticuloDTO guardado = service.guardar(articuloDTO);
            return ResponseEntity
                    .created(URI.create("/api/articulos/" + guardado.id))
                    .body(guardado);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error al crear artículo: " + e.getMessage(),
                    e
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticuloDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ArticuloDTO articuloDTO) {
        try {
            return ResponseEntity.ok(service.actualizar(id, articuloDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error al actualizar artículo: " + e.getMessage(),
                    e
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar artículo",
                    e
            );
        }
    }
}

