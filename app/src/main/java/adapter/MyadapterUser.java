package adapter;

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
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import model.Usuario;

public class MyadapterUser extends FirebaseRecyclerAdapter<Usuario,MyadapterUser.myviewholder> {

    public MyadapterUser(@NonNull FirebaseRecyclerOptions<Usuario> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Usuario model ){
        holder.txtNomeUser.setText(model.getNome());
        holder.txtEmailUser.setText(model.getEmail());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txtNomeUser.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_alterar_user))
                        .setExpanded(true,1500)
                        .create();
                notifyDataSetChanged();

                View myview = dialogPlus.getHolderView();
                final EditText nome = myview.findViewById(R.id.altNomeCompletoUser);
                final EditText emai = myview.findViewById(R.id.altEmailUser);
                final EditText sen = myview.findViewById(R.id.altSenhaUser);
                final EditText fon = myview.findViewById(R.id.altFoneUser);

                Button alt = myview.findViewById(R.id.btnAlterarUser);
                nome.setText(model.getNome());
                emai.setText(model.getEmail());
                sen.setText(model.getSenha());
                fon.setText(model.getFone());
                dialogPlus.show();

                alt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("nome",nome.getText().toString());
                        map.put("email",emai.getText().toString());
                        map.put("fone",fon.getText().toString());
                        map.put("senha",sen.getText().toString());

                        notifyItemChanged(holder.getAdapterPosition());
                        FirebaseDatabase.getInstance().getReference().child("usuarios")
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtEmailUser.getContext());
                builder.setMessage("Deseja excluir o usuário?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("usuarios")
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

    }//fim do onbindviewHolder



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_user,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView txtNomeUser, txtEmailUser;
        ImageView edit,delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            txtNomeUser = (TextView) itemView.findViewById(R.id.txtNomeUser);
            txtEmailUser = (TextView) itemView.findViewById(R.id.txtEmailUser);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }
}
