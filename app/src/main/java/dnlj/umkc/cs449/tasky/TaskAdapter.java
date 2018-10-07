package dnlj.umkc.cs449.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;


// REF: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
	private ArrayList<TaskInfo> data;
	
	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder
		implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
		
		public TextView mTextView;
		
		public ViewHolder(View view) {
			super(view);
			
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Click: " + getAdapterPosition());
				}
			});
			
			mTextView = view.findViewById(R.id.text_view);
			view.setOnLongClickListener(this);
		}
		
		@Override
		public boolean onLongClick(View v) {
			PopupMenu menu = new PopupMenu(v.getContext(), v);
			
			menu.getMenuInflater().inflate(R.menu.task_context_menu, menu.getMenu());
			menu.setOnMenuItemClickListener(this);
			menu.show();
			
			return true;
		}
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			System.out.println("Item Click");
			return true;
		}
	}
	
	public TaskAdapter() {
		data = new ArrayList<>();
	}
	
	public void addTask(TaskInfo task) {
		data.add(task);
		notifyItemInserted(data.size() - 1);
	}
	
	// Create new views (invoked by the layout manager)
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.task_card, parent, false);
		
		return new ViewHolder(view);
	}
	
	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.mTextView.setText(data.get(position).name);
	}
	
	@Override
	public int getItemCount() {
		return data.size();
	}
}
