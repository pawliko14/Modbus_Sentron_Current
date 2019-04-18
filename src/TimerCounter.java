import java.util.Date;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimerCounter extends Thread {
   public boolean suspended = false;
   public static Date dates;
   public static String datesString;
   
   public TimerCounter() {
   }
   
   public void run() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

      while(true) {
    		Date date = new Date();
    		String dates = dateFormat.format(date);	
 
    		//pass referencje to main program with current date
    		showConnections.Timer.setText(dates);
    		
    		try {
				if(showConnections.connection.isClosed()  )
				{
					showConnections.collecting_BN25.setForeground(Color.YELLOW);
					showConnections.collecting_BN25.setText("No Collecting");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
         
         try {
			Thread.sleep(1000);
			synchronized(this){
				while(suspended) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Problem with Timer Thread, try to check connections, restart application");
			e.printStackTrace();
		}
      }
   }
   
   // methods to 'pause' thread and releasing it
   public void suspendThread()
   {
	   suspended = true;
   }
   public void resumeThread()
   {
	   suspended = false;
	   synchronized(this) {
		   notify();
	   }
   }
}