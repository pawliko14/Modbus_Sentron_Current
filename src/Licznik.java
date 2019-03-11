import java.awt.Color;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;

import de.re.easymodbus.exceptions.ModbusException;
import de.re.easymodbus.modbusclient.ModbusClient;
import de.re.easymodbus.modbusclient.ModbusClient.RegisterOrder;

public class Licznik extends Thread {
	
private Thread t;

private static String IP;
private int LiczbaPomiarow;
private int CzestotliwoscPomiarow;
private int Rejestr;
private int Rejestr2;
private int Rejestr3;
private int Rejestr4;  // calkowita moc bierna
private int Rejestr5; // calkowita moc czynna
private int Rejestr6;
private int Offset;
private String SciezkaDoFolderu;
static Connection connection=null;
static ModbusClient modbusClient;

private String NazwaMaszynyWBazie;


private List<Double> ListaPomiarow = new ArrayList<Double>(); // zgromadzone dane w liscie
private List<Double> ListaPomiarowVolt = new ArrayList<Double>(); // dane gromadzone w przypadku przeciazenia konstruktora do 3 rejestrow
private List<Double> ListaPomiarowAmp = new ArrayList<Double>(); // dane gromadzone w przypadku przeciazenia konstruktora do 3 rejestrow
private List<Double> ListaPomiarowFreq = new ArrayList<Double>();
private List<Double> ListaPomiarowPass = new ArrayList<Double>();
private List<Double> ListaPomiarowActive = new ArrayList<Double>();
		public Licznik()
		{
			
		}
		
		
// podstawowy konsturktor dla jednego parametru
	public Licznik(String Ip, int liczbapomiarow, int czestotliwosc,int rejestr, int offset, String Sciezka, String nazwaMaszynyWBazie,Connection conn)
	{
		IP = Ip;
		LiczbaPomiarow = liczbapomiarow;
		CzestotliwoscPomiarow = czestotliwosc;
		Rejestr = rejestr;
		Offset = offset;
		SciezkaDoFolderu = Sciezka;
		NazwaMaszynyWBazie = nazwaMaszynyWBazie;
		connection=conn;
		
	}
	
	// rozszerzony konstruktor dla 3 parametrow
	public Licznik(String Ip, int liczbapomiarow, int czestotliwosc,int rejestr1,int rejestr2,int rejestr3, int offset, String Sciezka, String nazwaMaszynyWBazie,Connection conn)
	{
		IP = Ip;
		LiczbaPomiarow = liczbapomiarow;
		CzestotliwoscPomiarow = czestotliwosc;
		Rejestr = rejestr1;
		Rejestr2 = rejestr2;
		Rejestr3 = rejestr3;
		Offset = offset;
		SciezkaDoFolderu = Sciezka;
		NazwaMaszynyWBazie = nazwaMaszynyWBazie;
		connection=conn;
		
	}
	//rozszerzony konstrukow dla 6 parametrow
	public Licznik(String Ip, int liczbapomiarow, int czestotliwosc,int rejestr1,int rejestr2,int rejestr3,int rejestr4,int rejestr5,int rejestr6, int offset, String Sciezka, String nazwaMaszynyWBazie,Connection conn)
	{
		IP = Ip;
		LiczbaPomiarow = liczbapomiarow;
		CzestotliwoscPomiarow = czestotliwosc;
		Rejestr = rejestr1;
		Rejestr2 = rejestr2;
		Rejestr3 = rejestr3;
		Rejestr4 = rejestr4;
		Rejestr5 = rejestr5;
		Rejestr6 = rejestr6;
		Offset = offset;
		SciezkaDoFolderu = Sciezka;
		NazwaMaszynyWBazie = nazwaMaszynyWBazie;
		connection=conn;
		
	}
	
	public void DodajRekordDoListyKWh(Double pomiar)
	{
		this.ListaPomiarow.add(new Double(pomiar));
		
	}
	
	public void DodajRekordDoListyVolt(Double pomiar)
	{
		this.ListaPomiarowVolt.add(new Double(pomiar));
	}
	
	public void DodajRekordDoListyAmp(Double pomiar)
	{
		this.ListaPomiarowAmp.add(new Double(pomiar));
	}
	
	public void DodajRekordDoListyFreq(Double pomiar)
	{	
		this.ListaPomiarowFreq.add(new Double(pomiar));
	}
	
	public void DodajRekordDoListyPassive(Double pomiar)
	{
		this.ListaPomiarowPass.add(new Double(pomiar));
	}
	
	public void DodajRekordDoListyActive(Double pomiar)
	{
		this.ListaPomiarowActive.add(new Double(pomiar));
	}
	
	public void PokazListe()
	{
		for(int i = 0 ; i< this.ListaPomiarow.size();i++)
		{
			System.out.println("Rekord: ["+ i + "] = " + this.ListaPomiarow.get(i));
		}
	}
	
	
	public static String GetIP()
	{
		return IP;
	}
	
	public int GetLiczbaPomiarow()
	{
		return this.LiczbaPomiarow;
	}
	public int GetCzestotliwosc()
	{
		return this.CzestotliwoscPomiarow;
	}
	public int GetRejestr()
	{
		return this.Rejestr;
	}
	
	public int GetRejestr2()
	{
		return this.Rejestr2;
	}
	
	public int GetRejestr3()
	{
		return this.Rejestr3;
	}
	public int GetRejestr4()
	{
		return this.Rejestr4;
	}
	
	public int GetRejestr5()
	{
		return this.Rejestr5;
	}
	
	public int GetRejestr6()
	{
		return this.Rejestr6;
	}
	
	public int GetOffset()
	{
		return this.Offset;
	}
	public String getPath()
	{
		return this.SciezkaDoFolderu;
	}
	
	public String GetNazwaWBazie()
	{
		return this.NazwaMaszynyWBazie;
	}
	
 public void ConnectToLicznik() throws UnknownHostException, IOException, InterruptedException
    {
    	 modbusClient = new ModbusClient(GetIP(),502);
		try
		{
			modbusClient.Connect();
			
			if(modbusClient.isConnected())
				System.out.println("Connection is set as: "+ GetIP());
			

		}
		catch (Exception e)
		{		
			System.out.println("Connection lost, trying to connect:");      
			System.out.println(e.toString());
			
			 showConnections.collecting_BN25.setText("Collecting");
			 showConnections.collecting_BN25.setForeground(Color.GREEN);
			 
			//recurse run a program again, without losing collected data
			//modbusClient.Disconnect();
			ConnectToLicznik(); 

		}	
    }
 

 
 public void StartGenerateDataSet() throws IllegalArgumentException, UnknownHostException, SocketException, ModbusException, IOException, InterruptedException, SQLException
 {
	 double Wh;
	 double Voltage;
	 double Amp;
	 double freq;
	 double passive;
	 double active;
	 
	 System.out.println("checking");
	 
	 if(modbusClient.isConnected())
	 {
	//	 showConnections.collecting_BN25.setText("Collecting");
	//	 showConnections.collecting_BN25.setForeground(Color.GREEN);
		 System.out.println("Connection ");
		 
		// for(int i = 0 ; i< this.GetLiczbaPomiarow();i++)
		 int i  = 0;
			while(true) 
		 {
				i++;
				Wh = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr(), this.GetOffset()),RegisterOrder.HighLow);	 
				double kWh_1 = 0.001 * Wh; // przeliczenie na kWh
			
				Voltage = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr2(), this.GetOffset()),RegisterOrder.HighLow);
				Amp = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr3(), this.GetOffset()),RegisterOrder.HighLow);

				
				freq = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr4(), this.GetOffset()),RegisterOrder.HighLow);
				passive = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr5(), this.GetOffset()),RegisterOrder.HighLow);
				active = ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(this.GetRejestr6(), this.GetOffset()),RegisterOrder.HighLow);

				
				showConnections.Energy_BN25.setText(String.valueOf(kWh_1));	
				showConnections.Current_BN25.setText(String.valueOf(Amp));
				showConnections.Active_BN25.setText(String.valueOf(active));
				

				
				DodajRekordDoListyKWh(kWh_1);
				DodajRekordDoListyVolt(Voltage);
				DodajRekordDoListyAmp(Amp);
				
				DodajRekordDoListyFreq(freq);
				DodajRekordDoListyPassive(passive);
				DodajRekordDoListyActive(active);
	
				if(i%6 ==0) {

					 System.out.println("Rozmiary list Przed Wyczyszczeniem: ");
					 System.out.println("ListaPomiarow: "+ ListaPomiarow.size() + "  ListaPomiarowVolt "+ ListaPomiarowVolt.size() + "ListaPomiarowAmp "+ ListaPomiarowAmp.size()) ;

					this.InsertIntoDatabase6Param(connection);
					 System.out.println("DOdano rekord do bazy: "+ kWh_1 + " dla: "+ this.getId() + " dla maszyny(w bazie): "+  this.GetNazwaWBazie());
					
					 this.CleanLists();  // clean list to release memory( hope its workin)
				}
				 
				 System.out.println("liczba pomiarow dotychczas: " + this.GetLiczbaPomiarow() + " oraz [i]: +" + i);
				 System.out.println("voltage przy 3 parametrach " + Voltage );
				 System.out.println("Amperum przy 3 parametrach " + Amp );
				 Thread.sleep(this.GetCzestotliwosc());
		
		 }
	 }
	 
	 else {
		 showConnections.collecting_BN25.setText("Not Collecting");
		 showConnections.collecting_BN25.setForeground(Color.RED);
		 System.out.println("Connection lost");
		 modbusClient.Disconnect();
	 }
 }
    
 
 private void CleanLists() {
	// TODO Auto-generated method stub

	 this.ListaPomiarow.clear();
	 this.ListaPomiarowVolt.clear();
	 this.ListaPomiarowAmp.clear();
	 this.ListaPomiarowFreq.clear();
	 this.ListaPomiarowPass.clear();
	 this.ListaPomiarowActive.clear();
	 
	 System.out.println("Rozmiary list to wyczyszczeniu: ");
	 System.out.println("ListaPomiarow: "+ ListaPomiarow.size() + "  ListaPomiarowVolt "+ ListaPomiarowVolt.size() + "ListaPomiarowAmp "+ ListaPomiarowAmp.size()) ;
	 
}


// kiedy dojdzie juz drugi licznik do implementacji, poprawic kod run, zeby nie bylo zagniezdzonych tryCatch, a tylko jedna rekursja
    public void run()
    {
		try {	
		//	this.DeleteOldRekords();
			this.ConnectToLicznik();
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			System.out.println("no connection here 1");
			 showConnections.collecting_BN25.setText("Not Collecting");
			 showConnections.collecting_BN25.setForeground(Color.RED);
			e1.printStackTrace();
		}
		try {
			this.StartGenerateDataSet();
			//Thread.sleep(100);
			
		} catch (IllegalArgumentException | ModbusException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("no connection here 2");
			 showConnections.collecting_BN25.setText("Not Collecting");
			 showConnections.collecting_BN25.setForeground(Color.RED);
			try {
				Thread.sleep(2000);
				this.ConnectToLicznik();
				this.StartGenerateDataSet();

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				System.out.println("no connection here 11");
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("no connection here 12");
				
				try {
					Thread.sleep(2000);
					this.ConnectToLicznik();
					this.StartGenerateDataSet();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					System.out.println("no connection here 123");	
						try {
							System.out.println("no connection here - odpalanie rekursywnie run(): ");
							run();   // finalnie odpala 
						} catch (IllegalArgumentException e3) {
							 
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					
				
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IllegalArgumentException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ModbusException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				System.out.println("no connection here 13");
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ModbusException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("no connection here 3");
			e.printStackTrace();
		}
		
		this.PokazListe();
		
    }
    
    public void InsertIntoDatabase(Connection connection) throws SQLException
    {

		String query = "0";
	
		query = "INSERT INTO "+this.GetNazwaWBazie()+" (Date, Time, PowerConsumption) VALUES (?,?,?)";

		PreparedStatement pst=connection.prepareStatement(query);

			pst.setString(1, GetAcutalDate());
			pst.setString(2, GetAcutalTime());
			pst.setString(3, String.valueOf((ListaPomiarow.get(ListaPomiarow.size()-1))));
			
			
			pst.addBatch();
		
		pst.executeBatch();
		pst.close();	
		
		pst.executeBatch();
		pst.close();			

    }
    
    public void InsertIntoDatabase3Param(Connection connection) throws SQLException
    {

		String query = "0";
	
		query = "INSERT INTO "+this.GetNazwaWBazie()+" (Date, Time, PowerConsumption,Voltage,Current) VALUES (?,?,?,?,?)";

		PreparedStatement pst=connection.prepareStatement(query);

			pst.setString(1, GetAcutalDate());
			pst.setString(2, GetAcutalTime());
			pst.setString(3, String.valueOf((ListaPomiarow.get(ListaPomiarow.size()-1))));
			pst.setString(4, String.valueOf((ListaPomiarowVolt.get(ListaPomiarowVolt.size()-1))));
			pst.setString(5, String.valueOf((ListaPomiarowAmp.get(ListaPomiarowAmp.size()-1))));


			pst.addBatch();
		
		pst.executeBatch();
		pst.close();	
		
		pst.executeBatch();
		pst.close();			

    }
    public void InsertIntoDatabase6Param(Connection connection) throws SQLException
    {

		String query = "0";
	
		query = "INSERT INTO bn25_pr2 (Maszyna,Date, Time, PowerConsumption,Voltage,Current,Frequency,Calk_moc_pozorna,Calk_moc_czynna) VALUES (?,?,?,?,?,?,?,?,?)";

		PreparedStatement pst=connection.prepareStatement(query);

			pst.setString(1, this.GetNazwaWBazie());
			pst.setString(2, GetAcutalDate());
			pst.setString(3, GetAcutalTime());
			pst.setString(4, String.valueOf((ListaPomiarow.get(ListaPomiarow.size()-1))));
			pst.setString(5, String.valueOf((ListaPomiarowVolt.get(ListaPomiarowVolt.size()-1))));
			pst.setString(6, String.valueOf((ListaPomiarowAmp.get(ListaPomiarowAmp.size()-1))));

			pst.setString(7, String.valueOf((ListaPomiarowFreq.get(ListaPomiarowFreq.size()-1))));
			pst.setString(8, String.valueOf((ListaPomiarowPass.get(ListaPomiarowPass.size()-1))));
			pst.setString(9, String.valueOf((ListaPomiarowActive.get(ListaPomiarowActive.size()-1))));

			
			pst.addBatch();
		
		pst.executeBatch();
		pst.close();	
		
		pst.executeBatch();
		pst.close();			

    }
    
    
    private String GetAcutalDate()
    {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	Date date = new Date();
    	System.out.println("aktualna data: " + dateFormat.format(date)); // 2016/11/16 
    	String data =dateFormat.format(date);
    	
    	return data;
    	
    }
    private String GetAcutalTime()
    {
    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    	Date date = new Date();
    	System.out.println("aktualna godzina: " + dateFormat.format(date)); // 12:08:43
    	String godzina =dateFormat.format(date);
    	
    	return godzina;
    		
    }
    
    private void DeleteOldRekords() throws SQLException
    {
    	// sprawdza czy baza danyh zwiera rekordy starsze niz 3 miesiace, jezeli tak to usuwa te rekordy
    	// czyszczenie przeprowadzone bedzie raz na uruchomienie programu (na samym poczatku)
    	
    	String query = "null";
    	query = "DELETE FROM machines.bn25_pr2 WHERE `Date` < (CURDATE() - INTERVAL 3 month)";
    	try {
    		PreparedStatement pst=connection.prepareStatement(query);
    		pst.execute();
    		pst.close();
    		System.out.println("rekordy z ostatnich 3 miesicy zostaly usuniete");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Rekordy nie zostaly usuniete: nastepujacy blad:");
			e1.printStackTrace();
		}
    }
    

}


	


