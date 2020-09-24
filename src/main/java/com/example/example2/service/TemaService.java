package com.example.example2.service;

import com.example.example2.exceptions.NotFoundException;
import com.example.example2.model.Foro;
import com.example.example2.model.Tema;
import com.example.example2.model.Comentario;
import com.example.example2.model.ForoRepository;
import com.example.example2.model.TemaRepository;
import com.example.example2.model.ComentarioRepository;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TemaService
 */
@RestController
@CrossOrigin(origins = "*")
public class TemaService {

    @Autowired
    TemaRepository repository;
    @Autowired
    ComentarioRepository repositoryComentario;

    @GetMapping("/temas/{id}")
    Tema findTema(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Tema not found"));
    }

    @GetMapping("/temas/{id}/comentarios")
    Iterable<Comentario> findAllComentarios(@PathVariable("id") Long temasId) {
        // What happens if the company does not exist in the DB?
        return repository.findById(temasId).get().getComentarios();
    }

    @PostMapping("/temas")
    Tema createTema(@RequestBody Tema tema) {
        return repository.save(tema);
    }

    @DeleteMapping("/temas/{id}")
    void deleteTema(@PathVariable Long id) {
        if (repository.existsById(id)) {
            ArrayList<Comentario> comentarios = new ArrayList<>(repository.findById(id).get().getComentarios());
            for (int i = 0; i < comentarios.size(); i++) {
                repositoryComentario.deleteById(comentarios.get(i).getId());
            }
            repository.deleteById(id);
        }
        else {
            throw new NotFoundException();
        }
    }
}
