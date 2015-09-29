/**
 * 
 */
package com.seven10.nfs_mounter.helpers;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;

/**
 * @author kmm
 *
 */
public class MountPointListTransformer
{
	private static final Logger m_logger = LogManager.getFormatterLogger(MountPointListTransformer.class.getName());
	
	/**
	 * Add a mount point to the given list of mount points. If the item is already present, it won't be added again
	 * @param mountPoint the mount point to add
	 * @param mountPointList the list to add the mount point to
	 */
	public static void addToList(String mountPoint, Set<String> mountPointList)
	{
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".addToList(): mountPointList cannot be null");
		}
		if(isInList(mountPoint, mountPointList) == false)
		{
			m_logger.debug(".addToList(): mountPoint '%s' not in mountPointList so adding it", mountPoint);
			mountPointList.add(mountPoint);
		}		
	}
	/**
	 * Remove a mount point from the given list. If the item is not present, it will exit quietly
	 * @param mountPoint The mount point to remove
	 * @param mountPointList The list to remove from
	 */
	public static void removeFromList(String mountPoint,  Set<String> mountPointList)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".removeFromList(): mountPointList cannot be null");
		}
		
		if(mountPointList.remove(mountPoint))
		{
			m_logger.debug(".removeFromList(): mountPoint '%s' found in mountPointList so removing it", mountPoint);	
		}
		else
		{
			m_logger.debug(".removeFromList(): mountPoint '%s' not found in mountPointList", mountPoint);
		}
	}
	/**
	 * Indicates if the mount point is somewhere in the list of mount points
	 * @param mountPoint the mount point to test
	 * @param mountPointList the list to check in
	 * @return true if the mount point is in the list, false otherwise
	 */
	public static boolean isInList(String mountPoint,  Set<String> mountPointList)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".isInList(): mountPointList cannot be null");
		}
	
		return mountPointList.contains(mountPoint);
	}
}
