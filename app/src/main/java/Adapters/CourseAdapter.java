package Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.R;
import com.example.olsm_admin.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import courses.course;
import courses.scrolling_view_course;
import courses.view_course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    ArrayList<course> cs = new ArrayList<>();
    FirebaseStorage storage;
    private Context context;
    public CourseAdapter(ArrayList<course> cs,Context context){
        this.cs = cs;
        storage = FirebaseStorage.getInstance();
        this.context = context;
    }
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_courses_one,parent,false);
        CourseViewHolder v = new CourseViewHolder(view);

        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,v.name.getText().toString(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, scrolling_view_course.class);
                i.putExtra("c_id",v.id.getText().toString());
                context.startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        course cn = cs.get(position);
        StorageReference imageRef = storage.getReference().child("images").child(cn.getC_img());
        imageRef.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        holder.imageView.setImageBitmap(bitmap);
                    }
                });
        holder.name.setText(cn.getC_name());
        holder.desc.setText(cn.getC_desc());
        holder.level.setText(cn.getC_level());
        holder.batch.setText(cn.getC_batch());
        holder.id.setText(cn.getC_id());
    }

    @Override
    public int getItemCount() {
        return cs.size();
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView name,level,batch,desc,id;
        ImageView imageView;
        public CourseViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.c_name);
            level = itemView.findViewById(R.id.c_level);
            batch = itemView.findViewById(R.id.c_batch);
            desc = itemView.findViewById(R.id.c_desc);
            imageView = itemView.findViewById(R.id.imageView2);
            id = itemView.findViewById(R.id.c_id);
        }
    }

}
