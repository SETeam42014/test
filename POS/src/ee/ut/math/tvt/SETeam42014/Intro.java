package ee.ut.math.tvt.SETeam42014;

import java.io.IOException;


import ee.ut.math.tvt.SETeam42014.IntroUI;

public class Intro {
	 public static void main(String[] args){
		 IntroUI userInterface = new IntroUI();
		 System.out.println("TERE");
		 //if you want to run this programm, rightclick build.xml -- run as
		 //-- ant target (the second option). from targets select "run" and not "build".

		 try {
			userInterface.getPropertiesValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }	
}
