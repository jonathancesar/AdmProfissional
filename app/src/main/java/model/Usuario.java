package model;

import com.google.firebase.database.DatabaseReference;

import helper.ConfiguracaoFirebase;

public class Usuario {

    private String id;
    private String Nome;
    private String SobreNome;
    private String Email;
    private String Senha;
    private String Fone;


    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getId() );
        usuariosRef.setValue( this );
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getSobreNome() {
        return SobreNome;
    }

    public void setSobreNome(String sobreNome) {
        SobreNome = sobreNome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getFone() {
        return Fone;
    }

    public void setFone(String fone) {
        Fone = fone;
    }
}

