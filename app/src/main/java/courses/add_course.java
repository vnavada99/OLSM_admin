package courses;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olsm_admin.MainActivity;
import com.example.olsm_admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Instructor.instructor;

public class add_course extends AppCompatActivity {
    List<String> list;

    FirebaseStorage storage;
    StorageReference storageReference;

    private RadioGroup radioGroup;
    private final int PICK_IMAGE_REQUEST = 22;
    private DatabaseReference mDatabase;
    private Spinner sp;
    private Button bt,up_img;
    private Uri filePath;
    EditText c_name,c_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        up_img = findViewById(R.id.upload_img);
        bt = findViewById(R.id.addC);
        c_desc = findViewById(R.id.c_desc);
        c_name = findViewById(R.id.c_name);
        radioGroup = findViewById(R.id.radio);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        up_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,desc,sp_sel;
                name = c_name.getText().toString();
                desc = c_desc.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(name.isEmpty()) {
                    c_name.setError("Enter Course name");
                    c_name.requestFocus();
                }
                else if(desc.isEmpty()) {
                    c_desc.setError("Enter Course Description");
                    c_desc.requestFocus();
                }
                else if(selectedId == -1){
                    radioGroup.requestFocus();
                }
                else if(filePath == null){
                    up_img.requestFocus();
                }
                else {
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    sp_sel = radioButton.getText().toString();
                    String imgRef = uploadImage();
                    course cs = new course(UUID.randomUUID().toString(),name,sp_sel,desc,"nil",imgRef);
                    boolean s = create_new_course(cs);
                    startActivity(new Intent(add_course.this, MainActivity.class));
                }
            }
        });
    }
    private boolean create_new_course(course cs){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OLSM").child("courses");
        mDatabase.push().setValue(cs);
        Toast.makeText(add_course.this,"Added Successfully",Toast.LENGTH_LONG).show();
        return true;
    }
    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."),PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContentResolver(), filePath);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String uploadImage()
    {
        String imgRef = UUID.randomUUID().toString();
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + imgRef);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(add_course.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(add_course.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Creating new Course and Uploading Image " + (int)progress + "%");
                                }
                    });
        }
        return imgRef;
    }
}