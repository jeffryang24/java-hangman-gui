package aoop.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aoop.helper.*;
import sun.misc.JavaLangAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Hangman extends JFrame implements KeyListener {

	private JPanel contentPane;
	
	private String sql;
	private Statement st = DBContext.getStatement();
	private Vector<JTextField> listTextField = new Vector<JTextField>();
	private ArrayList<String> listKata = new ArrayList<String>();
	private ArrayList<String> listHurufPressed = new ArrayList<String>();
	private ArrayList<Integer> letakHuruf = new ArrayList<Integer>();
	private int totalWord = 0, totalAnswered = 0, totalTry = 0, allowedTry = 5;
	private String curWord = "";
	
	public static String UserName,TopicName;
	
	// Public scope component
	private JLabel lblTryInfo = new JLabel("%TRY%");
	private JPanel pnlMiddle = new JPanel();
	private static JTextField txtUserInput;
	private JLabel lblScoreText = new JLabel("%answered%/%total%");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman frame = new Hangman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 * @desc: call this function from another class,
	 * 		  don't use default constructor
	 */
	public static void OpenMe(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman frame = new Hangman(UserName, TopicName);
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Hangman() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txtUserInput.requestFocus();
			}
		});
		setResizable(false);
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlUpper = new JPanel();
		pnlUpper.setPreferredSize(new Dimension(10, 65));
		FlowLayout flowLayout = (FlowLayout) pnlUpper.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlUpper, BorderLayout.NORTH);
		
		JLabel lblScore = new JLabel("Score: ");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblScore);
		
		JLabel lblScoreText = new JLabel("%answered%/%total%");
		lblScoreText.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlUpper.add(lblScoreText);
		
		JLabel lblSpacing1 = new JLabel(" ");
		lblSpacing1.setPreferredSize(new Dimension(350, 14));
		pnlUpper.add(lblSpacing1);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblName);
		
		JLabel lblNameText = new JLabel("%name%");
		lblNameText.setPreferredSize(new Dimension(280, 14));
		lblNameText.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlUpper.add(lblNameText);
		
		JLabel lblTopic = new JLabel("Topic: ");
		lblTopic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblTopic);
		
		JLabel lblTopicText = new JLabel("%topic%");
		lblTopicText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTopicText.setPreferredSize(new Dimension(850, 14));
		pnlUpper.add(lblTopicText);
		
		JPanel pnlMiddle = new JPanel();
		contentPane.add(pnlMiddle, BorderLayout.CENTER);
		pnlMiddle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel pnlLeft = new JPanel();
		contentPane.add(pnlLeft, BorderLayout.WEST);
		
		JLabel lblHangmanPic = new JLabel("%lblhangman%");
		lblHangmanPic.setSize(new Dimension(350, 400));
		lblHangmanPic.setPreferredSize(new Dimension(350, 400));
		// Load Image Icon
		try {
			ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("/assets/images/icon.png")).getImage().getScaledInstance(lblHangmanPic.getWidth(), lblHangmanPic.getHeight(), Image.SCALE_SMOOTH));
			lblHangmanPic.setIcon(img);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		pnlLeft.add(lblHangmanPic);	
		
		JPanel pnlBottom = new JPanel();
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHangman = new JLabel("%HANGMAN%");
		lblHangman.setFont(new Font("Tahoma", Font.BOLD, 19));
		pnlBottom.add(lblHangman);
		
		JLabel lblTryInfo = new JLabel("%TRY%");
		lblTryInfo.setFont(new Font("Tahoma", Font.BOLD, 19));
		pnlBottom.add(lblTryInfo);
		
		JPanel pnlRight = new JPanel();
		contentPane.add(pnlRight, BorderLayout.EAST);
		
		txtUserInput = new JTextField();
		txtUserInput.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlRight.add(txtUserInput);
		txtUserInput.setColumns(10);
	}
	
	/*
	 * @param: UserID (int), TopicName (String)
	 */
	public Hangman(String UserName, String TopicName) {
		// Initialize DBConnection
		DBContext.Connect();

		// Get Words
		ResultSet rs;
		sql = "select b.answer from TopicHeader a, TopicDetail b where a.topicid=b.topicid and a.topicdesc='" + TopicName + "'";
		try {
			rs = st.executeQuery(sql);
			while(rs.next()){
				listKata.add(rs.getString("answer"));
			}
			rs.last();
			totalWord = rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setResizable(false);
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlUpper = new JPanel();
		pnlUpper.setPreferredSize(new Dimension(10, 60));
		FlowLayout flowLayout = (FlowLayout) pnlUpper.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlUpper, BorderLayout.NORTH);
		
		JLabel lblScore = new JLabel("Score: ");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblScore);
		
		lblScoreText.setFont(new Font("Tahoma", Font.BOLD, 15));
		// Set Initial Score
		lblScoreText.setText(String.valueOf(totalAnswered) + "/" + String.valueOf(totalWord));
		pnlUpper.add(lblScoreText);
		
		JLabel lblSpacing1 = new JLabel(" ");
		lblSpacing1.setPreferredSize(new Dimension(350, 14));
		pnlUpper.add(lblSpacing1);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblName);
		
		JLabel lblNameText = new JLabel("%name%");
		lblNameText.setPreferredSize(new Dimension(275, 14));
		lblNameText.setFont(new Font("Tahoma", Font.BOLD, 15));
		// Set Username
		lblNameText.setText(UserName);
		pnlUpper.add(lblNameText);
		
		JLabel lblTopic = new JLabel("Topic: ");
		lblTopic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlUpper.add(lblTopic);
		
		JLabel lblTopicText = new JLabel("%topic%");
		lblTopicText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTopicText.setPreferredSize(new Dimension(850, 14));
		// Set Current Topic
		lblTopicText.setText(TopicName);
		pnlUpper.add(lblTopicText);
		
		contentPane.add(pnlMiddle, BorderLayout.CENTER);
		pnlMiddle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		// add first occurence word
		// get random word from list
		String firstWord = listKata.get(RNG.randomNumber(0, listKata.size() - 1));
		curWord = firstWord;
		// allocate the textbox based on the word length
		for(int i=0;i<firstWord.length();i++){
			JTextField txtTemp = new JTextField(10);
			txtTemp.setFont(new Font("Tahoma", Font.BOLD, 20));
			txtTemp.setHorizontalAlignment(JTextField.CENTER);
			txtTemp.setEditable(false);
			txtTemp.addKeyListener(this);
			listTextField.addElement(txtTemp);
			pnlMiddle.add(listTextField.get(i));
		}
		
		JPanel pnlLeft = new JPanel();
		contentPane.add(pnlLeft, BorderLayout.WEST);
		
		JLabel lblHangmanPic = new JLabel("%lblhangman%");
		lblHangmanPic.setSize(new Dimension(350, 400));
		lblHangmanPic.setPreferredSize(new Dimension(350, 400));
		// Load Image
		try {
			ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("/assets/images/icon.png")).getImage().getScaledInstance(lblHangmanPic.getWidth(), lblHangmanPic.getHeight(), Image.SCALE_SMOOTH));
			lblHangmanPic.setIcon(img);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		pnlLeft.add(lblHangmanPic);	
		
		JPanel pnlBottom = new JPanel();
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHangman = new JLabel("%HANGMAN%");
		lblHangman.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblHangman.setText("Your try: ");
		pnlBottom.add(lblHangman);
		
		lblTryInfo.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblTryInfo.setText(String.valueOf(totalTry) + "/" + String.valueOf(allowedTry));
		pnlBottom.add(lblTryInfo);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int UserReply = JOptionPane.showConfirmDialog(null, "Are you sure want to back to main menu? Current condition will not be saved to database!", "Are you sure?", JOptionPane.YES_NO_OPTION);
				if (UserReply == JOptionPane.YES_OPTION){
					SplashScreen app = new SplashScreen();
					app.main(null);
					setVisible(false);
				}
			}
		});
		btnBackToMenu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pnlBottom.add(btnBackToMenu);
		
		JPanel pnlRight = new JPanel();
		contentPane.add(pnlRight, BorderLayout.EAST);
		
		txtUserInput = new JTextField();
		txtUserInput.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtUserInput.addKeyListener(this);
		pnlRight.add(txtUserInput);
		txtUserInput.setColumns(10);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txtUserInput.requestFocus();
			}
		});
		
		setLocationRelativeTo(null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		e.consume();
		Object source = e.getSource();
		
		// get input key
		String keyPressed = e.getKeyText(e.getKeyCode());
		
		// Ignore F1-F12 key
		if (source == txtUserInput){
			JTextField t = (JTextField)source;
			t.setText("");
			//JOptionPane.showMessageDialog(null, keyPressed);
			if (keyPressed.length() > 1){
				t.setText("");
				return;
			}
			
			// check user inputted character
			
			// check user's character is exist or not?
			if (listHurufPressed.contains(keyPressed.toLowerCase())) return;
			
			if (curWord.toLowerCase().contains(keyPressed.toLowerCase())){
				listHurufPressed.add(keyPressed.toLowerCase());
				for(int i=0;i<curWord.length();i++){
					if (keyPressed.equalsIgnoreCase(String.valueOf(curWord.charAt(i)))){
						letakHuruf.add(i);
					}
				}
			}else{
				t.setText("");
				
				totalTry++;
				lblTryInfo.setText(String.valueOf(totalTry) + "/" + String.valueOf(allowedTry));
			}		
			
			// check if total try is bigger than allowed try,
			// if true, then game over
			if (totalTry > allowedTry){
				// save to database, current user data
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sql = "insert into Score (userid, topicid, totalanswered, datetested) " + 
						"select a.userid, b.topicid, " + totalAnswered + ",'" + sdf.format(dt) + "' " + 
						"from User a, TopicHeader b where a.username='" + UserName + "' and b.topicdesc='" + TopicName + "'";
				try {
					st.executeUpdate(sql);
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				JOptionPane.showMessageDialog(null, "Game Over! Thanks for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				
				SplashScreen app = new SplashScreen();
				app.main(null);
				
				setVisible(false);
			}
			
			int total_unsolved = 0;
			for(int i=0;i<listTextField.size();i++){
				JTextField t1 = listTextField.get(i);
				if (t1.isEnabled()) total_unsolved++;
			}
			
			// total_unsolved minus 1 for fixing realtime issue
			if (total_unsolved > 0){
				for (int j=0;j<letakHuruf.size();j++){
					JTextField tmp = listTextField.get(letakHuruf.get(j));
					tmp.setText(String.valueOf(curWord.charAt(letakHuruf.get(j))));
					tmp.setEnabled(false);
					listTextField.set(letakHuruf.get(j), tmp);
				}
			}
			
			total_unsolved = 0;
			for(int i=0;i<listTextField.size();i++){
				JTextField t1 = listTextField.get(i);
				if (t1.isEnabled()) total_unsolved++;
			}
			
			if (total_unsolved == 0){
				totalAnswered += 1;
				
				// print score
				lblScoreText.setText(String.valueOf(totalAnswered) + "/" + String.valueOf(totalWord));
				
				// reset try
				totalTry = 0;
				
				// print try info
				lblTryInfo.setText(String.valueOf(totalTry) + "/" + String.valueOf(allowedTry));
				
				// check if user has finished all the test
				if (totalAnswered == totalWord){
					// save to database, current user data
					Date dt = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql = "insert into Score (userid, topicid, totalanswered, datetested) " + 
							"select a.userid, b.topicid, " + totalAnswered + ",'" + sdf.format(dt) + "' " + 
							"from User a, TopicHeader b where a.username='" + UserName + "' and b.topicdesc='" + TopicName + "'";
					try {
						st.executeUpdate(sql);
					} catch (SQLException ex) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					// set congratulations
					JOptionPane.showMessageDialog(null, "Congratulations! You have been successfully completed all of the topic words! Well Done!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					
					SplashScreen app = new SplashScreen();
					app.main(null);
					
					System.exit(0);
				}
				
				// remove current word from listKata
				listKata.remove(curWord);
				
				curWord = listKata.get(RNG.randomNumber(0, listKata.size() - 1));
				letakHuruf.clear();
				listHurufPressed.clear();
				listTextField.clear();
				
				pnlMiddle.removeAll();
				pnlMiddle.revalidate();
				pnlMiddle.repaint();
				
				for(int i=0;i<curWord.length();i++){
					JTextField txtTemp = new JTextField(10);
					txtTemp.setFont(new Font("Tahoma", Font.BOLD, 20));
					txtTemp.setHorizontalAlignment(JTextField.CENTER);
					txtTemp.setEditable(false);
					txtTemp.addKeyListener(this);
					listTextField.addElement(txtTemp);
					pnlMiddle.add(listTextField.get(i));
				}
				
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
