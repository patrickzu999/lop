package Inventory_Sys;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.Color;

public class Inventory {

	private JFrame frame;
	private JTextField txtID;
	private JTextField txtProduct_Name;
	private JTextField txtPrice;
	private JTextField txtDate;
		
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory window = new Inventory();
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
	public Inventory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 204));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ShowData();
			}
		});
		frame.setBounds(200, 200, 500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblInventoryControl = new JLabel("Inventory Control");
		lblInventoryControl.setBounds(163, 11, 110, 14);
		frame.getContentPane().add(lblInventoryControl);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(60, 52, 62, 14);
		frame.getContentPane().add(lblID);
		
		JLabel lblProduct_Name = new JLabel("Product_Name");
		lblProduct_Name.setBounds(60, 77, 86, 14);
		frame.getContentPane().add(lblProduct_Name);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(60, 102, 62, 14);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(60, 127, 62, 14);
		frame.getContentPane().add(lblDate);
		
		txtID = new JTextField();
		txtID.setBounds(156, 49, 272, 20);
		frame.getContentPane().add(txtID);
		txtID.setColumns(10);
		
		txtProduct_Name = new JTextField();
		txtProduct_Name.setBounds(156, 74, 272, 20);
		frame.getContentPane().add(txtProduct_Name);
		txtProduct_Name.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(156, 99, 272, 20);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(156, 124, 272, 20);
		frame.getContentPane().add(txtDate);
		txtDate.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveToDatabase();
			}
		});
		
		btnSave.setBounds(339, 155, 89, 23);
		frame.getContentPane().add(btnSave);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 189, 368, 76);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String code = table.getValueAt(table.getSelectedRow(), 0).toString();
				setTextField(code);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					Update(txtID.getText());
				}
			}
		});
		btnUpdate.setBounds(166, 155, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) 
					Delete(txtID.getText());
				
			}
		});
		btnDelete.setBounds(252, 155, 89, 23);
		frame.getContentPane().add(btnDelete);
	}
	
	static Connection conn() { /* This function sets up the database connection as 'conn' */
		try {
			String url = "jdbc:mysql://localhost:3306/product_database";
		    String user = "root";
		    String password = "Ak!NGR00BB8!"; /*Input local mysql database password */
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		    return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		}
		
		return null;
	}
	
	private void SaveToDatabase() {
		Connection conn = conn(); /* Calls the function 'conn' and connects to the database*/
		try {
			String query = "INSERT INTO product_table VALUES (?,?,?,?)"; /* 'query' uses SQL language */
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, txtID.getText());
				ps.setString(2, txtProduct_Name.getText());
				ps.setString(3, txtPrice.getText());
				ps.setString(4, txtDate.getText());
				ps.execute();
				JOptionPane.showMessageDialog(null, "Saved!");
				ShowData();
			
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		}
	}
	
	private void ShowData() {
		Connection conn = conn(); /* Calls the function 'conn' and connects to the database*/
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("product_name");
		model.addColumn("price");
		model.addColumn("date");
		try {
			String query = "SELECT * FROM product_table";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("ID"),
						rs.getString("product_name"),
						rs.getString("price"),
						rs.getString("date")
				});
			}
			rs.close();
			st.close();
			conn.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(0).setPreferredWidth(80); 
			table.getColumnModel().getColumn(0).setPreferredWidth(50); 
			table.getColumnModel().getColumn(0).setPreferredWidth(50); 
			
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		
	}
}	
	private void setTextField(String ID) {
		Connection conn = conn(); /* Calls the function 'conn' and connects to the database*/
		try {
			String query = "SELECT * FROM product_table WHERE ID = ?"; /* 'query' uses SQL language */
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtID.setText(rs.getString("ID"));
				txtProduct_Name.setText(rs.getString("product_name"));
				txtPrice.setText(rs.getString("price"));
				txtDate.setText(rs.getString("date"));
			}
			
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		
	}
	}
	private void Update(String ID) {
		Connection conn = conn(); /* Calls the function 'conn' and connects to the database*/
		try {
			String query = "UPDATE product_table SET product_name = ?, price = ?, date = ? WHERE ID = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, txtProduct_Name.getText());
			ps.setString(2, txtPrice.getText());
			ps.setString(3, txtDate.getText());		
			ps.setString(4, ID);			
			ps.execute();
			
			ps.close();
			conn.close();
			JOptionPane.showMessageDialog(null,"Update Complete");
			ShowData();
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		}		
	}
	private void Delete(String ID) {
		Connection conn = conn(); /* Calls the function 'conn' and connects to the database*/
		try {
			String query = "DELETE from product_table WHERE ID = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, txtID.getText());
	
			ps.execute();
			ps.close();
			conn.close();
			JOptionPane.showMessageDialog(null,"Deleted");
			ShowData();
		} catch (Exception e) {
			System.out.println("Connection failed" + e);
		}
	}
}
