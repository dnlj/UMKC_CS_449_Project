package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskInfo {
	@PrimaryKey(autoGenerate = true)
	public int taskID;
	
	public String name;
	
	public int interval;
	
	public boolean alert;
}
