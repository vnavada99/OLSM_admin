package Instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olsm_admin.MainActivity;
import com.example.olsm_admin.R;
import com.example.olsm_admin.ui.gallery.GalleryFragment;
import com.example.olsm_admin.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

import lectures.lecture;

public class select_instructor extends AppCompatActivity {
    private TextView i_id;

    ArrayList<instructor> ar = new ArrayList<>();
    public ArrayList<String> x = new ArrayList<>();
    public ArrayList<String> ins = new ArrayList<>();
    private HashSet<String> hashSet = new HashSet<>();
    StringBuffer sb = new StringBuffer();
    Spinner sp;
    Button bt;

    DatabaseReference mDatabase,iDatabase,lDatabase,iDatabase2,cDatabase,iDatabase3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_instructor);
        i_id = findViewById(R.id.i_id);
        sp = findViewById(R.id.spinner);
        bt = findViewById(R.id.bt);

        Intent i = getIntent();
        String c_id = i.getStringExtra("c_id");
        String date = i.getStringExtra("date");
        String c_name = i.getStringExtra("c_name");

        i_id.setText("Select Instructor for Scheduling Lecture of "+c_name+" Course on date "+date+" from the available Instructors.");

        getinstructor(date);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String men = sp.getSelectedItem().toString();
                iDatabase2 = FirebaseDatabase.getInstance().getReference().child("OLSM").child("Instructor");
                iDatabase2.addValueEventListener(new ValueEventListener() {
                    String i_id = null;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ite : snapshot.getChildren()) {
                            String name = ite.child("name").getValue().toString();
                            if (name.equals(men)) {
                                i_id = ite.getKey().toString();
                            }
                        }
                        lecture lc = new lecture(c_id,i_id,date,c_name,men);
                        //Toast.makeText(select_instructor.this,lc.getI_id().toString(),Toast.LENGTH_LONG).show();
                        lDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("lecture");
                        lDatabase.push().setValue(lc);

                        //iDatabase3 = FirebaseDatabase.getInstance().getReference().child("OLSM").child("Instructor").child(i_id).child("lecture");
                        //iDatabase3.push().setValue(lc);
                        startActivity(new Intent(select_instructor.this, MainActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
                //Toast.makeText(select_instructor.this,men,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getForbiddinInstructor(String date){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("lecture");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot ite : snapshot.getChildren()) {
                    String sp = ite.child("date").getValue().toString();
                    if (!sp.equals(date)) {
                        ins.add(ite.child("i_id").getValue().toString());
                    }
                }
                hashSet.addAll(ins);
                ins.clear();
                ins.addAll(hashSet);
                //Toast.makeText(select_instructor.this,ins.toString(),Toast.LENGTH_LONG).show();
                iDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("Instructor");
                iDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshoti) {
                        //Toast.makeText(select_instructor.this,ins.toString(),Toast.LENGTH_LONG).show();
                        for(int i=0;i<ins.size();i++){
                            x.add(snapshoti.child(ins.get(i)).child("name").getValue().toString());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(select_instructor.this, android.R.layout.simple_spinner_item, x);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp.setAdapter(dataAdapter);
                        //Toast.makeText(select_instructor.this,x.toString(),Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError errori) {

                    }
                });
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void getinstructor(String date) {
        getForbiddinInstructor(date);
       // Toast.makeText(select_instructor.this,"String is="+ins.toString(),Toast.LENGTH_LONG).show();
    }
}