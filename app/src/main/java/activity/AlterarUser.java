package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admprofissional.R;
import com.example.admprofissional.ui.listarProfi.ListarProfiFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import model.Usuario;

public class AlterarUser extends AppCompatActivity {

    private EditText altNomeCompletoUser, altEmailUser, altSenhaUser, altFoneUser;
    private Button btnAlterarUser;
    private FirebaseAuth auntenticacao;
    private DocumentReference db;
    private FirebaseFirestore fStore;
    private Usuario usuario;
    private FirebaseUser user;
    Intent data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_user);
        inicializarComponentes();
        auntenticacao = FirebaseAuth.getInstance();

        usuario = (Usuario) getIntent().getSerializableExtra("usuarios");
        data = getIntent();

        btnAlterarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCompleto = altNomeCompletoUser.getText().toString();

                if(altNomeCompletoUser.getText().toString().isEmpty() || altEmailUser.getText().toString().isEmpty() || altSenhaUser.getText().toString().isEmpty()) {
                    Toast.makeText(AlterarUser.this, "Um ou mais campos estão vazios", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = altEmailUser.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        CollectionReference doc = db.collection("usuarios");
                        DocumentReference docRef = fStore.collection("usuarios").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("nome", altNomeCompletoUser.getText().toString());
                        edited.put("email", altEmailUser);
                        edited.put("fone",altFoneUser.getText().toString());
                        edited.put("senha", altSenhaUser.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CollectionReference doc = db.collection("usuarios");
                                DocumentReference docRef = fStore.collection("usuarios").document(user.getUid());
                                Map<String,Object> edited = new HashMap<>();
                                edited.put("nome", altNomeCompletoUser.getText().toString());
                                edited.put("email", altEmailUser);
                                edited.put("fone",altFoneUser.getText().toString());
                                edited.put("senha", altSenhaUser.getText().toString());
                                Toast.makeText(AlterarUser.this,"Campo(s) alterado(s)",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListarProfiFragment.class));
                                finish();

                            }
                        });
                        Toast.makeText(AlterarUser.this,"Email alterado com sucesso",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AlterarUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        }

    private void inicializarComponentes(){
        altNomeCompletoUser = findViewById(R.id.altNomeCompletoUser);
        //altEmailUser = findViewById(R.id.altEmailUser);
        //altSenhaUser = findViewById(R.id.altEmailUser);
       // altFoneUser = findViewById(R.id.altFoneUser);
       // btnAlterarUser = findViewById(R.id.btnAlterarUser);
    }

}