package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admprofissional.MainActivity;
import com.example.admprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import helper.ConfiguracaoFirebase;
import model.Administrador;

public class LoginAdm extends AppCompatActivity {

    private EditText edtEmailAdmin, edtSenhaAdmin;
    private Button btnEntraradmin;
    private Administrador administrador;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_adm);
        inicializarComponentes();
        verificarUsuarioLogado();

        //FAZER LOGIN DO ADMIN
        btnEntraradmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = edtEmailAdmin.getText().toString();
                String txtSenha = edtSenhaAdmin.getText().toString();

                if(!txtEmail.isEmpty()){
                    if(!txtSenha.isEmpty()){
                        administrador = new Administrador();
                        administrador.setEmailAdm(txtEmail);
                        administrador.setSenhaAdm(txtSenha);
                        validarLogin(administrador);

                    }else{
                        Toast.makeText(LoginAdm.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginAdm.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void validarLogin(Administrador administrador){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                administrador.getEmailAdm(),
                administrador.getSenhaAdm()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();;
                }else{
                    Toast.makeText(LoginAdm.this,
                            "Erro ao fazer login!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    //VERIFICAR USUÁRIO LOGADO
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
    }

    private void inicializarComponentes(){
        edtEmailAdmin = findViewById(R.id.edtEmailAdmin);
        edtSenhaAdmin = findViewById(R.id.edtSenhaAdmin);
        btnEntraradmin = findViewById(R.id.btnEntraradmin);

        edtEmailAdmin.requestFocus();

    }


    public void abrirCadastro(View view){
        Intent i = new Intent(LoginAdm.this, CadastroAdmin.class);
        startActivity( i );
    }

}