package model;

import com.google.firebase.database.DatabaseReference;

import helper.ConfiguracaoFirebase;

public class Administrador {

    private String id;
    private String EmailAdm;
    private String senhaAdm;

    public Administrador() {
    }

    public void salvar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference profissionalRef = firebaseRef.child("Administrador").child(getId());
        profissionalRef.setValue(this);
    }

    private Administrador(String Id, String emailadm, String senhaAdm){
        this.id = Id;
        this.EmailAdm = emailadm;
        this.senhaAdm = senhaAdm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAdm() {
        return EmailAdm;
    }

    public void setEmailAdm(String emailAdm) {
        EmailAdm = emailAdm;
    }

    public String getSenhaAdm() {
        return senhaAdm;
    }

    public void setSenhaAdm(String senhaAdm) {
        this.senhaAdm = senhaAdm;
    }
}


