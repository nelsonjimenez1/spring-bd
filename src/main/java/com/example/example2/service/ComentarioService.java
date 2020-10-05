package com.example.example2.service;

import com.example.example2.exceptions.NotFoundException;
import com.example.example2.model.Comentario;
import com.example.example2.model.ComentarioRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
 * ComentarioService
 */
@RestController
public class ComentarioService {

    @Autowired
    ComentarioRepository repository;

    @GetMapping("/comentarios/{id}")
    Comentario findComentario(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Comentario not found"));
    }

    @GetMapping("/comentarios/{id}/respuestas")
    Iterable<Comentario> findComentarioRespuestas(@PathVariable Long id) {
        ArrayList<Comentario> comentarios = new ArrayList<>(repository.findById(id).get().getIdRespuestas());
        Collections.sort(comentarios, new Comparator() {
            @Override
            public int compare(Object c1, Object c2) {
                Comentario co1 = (Comentario) c1;
                Comentario co2 = (Comentario) c2;
                return new Integer(co2.getRanking()).compareTo(new Integer(co1.getRanking()));
            }
        });
        return comentarios;
    }

    @PostMapping("/comentarios")
    Comentario createComentario(@RequestBody Comentario comentario) {
        return repository.save(comentario);
    }

    @DeleteMapping("/comentarios/{id}")
    void deleteComentario(@PathVariable Long id) {
        if (repository.existsById(id)) {
            ArrayList<Comentario> comentarios = new ArrayList<>(repository.findById(id).get().getIdRespuestas());
            for (int i = 0; i < comentarios.size(); i++) {
                repository.deleteById(comentarios.get(i).getId());
            }
            repository.deleteById(id);
        }
        else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/moderator/comentarios/{id}", method = RequestMethod.PUT)
    Comentario aprobarComentario(@PathVariable Long id, @RequestBody Comentario comentarioData) {
        if (comentarioData.isAprobado()) {
            Comentario comentario = findComentario(id);
            comentario.setAprobado(comentarioData.isAprobado());
            return repository.save(comentario);
        }
        else {
          deleteComentario(id);
        }
        return null;
    }

    @PutMapping("/comentarios/{id}")
    Comentario updateComentario(@PathVariable Long id, @RequestBody Comentario comentarioData) {
        Comentario comentario = findComentario(id);
        comentario.setMensaje(comentarioData.getMensaje());
        comentario.setIdRespuestas(comentarioData.getIdRespuestas());
        comentario.setIdRespuesta(comentarioData.getIdRespuesta());
        comentario.setRanking(comentarioData.getRanking());
        return repository.save(comentario);
    }
}
