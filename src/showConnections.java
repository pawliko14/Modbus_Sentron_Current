import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;


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
	private boolean stopped = false;
	private JButton btnNewButton_1;
	private JPanel panel;
	private static JTextField txtMachineIp;
	private static JTextField txtMeasurmentFreq;
	private static JTextField txtDatabaseSave;
	private static JTextField txtMachineName;
	private static JTextField txtRemovalInterval;
	private static JTextField MachineIP;
	private static JTextField MachineSurvey;
	private static JTextField MachineFreqToDB;
	private static JTextField MachineID;
	private static JTextField MachineRemInterval;
	public static JTextField Timer;
	
	
	 private static Licznik l1;
	public static Connection connection;
	
	private static TimerCounter timer;
	public static  JTable table;
	
	public static JScrollPane scrollPane;
	static IniFile ini = new IniFile();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// tworzy printStream ktory odpowiada za przekazanie outputu z Consoli do pliku txt
					CreatePrintStream();
					connection = RCPdatabaseConnection.dbConnector("tosia", "1234","machines");
					
					// Let program work withour interruption, see next day how its memory consumption presents
			     	//	new time(); // <- zostalo ustalone, ze program zostanie zamkniety po 14h od uruchomienia programu
					
					ini.Ini();

					
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
	 * @throws IOException 
	 */
	public showConnections() throws IOException {
	
		initialize();

		 // ten drugi odpalic kiedy apka bedzie w pelni gotowa, aby uzytkownik mogl to sobie edytowac
		//l1 = new Licznik("192.168.90.145",640,20000, 25,1,13,55,63,65,2,"sciezka", "bn25_pr2" ,connection );
		
		l1 = new Licznik(ini.getIP(),Integer.parseInt(ini.getCZESTOTLIWOSC_ZAPISU()), Integer.parseInt(ini.getCZESTOTLIWOSC()), 25,1,13,55,63,65,2, ini.getNAZWA_MASZYNY() ,connection );

		timer = new TimerCounter();
		
		timer.start();
		l1.start();
		
		showIniFile();

		
	}

	private static void showIniFile()
	{
		
		 MachineSurvey.setText(String.valueOf(l1.GetCzestotliwosc()));
		 MachineIP.setText(l1.GetIP());
		 
//		private JTextField txtMeasurmentFreq;
		 MachineFreqToDB.setText(String.valueOf(l1.GetSaveToDbInterval()));
//		private JTextField txtMachineName;
//		private JTextField txtRemovalInterval;
//		private JTextField MachineIP;
		
//		private JTextField MachineFreqToDB;
		 MachineID.setText(l1.GetNazwaWBazie());
		 MachineRemInterval.setText(String.valueOf(l1.GetRemovalInterval()));
//	
		 }
	
	private static void CreatePrintStream() throws FileNotFoundException
	{
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        PrintStream fileOut = new PrintStream(Parameters.getPathToOut());
        PrintStream fileErr = new PrintStream(Parameters.getPathToErr());

        System.setOut(fileOut);
        System.setErr(fileErr);
	}
	
	public static void resultSetToTableModel(JTable table2,ResultSet rs) throws SQLException{
        //Create new table model
        DefaultTableModel tableModel = new DefaultTableModel();

        //Retrieve meta data from ResultSet
        ResultSetMetaData metaData = rs.getMetaData();

        //Get number of columns from meta data
        int columnCount = metaData.getColumnCount();

        //Get all column names from meta data and add columns to table model
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
            tableModel.addColumn(metaData.getColumnLabel(columnIndex));
        }

        //Create array of Objects with size of column count from meta data
        Object[] row = new Object[columnCount];

        //Scroll through result set
        while (rs.next()){
            //Get object from column with specific index of result set to array of objects
            for (int i = 0; i < columnCount; i++){
                row[i] = rs.getObject(i+1);
            }
            //Now add row to table model with that array of objects as an argument
            tableModel.addRow(row);
        }

        //Now add that table model to your table and you are done :D
        table2.setModel(tableModel);
    }


	private void initialize() {
		frame = new JFrame();

		
		
		
		frame.setBounds(100, 100, 475, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnNewButton_1 = new JButton("Reset Conn");
		btnNewButton_1.setBounds(325, 359, 124, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to reset connection?");
				if(dialogResult == JOptionPane.YES_OPTION){
					
					l1.stop();
					try {
						JOptionPane.showMessageDialog(null, "Connection restarted, wait 5 sec");
						Thread.sleep(5000); // there is must to be delay, giving some to to reconnect to Counter
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!l1.isAlive()) {
						
						 l1 = new Licznik(ini.getIP(),Integer.parseInt(ini.getCZESTOTLIWOSC_ZAPISU()), Integer.parseInt(ini.getCZESTOTLIWOSC()), 25,1,13,55,63,65,2, ini.getNAZWA_MASZYNY() ,connection );
						 l1.start();
					}
					
				}

				
			}
		});
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(8, 44, 441, 109);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data collectors");
		lblNewLabel.setBounds(106, 0, 220, 21);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtStatus = new JTextField();
		txtStatus.setBounds(81, 27, 64, 20);
		panel.add(txtStatus);
		txtStatus.setEditable(false);
		txtStatus.setText("Status");
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setColumns(10);
		
		txtEnergy = new JTextField();
		txtEnergy.setBounds(157, 27, 64, 20);
		panel.add(txtEnergy);
		txtEnergy.setEditable(false);
		txtEnergy.setText("Energy");
		txtEnergy.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnergy.setColumns(10);
		
		txtCurrent = new JTextField();
		txtCurrent.setBounds(231, 27, 56, 20);
		panel.add(txtCurrent);
		txtCurrent.setText("Current");
		txtCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrent.setEditable(false);
		txtCurrent.setColumns(10);
		
		txtActive = new JTextField();
		txtActive.setBounds(294, 27, 64, 20);
		panel.add(txtActive);
		txtActive.setText("Active");
		txtActive.setEditable(false);
		txtActive.setHorizontalAlignment(SwingConstants.CENTER);
		txtActive.setColumns(10);
		
		txtBnpr = new JTextField();
		txtBnpr.setBounds(0, 55, 73, 20);
		panel.add(txtBnpr);
		txtBnpr.setEditable(false);
		txtBnpr.setHorizontalAlignment(SwingConstants.CENTER);
		txtBnpr.setText("BN25-PR2");
		txtBnpr.setColumns(10);
		
		// sprawdzic pozniej jak przy starcie programu zmienic status
			collecting_BN25 = new JLabel("Not collecting");
			collecting_BN25.setBounds(81, 58, 63, 20);
			panel.add(collecting_BN25);
			collecting_BN25.setForeground(Color.RED);
			
			Energy_BN25 = new JTextField();
			Energy_BN25.setBounds(157, 58, 63, 20);
			panel.add(Energy_BN25);
			Energy_BN25.setEditable(false);
			Energy_BN25.setHorizontalAlignment(SwingConstants.CENTER);
			Energy_BN25.setColumns(10);
			
			Current_BN25 = new JTextField();
			Current_BN25.setBounds(227, 58, 63, 20);
			panel.add(Current_BN25);
			Current_BN25.setHorizontalAlignment(SwingConstants.CENTER);
			Current_BN25.setEditable(false);
			Current_BN25.setColumns(10);
			
			Active_BN25 = new JTextField();
			Active_BN25.setBounds(294, 58, 63, 20);
			panel.add(Active_BN25);
			Active_BN25.setHorizontalAlignment(SwingConstants.CENTER);
			Active_BN25.setEditable(false);
			Active_BN25.setColumns(10);
			
			JButton Stop_BN25 = new JButton("Stop");
			Stop_BN25.setBounds(370, 58, 68, 23);
			panel.add(Stop_BN25);
			
			txtPorebapr = new JTextField();
			txtPorebapr.setBounds(0, 86, 73, 20);
			panel.add(txtPorebapr);
			txtPorebapr.setText("Poreba-PR6");
			txtPorebapr.setHorizontalAlignment(SwingConstants.CENTER);
			txtPorebapr.setEditable(false);
			txtPorebapr.setColumns(10);
			
			Collecting_Poreba = new JLabel("Not collecting");
			Collecting_Poreba.setBounds(81, 89, 63, 20);
			panel.add(Collecting_Poreba);
			Collecting_Poreba.setForeground(Color.RED);
			
			Energy_Poreba = new JTextField();
			Energy_Poreba.setBounds(157, 86, 63, 20);
			panel.add(Energy_Poreba);
			Energy_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
			Energy_Poreba.setEditable(false);
			Energy_Poreba.setColumns(10);
			
			Current_Poreba = new JTextField();
			Current_Poreba.setBounds(227, 86, 63, 20);
			panel.add(Current_Poreba);
			Current_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
			Current_Poreba.setEditable(false);
			Current_Poreba.setColumns(10);
			
			Active_Poreba = new JTextField();
			Active_Poreba.setBounds(294, 86, 63, 20);
			panel.add(Active_Poreba);
			Active_Poreba.setHorizontalAlignment(SwingConstants.CENTER);
			Active_Poreba.setEditable(false);
			Active_Poreba.setColumns(10);
			
			JButton Stop_Poreba = new JButton("Stop");
			Stop_Poreba.setBounds(370, 85, 68, 23);
			panel.add(Stop_Poreba);
			Stop_BN25.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(stopped == false)
					{
						l1.suspend();
						stopped = true;
						Stop_BN25.setText("Resume T");
						collecting_BN25.setForeground(Color.RED);
						collecting_BN25.setText("No collecting");
					}
					else if(stopped == true)
					{
				      //  JOptionPane.showMessageDialog(null,  "Thread is inactive already! " );
				        stopped = false;
				        l1.resume();
						Stop_BN25.setText("Stop T");
						collecting_BN25.setForeground(Color.GREEN);
						collecting_BN25.setText("collecting");



					}
				
				}
			});
		frame.getContentPane().add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 164, 428, 79);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("INI file");
		lblNewLabel_1.setBounds(96, 0, 240, 21);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtMachineIp = new JTextField();
		txtMachineIp.setBounds(0, 28, 73, 20);
		panel_1.add(txtMachineIp);
		txtMachineIp.setHorizontalAlignment(SwingConstants.CENTER);
		txtMachineIp.setText("Machine IP");
		txtMachineIp.setEditable(false);
		txtMachineIp.setColumns(10);
		
		txtMeasurmentFreq = new JTextField();
		txtMeasurmentFreq.setBounds(83, 28, 73, 20);
		panel_1.add(txtMeasurmentFreq);
		txtMeasurmentFreq.setText("Survey Freq");
		txtMeasurmentFreq.setHorizontalAlignment(SwingConstants.CENTER);
		txtMeasurmentFreq.setEditable(false);
		txtMeasurmentFreq.setColumns(10);
		
		txtDatabaseSave = new JTextField();
		txtDatabaseSave.setBounds(166, 28, 73, 20);
		panel_1.add(txtDatabaseSave);
		txtDatabaseSave.setText("Freq to DB");
		txtDatabaseSave.setHorizontalAlignment(SwingConstants.CENTER);
		txtDatabaseSave.setEditable(false);
		txtDatabaseSave.setColumns(10);
		
		txtMachineName = new JTextField();
		txtMachineName.setBounds(249, 28, 73, 20);
		panel_1.add(txtMachineName);
		txtMachineName.setText("Machine ID");
		txtMachineName.setHorizontalAlignment(SwingConstants.CENTER);
		txtMachineName.setEditable(false);
		txtMachineName.setColumns(10);
		
		txtRemovalInterval = new JTextField();
		txtRemovalInterval.setBounds(332, 28, 96, 20);
		panel_1.add(txtRemovalInterval);
		txtRemovalInterval.setHorizontalAlignment(SwingConstants.CENTER);
		txtRemovalInterval.setText("Removal Interval");
		txtRemovalInterval.setEditable(false);
		txtRemovalInterval.setColumns(10);
		
		MachineIP = new JTextField();
		MachineIP.setFont(new Font("Tahoma", Font.PLAIN, 9));
		MachineIP.setBounds(0, 59, 73, 20);
		panel_1.add(MachineIP);
		MachineIP.setEditable(false);
		MachineIP.setHorizontalAlignment(SwingConstants.CENTER);
		MachineIP.setColumns(10);
		
		MachineSurvey = new JTextField();
		MachineSurvey.setBounds(83, 59, 73, 20);
		panel_1.add(MachineSurvey);
		MachineSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		MachineSurvey.setEditable(false);
		MachineSurvey.setColumns(10);
		
		MachineFreqToDB = new JTextField();
		MachineFreqToDB.setBounds(166, 59, 73, 20);
		panel_1.add(MachineFreqToDB);
		MachineFreqToDB.setHorizontalAlignment(SwingConstants.CENTER);
		MachineFreqToDB.setEditable(false);
		MachineFreqToDB.setColumns(10);
		
		MachineID = new JTextField();
		MachineID.setBounds(249, 59, 73, 20);
		panel_1.add(MachineID);
		MachineID.setHorizontalAlignment(SwingConstants.CENTER);
		MachineID.setEditable(false);
		MachineID.setColumns(10);
		
		MachineRemInterval = new JTextField();
		MachineRemInterval.setBounds(342, 59, 73, 20);
		panel_1.add(MachineRemInterval);
		MachineRemInterval.setHorizontalAlignment(SwingConstants.CENTER);
		MachineRemInterval.setEditable(false);
		MachineRemInterval.setColumns(10);
		
		Timer = new JTextField();
		Timer.setHorizontalAlignment(SwingConstants.CENTER);
		Timer.setEditable(false);
		Timer.setBounds(319, 11, 130, 20);
		frame.getContentPane().add(Timer);
		Timer.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(8, 254, 430, 87);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		table = new JTable();
		table.setBounds(0, 46, 430, 41);
		panel_2.add(table);
		table.setFillsViewportHeight(true);
		
		JLabel lblNewLabel_2 = new JLabel("Last Records in Database");
		lblNewLabel_2.setBounds(106, 0, 229, 35);
		panel_2.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_2.setForeground(Color.BLACK);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		

	}
}
