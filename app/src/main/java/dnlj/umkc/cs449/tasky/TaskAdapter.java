package dnlj.umkc.cs449.tasky;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;


// REF: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
	private ArrayList<TaskInfo> data;
	private MainActivity owner;
	
	public class ViewHolder extends RecyclerView.ViewHolder
		implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener, View.OnClickListener {
		
		public TextView mTextView;
		
		public ViewHolder(View view) {
			super(view);
			
			mTextView = view.findViewById(R.id.text_view);
			
			view.setOnClickListener(this);
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
			int id = item.getItemId();
			TaskInfo info = data.get(getAdapterPosition());
			
			if (id == R.id.delete_task) {
				owner.removeTask(info);
			} else if (id == R.id.edit_task) {
				owner.showNewTaskDialog().setTaskInfo(info);
			}
			
			return true;
		}
		
		@Override
		public void onClick(View v) {
			Context ctx = v.getContext();
			Intent intent = new Intent(ctx, TaskActivity.class);
			intent.putExtra("task_name", data.get(getAdapterPosition()).name);
			ctx.startActivity(intent);
		}
	}
	
	public TaskAdapter(MainActivity owner) {
		data = new ArrayList<>();
		this.owner = owner;
	}
	
	public void removeTask(TaskInfo task) {
		int index = data.indexOf(task);
		if (index == -1) { return; }
		removeTask(index);
	}
	
	private void removeTask(int position) {
		data.remove(position);
		notifyItemRemoved(position);
	}
	
	public void addTask(TaskInfo task) {
		data.add(task);
		notifyItemInserted(data.size() - 1);
	}
	
	public void updateTask(String name, TaskInfo task) {
		for (int i = 0; i < data.size(); ++i) {
			if (data.get(i).name == name) {
				data.set(i, task);
				notifyItemChanged(i);
				break;
			}
		}
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
