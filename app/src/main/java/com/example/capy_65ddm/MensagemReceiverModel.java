package com.example.capy_65ddm;

public class MensagemReceiverModel {
    private String nome;
    private String mensagem;
    private String img;
    private String likes;
    private String id;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public MensagemReceiverModel(String nome, String mensagem, String likes, String id) {
        this.nome = nome;
        this.mensagem = mensagem;
        this.likes = likes;
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "MensagemReceiverModel{" +
                "nome='" + nome + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
