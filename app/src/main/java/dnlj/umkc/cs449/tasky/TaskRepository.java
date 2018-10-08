package dnlj.umkc.cs449.tasky;

import android.os.AsyncTask;

public class TaskRepository {
	private TaskDatabase taskDatabase;
	
	public TaskRepository(TaskDatabase taskDatabase) {
		this.taskDatabase = taskDatabase;
	}
	
	public void addTask(TaskInfo info) {
		new AddTaskAsync(taskDatabase.taskInfoDAO()).execute(info);
	}
	
	private static class AddTaskAsync extends AsyncTask<TaskInfo, Void, Void> {
		private TaskInfoDAO taskInfoDAO;
		
		public AddTaskAsync(TaskInfoDAO taskInfoDAO) {
			this.taskInfoDAO = taskInfoDAO;
		}
		
		@Override
		protected Void doInBackground(TaskInfo... params) {
			taskInfoDAO.addTask(params[0]);
			return null;
		}
	}
}
