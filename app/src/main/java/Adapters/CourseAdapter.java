package Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import courses.course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    ArrayList<course> cs = new ArrayList<>();
    FirebaseStorage storage;
    public CourseAdapter(ArrayList<course> cs){
        this.cs = cs;
        storage = FirebaseStorage.getInstance();
    }
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_courses_one,parent,false);
        return new CourseViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return cs.size();
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView name,level,batch,desc;
        ImageView imageView;
        public CourseViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.c_name);
            level = itemView.findViewById(R.id.c_level);
            batch = itemView.findViewById(R.id.c_batch);
            desc = itemView.findViewById(R.id.c_desc);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }
}
