package ee.ut.math.tvt.SETeam42014;

import java.io.IOException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.SETeam42014.IntroUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		IntroUI userInterface = new IntroUI();
		log.info("SalesSystem started");

		System.out.println("TERE");
		// if you want to run this programm, rightclick build.xml -- run as
		// -- ant target (the second option). from targets select "run" and not
		// "build".

		// try {
		// userInterface.getPropertiesValues();
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
