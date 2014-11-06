package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;

/**
 * Base interface for data items, so one JTable can be used to display different
 * entities.
 */
public interface DisplayableItem extends Serializable {
	/**
	 * Id of entity.
	 */
	public Long getId();

	/**
	 * Name of entity
	 * 
	 * @return String
	 */
	public String getName();
}
