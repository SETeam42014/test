package ee.ut.math.tvt.SETeam42014;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class IntroUI {




	/**
	 * Graphical user interface of the sales system.
	 * @return 
	 */

	
	public String getPropertiesValues() throws IOException{
		 
		 String result="";
		 Properties properties = new Properties();
		 String propertiesFileName = "version.properties";
		 
		 InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
		 properties.load(inputStream);
		 if (inputStream == null){
			 throw new FileNotFoundException("Property file " + propertiesFileName + " not found in the directory");
		 }
		 
		 Date time = new Date (System.currentTimeMillis());
		 
		 //get property value and print it out
		 String buildNumber = properties.getProperty("build.number");
		 
		 result=buildNumber;
		 
		 System.out.println(result + "\nProgram ran on " + time);
		 
		 return result;
	 }
	  public void SalesSystemUI() {
	 

	    setTitle("Sales system");

	    // set L&F to the nice Windows style
	    try {
	      UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());

	    } catch (UnsupportedLookAndFeelException e1) {
	      //log.warn(e1.getMessage());
	    }

	    drawWidgets();

	    // size & location
	    int width = 600;
	    int height = 400;
	    setSize(width, height);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screen.width - width) / 2, (screen.height - height) / 2);

	    addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	  }
	  

	  private void addWindowListener(WindowAdapter windowAdapter) {
		// TODO Auto-generated method stub
		
	  ;}

	private void setLocation(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	private void setSize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	private void setTitle(String string) {
		// TODO Auto-generated method stub
		
	}

	private void drawWidgets() {

	  }
}

