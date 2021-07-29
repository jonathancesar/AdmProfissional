package adapter;

import android.media.Image;
import android.widget.ImageView;

public class AdapterPesquisa {

    private String nomeCompleto;
    private String funcao;


    public AdapterPesquisa() {

    }

    public AdapterPesquisa(String nomeCompleto, String funcao) {
        this.nomeCompleto = nomeCompleto;
        this.funcao = funcao;

    }

    public String getFuncao() {
        return funcao;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }
}


