package dnlj.umkc.cs449.tasky;

import android.support.v4.app.FragmentManager;
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
	private RecyclerView.Adapter adapter;
	
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
		String[] data = new String[100];
		
		for (int i=0; i < 100; ++i) {
			data[i] = "Element: " + i;
		}
		
		recyclerView = findViewById(R.id.task_view);
		layoutManager = new LinearLayoutManager(this);
		adapter = new TaskAdapter(data);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		
		// Other setup
		setupListeners();
	}
	
	private void setupListeners() {
		// TODO:
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
