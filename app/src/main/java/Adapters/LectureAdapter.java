package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olsm_admin.R;

import java.util.ArrayList;

import lectures.lecture;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureViewHolder> {
    ArrayList<lecture> lc = new ArrayList<>();
    private Context context;

    public LectureAdapter(ArrayList<lecture> lc,Context context){
        this.lc = lc;
        this.context = context;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_schedule_single,parent,false);
        LectureViewHolder v = new LectureViewHolder(view);
        return v;
    }

    public void onBindViewHolder(@NonNull LectureAdapter.LectureViewHolder holder,int position){
        lecture l = lc.get(position);
        holder.c_name.setText(l.getC_name());
        holder.i_name.setText(l.getI_name());
        holder.date.setText(l.getDate());
    }
    public class LectureViewHolder extends RecyclerView.ViewHolder{
        TextView c_name,i_name,date;
        public LectureViewHolder(View itemView){
            super(itemView);
            c_name = itemView.findViewById(R.id.c_name);
            i_name = itemView.findViewById(R.id.i_name);
            date = itemView.findViewById(R.id.date);
        }
    }

    public int getItemCount(){
        return lc.size();
    }
}
