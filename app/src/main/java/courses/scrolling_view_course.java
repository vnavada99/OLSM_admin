package courses;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olsm_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class scrolling_view_course extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView c_name,c_desc,c_level,c_batch,c_id;
    ImageView c_img;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_view_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        c_name = findViewById(R.id.c_name);
        c_desc = findViewById(R.id.c_desc);
        c_id = findViewById(R.id.c_id);
        c_batch = findViewById(R.id.c_batch);
        c_level = findViewById(R.id.c_level);
        c_img = findViewById(R.id.c_img);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent i = getIntent();
        String id = i.getStringExtra("c_id");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("courses");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                //Toast.makeText(scrolling_view_course.this,snapshot.child(id).child("c_img").getValue().toString(),Toast.LENGTH_LONG).show();
                setImage(snapshot.child(id).child("c_img").getValue().toString());
                toolBarLayout.setTitle(snapshot.child(id).child("c_name").getValue().toString());
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

    private void setImage(String img_loc) {
        storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("images/").child(img_loc);
        imageRef.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        c_img.setImageBitmap(bitmap);
                    }
                });
    }
}