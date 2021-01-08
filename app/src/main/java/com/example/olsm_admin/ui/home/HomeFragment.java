package com.example.olsm_admin.ui.home;

import android.content.Intent;
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

import com.example.olsm_admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Adapters.CourseAdapter;
import Adapters.ProgrammingAdapter;
import Instructor.instructor;
import courses.add_course;
import courses.course;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private DatabaseReference mDatabase;
    private ArrayList<course> in = new ArrayList<>();

    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), add_course.class));
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("courses");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for(DataSnapshot ite : snapshot.getChildren()){
                    String name,level,desc,batch,id,img;
                    id = ite.getKey().toString();
                    name = ite.child("c_name").getValue().toString();
                    desc = ite.child("c_desc").getValue().toString();
                    level = ite.child("c_level").getValue().toString();
                    img = ite.child("c_img").getValue().toString();
                    batch = ite.child("c_batch").getValue().toString();
                    course ip = new course(id,name,level,desc,batch,img);
                    in.add(ip);
                }
                //Toast.makeText(getActivity(),in.toString(),Toast.LENGTH_LONG).show();
                RecyclerView rec = root.findViewById(R.id.c_recycler);
                rec.setAdapter(new CourseAdapter(in));
                rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}