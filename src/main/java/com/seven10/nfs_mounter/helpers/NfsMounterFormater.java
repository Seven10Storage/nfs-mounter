/**
 * 
 */
package com.seven10.nfs_mounter.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;
import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class NfsMounterFormater
{
	private static final Logger m_logger = LogManager.getFormatterLogger(NfsMounter.class.getName());
	/**
	 * format single volume parameter into a line suitable for the Autofs template file
	 * @param parameter The parameter object that contains the mount specific information
	 * @return a string containing the mount parameters for this volume
	 */
	public static String formatParamterForLine(NfsMountVolumesParameter parameter, NfsMounterFactorySettings factorySettings)
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
		String shareName = parameter.getLinuxShareName();
		
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateShareName(shareName);
		rval = String.format("%s %s %s:%s", mountPoint,
				factorySettings.getLinuxOptionsString(),
									location,
									shareName);		
		m_logger.debug(".formatParamterForLine(): parameter formatted for autofs config line='%s'", rval);
		return rval;
	}
	
	public static String formatMountAsUnc(NfsMountVolumesParameter parameter)
	{
		if( parameter == null)
		{
			throw new IllegalArgumentException(".formatMountAsUnc(): parameter must not be null");
		}
		m_logger.debug(".formatMountAsUnc(): parameter='%s'", parameter.toString());
		String path = String.format("\\\\%s%s", parameter.getLocation(), parameter.getWindowsShareName());
		m_logger.debug(".formatMountAsUnc(): parameter formatted as UNC address='%s'", path);
		return path;
	}
}
