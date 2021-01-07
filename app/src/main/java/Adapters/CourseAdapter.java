package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.R;

import java.util.ArrayList;

import courses.course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    ArrayList<course> cs = new ArrayList<>();
    public CourseAdapter(ArrayList<course> cs){
        this.cs = cs;
    }
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_courses_one,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        course cn = cs.get(position);
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
        public CourseViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.c_name);
            level = itemView.findViewById(R.id.c_level);
            batch = itemView.findViewById(R.id.c_batch);
            desc = itemView.findViewById(R.id.c_desc);
        }
    }
}
