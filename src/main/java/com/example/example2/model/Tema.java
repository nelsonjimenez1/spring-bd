package com.example.example2.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tema {
    @Id
    @GeneratedValue
    Long id;

    @OneToMany(mappedBy = "tema")
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Comentario> comentarios;

    private String titulo;
    private String descripcion;
    private Date fecha;

    @ManyToOne
    private Foro foro;

    private boolean aprobado;
    private int ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
      this.comentarios = comentarios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Foro getForo() {
        return foro;
    }

    public void setForo(Foro foro) {
        this.foro = foro;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
