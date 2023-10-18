package model.entities;

public class Respostas {
    private int id;
    private String texto;
    private Boolean correta;
    private Perguntas pergunta;

    public Respostas() {
    }

    public Respostas(int id, String resposta, Boolean correta, Perguntas pergunta) {
        this.id = id;
        this.texto = resposta;
        this.correta = correta;
        this.pergunta = pergunta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    public Perguntas getPergunta() {
        return pergunta;
    }

    public void setPergunta(Perguntas pergunta) {
        this.pergunta = pergunta;
    }

    @Override
    public String toString() {
        return "Respostas{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", correta=" + correta +
                ", pergunta=" + pergunta.getCabecalho() +
                '}';
    }
}
