package dnlj.umkc.cs449.tasky;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

public class NewTaskDialogFragment extends DialogFragment {
	private String name;
	private TaskInfo info;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialog_new_task, container, false);
		setupListeners(v);
		updateViewElements(v);
		return v;
	}
	
	public void setTaskInfo(TaskInfo info) {
		this.name = info.name;
		this.info = info;
	}
	
	private void updateViewElements(View v) {
		if (info == null) { return; }
		
		((EditText)v.findViewById(R.id.new_task_name)).setText(info.name);
		((Switch)v.findViewById(R.id.new_task_alert_toggle)).setChecked(info.alert);
		
		Spinner spinner = v.findViewById(R.id.new_task_interval);
		ArrayAdapter<String> adapter = (ArrayAdapter<String>)spinner.getAdapter();
		
		for (int i = 0; i < adapter.getCount(); ++i) {
			if (adapter.getItem(i).equals(info.interval)) {
				spinner.setSelection(i, false);
				break;
			}
		}
	}
	
	private void setupListeners(final View v) {
		v.findViewById(R.id.new_task_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View clickView) {
				dismiss();
			}
		});
		
		v.findViewById(R.id.new_task_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View clickView) {
				MainActivity activity = (MainActivity)getActivity();
				
				TaskInfo task = new TaskInfo();
				
				task.name = ((EditText) v.findViewById(R.id.new_task_name)).getText().toString();
				task.interval = ((Spinner) v.findViewById(R.id.new_task_interval)).getSelectedItem().toString();
				task.alert = ((Switch) v.findViewById(R.id.new_task_alert_toggle)).isChecked();
				
				if (name == null) {
					activity.addTask(task);
				} else {
					activity.updateTask(name, task);
				}
				
				dismiss();
			}
		});
	}
}
