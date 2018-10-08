package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface TaskInfoDAO {
	@Insert
	public void addTask(TaskInfo info);
	
	@Update
	public void updateTask(TaskInfo info);
	
	@Delete
	public void removeTask(TaskInfo info);
	
	@Query("SELECT * FROM TaskInfo")
	public TaskInfo[] loadTasks();
}
