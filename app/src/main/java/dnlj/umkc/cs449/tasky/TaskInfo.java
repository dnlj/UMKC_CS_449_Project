package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TaskInfo {
	@PrimaryKey
	@NonNull
	public String name;
	
	public int interval;
	
	public boolean alert;
}
