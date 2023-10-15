import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import java.awt.Canvas;
import javax.swing.JRadioButton;
import java.awt.Button;
import javax.swing.JCheckBox;
import java.awt.Panel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Scrollbar;
import java.awt.ScrollPane;

//Eyiara Oladipo
//11/17/2022
//Lab 9
public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField NameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		//Initializing the frame for the user using 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 213, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Application Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1_1 = new JPanel();
		panel.add(panel_1_1);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_2_1 = new JLabel("Name");
		panel_1_1.add(lblNewLabel_2_1);
		
		NameTextField = new JTextField();
		NameTextField.setColumns(10);
		panel_1_1.add(NameTextField);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel GenderTextField = new JLabel("Gender");
		panel_1.add(GenderTextField);
		
		JComboBox GenderComboBox = new JComboBox();
		GenderComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Male", "Female"}));
		panel.add(GenderComboBox);
		
		Panel panel_2 = new Panel();
		panel.add(panel_2);
		
		JCheckBox termsAndConditionsTextBox = new JCheckBox("Accept Terms and Conditions");
		panel_2.add(termsAndConditionsTextBox);
		
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setEditable(false);
		textArea.setColumns(17);
		textArea.setRows(5);
		panel.add(scrollPane);
		
		//Initializing the action listener to add the record to the text area and
		//validate user input
		Button button = new Button("Insert a record");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checking for if any of the fields are empty and showing the appropriate warning
				if(NameTextField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a name");
				}else if(GenderComboBox.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a gender");
				} else if(!termsAndConditionsTextBox.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please acccept our Terms and Conditions");
				} else {
					//Resetting the fields and appending the user's information
					textArea.append(NameTextField.getText() + " " + GenderComboBox.getSelectedItem() +"\n");
					termsAndConditionsTextBox.setSelected(false);
					NameTextField.setText("");
					GenderComboBox.setSelectedIndex(0);				
				}
			}
		});
		contentPane.add(button, BorderLayout.SOUTH);
	}

}
