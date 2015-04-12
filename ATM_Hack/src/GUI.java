import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

	private JButton enter;
	private JButton reset;
	private JLabel address;
	private JLabel title;
	private JTextField addy;
	private ImageIcon header;
	private ImageIcon atm;
	private JLabel[] result = new JLabel[10];
	ArrayList<ATM> atms;

	public GUI(ArrayList<ATM> atms){
		//setLayout(new GridBagLayout());
		this.atms = atms;
		JPanel panel = new JPanel(new GridBagLayout());
		address = new JLabel("Address:");
		addy = new JTextField(15);
		enter = new JButton("Enter!");
		enter.addActionListener(this);
		reset = new JButton("Reset!");
		reset.addActionListener(this);

		this.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagConstraints c = new GridBagConstraints();
		// add in image
		// image = new ImageIcon(getClass().getResource("path"));
		// label = new JLabel(image);
		// add(label);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		header = new ImageIcon(getClass().getResource("header.png"));
		JLabel label1 = new JLabel(header);
		panel.add(label1, c); // good 
		c.insets = new Insets(20,10,10,10);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(address, c); // good
		c.gridx = 1;
		c.gridy = 1;
		panel.add(addy, c); // good
		atm = new ImageIcon(getClass().getResource("atm.png"));
		JLabel label2 = new JLabel(atm);
		c.insets = new Insets(10,10,10,10);
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 2;
		panel.add(label2, c); // good ? 
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		panel.add(enter, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		panel.add(reset, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		title = new JLabel(new ImageIcon(getClass().getResource("default_title.png"))); // make image and change out
		panel.add(title, c);
		c.insets = new Insets(5, 10, 5,10);
		for(int i = 0; i < 10; i++) {
			c.gridy = 6 + i*2;
			int temp = i + 1;
			result[i] = new JLabel("");
			panel.add(result[i], c);
		}
		this.setInitial(atms);
	}
	
	public void setInitial(ArrayList<ATM> atms) {
		Collections.sort(atms);
		for (int i = 0; i < 10; i++) {
			int temp = i + 1;
			result[i].setText(temp + ". [Rank "+ atms.get(i).getRanking() + "] " + atms.get(i).getAddress());
			result[i].setVisible(true);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enter) {
			// need to take the values from lat and lng and input it into findATMs
			title.setIcon(new ImageIcon(getClass().getResource("new_title.png")));
			double lng = 0, lat = 0;

			String s =  addy.getText();
			String sc = parse(s);
			try {
				URL u = new URL(sc);
				URLConnection yc = u.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader( yc.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null){
					if(inputLine.compareTo("   <location>") == 0){
						inputLine = in.readLine();
						inputLine.replaceAll(" ", "");
						int i = inputLine.length();
						String vlat = inputLine.substring(9, i-6);
						lat = Double.parseDouble(vlat);
						inputLine = in.readLine();
						inputLine.replaceAll(" ", "");
						i = inputLine.length();
						String vlng = inputLine.substring(9, i-6);
						lng = Double.parseDouble(vlng);
						System.out.println("GOOGLE API WORKED");
					}      
				}
				in.close();
			} catch (Exception ex) {

			}

			//double latData = Double.parseDouble(addy.getText());

			String input = RealDriver.processData(lng, lat);
			String[] print = input.split("!");
			int counter = 1;
			for (int i = 1; i < print.length; i++) {
				result[i - 1].setText(print[i]);
				result[i - 1].setVisible(true);
				counter++;
			}
			counter--;
			while (counter < 10) {
				result[counter].setVisible(false);
				counter++;
			}
		} else if (e.getSource() == reset) {
			title.setIcon(new ImageIcon(getClass().getResource("default_title.png")));
			this.setInitial(atms);
		}

	}

	private static String parse(String s) {
		s = s.replaceAll(" ", "+");
		s = "https://maps.googleapis.com/maps/api/geocode/xml?address="
				+ s + "";

		return s;
	}

}
