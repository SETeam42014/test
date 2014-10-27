package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class InfoTab {
	// TODO - implement!
	private static final Logger log = Logger.getLogger(InfoTab.class);
    public InfoTab() {} 
    
    public Component draw() {
        JPanel panel = new JPanel(new BorderLayout());
      //guiAken = new JFrame();

      		// created prop to get Properties
      		Properties prop = new Properties();
      		// propFileName given value to get information from version.properties
      		String propFileName = "version.properties";
      		// inputstream from version.properties
      		InputStream inputStream = getClass().getClassLoader()
      				.getResourceAsStream(propFileName);
      		try {
      			// inputstream executed
      			prop.load(inputStream);
      		} catch (IOException e) {
      			// TODO Auto-generated catch block
      			// e.printStackTrace();
      			log.warn(e.toString());
      		}
      		// version given build number value from value.properties
      		String version = prop.getProperty("build.number");
      		// propFileName changed to application.properties
      		propFileName = "application.properties";
      		// new inputstream created
      		InputStream inputStream2 = getClass().getClassLoader()
      				.getResourceAsStream(propFileName);
      		try {
      			// new inputstream executed
      			prop.load(inputStream2);
      		} catch (IOException e) {
      			// TODO Auto-generated catch block
      			// e.printStackTrace();
      			log.warn(e.toString());
      		}
      		// getting team members and leader info from application.properties
      		String leader = prop.getProperty("team.leader");
      		String email = prop.getProperty("team.email");
      		String mem1 = prop.getProperty("team.mem1");
      		String mem2 = prop.getProperty("team.mem2");
      		String mem3 = prop.getProperty("team.mem3");
      		// team name from application.properties
      		String teamname = prop.getProperty("team.name");
      		// string list created using info from application.properties
      		String[] teamOptions = { "Team name:", teamname, " ","Team leader:", leader, " ",
      				"Team leader e-mail:", email, " ", "Other team members:", mem1,
      				mem2, mem3, " ", "Version number:", version };

      		// created new JPanel for info, using borderlayout as argument
      		// Panel set visible
      		panel.setVisible(true);
      		// new list created with previously defined team
      		JList team = new JList(teamOptions);
      		// JList team added to Jpanel listPanel
      		panel.add(team, BorderLayout.WEST);
      		// New button created
      		JButton tiimilogo = new JButton("Tiimi logo");
      		// chosen picture as team logo from etc folder and added to guiFrame,
      		// set to center
      		final JLabel pilt = new JLabel(new ImageIcon("etc/panaan2.png"));///resources/ juurde lisama, et panaani syya kuhugi
      		panel.add(pilt, BorderLayout.CENTER);
      		// new actionlistener added to team logo- set visible/invisible when
      		// clicked
      		// on button tiimilogo
      		tiimilogo.addActionListener(new ActionListener() {
      			@Override
      			public void actionPerformed(ActionEvent event) {
      				pilt.setVisible(!pilt.isVisible());
      			}
      		});
      		//button tiimilogo added to InfoTab
      		panel.add(tiimilogo, BorderLayout.SOUTH);
      		// guiFrame set visible
      		// Datetime used for timestamp
      		Date date = new Date();
      		// Window opened log entry
      		log.info("Teaminfo in InfoTab opened. Class: " + log.getName()
      				+ ". Timestamp: " + date.toString());
        return panel;
    }
}
