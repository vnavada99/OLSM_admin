package courses;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olsm_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import Instructor.instructor;

public class add_course extends AppCompatActivity {
    List<String> list;
    private DatabaseReference mDatabase;
    private Spinner sp;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        addInstructorToList();
        addC();
    }
    public void addInstructorToList(){
        list = new ArrayList<String>();
        sp = (Spinner) findViewById(R.id.spinner_ins);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("Instructor");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot ite : snapshot.getChildren()) {
                    String name;
                    name = ite.child("name").getValue().toString();
                    list.add(name);
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
    }
    public void addC(){
        bt = findViewById(R.id.addC);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(add_course.this,sp.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}