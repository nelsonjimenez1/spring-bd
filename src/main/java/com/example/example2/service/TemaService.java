package com.example.example2.service;

import com.example.example2.exceptions.NotFoundException;
import com.example.example2.model.Foro;
import com.example.example2.model.Tema;
import com.example.example2.model.Comentario;
import com.example.example2.model.ForoRepository;
import com.example.example2.model.TemaRepository;
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
 * TemaService
 */
@RestController
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
        ArrayList<Comentario> comentarios = new ArrayList<>(repository.findById(temasId).get().getComentarios());
        ArrayList<Comentario> comentariosR = new ArrayList<Comentario>();

        for (int i = 0; i < comentarios.size(); i++) {
          if (comentarios.get(i).getIdRespuesta() == null) {
            comentariosR.add(comentarios.get(i));
          }
        }
        Collections.sort(comentariosR, new Comparator() {
	          @Override
	          public int compare(Object c1, Object c2) {
                Comentario co1 = (Comentario) c1;
                Comentario co2 = (Comentario) c2;
                return new Integer(co2.getRanking()).compareTo(new Integer(co1.getRanking()));
	          }
        });
        return comentariosR;
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

    @RequestMapping(value = "/moderator/temas/{id}", method = RequestMethod.PUT)
    Tema aprobarTema(@PathVariable Long id, @RequestBody Tema temaData) {
        if(temaData.isAprobado()) {
            Tema tema = findTema(id);
            tema.setAprobado(temaData.isAprobado());
            return repository.save(tema);
        }
        else {
            deleteTema(id);
        }
        return null;
    }

    @PutMapping("/temas/{id}")
    Tema updateTema(@PathVariable Long id, @RequestBody Tema temaData) {
        Tema tema = findTema(id);
        tema.setTitulo(temaData.getTitulo());
        tema.setDescripcion(temaData.getDescripcion());
        tema.setRanking(temaData.getRanking());
        return repository.save(tema);
    }
}
