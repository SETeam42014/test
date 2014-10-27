/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.exception;

/**
 * @author Johani
 *
 */
public class OutOfStockException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new <code>VerificationFailedException</code>.
	 */
	public OutOfStockException() {
		super();
	}

	/**
	 * Constructs new <code>VerificationFailedException</code> with with the
	 * specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public OutOfStockException(final String message) {
		super(message);
	}
}
