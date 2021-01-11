package com.example.olsm_admin.ui.slideshow;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapters.CourseAdapter;
import Adapters.LectureAdapter;
import lectures.lecture;

public class SlideshowFragment extends Fragment {
    ArrayList<lecture> arr = new ArrayList<>();
    DatabaseReference mDatabase;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("lecture");
        mDatabase.addValueEventListener(new ValueEventListener() {
            String c_name,i_name,date,c_id,i_id;
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ite : snapshot.getChildren()){
                    c_name = ite.child("c_name").getValue().toString();
                    i_name = ite.child("i_name").getValue().toString();
                    date = ite.child("date").getValue().toString();
                    c_id = ite.child("c_id").getValue().toString();
                    i_id = ite.child("i_id").getValue().toString();
                    lecture lc = new lecture(c_id,i_id,date,c_name,i_name);
                    arr.add(lc);
                }
                //Toast.makeText(getActivity(),arr.toString(),Toast.LENGTH_LONG).show();
                RecyclerView rec = root.findViewById(R.id.c_recycler);
                rec.setAdapter(new LectureAdapter(arr,getContext()));
                rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}