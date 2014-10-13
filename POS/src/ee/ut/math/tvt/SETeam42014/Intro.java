package ee.ut.math.tvt.SETeam42014;

import java.util.Date;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.SETeam42014.IntroUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		// Datetime used for timestamp
		Date date = new Date();
		// Program running log entry
		log.info("POS started, class name: " + log.getName() + ". Timestamp: "
				+ date.toString());
		// testing purposes
		// System.out.println("TERE");

		// IntroUi used to get GUI
		IntroUI userInterface = new IntroUI();
	}
}
