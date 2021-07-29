package helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import model.Profissional;

public class ProfissionalFirebase {

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth profisisonal = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return profisisonal.getCurrentUser();

    }

    public static Profissional getDadosUsuarioLogado(){

        FirebaseUser firebaseUser = getUsuarioAtual();
        Profissional profissional = new Profissional();
        profissional.setEmailProfi(firebaseUser.getEmail());
        profissional.setNomeProfi(firebaseUser.getDisplayName());
        profissional.setFuncao(firebaseUser.getDisplayName());
        profissional.setId(firebaseUser.getUid());

        return profissional;

    }






}
