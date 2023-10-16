package model.entities;

import java.io.Serializable;

public class Agendamento implements Serializable {
    private int id;
    private String dia;
    private String hora;
    private boolean feito;
    private Usuario usuario;

    public Agendamento(){

    }

    public Agendamento(int id, String dia, String hora, boolean ok, Usuario usuario) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.feito = ok;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "| Id=" + id +
                "| Dia='" + dia + '\'' +
                "| Hora='" + hora + '\'' +
                "| ok='" + feito + '\'' +
                "| usuario=" + usuario.getNome();
    }


}
