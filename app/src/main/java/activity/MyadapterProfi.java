package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import model.Profissional;

public class MyadapterProfi extends FirebaseRecyclerAdapter<Profissional, MyadapterProfi.myviewholder> {
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private MyadapterProfi myadapterProfi;


    public MyadapterProfi(@NonNull FirebaseRecyclerOptions<Profissional> options){
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Profissional model) {

        holder.txtNomeProfi.setText(model.getNomeProfi());
        holder.txtFuncao.setText(model.getFuncao());


       // Log.i("TESTE3","TESTE3" + Profissional.class);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txtNomeProfi.getContext())
                      .setContentHolder(new ViewHolder(R.layout.activity_alterar_profi))
                      .setExpanded(true,1500)
                      .create();
                notifyDataSetChanged();

                fStore = FirebaseFirestore.getInstance();

                View myview=dialogPlus.getHolderView();
                final EditText name=myview.findViewById(R.id.altNomeCompletoProfi);
                final EditText fun=myview.findViewById(R.id.altFuncao);
                final EditText ape=myview.findViewById(R.id.altApelidoProfi);
                final EditText emai=myview.findViewById(R.id.altEmailProfi);
                final EditText sen=myview.findViewById(R.id.altSenhaProfi);
                final EditText fon=myview.findViewById(R.id.altFoneProfi);
                final EditText descr=myview.findViewById(R.id.altDescricao);


                Button alt=myview.findViewById(R.id.altbtnAlterar);
                name.setText(model.getNomeProfi());
                fun.setText(model.getFuncao());
                ape.setText(model.getApelidoProfi());
                emai.setText(model.getEmailProfi());
                sen.setText(model.getSenhaProfi());
                fon.setText(model.getFoneProfi());
                descr.setText(model.getDescricao());

                dialogPlus.show();

                alt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Map<String,Object> map=new HashMap<>();
                        map.put("NomeCompleto",name.getText().toString());
                        map.put("funcao",fun.getText().toString());
                        map.put("apelido",ape.getText().toString());
                        map.put("emailProfi",emai.getText().toString());
                        map.put("senhaProfi",sen.getText().toString());
                        map.put("descricao",descr.getText().toString());




                        notifyItemChanged(holder.getAdapterPosition());
                        FirebaseDatabase.getInstance().getReference().child("Profissional")
                              .child(getRef(position).getKey()).updateChildren(map)
                              .addOnSuccessListener(new OnSuccessListener<Void>() {

                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      notifyItemChanged(holder.getAdapterPosition());
                                      dialogPlus.dismiss();
                                      notifyItemChanged(holder.getAdapterPosition());

                                  }
                              })
                              .addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      dialogPlus.dismiss();
                                      notifyItemChanged(holder.getAdapterPosition());
                                  }

                              });
                    }
                });

            }

        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtNomeProfi.getContext());
                //builder.setTitle("Deseja excluir o usuário");
                builder.setMessage("Deseja excluir o profissional?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Profissional")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                notifyDataSetChanged();
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });




    }//final onBindView



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);

    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView txtNomeProfi,txtFuncao;
        ImageView edit,delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            txtNomeProfi = (TextView) itemView.findViewById(R.id.txtNomeProfi);
            txtFuncao = (TextView) itemView.findViewById(R.id.txtFuncao);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }


}
