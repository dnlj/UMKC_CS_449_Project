package dnlj.umkc.cs449.tasky;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

public class NewTaskDialogFragment extends DialogFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialog_new_task, container, false);
		setupListeners(v);
		return v;
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
				TaskInfo task = new TaskInfo();
				
				task.name = ((EditText)v.findViewById(R.id.new_task_name)).getText().toString();
				task.interval = 0;
				task.alert = ((Switch)v.findViewById(R.id.new_task_alert_toggle)).isChecked();
				
				((MainActivity)getActivity()).addTask(task);
				dismiss();
			}
		});
	}
}
