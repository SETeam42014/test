package ee.ut.math.tvt.SETeam42014;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

public class IntroUI {

	private static final Logger log = Logger.getLogger(IntroUI.class);

	public IntroUI() {
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
		String[] teamOptions = { "Team leader:", leader, " ",
				"Team leader e-mail:", email, " ", "Other team members:", mem1,
				mem2, mem3, " ", "Version number:", version };

		// new guiFrame created
		JFrame guiFrame = new JFrame();
		// guiFrame close operatsion set to exit_on_close
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// guiFrame title set to team name (SETeam2014)
		guiFrame.setTitle(teamname);
		// guiFrame size and location set
		guiFrame.setSize(600, 400);
		guiFrame.setLocationRelativeTo(null);
		try {
			// guiFrame lookandfeel set to windowslookandfeel
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.warn(e.toString());
		}
		// created new JPanel for info, using borderlayout as argument
		final JPanel listPanel = new JPanel(new BorderLayout());
		// Panel set visible
		listPanel.setVisible(true);
		// new list created with previously defined team
		JList team = new JList(teamOptions);
		// JList team added to Jpanel listPanel
		listPanel.add(team);
		// New button created
		JButton tiimilogo = new JButton("Tiimi logo");
		// chosen picture as team logo from etc folder and added to guiFrame,
		// set to center
		final JLabel pilt = new JLabel(new ImageIcon("etc/panaan2.png"));
		guiFrame.add(pilt, BorderLayout.CENTER);
		// new actionlistener added to team logo- set visible/invisible when
		// clicked
		// on button tiimilogo
		tiimilogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				pilt.setVisible(!pilt.isVisible());
			}
		});
		// listpanel with team members' info added to west and tiimilogo button
		// added to south
		guiFrame.add(listPanel, BorderLayout.WEST);
		guiFrame.add(tiimilogo, BorderLayout.SOUTH);
		// guiFrame set visible
		guiFrame.setVisible(true);
		// Datetime used for timestamp
		Date date = new Date();
		// Window opened log entry
		log.info("IntoUI Window opened. Class: " + log.getName()
				+ ". Timestamp: " + date.toString());
	}
}
