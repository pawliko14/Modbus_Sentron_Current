import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
 
/**
 * Author: Crunchify.com
 */
 
public class time {
	Timer timer;
 
	public time() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		timer = new Timer();
		// ustali sie, ze program sie zamyka po 14 godzinach od uruchomienia ( czy to batch czy manualnie)
		timer.schedule(new CrunchifyReminder(), 50400 * 1000);
	}
	
	public long gettime()
	{
		Instant instant = Instant.now();
		long timeStampMills = instant.toEpochMilli();
		
		
		return timeStampMills;
	}
 
	class CrunchifyReminder extends TimerTask {
		public void run() {
	//System.out.format("Timer Task Finished..!%n");
//	System.out.println("time in millse: " + gettime());
			timer.cancel(); // Terminate the timer thread
			System.exit(0);
		}
	}
}

// ~~~~~14h: w przyblizeniu
// 50736829





