package Instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olsm_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class select_instructor extends AppCompatActivity {
    private TextView i_id;

    ArrayList<instructor> ar = new ArrayList<>();
    public ArrayList<String> x = new ArrayList<>();
    public ArrayList<String> ins = new ArrayList<>();
    StringBuffer sb = new StringBuffer();
    Spinner sp;

    DatabaseReference mDatabase,iDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_instructor);
        i_id = findViewById(R.id.i_id);
        sp = findViewById(R.id.spinner);

        Intent i = getIntent();
        String c_id = i.getStringExtra("c_id");
        String date = i.getStringExtra("date");
        String c_name = i.getStringExtra("c_name");

        i_id.setText("Select Instructor for Scheduling Lecture of "+c_name+" Course on date "+date+" from the available Instructors.");

        getinstructor(date);
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