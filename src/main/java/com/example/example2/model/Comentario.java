package com.example.example1.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;

@Entity
public class Comentario {
    @Id
    @GeneratedValue
    Long id;

    private String mensaje;
    private Date fecha;
    private Long idRespuesta;

    @ManyToOne
    private Tema tema;

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

    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public void getIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
