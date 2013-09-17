package org.jfree.data.datasetextension;

import java.io.Serializable;

import org.jfree.data.datasetextension.impl.DatasetExtensionManager;
import org.jfree.data.general.Dataset;

/**
 * Base interface for dataset extensions. A dataset extension typically stores
 * additional information for data items (e.g. {@link DatasetSelectionExtension}). Dataset
 * extensions can be implemented in separate helper objects or as part of a datasets. This
 * allows to extend older dataset implementations.<br>
 * <br> 
 * The {@link DatasetExtensionManager} class can be used to pair datasets and external helper
 * objects and allows to treat paired datasets the same way as datasets that directly implement
 * a datasetextension. 
 * 
 * @author zinsmaie
 *
 */
public interface DatasetExtension extends Serializable {

	/**
	 * @return the dataset that is extended
	 */
	public Dataset getDataset();
	
}
