package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface TaskInfoDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void addTask(TaskInfo info);
	
	@Delete
	public void removeTask(TaskInfo info);
	
	@Query("SELECT * FROM TaskInfo")
	public TaskInfo[] loadTasks();
	
	@Query("SELECT * FROM TaskInfo WHERE name = :name")
	public TaskInfo loadTask(String name);
	
	@Query("Update TaskInfo SET name = :name WHERE name = :task")
	public void updateTaskName(String task, String name);
	
	@Query("Update TaskInfo SET interval = :interval WHERE name = :task")
	public void updateTaskInterval(String task, String interval);
	
	@Query("Update TaskInfo SET alert = :alert WHERE name = :task")
	public void updateTaskAlert(String task, boolean alert);
}
