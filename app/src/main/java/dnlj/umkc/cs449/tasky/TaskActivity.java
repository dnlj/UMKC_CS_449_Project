package dnlj.umkc.cs449.tasky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TableLayout;

public class TaskActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		// Update toolbar
		Toolbar toolbar = findViewById(R.id.task_toolbar);
		toolbar.setTitle("Task: " + getIntent().getIntExtra("task_id", 0)); // TODO: Load title from DB
		
		// Generate calendar
		GridLayout gridLayout = findViewById(R.id.task_calendar_grid);
		gridLayout.setRowCount(5);
		gridLayout.setColumnCount(7);
		
		for (int i=0; i < 7*5; ++i) {
			AppCompatButton btn = new AppCompatButton(gridLayout.getContext());
			btn.setText(String.valueOf(i));
			
			GridLayout.LayoutParams params = new GridLayout.LayoutParams();
			params.width = 0;
			params.height = 0;
			params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			
			gridLayout.addView(btn, params);
		}
	}
}
