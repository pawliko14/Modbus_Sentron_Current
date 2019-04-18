import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class IniFile 
	{ 
	private static String LICZBA_POMIAROW= "" ;
	private static String CZESTOTLIWOSC = "";
	private static String IP = "";
	private static String CZESTOTLIWOSC_ZAPISU = "";
	private static String NAZWA_MASZYNY ="";
	private static String REMOVAL_INTERVAL ="";

	public static void setREMOVAL_INTERVAL(String rEMOVAL_INTERVAL) {
		REMOVAL_INTERVAL = rEMOVAL_INTERVAL;
	}

	public static void setLICZBA_POMIAROW(String lICZBA_POMIAROW) {
		LICZBA_POMIAROW = lICZBA_POMIAROW;
	}

	public static void setCZESTOTLIWOSC(String cZESTOTLIWOSC) {
		CZESTOTLIWOSC = cZESTOTLIWOSC;
	}

	public static void setIP(String iP) {
		IP = iP;
	}

	public static void setCZESTOTLIWOSC_ZAPISU(String cZESTOTLIWOSC_ZAPISU) {
		CZESTOTLIWOSC_ZAPISU = cZESTOTLIWOSC_ZAPISU;
	}

	public static void setNAZWA_MASZYNY(String nAZWA_MASZYNY) {
		NAZWA_MASZYNY = nAZWA_MASZYNY;
	}
	
	public String getLICZBA_POMIAROW()
	{
		return LICZBA_POMIAROW;
	}
	
	public String getCZESTOTLIWOSC()
	{
		return CZESTOTLIWOSC;
	}
	
	public String getIP()
	{
		return IP;
	}
	public  String getCZESTOTLIWOSC_ZAPISU()
	{
		return CZESTOTLIWOSC_ZAPISU;
	}
	public  String getNAZWA_MASZYNY()
	{
		return NAZWA_MASZYNY;
	}
	public  String getREMOVAL_INTERVAL()
	{
		return REMOVAL_INTERVAL;
	}
	
	
	
//	LICZBA_POMIAROW =640
//	CZESTOTLIWOSC =20000
//	IP =192.168.90.145
//	CzestotliwoscZapisu =6
//	NAZWA_MASZYNY =bn25_pr2
//	REMOVAL_INTERVAL =3

	
	  public void Ini() throws IOException 
	  { 
	  File file = new File("ini.txt");   
	  BufferedReader br = new BufferedReader(new FileReader(file));   
	  String st; 
	  int x = 0;
	  
	  while ((st = br.readLine()) != null) {
	  		for(int i = 0; i< st.length();i++)
	  		{
	  			if(st.charAt(i) == '=')
	  			{
	  			i++;
		  			if(x == 0)
		  			{
		  				if(st.contains("LICZBA_POMIAROW"))
		  					setLICZBA_POMIAROW(st.substring(i, st.length()));
		  				else
		  					setLICZBA_POMIAROW("-1");
		  			}
		  			else if (x == 1)
		  			{
		  				if(st.contains("CZESTOTLIWOSC"))
		  					setCZESTOTLIWOSC(st.substring(i, st.length()));
		  				else
		  					setCZESTOTLIWOSC("INI ERR");
		  			}
		  			else if (x == 2)
		  				IP = st.substring(i, st.length());
		  			else if (x == 3)
		  			{
		  				if(st.contains("CzestotliwoscZapisu"))
		  					setCZESTOTLIWOSC_ZAPISU(st.substring(i, st.length()));
		  				else
		  					setCZESTOTLIWOSC_ZAPISU("INI ERR");
		  			}
		  			else if (x == 4)
		  				if(st.contains("NAZWA_MASZYNY"))
		  					setNAZWA_MASZYNY(st.substring(i, st.length()));
		  				else
		  					setNAZWA_MASZYNY("INI ERR");
		  			
		  			else if (x == 5)
		  				REMOVAL_INTERVAL =st.substring(i, st.length());
	  			}		
	  		} 
	  		x++;
	      }   
	  } 
	} 
