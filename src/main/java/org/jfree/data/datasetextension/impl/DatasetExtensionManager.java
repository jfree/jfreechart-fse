package org.jfree.data.datasetextension.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.datasetextension.DatasetExtension;
import org.jfree.data.general.Dataset;

/**
 * Allows the handling of separate {@link DatasetExtension}. Pairs a dataset and a DatasetExtension together and
 * provides unified access to DatasetExtensions regardless of their implementation (in a dataset or separate)
 * 
 * @author zinsmaie
 *
 */
public class DatasetExtensionManager implements Serializable {

	/** a generated serial id */
	private static final long serialVersionUID = 3727659792806462637L;
	
	/**
	 * all separate extensions have to be registered here
	 */
	private HashMap<Dataset, List<DatasetExtension>> registeredExtensions = new HashMap<Dataset, List<DatasetExtension>>(); 
	
	/**
	 * Registers a separate dataset extension at the extension manager (the extension is automatically paired
	 * with its dataset)
	 * @param extension 
	 */
	public void registerDatasetExtension(DatasetExtension extension) {
		List<DatasetExtension> extensionList = registeredExtensions.get(extension.getDataset()); 
		if (extensionList != null) {
			extensionList.add(extension);
		} else {
			extensionList = new LinkedList<DatasetExtension>();
			extensionList.add(extension);
			registeredExtensions.put(extension.getDataset(), extensionList);
		}
	}

	/**
	 * @param dataset 
	 * @param interfaceClass
	 * @return true if a.) the dataset implements the interface class or b.) a separate object that
	 * implements the interface for the dataset has been registered. a is always checked before b
	 */
	public boolean supports(Dataset dataset, Class<? extends DatasetExtension> interfaceClass) {
		return getExtension(dataset, interfaceClass) != null;
	}
	
	/**
	 * @param dataset
	 * @param interfaceClass
	 * @return the implementation of the interfaceClass for the specified dataset or null if no
	 * supporting implementation could be found i.e. the dataset does not implement the interface itself
	 * and there no separate implementation has been registered for the dataset
	 */
	public <T extends DatasetExtension> T getExtension(Dataset dataset, Class<T> interfaceClass) {		
		if (interfaceClass.isAssignableFrom(dataset.getClass())) {
			//the dataset supports the interface
			return interfaceClass.cast(dataset);
		} else {
			List<DatasetExtension> extensionList = registeredExtensions.get(dataset);
			if (extensionList != null) {
				for (DatasetExtension extension : extensionList) {
					if (interfaceClass.isAssignableFrom(extension.getClass())) {
						//the dataset does not support the extension but
						//a matching helper object is registered for the dataset
						return interfaceClass.cast(extension);
					}
				}
			}
		}
				
		return null;		
	}

	
}
