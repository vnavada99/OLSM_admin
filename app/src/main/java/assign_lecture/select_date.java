package assign_lecture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olsm_admin.R;

import Instructor.select_instructor;

public class select_date extends AppCompatActivity {
    private DatePicker dp;
    private TextView sel_date_c_name;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        sel_date_c_name = findViewById(R.id.sel_date_c_name);

        Intent i = getIntent();
        String c = i.getStringExtra("c_id");
        String c_name = i.getStringExtra("c_name");
        Toast.makeText(select_date.this,c_name,Toast.LENGTH_LONG).show();
        sel_date_c_name.setText("Select date for scheduling Lecture for "+c_name+" Course.");

        dp = findViewById(R.id.editTextDate);
        sel_date_c_name = findViewById(R.id.sel_date_c_name);
        bt = findViewById(R.id.schedule_lec);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mon = dp.getMonth()+1;
                int day = dp.getDayOfMonth();
                int year = dp.getYear();
                Intent inte = new Intent(select_date.this, select_instructor.class);
                inte.putExtra("c_id",c);
                inte.putExtra("date",String.valueOf(day)+"/"+String.valueOf(mon)+"/"+String.valueOf(year));
                inte.putExtra("c_name",c_name);
                startActivity(inte);
                //Toast.makeText(select_date.this,String.valueOf(day)+"/"+String.valueOf(mon)+"/"+String.valueOf(year),Toast.LENGTH_LONG).show();
            }
        });

    }
}