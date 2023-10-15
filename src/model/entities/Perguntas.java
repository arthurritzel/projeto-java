package model.entities;

public class Perguntas {
    private int id;
    private String cabecalho;
    private String tipo_prova;
    private int nivel;


    public Perguntas(int id, String cabecalho, String tipo_prova, int nivel) {
        this.id = id;
        this.cabecalho = cabecalho;
        this.tipo_prova = tipo_prova;
        this.nivel = nivel;
    }

    public Perguntas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public String getTipo_prova() {
        return tipo_prova;
    }

    public void setTipo_prova(String tipo_prova) {
        this.tipo_prova = tipo_prova;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Perguntas{" +
                "id=" + id +
                ", cabecalho='" + cabecalho + '\'' +
                ", tipo_prova='" + tipo_prova + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
