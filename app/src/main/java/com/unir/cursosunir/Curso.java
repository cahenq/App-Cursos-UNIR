package com.unir.cursosunir;

public class Curso {

    private String nome;
    private String campus;
    private String grau;
    private String turno;
    private String descricao;
    private String imagem;
    private String site;

    public Curso(String nome,
                 String campus,
                 String grau,
                 String turno,
                 String descricao,
                 String imagem,
                 String site) {

        this.nome = nome;
        this.campus = campus;
        this.grau = grau;
        this.turno = turno;
        this.descricao = descricao;
        this.imagem = imagem;
        this.site = site;
    }

    public String getNome() {
        return nome;
    }

    public String getCampus() {
        return campus;
    }

    public String getGrau() {
        return grau;
    }

    public String getTurno() {
        return turno;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public String getSite() {
        return site;
    }
}
