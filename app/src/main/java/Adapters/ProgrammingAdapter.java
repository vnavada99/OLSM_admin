package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.R;

import java.util.ArrayList;

import Instructor.instructor;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ProgrammingViewHolder> {
    private ArrayList<instructor> ins;
    public ProgrammingAdapter(ArrayList<instructor> ins){
        this.ins = ins;
    }
    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_instructor_view,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {
        instructor in = ins.get(position);
        holder.name.setText(in.getName());
        holder.id.setText(in.getId());
        holder.expertise.setText(in.getExpertise());
        holder.age.setText(in.getAge());
    }

    @Override
    public int getItemCount() {
        return ins.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder{
        TextView name,id,age,expertise;
        public ProgrammingViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id_num);
            expertise = itemView.findViewById(R.id.expertise);
            age = itemView.findViewById(R.id.age);
        }
    }
}
