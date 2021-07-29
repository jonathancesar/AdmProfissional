package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admprofissional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

import helper.ConfiguracaoFirebase;
import model.Administrador;

public class CadastroAdmin extends AppCompatActivity {

    private EditText edtEmailAdmin,edtSenhaAdmin;
    private Button btnCadastrar;
    private Administrador administrador;
    private FirebaseAuth autenticacao;
    private FirebaseFirestore fStore;
    RecyclerView recview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_admin);
        inicializarComponentes();
        autenticacao = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        //CADASTRAR USUÁRIO
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = edtEmailAdmin.getText().toString();
                String txtSenha = edtSenhaAdmin.getText().toString();


                if (!txtEmail.isEmpty()) {
                    if (!txtSenha.isEmpty()) {


                        administrador = new Administrador();
                        administrador.setEmailAdm(txtEmail);
                        administrador.setSenhaAdm(txtSenha);
                        cadastrar(administrador);

                    } else {
                        Toast.makeText(CadastroAdmin.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroAdmin.this,
                            "Preencha o E-mail!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void inicializarComponentes() {
        edtEmailAdmin = findViewById(R.id.edtEmailAdmin);
        edtSenhaAdmin = findViewById(R.id.edtSenhaAdmin);
        btnCadastrar = findViewById(R.id.btnCadastrarAdmin);
        edtEmailAdmin.requestFocus();


    }

    //MÉTODO PARA CADASTRAR USUÁRIO

    public void cadastrar(Administrador administrador){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                administrador.getEmailAdm(),
                administrador.getSenhaAdm()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {

                        //Salvar dados no firebase e FS
                        FirebaseUser user = autenticacao.getCurrentUser();
                        DocumentReference df = fStore.collection("Administrador").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("Email", edtEmailAdmin.getText().toString());
                        userInfo.put("Senha", edtSenhaAdmin.getText().toString());
                        userInfo.put("Administrador", "3");
                        df.set(userInfo);
                        String idUsario = task.getResult().getUser().getUid();
                        administrador.setId(idUsario);
                        administrador.salvar();
                        Toast.makeText(CadastroAdmin.this,
                                "Cadastro realizado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginAdm.class)); //Mundar aqui para LoginAdmin
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                        String erroExcecao = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erroExcecao = "Digite uma senha mais forte!";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erroExcecao = "Por favor, digite um e-mail válido";
                        } catch (FirebaseAuthUserCollisionException e) {
                            erroExcecao = "Esta conta já foi cadastrada";
                        } catch (Exception e) {
                            erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                            e.printStackTrace();
                        }

                        Toast.makeText(CadastroAdmin.this,
                                "Erro: " + erroExcecao,
                                Toast.LENGTH_SHORT).show();

                }


            }
        });




    }




}