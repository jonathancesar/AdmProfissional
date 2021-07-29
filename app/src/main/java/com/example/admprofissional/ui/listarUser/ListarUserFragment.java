package com.example.admprofissional.ui.listarUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admprofissional.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import adapter.MyadapterUser;
import model.Usuario;

public class ListarUserFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recviewUser;
    MyadapterUser adapterUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listar_usuario, container, false);
        firebaseFirestore = firebaseFirestore.getInstance();
        recviewUser = (RecyclerView) view.findViewById(R.id.recviewUser);
        recviewUser.setLayoutManager(new LinearLayoutManager(getActivity()));


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("usuarios")
                .limitToLast(50);

        FirebaseRecyclerOptions<Usuario> options =
                new FirebaseRecyclerOptions.Builder<Usuario>()
                .setQuery(query,Usuario.class)
                .build();

       adapterUser = new MyadapterUser(options);
        adapterUser.startListening();
       recviewUser.setAdapter(adapterUser);
       adapterUser.notifyItemChanged(1,adapterUser);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recviewUser.setHasFixedSize(true);
        recviewUser =(RecyclerView) view.findViewById(R.id.recview);
       LinearLayoutManager lllm = new LinearLayoutManager(getActivity());
        lllm.setOrientation(LinearLayoutManager.VERTICAL);
       //recviewUser.setLayoutManager(lllm);
        adapterUser.startListening();
       adapterUser.notifyItemChanged(0,adapterUser.getItemCount());


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
       adapterUser.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
       adapterUser.stopListening();

    }
}
