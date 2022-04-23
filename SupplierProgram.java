package SupplierProgram;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SupplierProgram{
    public static void main(String[] args){
        new frame();
    }
}
class frame extends JFrame implements ActionListener{
    JLabel label1,label2,label3,label4,label5;
    JTextField text1,text2,text3,text4,text5;
    JButton button1;
    public frame(){
        setLayout(null);
        calendar cal=new calendar();
        label1=new JLabel("Supplier Name");
        label1.setForeground(Color.red);
        label1.setFont(new Font("Times New Roman",Font.BOLD,20));
        label1.setBounds(20,40,200,50);
        add(label1);
        text1=new JTextField();
        text1.setForeground(Color.black);
        text1.setFont(new Font("Times New Roman",Font.BOLD,20));
        text1.setBounds(200,50,200,30);
        add(text1);
        label2=new JLabel("Item Description");
        label2.setForeground(Color.red);
        label2.setFont(new Font("Times New Roman",Font.BOLD,20));
        label2.setBounds(20,90,200,50);
        add(label2);
        text2=new JTextField();
        text2.setForeground(Color.black);
        text2.setFont(new Font("Times New Roman",Font.BOLD,20));
        text2.setBounds(200,100,200,30);
        add(text2);
        label3=new JLabel("Item Price");
        label3.setForeground(Color.red);
        label3.setFont(new Font("Times New Roman",Font.BOLD,20));
        label3.setBounds(20,140,200,50);
        add(label3);
        text3=new JTextField();
        text3.setForeground(Color.black);
        text3.setFont(new Font("Times New Roman",Font.BOLD,20));
        text3.setBounds(200,150,200,30);
        add(text3);
        label4=new JLabel("Received Date");
        label4.setForeground(Color.red);
        label4.setFont(new Font("Times New Roman",Font.BOLD,20));
        label4.setBounds(20,190,200,50);
        add(label4);
        text4=new JTextField();
        text4.setForeground(Color.black);
        text4.setFont(new Font("Times New Roman",Font.BOLD,20));
        text4.setBounds(200,200,200,30);
        text4.setText(cal.getDate());
        add(text4);
        label5=new JLabel("Quantity");
        label5.setForeground(Color.red);
        label5.setFont(new Font("Times New Roman",Font.BOLD,20));
        label5.setBounds(20,240,200,50);
        add(label5);
        text5=new JTextField();
        text5.setForeground(Color.black);
        text5.setFont(new Font("Times New Roman",Font.BOLD,20));
        text5.setBounds(200,250,200,30);
        add(text5);
        button1=new JButton("Save");
        button1.setForeground(Color.red);
        button1.setFont(new Font("Times New Roman",Font.BOLD,20));
        button1.setBounds(260,290,100,30);
        add(button1);
        button1.addActionListener(this);
        setVisible(true);
        setResizable(false);
        setSize(450,400);
        setTitle("Supplier Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5486622","sql5486622","IbRuiWFJyJ");
            String query="INSERT INTO supplier (supp_name,item_desc,price,date,quantity) VALUES (?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,text1.getText());
            ps.setString(2,text2.getText());
            ps.setFloat(3,Float.parseFloat(text3.getText()));
            ps.setString(4,text4.getText());
            ps.setInt(5,Integer.parseInt(text5.getText()));
            ps.execute();
            con.close();
        }
        catch(Exception ex){
            System.out.print(ex);
        }
        display viewdata=new display();
    }
}
class calendar{
    public String getDate(){
        Calendar cal=Calendar.getInstance();
        String days=Integer.toString(cal.get(Calendar.DATE));
        int month=cal.get(Calendar.MONTH)+1;
        String months=Integer.toString(month);
        String years=Integer.toString(cal.get(Calendar.YEAR));
        return days+"-"+months+"-"+years;
    }
}
class display extends JFrame implements ActionListener{
    JLabel id,sname,idesc,iprice,date,qty;
    JTextField ids,snames,idescs,iprices,dates,qtys;
    JButton show;
    public display(){
        setLayout(null);
        show=new JButton("Display");
        show.setForeground(Color.red);
        show.setFont(new Font("Times New Roman",Font.BOLD,20));
        show.setBounds(260,10,100,30);
        add(show);
        show.addActionListener(this);
        setVisible(true);
        setResizable(false);
        setSize(900,1200);
        setTitle("Database Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5486622","sql5486622","IbRuiWFJyJ");
            String query="SELECT * FROM supplier";
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            id=new JLabel("ID");
            id.setForeground(Color.red);
            id.setFont(new Font("Times New Roman",Font.BOLD,20));
            id.setBounds(20,40,100,50);
            add(id);
            sname=new JLabel("Supplier Name");
            sname.setForeground(Color.red);
            sname.setFont(new Font("Times New Roman",Font.BOLD,20));
            sname.setBounds(120,40,100,50);
            add(sname);
            idesc=new JLabel("Item Description");
            idesc.setForeground(Color.red);
            idesc.setFont(new Font("Times New Roman",Font.BOLD,20));
            idesc.setBounds(220,40,100,50);
            add(idesc);
            iprice=new JLabel("Price");
            iprice.setForeground(Color.red);
            iprice.setFont(new Font("Times New Roman",Font.BOLD,20));
            iprice.setBounds(350,40,100,50);
            add(iprice);
            date=new JLabel("Date");
            date.setForeground(Color.red);
            date.setFont(new Font("Times New Roman",Font.BOLD,20));
            date.setBounds(450,40,100,50);
            add(date);
            qty=new JLabel("Quantity");
            qty.setForeground(Color.red);
            qty.setFont(new Font("Times New Roman",Font.BOLD,20));
            qty.setBounds(550,40,100,50);
            add(qty);
            int j=0;
            while(rs.next()){
                j++;
                ids=new JTextField(rs.getString(1));
                ids.setForeground(Color.black);
                ids.setFont(new Font("Times New Roman",Font.BOLD,20));
                ids.setBounds(20,40*j+40,100,50);
                add(ids);
                snames=new JTextField(rs.getString(2));
                snames.setForeground(Color.black);
                snames.setFont(new Font("Times New Roman",Font.BOLD,20));
                snames.setBounds(120,40*j+40,100,50);
                add(snames);
                idescs=new JTextField(rs.getString(3));
                idescs.setForeground(Color.black);
                idescs.setFont(new Font("Times New Roman",Font.BOLD,20));
                idescs.setBounds(220,40*j+40,100,50);
                add(idescs);
                iprices=new JTextField(rs.getString(4));
                iprices.setForeground(Color.black);
                iprices.setFont(new Font("Times New Roman",Font.BOLD,20));
                iprices.setBounds(350,40*j+40,100,50);
                add(iprices);
                dates=new JTextField(rs.getString(5));
                dates.setForeground(Color.black);
                dates.setFont(new Font("Times New Roman",Font.BOLD,20));
                dates.setBounds(450,40*j+40,100,50);
                add(dates);
                qtys=new JTextField(rs.getString(6));
                qtys.setForeground(Color.black);
                qtys.setFont(new Font("Times New Roman",Font.BOLD,20));
                qtys.setBounds(550,40*j+40,100,50);
                add(qtys);
            }
            con.close();
        }
        catch(Exception ex){
            System.out.print(ex);
        }
    }
}
