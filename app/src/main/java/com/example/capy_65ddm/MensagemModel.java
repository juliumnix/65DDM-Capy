package com.example.capy_65ddm;


import java.sql.Timestamp;
import java.util.Date;

public class MensagemModel {
    private String usuarioEnviou;
    private String usuarioRecebeu;
    private String mensagem;
    private Long dateObjetc;

    public MensagemModel(String usuarioEnviou, String usuarioRecebeu,String mensagem, Long dateObjetc){
        this.mensagem = mensagem;
        this.dateObjetc = dateObjetc;
        this.usuarioEnviou = usuarioEnviou;
        this.usuarioRecebeu = usuarioRecebeu;
    }

    public Long getDateObjetc() {
        return dateObjetc;
    }

    public void setDateObjetc(Long dateObjetc) {
        this.dateObjetc = dateObjetc;
    }

    public String getUsuarioEnviou() {
        return usuarioEnviou;
    }

    public void setUsuarioEnviou(String usuarioEnviou) {
        this.usuarioEnviou = usuarioEnviou;
    }

    public String getUsuarioRecebeu() {
        return usuarioRecebeu;
    }

    public void setUsuarioRecebeu(String usuarioRecebeu) {
        this.usuarioRecebeu = usuarioRecebeu;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "MensagemModel{" +
                "usuarioEnviou='" + usuarioEnviou + '\'' +
                ", usuarioRecebeu='" + usuarioRecebeu + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", dateObjetc=" + dateObjetc +
                '}';
    }
}
