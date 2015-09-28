package com.seven10.nfs_mounter;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.exceptions.NfsClientException;
import com.seven10.nfs_mounter.linux.AutoFsMgr;
import com.seven10.nfs_mounter.linux.LinuxNfsMounter;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;
import com.seven10.nfs_mounter.windows.WindowsNfsMounter;

public class NfsMounterFactory
{
	protected static final Logger m_logger = LogManager.getFormatterLogger(NfsMounterFactory.class.getName());
	private static NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
	
	public static NfsMounter getMounter() throws NfsClientException
	{
		NfsMounter rval;
		// test if OS is windows or linux
		if( SystemUtils.IS_OS_LINUX )
		{
			m_logger.debug(".getMounter(): creating linux nfs mounter");	
			AutoFsMgr autoFsMgr = new AutoFsMgr(nfsFactorySettings.linuxAutoFsTemplatePath);
			rval = new LinuxNfsMounter(nfsFactorySettings, autoFsMgr);
		}
		else if( SystemUtils.IS_OS_WINDOWS)
		{
			m_logger.debug(".getMounter(): creating windows nfs mounter");
			rval = new WindowsNfsMounter(nfsFactorySettings);
		}
		else
		{
			throw new NfsClientException(".getMounter(): could not determine client OS");
		}
		return rval;
	}
	public static void setMounterConfig(NfsMounterFactorySettings settings)
	{
		if( settings == null)
		{
			throw new IllegalArgumentException(".setMounterConfig(): settings cannot be null");
		}
		m_logger.debug(".setMounterConfig(): setting new factory settings = %s", settings.toString());
		nfsFactorySettings = settings;
	}
	public static NfsMounterFactorySettings getMounterConfig()
	{
		return nfsFactorySettings;
	}
}