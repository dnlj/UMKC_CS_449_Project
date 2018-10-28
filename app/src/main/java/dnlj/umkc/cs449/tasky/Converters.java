package dnlj.umkc.cs449.tasky;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Converters {
	@TypeConverter
	public static String fromArrayList(ArrayList<DateEvent> events) {
		return new Gson().toJson(events);
	}
	
	@TypeConverter
	public static ArrayList<DateEvent> fromString(String events) {
		return new Gson().fromJson(
			events,
			new TypeToken<ArrayList<DateEvent>>(){}.getType()
		);
	}
}
