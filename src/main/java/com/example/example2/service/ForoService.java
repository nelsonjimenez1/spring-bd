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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ForoService
 */
@RestController
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

    @RequestMapping(value = "/admin/foros", method = RequestMethod.POST)
    Foro createForo(@RequestBody Foro foro) {
        return repository.save(foro);
    }

    @RequestMapping(value = "/admin/foros/{id}", method = RequestMethod.DELETE)
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
