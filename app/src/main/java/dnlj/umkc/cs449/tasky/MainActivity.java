package dnlj.umkc.cs449.tasky;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private TaskAdapter adapter;
	
	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Setup toolbar
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.inflateMenu(R.menu.toolbar);
		
		// Setup recycler view
		recyclerView = findViewById(R.id.task_view);
		layoutManager = new LinearLayoutManager(this);
		adapter = new TaskAdapter();
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		
		// Other setup
		setupListeners();
		
		for (int i =0; i < 5; ++i) {
			TaskInfo task = new TaskInfo();
			
			task.name = "Task: " + String.valueOf(i);
			task.interval = 0;
			task.alert = true;
			
			adapter.addTask(task);
		}
	}
	
	private void setupListeners() {
		// TODO:
	}
	
	public void addTask(TaskInfo task) {
		System.out.println("~~~ Add Task");
		adapter.addTask(task);
	}
	
	private void showNewTaskDialog() {
		NewTaskDialogFragment ntdf = new NewTaskDialogFragment();
		ntdf.show(getSupportFragmentManager(), null);
		ntdf.setCancelable(true);
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
