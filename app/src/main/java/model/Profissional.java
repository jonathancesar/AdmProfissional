package model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import helper.ConfiguracaoFirebase;

public class Profissional implements Serializable {

    private String id;
    private String nomeProfi;
    private String ApelidoProfi;
    private String emailProfi;
    private String senhaProfi;
    private String foneProfi;
    private String funcao;
    private String descricao;
    private String seg,ter,quar,quin,sex,sab,dom;

    public Profissional() {
    }

    private Profissional(String nomeprofi, String apelidoprofi, String emailprofi, String senhaprofi, String foneprofi, String Funcao, String Descricao, String Seg, String Ter, String Quar, String Quin, String Sex, String Sab, String Dom){
        this.nomeProfi = nomeprofi;
        this.ApelidoProfi = apelidoprofi;
        this.emailProfi = emailprofi;
        this.senhaProfi = senhaprofi;
        this.foneProfi = foneprofi;
        this.funcao = Funcao;
        this.descricao = Descricao;
        this.seg = Seg;
        this.ter = Ter;
        this.quar = Quar;
        this.quin = Quin;
        this.sex = Sex;
        this.sab = Sab;
        this.dom = Dom;
    }

    public void salvar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference profissionalRef = firebaseRef.child("Profissional").child(getId());
        profissionalRef.setValue(this);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProfi() {
        return nomeProfi;
    }

    public void setNomeProfi(String nomeProfi) {
        this.nomeProfi = nomeProfi;
    }

    public String getApelidoProfi() {
        return ApelidoProfi;
    }

    public void setApelidoProfi(String apelidoProfi) {
        ApelidoProfi = apelidoProfi;
    }

    public String getEmailProfi() {
        return emailProfi;
    }

    public void setEmailProfi(String emailProfi) {
        this.emailProfi = emailProfi;
    }

    public String getSenhaProfi() {
        return senhaProfi;
    }

    public void setSenhaProfi(String senhaProfi) {
        this.senhaProfi = senhaProfi;
    }

    public String getFoneProfi() {
        return foneProfi;
    }

    public void setFoneProfi(String foneProfi) {
        this.foneProfi = foneProfi;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSeg() {
        return seg;
    }

    public void setSeg(String seg) {
        this.seg = seg;
    }

    public String getTer() {
        return ter;
    }

    public void setTer(String ter) {
        this.ter = ter;
    }

    public String getQuar() {
        return quar;
    }

    public void setQuar(String quar) {
        this.quar = quar;
    }

    public String getQuin() {
        return quin;
    }

    public void setQuin(String quin) {
        this.quin = quin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSab() {
        return sab;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }
}

