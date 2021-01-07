package com.example.olsm_admin.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.CustomAdapter;
import com.example.olsm_admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Adapters.ProgrammingAdapter;
import Instructor.instructor;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private DatabaseReference mDatabase;

    private ArrayList<instructor> in = new ArrayList<>();
    private TextView fnam;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Add Instructor from here",Toast.LENGTH_LONG).show();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("Instructor");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for(DataSnapshot ite : snapshot.getChildren()){
                    String name,expertise,id,age;
                    id = ite.getKey().toString();
                    age = ite.child("age").getValue().toString();
                    name = ite.child("name").getValue().toString();
                    expertise = ite.child("expertise").getValue().toString();
                    instructor ip = new instructor(name,id,age,expertise);
                    in.add(ip);
                }
                //Toast.makeText(getActivity(),in.toString(),Toast.LENGTH_LONG).show();
                RecyclerView rec = root.findViewById(R.id.recyc_view);
                rec.setAdapter(new ProgrammingAdapter(in));
                rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return root;
    }
}


