package dnlj.umkc.cs449.tasky;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TaskActivity extends AppCompatActivity implements View.OnLongClickListener {
	private TaskInfo info;
	private TaskRepository taskRepository;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		// Get DB
		taskRepository = new TaskRepository(TaskDatabase.getDatabase(getApplicationContext()));
		
		// Get task info
		try {
			info = taskRepository.loadTask(getIntent().getStringExtra("task_name"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (info.events == null) {
			info.events = new ArrayList<>();
		}
		
		// Update toolbar
		Toolbar toolbar = findViewById(R.id.task_toolbar);
		toolbar.setTitle("Task: " + info.name);
		
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
			btn.setTag(date);
			btn.setOnLongClickListener(this);
			btn.setBackground(getResources().getDrawable(R.drawable.button_calendar, getTheme()));
			
			if (date.isBefore(startOfMonth) || date.isAfter(endOfMonth)) {
				btn.setEnabled(false);
			} else if (date.isEqual(currentDate)) {
				setBackgroundTintFromResourceId(btn, R.attr.colorAccent);
			}
			
			GridLayout.LayoutParams params = new GridLayout.LayoutParams();
			params.width = 0;
			params.height = 0;
			params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			
			gridLayout.addView(btn, params);
		}
		
		for (int i = 0; i < info.events.size(); ++i) {
			DateEvent event = info.events.get(i);
			LocalDate date = LocalDate.parse(event.date, DateTimeFormatter.ISO_DATE);
			AppCompatButton btn = gridLayout.findViewWithTag(date);
			
			switch (event.state) {
				case 0:
					setAsUnknown(btn);
					break;
				case 1:
					setAsComplete(btn);
					break;
				case 2:
					setAsIncomplete(btn);
					break;
			}
		}
	}
	
	@Override
	public boolean onLongClick(final View v) {
		PopupMenu menu = new PopupMenu(v.getContext(), v);
		menu.getMenuInflater().inflate(R.menu.task_context_complete, menu.getMenu());
		
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				final int id = item.getItemId();
				
				DateEvent event = new DateEvent();
				event.date = ((LocalDate)v.getTag()).format(DateTimeFormatter.ISO_DATE);
				
				if (id == R.id.unknown) {
					event.state = 0;
					setAsUnknown(v);
				} else if (id == R.id.complete) {
					event.state = 1;
					setAsComplete(v);
				} else if (id == R.id.incomplete) {
					event.state = 2;
					setAsIncomplete(v);
				}
				
				updateDateEvent(event);
				
				return true;
			}
		});
		
		menu.show();
		return true;
	}
	
	private void updateDateEvent(DateEvent event) {
		for (int i = 0; i < info.events.size(); ++i) {
			if (info.events.get(i).date == event.date) {
				info.events.remove(i);
				break;
			}
		}
		
		info.events.add(event);
		taskRepository.addTask(info);
	}
	
	private void setAsUnknown(View v) {
		v.setBackgroundTintList(ColorStateList.valueOf(0));
	}
	
	private void setAsComplete(View v) {
		setBackgroundTintFromResourceId(v, R.attr.colorPrimary);
	}
	
	private void setAsIncomplete(View v) {
		setBackgroundTintFromResourceId(v, R.attr.colorControlNormal);
	}
	
	private void setBackgroundTintFromResourceId(View v, int resColor) {
		TypedArray arr = obtainStyledAttributes(new int[]{resColor});
		v.setBackgroundTintList(ColorStateList.valueOf(arr.getColor(0, 0)));
		arr.recycle();
	}
}
