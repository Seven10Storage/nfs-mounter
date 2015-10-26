package com.seven10.nfs_mounter.linux;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.seven10.nfs_mounter.helpers.MountPointListTransformer;
import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

public class LinuxNfsMounter extends NfsMounter
{
	private final AutoFsMgr autoFsMgr;
	
	/**
	 * Constructor
	 * 
	 * @param factorySettings The factory settings object received from the factory
	 * @param afsMgr The afsMgr used to write to the template file
	 *            
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
	 * creates a list of file handles for each mounted export in the list
	 * 
	 * @param parameterObjects The list of parameter objects that provide the mount point
	 *            
	 * @return the array of file handles from the mounted exports.
	 */
	private List<File> getMountPointFileList(List<NfsMountExportParameter> parameterObjects) throws IOException
	{
		List<File> rval = new ArrayList<File>();
		for (NfsMountExportParameter parameter : parameterObjects)
		{
			m_logger.debug(".getMountPointFileList(): processing mountPoint '%s'", parameter.toString());
			
			String fullMountPoint = String.format("%s/%s", settings.linuxBaseMntDir, parameter.getMountPoint());
			m_logger.debug(".getMountPointFileList(): fullMountPoint=%s", fullMountPoint);
			
			File mpFile = new File(fullMountPoint);
			if (mpFile.exists())
			{
				m_logger.debug(".getMountPointFileList(): mountPoint '%s' file exists. Adding to list",
								parameter.toString());
				rval.add(mpFile);
			}
			else
			{
				m_logger.error(".getMountPointFileList(): mountPoint '%s' does not exists. Ignoring",
								parameter.toString());
				
				throw new IOException("Export: " + parameter.getLinuxExportName() + 
									  " not mounted correctly at path: " + 
									  parameter.getMountPoint());
			}
		}
		return rval;
	}

	/**
	 * function to mount the requested export via autofs
	 * 
	 * @param parameterObjects
	 *            The list of parameter objects that provide information for the
	 *            mount points
	 * @return The array of file handles from the mounted exports
	 * @throws IOException thrown if the template file can't be opened for writing
	 */
	@Override
	public List<File> mountExports(List<NfsMountExportParameter> parameterObjects) throws IOException
	{
		if (parameterObjects == null)
		{
			throw new IllegalArgumentException(".mountExports(): parameterObjects must not be null");
		}
		
		Set<String> lines = autoFsMgr.getAutoFsEntryList();
		for (NfsMountExportParameter parameter : parameterObjects)
		{
			m_logger.debug(".mountExports(): attempting to mount export with parameters='%s'", parameter.toString());
			String newLine = NfsMounterFormater.formatParamterForLine(parameter, settings);
			if (newLine.isEmpty() == false)
			{
				m_logger.debug(".mountExports(): adding mount line='%s'", newLine.toString());
				lines.add(newLine);
			}
		}
		
		autoFsMgr.setAutoFsEntryList(lines);
		autoFsMgr.updateFile();
		
		return getMountPointFileList(parameterObjects);
	}
	
	/**
	 * function to mount a single export via autofs
	 * 
	 * @param parameterObjects
	 *            The list of parameter objects that provide information for the
	 *            mount points
	 * @return The array of file handles from the mounted exports
	 * @throws IOException thrown if the template file can't be opened for writing
	 */
	@Override
	public File mountExport(NfsMountExportParameter parameterObjects) throws IOException
	{
		List<NfsMountExportParameter> paramsList = new ArrayList<NfsMountExportParameter>(1);
		paramsList.add(parameterObjects);
		List<File> files = mountExports(paramsList);
		
		if (files.size() < 1)
		{
			m_logger.error(".mountExport(): returned list of files for the mount does not contain the mount expected");
			
			throw new IOException(String.format(".mountExport(): the requested export '%s' could not be opened", 
								  parameterObjects.getMountPoint()));
		}
		
		File rval = files.get(0);
		return rval;
	}
	
	/**
	 * function to remove the requested export from the autofs template file.
	 * autofs will remove the mount points after the amount of time configured
	 * in /etc/auto.master
	 * 
	 * @param mountPoints
	 *            The list of mount point names to remove from the template file
	 * @throws IOException thrown if the template file can't be opened for writing
	 * @throws FileNotFoundException thrown if the template file can't be found
	 */
	@Override
	public void unMountExports(List<String> mountPoints) throws FileNotFoundException, IOException
	{
		if (mountPoints == null)
		{
			throw new IllegalArgumentException(".unMountExports(): mountPoints must not be null");
		}
		Set<String> mountPointList = autoFsMgr.getAutoFsEntryList();
		m_logger.debug(".unMountExports(): exports to unmount='%s'", StringUtils.join(mountPointList, File.pathSeparator));
		
		boolean listUpdated = false;
		for (String mp : mountPoints)
		{
			NfsMountParamsValidator.validateMountPoint(mp);
			m_logger.debug(".unMountExports(): unmounting export '%s'", mp);
			MountPointListTransformer.removeFromList(mp, mountPointList);
			listUpdated = true;
		}
		if (listUpdated)
		{
			autoFsMgr.setAutoFsEntryList(mountPointList);
			autoFsMgr.updateFile();
		}
	}
	
	/**
	 * function to remove the requested export from the autofs template file.
	 * autofs will remove the mount points after the amount of time configured
	 * in /etc/auto.master
	 * 
	 * @param mountPoints
	 *            The list of mount point names to remove from the template file
	 * @throws IOException Thrown if the template file cannot be opened
	 * @throws FileNotFoundException thrown if the template file path cannot be found
	 */
	@Override
	public void unMountExport(String mountPoints) throws FileNotFoundException, IOException
	{
		List<String> paramsList = new ArrayList<String>(1);
		paramsList.add(mountPoints);
		unMountExports(paramsList);
	}
	
	@Override
	public boolean isMounted(String mountPoint) throws FileNotFoundException, IOException
	{
		Set<String> mountPointList = autoFsMgr.getAutoFsEntryList();
		m_logger.debug(".isMounted(): testing mountPoint='%s'", mountPoint);
		return MountPointListTransformer.isInList(mountPoint, mountPointList);
		
	}

	@Override
	public void unMountAll() throws IOException
	{
		Set<String> mountPointList = new HashSet<String>();
		autoFsMgr.setAutoFsEntryList(mountPointList);
		autoFsMgr.updateFile();
		
	}
}