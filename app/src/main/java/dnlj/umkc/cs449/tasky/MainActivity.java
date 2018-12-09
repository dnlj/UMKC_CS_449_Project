package dnlj.umkc.cs449.tasky;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private TaskAdapter adapter;
	private TaskRepository taskRepository;
	
	private Toolbar toolbar;
	public static String notificationChannelId = "dnlj.umkc.cs449.tasky";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create database
		taskRepository = new TaskRepository(TaskDatabase.getDatabase(getApplicationContext()));
		
		// Setup toolbar
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.inflateMenu(R.menu.toolbar);
		
		// Setup recycler view
		recyclerView = findViewById(R.id.task_view);
		layoutManager = new LinearLayoutManager(this);
		adapter = new TaskAdapter(this);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		
		// Setup notification channel
		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
			.createNotificationChannel(new NotificationChannel(
					notificationChannelId,
					notificationChannelId,
					NotificationManager.IMPORTANCE_DEFAULT
			));
		
		// Load Tasks
		loadTasks();
	}
	
	public Intent getNewAlarmIntent(String name) {
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.addCategory(name);
		return intent;
	}
	
	public void removeTaskAlert(String name) {
		((AlarmManager) getSystemService(Context.ALARM_SERVICE))
			.cancel(PendingIntent.getBroadcast(this, 0, getNewAlarmIntent(name), 0));
	}
	
	public void updateTaskAlert(String name, TaskInfo task) {
		removeTaskAlert(name);
		addTaskAlert(task);
	}
	
	public void addTaskAlert(TaskInfo task) {
		if (task.alert) {
			AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			
			Intent intent = getNewAlarmIntent(task.name);
			intent.putExtra("text", "Don't forget to " + task.name + "!");
			
			PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, 0);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.set(Calendar.HOUR_OF_DAY, 11);
			cal.set(Calendar.MINUTE, 30);
			
			long interval = AlarmManager.INTERVAL_DAY;
			
			if (task.interval.equals("Monthly")) {
				interval *= 30;
			} else if (task.interval.equals("Weekly")) {
				interval *= 7;
			}
			
			am.setInexactRepeating(
				AlarmManager.RTC,
				cal.getTimeInMillis() + interval,
				interval,
				pending
			);
		}
	}
	
	public void updateTask(String name, TaskInfo task) {
		taskRepository.updateTask(name, task);
		adapter.updateTask(name, task);
		updateTaskAlert(name, task);
	}
	
	public void addTask(TaskInfo task) {
		taskRepository.addTask(task);
		adapter.addTask(task);
		addTaskAlert(task);
	}
	
	public void removeTask(TaskInfo task) {
		taskRepository.removeTask(task);
		adapter.removeTask(task);
		removeTaskAlert(task.name);
	}
	
	public void loadTasks() {
		try {
			TaskInfo[] tasks = taskRepository.loadTasks();
			for (TaskInfo task : tasks) {
				adapter.addTask(task);
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public NewTaskDialogFragment showNewTaskDialog() {
		NewTaskDialogFragment ntdf = new NewTaskDialogFragment();
		ntdf.show(getSupportFragmentManager(), null);
		ntdf.setCancelable(true);
		return ntdf;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.toolbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.add_task) {
			showNewTaskDialog();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
