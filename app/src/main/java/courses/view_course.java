package courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.olsm_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import Adapters.CourseAdapter;

public class view_course extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private TextView c_name,c_desc,c_level,c_batch,c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        c_name = findViewById(R.id.c_name);
        c_desc = findViewById(R.id.c_desc);
        c_id = findViewById(R.id.c_id);
        c_batch = findViewById(R.id.c_batch);
        c_level = findViewById(R.id.c_level);

        Intent i = getIntent();
        String id = i.getStringExtra("c_id");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("courses");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                c_name.setText(snapshot.child(id).child("c_name").getValue().toString());
                c_desc.setText(snapshot.child(id).child("c_desc").getValue().toString());
                c_id.setText(id);
                c_batch.setText(snapshot.child(id).child("c_batch").getValue().toString());
                c_level.setText(snapshot.child(id).child("c_level").getValue().toString());
                //Toast.makeText(getActivity(),in.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}