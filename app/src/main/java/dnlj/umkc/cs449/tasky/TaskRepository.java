package dnlj.umkc.cs449.tasky;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

public class TaskRepository {
	private TaskDatabase taskDatabase;
	
	public TaskRepository(TaskDatabase taskDatabase) {
		this.taskDatabase = taskDatabase;
	}
	
	public void addTask(TaskInfo info) {
		new AddTaskAsync(taskDatabase.taskInfoDAO()).execute(info);
	}
	
	public void removeTask(TaskInfo info) {
		new RemoveTaskAsync(taskDatabase.taskInfoDAO()).execute(info);
	}
	
	public TaskInfo[] loadTasks() throws ExecutionException, InterruptedException {
		return new LoadTasksAsync(taskDatabase.taskInfoDAO()).execute().get();
	}
	
	public TaskInfo loadTask(String name) throws ExecutionException, InterruptedException {
		return new LoadTaskAsync(taskDatabase.taskInfoDAO()).execute(name).get();
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
	
	private static class RemoveTaskAsync extends AsyncTask<TaskInfo, Void, Void> {
		private TaskInfoDAO taskInfoDAO;
		
		public RemoveTaskAsync(TaskInfoDAO taskInfoDAO) {
			this.taskInfoDAO = taskInfoDAO;
		}
		
		@Override
		protected Void doInBackground(TaskInfo... params) {
			taskInfoDAO.removeTask(params[0]);
			return null;
		}
	}
	
	private static class LoadTasksAsync extends AsyncTask<Void, Void, TaskInfo[]> {
		private TaskInfoDAO taskInfoDAO;
		
		public LoadTasksAsync(TaskInfoDAO taskInfoDAO) {
			this.taskInfoDAO = taskInfoDAO;
		}
		
		@Override
		protected TaskInfo[] doInBackground(Void... params) {
			return taskInfoDAO.loadTasks();
		}
	}
	
	private static class LoadTaskAsync extends AsyncTask<String, Void, TaskInfo> {
		private TaskInfoDAO taskInfoDAO;
		
		public LoadTaskAsync(TaskInfoDAO taskInfoDAO) {
			this.taskInfoDAO = taskInfoDAO;
		}
		
		@Override
		protected TaskInfo doInBackground(String... params) {
			return taskInfoDAO.loadTask(params[0]);
		}
	}
}
