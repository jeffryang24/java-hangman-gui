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
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class Hangman extends JFrame implements KeyListener {

	private JPanel contentPane;
	
	private String sql;
	private Statement st = DBContext.getStatement();
	private Vector<JTextField> listTextField = new Vector<JTextField>();
	private ArrayList<String> listKata = new ArrayList<String>();
	private ArrayList<Integer> letakHuruf = new ArrayList<Integer>();
	private int totalWord = 0, totalAnswered = 0, totalTry = 0, allowedTry = 5;
	private String curWord = "";
	
	// Public scope component
	private JLabel lblTryInfo = new JLabel("%TRY%");

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

	/**
	 * Create the frame.
	 */
	public Hangman() {
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
		
		JLabel lblScoreText = new JLabel("%answered%/%total%");
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
		
		JPanel pnlMiddle = new JPanel();
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		// get input key
		String keyPressed = e.getKeyText(e.getKeyCode());
		
		// Ignore F1-F12 key
		if (keyPressed.length() > 1) return;
		
		// find occurence word
		if (curWord.toLowerCase().contains(keyPressed.toLowerCase())){
			for(int i=0;i<curWord.length();i++){
				if (keyPressed.equalsIgnoreCase(String.valueOf(curWord.charAt(i)))){
					letakHuruf.add(i);
				}else{
					// clear the textbox
					for(int j=0;j<listTextField.size();j++){
						if (source == listTextField.get(j)){
							JTextField tmp = listTextField.get(j);
							tmp.setText("");
							listTextField.set(j, tmp);
							
							// use try
							totalTry++;
							lblTryInfo.setText(String.valueOf(totalTry) + "/" + String.valueOf(allowedTry));
						}
					}
				}
			}
		}
		
		
		// check if total try is bigger than allowed try,
		// if true, then game over
		if (totalTry > allowedTry){
			JOptionPane.showMessageDialog(null, "Game Over! Thanks for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		// cek kalau jawaban sudah benar semua
		if (listTextField.size() == 0){
			totalAnswered += 1;
			return;
		}
		
		for(int k=0;k<listTextField.size();k++){
			if (source == listTextField.get(k)){
				for(int j=0;j<letakHuruf.size();j++){
					JTextField tmp = listTextField.get(letakHuruf.get(j));
					tmp.setText(String.valueOf(curWord.charAt(letakHuruf.get(j))));
					tmp.setEnabled(false);
					listTextField.set(letakHuruf.get(j), tmp);
				}
			}
		}
	}

}
