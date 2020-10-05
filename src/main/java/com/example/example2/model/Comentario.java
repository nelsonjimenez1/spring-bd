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
public class Comentario {
    @Id
    @GeneratedValue
    Long id;

    private String mensaje;
    private Date fecha;

    @OneToMany(mappedBy = "idRespuesta")
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Comentario> idRespuestas;

    @ManyToOne
    private Comentario idRespuesta;

    @ManyToOne
    private Tema tema;

    private boolean aprobado;
    private int ranking;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getIdRespuestas() {
        return idRespuestas;
    }

    public void setIdRespuestas(List<Comentario> idRespuestas) {
        this.idRespuestas = idRespuestas;
    }

    public Comentario getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Comentario idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
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
