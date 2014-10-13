package ee.ut.math.tvt.SETeam42014;

import java.io.IOException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.SETeam42014.IntroUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		//just some politeness
		System.out.println("TERE");
		//IntroUi used to gei GUI
		IntroUI userInterface = new IntroUI();
		//Gets started classname from log
		log.info("POS started, class name: " + log.getName());

		// if you want to run this programm, rightclick build.xml -- run as
		// -- ant target (the second option). from targets select "run" and not
		// "build".


	}
}
