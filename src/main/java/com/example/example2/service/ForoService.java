package com.example.example1.service;

import com.example.example1.exceptions.NotFoundException;
import com.example.example1.model.Foro;
import com.example.example1.model.Tema;
import com.example.example1.model.Comentario;
import com.example.example1.model.ForoRepository;
import com.example.example1.model.TemaRepository;
import com.example.example1.model.ComentarioRepository;
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
 * ForoService
 */
@RestController
@CrossOrigin(origins = "*")
public class ForoService {

    @Autowired
    ForoRepository repository;
    @Autowired
    TemaRepository repositoryTema;
    @Autowired
    ComentarioRepository repositoryComentario;

    @GetMapping("/foros")
    Iterable<Foro> getForos() {
        return repository.findAll();
    }

    @GetMapping("/foros/{id}")
    Foro findForo(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Foro not found"));
    }

    @GetMapping("/foros/{id}/temas")
    Iterable<Tema> findAllTemas(@PathVariable("id") Long forosId) {
        // What happens if the company does not exist in the DB?
        return repository.findById(forosId).get().getTemas();
    }

    @PostMapping("/foros")
    Foro createForo(@RequestBody Foro foro) {
        return repository.save(foro);
    }

    @DeleteMapping("/foros/{id}")
    void deleteForo(@PathVariable Long id) {
        if (repository.existsById(id)) {
            ArrayList<Tema> temas = new ArrayList<>(repository.findById(id).get().getTemas());

            for (int i = 0; i < temas.size(); i++) {
              for (int j = 0; j < temas.get(i).getComentarios().size(); j++) {
                repositoryComentario.deleteById(temas.get(i).getComentarios().get(j).getId());
              }
              repositoryTema.deleteById(temas.get(i).getId());
            }
            repository.deleteById(id);
        }
        else {
            throw new NotFoundException();
        }
    }
}
