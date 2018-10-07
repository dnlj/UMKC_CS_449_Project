package dnlj.umkc.cs449.tasky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;

import java.time.LocalDate;

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
		
		LocalDate currentDate = LocalDate.now();
		LocalDate startOfMonth = currentDate.withDayOfMonth(1);
		LocalDate endOfMonth = startOfMonth.plusDays(startOfMonth.lengthOfMonth() - 1);
		LocalDate startDate = startOfMonth.minusDays(startOfMonth.getDayOfWeek().getValue());
		LocalDate endDate = startDate.plusDays(7*5);
		
		
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
			AppCompatButton btn = new AppCompatButton(gridLayout.getContext());
			btn.setText(String.valueOf(date.getDayOfMonth()));
			
			if (date.isBefore(startOfMonth) || date.isAfter(endOfMonth)) {
				btn.setEnabled(false);
			} else if (date.isEqual(currentDate)) {
				// TODO: Hightlight current date
			}
			
			GridLayout.LayoutParams params = new GridLayout.LayoutParams();
			params.width = 0;
			params.height = 0;
			params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			
			gridLayout.addView(btn, params);
		}
	}
}
