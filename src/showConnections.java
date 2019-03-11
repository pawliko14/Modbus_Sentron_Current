import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;


public class showConnections {

	private JFrame frame;
	private JTextField txtBnpr;
	private JTextField txtStatus;
	private JTextField txtEnergy;
	private JTextField txtCurrent;
	private JTextField txtActive;
	public static JTextField Energy_BN25;
	public static JTextField Current_BN25;
	public static JTextField Active_BN25;
	private JTextField txtPorebapr;
	private JLabel Collecting_Poreba;
	private JTextField Energy_Poreba;
	private JTextField Current_Poreba;
	private JTextField Active_Poreba;
	public static JLabel collecting_BN25;

	static Connection connection;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// tworzy printStream ktory odpowiada za przekazanie outputu z Consoli do pliku txt
					CreatePrintStream();
					connection = RCPdatabaseConnection.dbConnector("tosia", "1234","machines");
					new time(); // <- zostalo ustalone, ze program zostanie zamkniety po 14h od uruchomienia programu
					showConnections window = new showConnections();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public showConnections() {
		initialize();
							// 640, 20000,25 ....
		Licznik l1 = new Licznik("192.168.90.145", 640,20000,25,1,13,55,63,65,2,"sciezka", "bn25_pr2" ,connection ); // 60000 to 1minuta, wiece to wysypuje program	
		l1.start();
	}
	
	private static void CreatePrintStream() throws FileNotFoundException
	{
	    // Save original out stream.
        PrintStream originalOut = System.out;
        // Save original err stream.
        PrintStream originalErr = System.err;

        // Create a new file output stream.
        PrintStream fileOut = new PrintStream("\\\\dataserver/Common/Programy/ModbusCommunication/out.txt");
        // Create a new file error stream. 
        PrintStream fileErr = new PrintStream("\\\\dataserver/Common/Programy/ModbusCommunication/err.txt");

        // Redirect standard out to file.
        System.setOut(fileOut);
        // Redirect standard err to file.
        System.setErr(fileErr);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 452, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data collectors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 11, 220, 21);
		frame.getContentPane().add(lblNewLabel);
		
		txtBnpr = new JTextField();
		txtBnpr.setEditable(false);
		txtBnpr.setHorizontalAlignment(SwingConstants.CENTER);
		txtBnpr.setText("BN25-PR2");
		txtBnpr.setBounds(10, 67, 73, 20);
		frame.getContentPane().add(txtBnpr);
		txtBnpr.setColumns(10);
		
	    collecting_BN25 = new JLabel("Not collecting");
		collecting_BN25.setForeground(Color.RED);
		collecting_BN25.setBounds(93, 67, 63, 20);
		frame.getContentPane().add(collecting_BN25);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setText("Status");
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setBounds(92, 36, 64, 20);
		frame.getContentPane().add(txtStatus);
		txtStatus.setColumns(10);
		
		txtEnergy = new JTextField();
		txtEnergy.setEditable(false);
		txtEnergy.setText("Energy");
		txtEnergy.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnergy.setColumns(10);
		txtEnergy.setBounds(166, 36, 64, 20);
		frame.getContentPane().add(txtEnergy);
		
		txtCurrent = new JTextField();
		txtCurrent.setText("Current");
		txtCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrent.setEditable(false);
		txtCurrent.setColumns(10);
		txtCurrent.setBounds(240, 36, 64, 20);
		frame.getContentPane().add(txtCurrent);
		
		txtActive = new JTextField();
		txtActive.setText("Active");
		txtActive.setEditable(false);
		txtActive.setHorizontalAlignment(SwingConstants.CENTER);
		txtActive.setColumns(10);
		txtActive.setBounds(314, 36, 64, 20);
		frame.getContentPane().add(txtActive);
		
		Energy_BN25 = new JTextField();
		Energy_BN25.setEditable(false);
		Energy_BN25.setHorizontalAlignment(SwingConstants.CENTER);
		Energy_BN25.setBounds(166, 67, 63, 20);
		frame.getContentPane().add(Energy_BN25);
		Energy_BN25.setColumns(10);
		
		Current_BN25 = new JTextField();
		Current_BN25.setHorizontalAlignment(SwingConstants.CENTER);
		Current_BN25.setEditable(false);
		Current_BN25.setColumns(10);
		Current_BN25.setBounds(240, 67, 63, 20);
		frame.getContentPane().add(Current_BN25);
		
		Active_BN25 = new JTextField();
		Active_BN25.setHorizontalAlignment(SwingConstants.CENTER);
		Active_BN25.setEditable(false);
		Active_BN25.setColumns(10);
		Active_BN25.setBounds(315, 67, 63, 20);
		frame.getContentPane().add(Active_BN25);
		
		txtPorebapr = new JTextField();
		txtPorebapr.setText("Poreba-PR6");
		txtPorebapr.setHorizontalAlignment(SwingConstants.CENTER);
		txtPorebapr.setEditable(false);
		txtPorebapr.setColumns(10);
		txtPorebapr.setBounds(10, 98, 73, 20);
		frame.getContentPane().add(txtPorebapr);
		
		Collecting_Poreba = new JLabel("Not collecting");
		Collecting_Poreba.setForeground(Color.RED);
		Collecting_Poreba.setBounds(93, 98, 63, 20);
		frame.getContentPane().add(Collecting_Poreba);
		
		Energy_Poreba = new JTextField();
		Energy_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
		Energy_Poreba.setEditable(false);
		Energy_Poreba.setColumns(10);
		Energy_Poreba.setBounds(166, 98, 63, 20);
		frame.getContentPane().add(Energy_Poreba);
		
		Current_Poreba = new JTextField();
		Current_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
		Current_Poreba.setEditable(false);
		Current_Poreba.setColumns(10);
		Current_Poreba.setBounds(240, 98, 63, 20);
		frame.getContentPane().add(Current_Poreba);
		
		Active_Poreba = new JTextField();
		Active_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
		Active_Poreba.setEditable(false);
		Active_Poreba.setColumns(10);
		Active_Poreba.setBounds(314, 98, 63, 20);
		frame.getContentPane().add(Active_Poreba);
	}
}
