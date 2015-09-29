/**
 * 
 */
package com.seven10.nfs_mounter.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class NfsMounterFormater
{
	private static final Logger m_logger = LogManager.getFormatterLogger(NfsMounter.class.getName());
	/**
	 * format single export parameter into a line suitable for the Autofs template file
	 * @param parameter The parameter object that contains the mount specific information
	 * @param factorySettings factory settings to use
	 * @return a string containing the mount parameters for this export
	 */
	public static String formatParamterForLine(NfsMountExportParameter parameter, NfsMounterFactorySettings factorySettings)
	{
		if( parameter == null)
		{
			throw new IllegalArgumentException(".formatParameterForLine(): parameter must not be null");
		}
		if( factorySettings == null)
		{
			throw new IllegalArgumentException(".formatParameterForLine(): factorySettings must not be null");
		}
		m_logger.debug(".formatParamterForLine(): parameter='%s'", parameter.toString());
		String rval = "";
		String mountPoint = parameter.getMountPoint();
		String location = parameter.getLocation();
		String shareName = parameter.getLinuxExportName();
		
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateExportName(shareName);
		rval = String.format("%s %s %s:%s", mountPoint,
				factorySettings.getLinuxOptionsString(),
									location,
									shareName);		
		m_logger.debug(".formatParamterForLine(): parameter formatted for autofs config line='%s'", rval);
		return rval;
	}
	
	public static String formatMountAsUnc(NfsMountExportParameter parameter)
	{
		if( parameter == null)
		{
			throw new IllegalArgumentException(".formatMountAsUnc(): parameter must not be null");
		}
		m_logger.debug(".formatMountAsUnc(): parameter='%s'", parameter.toString());
		String path = String.format("\\\\%s%s", parameter.getLocation(), parameter.getWindowsExportName());
		m_logger.debug(".formatMountAsUnc(): parameter formatted as UNC address='%s'", path);
		return path;
	}
}
