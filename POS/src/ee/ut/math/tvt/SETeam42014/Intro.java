package ee.ut.math.tvt.SETeam42014;

/**
 * KySIMUSED:
 * 
 * KUS TULEKS ALUSTADA DB SERVICE?
 * 
 * MIS VAJADUS ON DOMAINCONTROLLERIL?
 * 
 * Kuidas tuleb ajalugu (ning myydud esemeid) hoida?
 * 
 * Kuidas andmeid mudeli ja DB vahel sünkroonida?
 * 
 * Mida teeb startNewPurchase domain controlleris?
 */
import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";

	public static void main(String[] args) {

		// start DB service
		// final HibernateDataService service = new HibernateDataService();

		// domainController
		final SalesDomainControllerImpl domainController = new SalesDomainControllerImpl();

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// // Datetime used for timestamp
		// Date date = new Date();
		// // Program running log entry
		// log.info("POS started, class name: " + log.getName() +
		// ". Timestamp: "
		// + date.toString());
	}
}
