/**
 * 
 */
package com.seven10.nfs_mounter.helpers;

import java.util.List;

import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;

/**
 * @author kmm
 *
 */
public class MountPointListTransformer
{
	/**
	 * Add a mount point to the given list of mount points. If the item is already present, it won't be added again
	 * @param mountPoint the mount point to add
	 * @param mountPointList the list to add the mount point to
	 */
	public static void addToList(String mountPoint, List<String> mountPointList)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".addToList(): mountPointList cannot be null");
		}
		if(isInList(mountPoint, mountPointList) == false)
		{
			mountPointList.add(mountPoint);
		}		
	}
	/**
	 * Remove a mount point from the given list. If the item is not present, it will exit quietly
	 * @param mountPoint The mount point to remove
	 * @param mountPointList The list to remove from
	 */
	public static void removeFromList(String mountPoint,  List<String> mountPointList)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".removeFromList(): mountPointList cannot be null");
		}
		int listIndex;
		if( (listIndex = getListIndex(mountPoint, mountPointList)) != -1)
		{
			mountPointList.remove(listIndex);
		}		
	}
	/**
	 * Gets the index in the mountPointList that the mountPoint occupies.
	 * @param mountPoint The mount point to test
	 * @param mountPointList The list to check in
	 * @return the index if the mountPoint is in the mountPointList, -1 otherwise
	 */
	public static int getListIndex(String mountPoint, List<String> mountPointList)
	{
		if(mountPoint == null || mountPoint.isEmpty())
		{
			throw new IllegalArgumentException(".validateMountPoint(): mountPoint cannot be null or empty");
		}
		if(mountPointList == null)
		{
			throw new IllegalArgumentException(".getListIndex(): mountPointList cannot be null");
		}
		int rval = -1;
		for(int index = 0; index < mountPointList.size(); index ++)
		{
			String itemMpEntry = mountPointList.get(index).substring(0, mountPoint.length());
			if( itemMpEntry == mountPoint )
			{
				rval = index;
				break;
			}
		}
		return rval;
	}
	/**
	 * Indicates if the mount point is somewhere in the list of mount points
	 * @param mountPoint the mount point to test
	 * @param mountPointList the list to check in
	 * @return true if the mount point is in the list, false otherwise
	 */
	public static boolean isInList(String mountPoint,  List<String> mountPointList)
	{
		return getListIndex(mountPoint, mountPointList) != -1;
	}
}
