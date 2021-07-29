package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import model.Profissional;

public class AlterarProfi extends AppCompatActivity {

    private EditText altNomeCompletoProfi, altApelidoProfi,altEmailProfi, altSenhaProfi, altFoneProfi,  altFuncao, altdescricao;
    private Button btnAlterar;
    private CheckBox altcheckSeg,altcheckTer,altcheckQuar,altcheckQuin,altcheckSex,altcheckSab,altcheckDom;
    private FirebaseAuth auntenticacao;
    private FirebaseFirestore fStore;
    private Profissional profissionalLogado;
    private DocumentReference db;
    private FirebaseUser user;
    private Profissional profissional;
    private DocumentReference documenteReference;
    Intent data;

    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_profi);
        inicializarComponentes();

        //user = auntenticacao.getCurrentUser();
       auntenticacao = FirebaseAuth.getInstance();
       fStore = FirebaseFirestore.getInstance();

        profissional = (Profissional) getIntent().getSerializableExtra("Profissional");
        data = getIntent();
       // String nomeCompleto = data.getStringExtra("NomeCompleto");
       // String email = data.getStringExtra("Email");
       // String funcao = data.getStringExtra("Funcao");

       // altNomeCompletoProfi.setText(nomeCompleto);
       // altEmailProfi.setText(email);
       // altFuncao.setText(funcao);
       // Log.d(TAG,"Dados" + nomeCompleto + "" + email+ "" + funcao);




        //Salvar as alterações
        btnAlterar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String nCompleto = altNomeCompletoProfi.getText().toString();



                if(altNomeCompletoProfi.getText().toString().isEmpty() || altEmailProfi.getText().toString().isEmpty() || altFuncao.getText().toString().isEmpty()){
                    Toast.makeText(AlterarProfi.this,"Um ou mais campos estão vazios",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = altEmailProfi.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        CollectionReference doc = db.collection("Profissional");
                        DocumentReference docRef = fStore.collection("Profissional").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("NomeCompleto", altNomeCompletoProfi.getText().toString());
                        edited.put("Email",email);
                        edited.put("Funcao",altFuncao.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CollectionReference doc = db.collection("Profissional");
                                DocumentReference docRef = fStore.collection("Profissional").document(user.getUid());
                                Map<String,Object> edited = new HashMap<>();
                                edited.put("NomeCompleto", altNomeCompletoProfi.getText().toString());
                                edited.put("Email",email);
                                edited.put("Funcao",altFuncao.getText().toString());
                                Toast.makeText(AlterarProfi.this,"Campo(s) alterado(s)",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ListarProfiFragment.class));
                                finish();
                            }
                        });
                        Toast.makeText(AlterarProfi.this,"Email alterado com sucesso",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AlterarProfi.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


    }

    private void inicializarComponentes(){
        altNomeCompletoProfi = findViewById(R.id.altNomeCompletoProfi);
        altApelidoProfi= findViewById(R.id.altApelidoProfi);
        altEmailProfi =  findViewById(R.id.altEmailProfi);
        altSenhaProfi = findViewById(R.id.altSenhaProfi);
        altFoneProfi = findViewById(R.id.altFoneProfi);
        altdescricao = findViewById(R.id.altDescricao);
        altFuncao = findViewById(R.id.altFuncao);
        btnAlterar = findViewById(R.id.altbtnAlterar);
        altcheckSeg = findViewById(R.id.altcheckSeg);
        altcheckTer = findViewById(R.id.altcheckTer);
        altcheckQuar = findViewById(R.id.altcheckQuar);
        altcheckQuin = findViewById(R.id.altcheckQuin);
        altcheckSex = findViewById(R.id.altcheckSex);
        altcheckSab = findViewById(R.id.altcheckSab);
        altcheckDom = findViewById(R.id.altcheckDom);

    }
}