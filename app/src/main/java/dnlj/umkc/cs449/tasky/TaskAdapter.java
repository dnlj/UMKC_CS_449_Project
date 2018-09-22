package dnlj.umkc.cs449.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


// REF: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
	private String[] data;
	
	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView mTextView;
		
		public ViewHolder(View view) {
			super(view);
			mTextView = view.findViewById(R.id.text_view);
		}
	}
	
	public TaskAdapter(String[] data) {
		this.data = data;
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
		holder.mTextView.setText(data[position]);
	}
	
	@Override
	public int getItemCount() {
		return data.length;
	}
}
