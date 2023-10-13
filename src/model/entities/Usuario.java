package model.entities;


import java.io.Serializable;
import java.util.Date;
public class Usuario implements Serializable{
    private int id;
    private String nome;
    private String cpf;
    private Empresa empresa;



    public Usuario(){

    }
    public Usuario(int id, String nome, Empresa empresa) {
        this.id = id;
        this.nome = nome;
        this.empresa = empresa;

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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return
                "| Id=" + id +
                "| Nome='" + nome + '\'' +
                "| Cpf='" + cpf + '\'' +
                "| Nome empresa=" + empresa.getNome();
    }
}
