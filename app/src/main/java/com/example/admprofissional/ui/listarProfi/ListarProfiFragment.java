package com.example.admprofissional.ui.listarProfi;

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

import activity.MyadapterProfi;
import model.Profissional;

public class ListarProfiFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    //FirebaseFirestore fStore;
    RecyclerView recview;
    MyadapterProfi adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_profissional, container, false);

        firebaseFirestore = firebaseFirestore.getInstance();


        //recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview =(RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
              .getReference()
              .child("Profissional")
              .limitToLast(50);

        FirebaseRecyclerOptions<Profissional> options =
                new FirebaseRecyclerOptions.Builder<Profissional>()
                      .setQuery(query,Profissional.class)
                .build();
        //Log.i("Profiiiii","profiiiiii" + recview);
        //Log.d( ">>prof<<", "kkkk"+ options);


        adapter = new MyadapterProfi(options);
        adapter.startListening();
        recview.setAdapter(adapter);
        adapter.notifyItemChanged(1,adapter);



        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recview.setHasFixedSize(true);
        recview =(RecyclerView) view.findViewById(R.id.recview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recview.setLayoutManager(llm);
        adapter.startListening();
        adapter.notifyItemChanged(0,adapter.getItemCount());


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }
}


