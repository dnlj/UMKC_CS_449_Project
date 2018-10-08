package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(
	entities = {
		TaskInfo.class
	},
	version = 1
)
public abstract class TaskDatabase extends RoomDatabase {
	private static volatile TaskDatabase instance;
	
	public abstract TaskInfoDAO taskInfoDAO();
	
	public static TaskDatabase getDatabase(final Context context) {
		if (instance == null) {
			synchronized (TaskDatabase.class) {
				if (instance == null) {
					instance = Room.databaseBuilder(
						context.getApplicationContext(),
						TaskDatabase.class,
						"task_database"
					).build();
				}
			}
		}
		
		return instance;
	}
}
