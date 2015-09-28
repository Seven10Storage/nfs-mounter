package com.seven10.nfs_mounter.linux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.seven10.nfs_mounter.helpers.MountPointListTransformer;
import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;
import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

public class LinuxNfsMounter extends NfsMounter
{
	AutoFsMgr autoFsMgr;
	
	/**
	 * creates a list of file handles for each mounted volume in the list
	 * 
	 * @param parameterObjects
	 *            The list of parameter objects that provide the mount point
	 * @return the array of file handles from the mounted volumes.
	 */
	private List<File> getMountPointFileList(List<NfsMountVolumesParameter> parameterObjects)
	{
		List<File> rval = new ArrayList<File>();
		for (NfsMountVolumesParameter parameter : parameterObjects)
		{
			m_logger.debug(".getMountPointFileList(): processing mountPoint '%s'", parameter.toString());
			String fullMountPoint = String.format("%s/%s", settings.linuxBaseMntDir, parameter.getMountPoint());
			m_logger.debug(".getMountPointFileList(): fullMountPoint=%s", parameter.toString());
			
			File mpFile = new File(fullMountPoint);
			if (mpFile.exists())
			{
				m_logger.debug(".getMountPointFileList(): mountPoint '%s' file exists. Adding to list",
						parameter.toString());
				rval.add(mpFile);
			}
			else
			{
				m_logger.debug(".getMountPointFileList(): mountPoint '%s' does not exists. Ignoring",
						parameter.toString());
			}
		}
		return rval;
	}
	
	/**
	 * Constructor
	 * 
	 * @param factorySettings
	 *            The factory settings object received from the factory
	 */
	public LinuxNfsMounter(NfsMounterFactorySettings factorySettings, AutoFsMgr afsMgr)
	{
		super(factorySettings);
		if (afsMgr == null)
		{
			throw new IllegalArgumentException(".ctor(): afsMgr must not be null");
		}
		autoFsMgr = afsMgr;
	}
	
	/**
	 * function to mount the requested functions via autofs
	 * 
	 * @param parameterObjects
	 *            The list of parameter objects that provide information for the
	 *            mount points
	 * @return The array of file handles from the mounted volumes
	 * @throws IOException
	 */
	@Override
	public List<File> mountVolumes(List<NfsMountVolumesParameter> parameterObjects) throws IOException
	{
		if (parameterObjects == null)
		{
			throw new IllegalArgumentException(".mountVolumes(): parameterObjects must not be null");
		}
		List<String> lines = autoFsMgr.getMountPointList();
		for (NfsMountVolumesParameter parameter : parameterObjects)
		{
			m_logger.debug(".mountVolumes(): attempting to mount volume with parameters='%s'", parameter.toString());
			String newLine = NfsMounterFormater.formatParamterForLine(parameter, settings);
			if (newLine.isEmpty() == false)
			{
				m_logger.debug(".mountVolumes(): adding mount line='%s'", newLine.toString());
				lines.add(newLine);
			}
		}
		autoFsMgr.setMountPointsList(lines);
		autoFsMgr.updateFile();
		return getMountPointFileList(parameterObjects);
	}
	
	/**
	 * function to remove the requested functions from the autofs template file.
	 * autofs will remove the mount points after the amount of time configured
	 * in /etc/auto.master
	 * 
	 * @param mountPoints
	 *            The list of mount point names to remove from the template file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public void unMountVolumes(List<String> mountPoints) throws FileNotFoundException, IOException
	{
		if (mountPoints == null)
		{
			throw new IllegalArgumentException(".unMountVolumes(): mountPoints must not be null");
		}
		List<String> mountPointList = autoFsMgr.getMountPointList();
		m_logger.debug(".unMountVolumes(): volumes to unmount='%s'",
				StringUtils.join(mountPointList, File.pathSeparator));
		boolean listUpdated = false;
		for (String mp : mountPoints)
		{
			NfsMountParamsValidator.validateMountPoint(mp);
			m_logger.debug(".unMountVolumes(): unmounting volume '%s'", mp);
			MountPointListTransformer.removeFromList(mp, mountPointList);
			listUpdated = true;
		}
		if (listUpdated)
		{
			autoFsMgr.setMountPointsList(mountPointList);
			autoFsMgr.updateFile();
		}
	}
	
	@Override
	public boolean isMounted(String mountPoint) throws FileNotFoundException, IOException
	{
		List<String> mountPointList = autoFsMgr.getMountPointList();
		m_logger.debug(".isMounted(): testing mountPoint='%s'", mountPoint);
		return MountPointListTransformer.isInList(mountPoint, mountPointList);
		
	}
	
}