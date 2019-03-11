import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import de.re.easymodbus.exceptions.ModbusException;
import java.lang.String;

public class MainProgram {

	static Connection connection;
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, IllegalArgumentException, ModbusException
	{
		//public Licznik(String Ip, int liczbapomiarow, int czestotliwosc,int rejestr, int offset, String Sciezka, nazwa w bazie danych) <- konstruktor
		
		
		// w przyszlosci liczbe danych wywalic, bo licznik ma wysylac dane no-stop
		
		// trzeba okreslic ile licznikow bedzie na jakich maszynach, Wstepnie mozna zalozyc o 10 maszynach
		// zatem 10 roznych polaczen po roznych ip ( moze byc nowa siec, wiec ip sie zmienia)
		// USPRAWNIC DZIALANIE WATKOWE, WATKI NIE MOGA SOBIE PRZESZKADZAC!
		
		
		// sprawdzic po nowym roku
		
		connection = RCPdatabaseConnection.dbConnector("tosia", "1234","machines");
		
		// ma byc 20 000
		
		// przy 20000 dziala doskonale, sprawdzone dla 60000 i wiecej, wywala i brakuje rozlacza. Zrobione 3 proby polaczenia do licznika
		// nie sprawdzone na 2 licznikach, narazie tylko jeden( bez wielowatkowosci )
		
		// KIEDY POJAWI SIE DRUGI LICZNIK SPRAWDZIC WIELOWATKOWSC  --- -OBOWIAZKOWO ---- 
	// 	ZROBIC TEZ PROSTE MENU UZYTKOWNIKA DLA SPRAWDZENIA CZY DANE SA POBIERNAE --- OBOWIAZKOWO ----
		
	//	Licznik l1 = new Licznik("192.168.90.145", 640,20000,25,2,"sciezka", "bn25_pr2" ,connection ); // 60000 to 1minuta, wiece to wysypuje program
	//	Licznik l2 = new Licznik("192.168.90.145", 200,30000,25,2,"sciezka", "bn25_pr2_1", connection); // sprawdzic, bo wczesniej sie dalo 3-4 min

		
		// proba z drugim konstruktorem z 3 parametrami
	//Licznik l1 = new Licznik("192.168.90.145", 640,20000,25,1,13,2,"sciezka", "bn25_pr2" ,connection ); // 60000 to 1minuta, wiece to wysypuje program

	
		// proba z 3 konstruktorem z cal moc czynna i bierna
	Licznik l1 = new Licznik("192.168.90.145", 2,20000,25,1,13,55,63,65,2,"sciezka", "bn25_pr2" ,connection ); // 60000 to 1minuta, wiece to wysypuje program
 
		
		
	l1.start();
	//	l2.start();
		

	}
	
}
