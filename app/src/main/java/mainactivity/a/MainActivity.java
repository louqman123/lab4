package mainactivity.a;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Switch urgentSwitch;
    private Button addButton; // Fix: Declare addButton here
    private List<TodoItem> todoItems;
    private CustomAdapter adapter; // Fix: Remove package prefix

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addButton = findViewById(R.id.addButton); // Fix: Initialize addButton

        ListView listView = findViewById(R.id.listView);

        todoItems = new ArrayList<>();

        adapter = new CustomAdapter(this, todoItems);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = editText.getText().toString();
                boolean isUrgent = urgentSwitch.isChecked();

                TodoItem newTodoItem = new TodoItem(taskText, isUrgent);
                todoItems.add(newTodoItem);
                adapter.notifyDataSetChanged();

                editText.setText("");
                urgentSwitch.setChecked(false);

                Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
                return true;
            }
        });
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete this?");
        builder.setMessage("The selected row is: " + position);
        builder.setPositiveButton("Delete", (dialog, which) -> {
            todoItems.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
