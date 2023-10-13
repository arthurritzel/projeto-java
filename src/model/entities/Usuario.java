package model.entities;


import java.io.Serializable;
import java.util.Date;
public class Usuario implements Serializable{
    private int id;
    private String nome;
    private String cpf;
    private int id_empresa;



    public Usuario(){

    }
    public Usuario(int id, String nome, int id_empresa) {
        this.id = id;
        this.nome = nome;
        this.id_empresa = id_empresa;

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }



    @Override
    public String toString() {
        return
                "| Id=" + id +
                "| Nome='" + nome + '\'' +
                "| Cpf='" + cpf + '\'' +
                "| Id_empresa=" + id_empresa;
    }
}
