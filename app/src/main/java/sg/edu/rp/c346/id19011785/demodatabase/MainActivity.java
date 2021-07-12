package sg.edu.rp.c346.id19011785.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTask, etDate;
    Button btnInsert, btnGetTasks;
    TextView tvResults;

    ListView lvTasks;
    ArrayList<Task> alTasks;
    ArrayAdapter<Task> aaTasks;

    boolean asc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.editTasks);
        etDate = findViewById(R.id.editDate);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);

        lvTasks = findViewById(R.id.lvTasks);
        alTasks = new ArrayList<>();
        aaTasks = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alTasks);
        lvTasks.setAdapter(aaTasks);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Worksheet Section J
                String nTask = etTask.getText().toString();
                String nDate = etDate.getText().toString();

                // Insert a task
                db.insertTask(nTask, nDate);
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                ArrayList<String> data = db.getTaskContent();
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++){
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);

                // for Section I of Worksheet
                if (asc == false) {
                    alTasks.clear();
                    alTasks.addAll(db.getTasks(asc));
                    aaTasks.notifyDataSetChanged();
                    asc = true;
                }
                else {
                    alTasks.clear();
                    alTasks.addAll(db.getTasks(asc));
                    aaTasks.notifyDataSetChanged();
                    asc = false;
                }

            }
        });

    }
}