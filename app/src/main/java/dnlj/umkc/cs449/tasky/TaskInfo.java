package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity
public class TaskInfo {
	@PrimaryKey
	@NonNull
	public String name;
	
	public String interval;
	
	public boolean alert;
	
	public ArrayList<DateEvent> events;
}
