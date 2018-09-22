package dnlj.umkc.cs449.tasky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get UI elements
		recyclerView = findViewById(R.id.task_view);
		
		// Setup recycler view
		String[] data = new String[100];
		
		for (int i=0; i < 100; ++i) {
			data[i] = "Element: " + i;
		}
		
		layoutManager = new LinearLayoutManager(this);
		adapter = new TaskAdapter(data);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
	}
}
