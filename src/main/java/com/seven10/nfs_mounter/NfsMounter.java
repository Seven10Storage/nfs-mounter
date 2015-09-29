/**
 * 
 */
package com.seven10.nfs_mounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public abstract class NfsMounter
{
	protected static final Logger m_logger = LogManager.getFormatterLogger(NfsMounter.class.getName());
	protected NfsMounterFactorySettings settings;
	
	protected NfsMounter(NfsMounterFactorySettings factorySettings)
	{
		if(factorySettings == null)
		{
			throw new IllegalArgumentException(".ctor(): factorySettings must not be null");
		}
		m_logger.debug(".ctor(): factory settings = %s", factorySettings.toString());
		settings = factorySettings;
	}
	
	public abstract List<File> mountExports(List<NfsMountExportParameter> parameterObject) throws IOException;
	public abstract File mountExport(NfsMountExportParameter parameterObject) throws IOException;
	public abstract void unMountExports(List<String> mountPoints) throws FileNotFoundException, IOException;
	public abstract void unMountExport(String mountPoint) throws FileNotFoundException, IOException;
	public abstract boolean isMounted(String mountPoint) throws FileNotFoundException, IOException;	
}