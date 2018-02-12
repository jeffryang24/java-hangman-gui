package aoop.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JButton;

import aoop.app.*;
import aoop.helper.*;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SplashScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtSplashName;
	
	private Statement st;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SplashScreen frame = new SplashScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SplashScreen() {
		// Initialize DB Connection
		if (!DBContext.Connect()){
			JOptionPane.showMessageDialog(null, "Error connecting to database!", "Error DB", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}else{
			st = DBContext.getStatement();
		}
		
		/*
		 * For debugging only
		 */
		//String db = DBContext.DebugConnect();
		//if (!db.equals("SUCCESS")) JOptionPane.showMessageDialog(null, db, "Error DB", JOptionPane.ERROR_MESSAGE);
		/*
		 * End of debugging code
		 */

		setBackground(UIManager.getColor("EditorPane.inactiveBackground"));
		setTitle("Hangman");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 322);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblSplashInsert = new JLabel("Insert your name");
		lblSplashInsert.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSplashInsert.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblSplashInsert);
		
		txtSplashName = new JTextField();
		txtSplashName.setToolTipText("Insert your name here!");
		txtSplashName.setHorizontalAlignment(SwingConstants.CENTER);
		txtSplashName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(txtSplashName);
		txtSplashName.setColumns(10);
		
		JLabel lblSplashTopic = new JLabel("Choose topic");
		lblSplashTopic.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSplashTopic.setAlignmentX(0.5f);
		panel.add(lblSplashTopic);
		
		JComboBox cbSplashTopic = new JComboBox();
		panel.add(cbSplashTopic);
		
		JLabel lblNewLine = new JLabel("");
		lblNewLine.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel.add(lblNewLine);
		
		JButton btnSplashEnter = new JButton("Enter");
		btnSplashEnter.setMnemonic(KeyEvent.VK_E);
		btnSplashEnter.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSplashEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnSplashEnter);
		
		JLabel lblNewLine2 = new JLabel("");
		lblNewLine2.setBorder(new EmptyBorder(15, 0, 0, 0));
		panel.add(lblNewLine2);
		
		JButton btnAddTopic = new JButton("Create New Topic");
		btnAddTopic.setMnemonic(KeyEvent.VK_N);
		btnAddTopic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddTopic.setBackground(Color.GREEN);
		btnAddTopic.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnAddTopic);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblAoop = new JLabel("\u00A9 2016 AOOP Team");
		panel_1.add(lblAoop);
		
		JPanel pnlUpper = new JPanel();
		contentPane.add(pnlUpper, BorderLayout.NORTH);
		
		JLabel lblSplashTitle = new JLabel("Hangman");
		lblSplashTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSplashTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblSplashTitle.setBorder(new EmptyBorder(20, 0, 0, 0));
		pnlUpper.add(lblSplashTitle);
		
		// Logic
		// load data to combo box
		String query = "select * from TopicHeader";
		ResultSet rs;
		try {
			rs = st.executeQuery(query);
			if (rs.isBeforeFirst()){
				// if has one/more data, then pass to combobox
				cbSplashTopic.addItem("Choose your topic");
				while(rs.next()){
					cbSplashTopic.addItem(rs.getString("TopicDesc"));
				}
				cbSplashTopic.setSelectedIndex(0);
			}else{
				// force user to add new topic and disable enter button
				cbSplashTopic.addItem("No Topic Available!");
				cbSplashTopic.setSelectedIndex(0);
				btnSplashEnter.setEnabled(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		// Finally Show it!!!
		setVisible(true);
	}

}
