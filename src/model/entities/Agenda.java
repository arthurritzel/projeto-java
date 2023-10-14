package model.entities;

import java.io.Serializable;

public class Agenda implements Serializable {
    private int id;
    private String dia;
    private String hora;
    private boolean ok;
    private int id_usuario;

    public Agenda(){

    }

    public Agenda(int id, String dia, String hora, boolean ok, int id_usuario) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.ok = ok;
        this.id_usuario = id_usuario;
    }

    public int getId(int i) {
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

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "| Id=" + id +
                "| Dia='" + dia + '\'' +
                "| Hora='" + hora + '\'' +
                "| ok='" + ok+ '\'' +
                "| Id_usuario=" + id_usuario;
    }


}
