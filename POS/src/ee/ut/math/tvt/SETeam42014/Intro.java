package ee.ut.math.tvt.SETeam42014;

/**
 * KySIMUSED:
 * 
 * KUS TULEKS ALUSTADA DB SERVICE?
 * 
 * MIS VAJADUS ON DOMAINCONTROLLERIL?
 */
import java.util.Date;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";

	public static void main(String[] args) {

		// start DB service
		final HibernateDataService service = new HibernateDataService();

		// domainController
		final SalesDomainController domainController = new SalesDomainControllerImpl();

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			// IntroUI introUI = new IntroUI();
			// introUI.setVisible(true);
			// introUI.setAlwaysOnTop(true);

			final SalesSystemUI ui = new SalesSystemUI(domainController,
					service);
			ui.setVisible(true);

			// introUI.setAlwaysOnTop(false);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// introUI.setVisible(false);
		}
		// Datetime used for timestamp
		Date date = new Date();
		// Program running log entry
		log.info("POS started, class name: " + log.getName() + ". Timestamp: "
				+ date.toString());
		// testing purposes
		// System.out.println("TERE");

	}
}
