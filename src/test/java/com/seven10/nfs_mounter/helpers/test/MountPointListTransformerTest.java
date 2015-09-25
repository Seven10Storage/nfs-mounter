/**
 * 
 */
package com.seven10.nfs_mounter.helpers.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.seven10.nfs_mounter.helpers.MountPointListTransformer;

/**
 * @author kmm
 *
 */
public class MountPointListTransformerTest
{
	
	private String mountPointValid = "/mnt1";
	private String mountPointValid2 = "/mnt2";
	private String mountPointValid3 = "/mnt3";
	private String mountPointInvalid = "?notV@l\"id";
	

	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#addToList(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testAddToList_valid()
	{
		List<String> mountPointList = new ArrayList<String>();
		MountPointListTransformer.addToList(mountPointValid, mountPointList);
		assertTrue(mountPointList.contains(mountPointValid));

		MountPointListTransformer.addToList(mountPointValid2, mountPointList);
		assertTrue(mountPointList.contains(mountPointValid2));
		assertTrue(mountPointList.contains(mountPointValid));
		
		
		//when we insert a repeat into the list, no items should have been added (the size shouldn't change)
		int expectedSize = mountPointList.size();
		MountPointListTransformer.addToList(mountPointValid2, mountPointList);
		assertTrue(mountPointList.contains(mountPointValid2));
		assertTrue(mountPointList.contains(mountPointValid));
		int actualSize = mountPointList.size();
		assertEquals(expectedSize, actualSize);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#addToList(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testAddToList_invalid_mountPoint()
	{		
		String mountPoint = mountPointInvalid;
		List<String> mountPointList = new ArrayList<String>();
		
		int expectedSize = mountPointList.size();		
		MountPointListTransformer.addToList(mountPoint, mountPointList);
		int actualSize = mountPointList.size();
		
		assertFalse(mountPointList.contains(mountPoint)); // the item wasn't added		
		assertEquals(expectedSize, actualSize);	// the size hasn't changed
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#addToList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddToList_empty_mountPoint()
	{		
		String mountPoint = "";
		List<String> mountPointList = new ArrayList<String>();

		MountPointListTransformer.addToList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#addToList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddToList_null_mountPoint()
	{		
		String mountPoint = null;
		List<String> mountPointList = new ArrayList<String>();

		MountPointListTransformer.addToList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#addToList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddToList_null_mountPointList()
	{		
		String mountPoint = mountPointValid;
		List<String> mountPointList = null;

		MountPointListTransformer.addToList(mountPoint, mountPointList);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#removeFromList(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testRemoveFromList_valid()
	{
		List<String> mountPointList = new ArrayList<String>();
		//a remove from an empty list shouldn't cause a problem
		MountPointListTransformer.removeFromList(mountPointValid, mountPointList);
		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		MountPointListTransformer.removeFromList(mountPointValid, mountPointList);
		assertFalse(mountPointList.contains(mountPointValid));
		
		int expectedSize = mountPointList.size();
		// if we try to remove something that isn't there, it should be graceful
		MountPointListTransformer.removeFromList(mountPointValid, mountPointList);
		int actualSize = mountPointList.size();
		
		// the item still shouldn't be there
		assertFalse(mountPointList.contains(mountPointValid));
		//and nothing else should have been removed (the size shouldn't change)
		assertEquals(expectedSize, actualSize);		
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#removeFromList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveFromList_emptyMountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = "";
		MountPointListTransformer.removeFromList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#removeFromList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveFromList_nullMountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = null;
		MountPointListTransformer.removeFromList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#removeFromList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveFromList_nullMountPointList()
	{
		List<String> mountPointList = null;
		String mountPoint = mountPointValid;
		MountPointListTransformer.removeFromList(mountPoint, mountPointList);
	}
	
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#getListIndex(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testGetListIndex_valid()
	{
		List<String> mountPointList = new ArrayList<String>();
		//empty list should return not found
		int actual = MountPointListTransformer.getListIndex(mountPointValid, mountPointList);
		assertEquals(-1, actual);
		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		actual = MountPointListTransformer.getListIndex(mountPointValid3, mountPointList);
		assertEquals(-1, actual);
		actual = MountPointListTransformer.getListIndex(mountPointValid, mountPointList);
		assertEquals(0, actual);
		actual = MountPointListTransformer.getListIndex(mountPointValid2, mountPointList);
		assertEquals(1, actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#getListIndex(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testGetListIndex_invalid_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = mountPointInvalid;
		int actual = MountPointListTransformer.getListIndex(mountPoint, mountPointList);
		assertEquals(-1,actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#getListIndex(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetListIndex_empty_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = "";
		MountPointListTransformer.getListIndex(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#getListIndex(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetListIndex_null_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = null;
		MountPointListTransformer.getListIndex(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#getListIndex(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetListIndex_null_mountPointList()
	{
		List<String> mountPointList = null;
		String mountPoint = mountPointValid;
		MountPointListTransformer.getListIndex(mountPoint, mountPointList);
	}


	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#isInList(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testIsInList_valid()
	{
		List<String> mountPointList = new ArrayList<String>();
		//empty list should return not found
		boolean actual = MountPointListTransformer.isInList(mountPointValid, mountPointList);
		assertEquals(false, actual);
		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		actual = MountPointListTransformer.isInList(mountPointValid3, mountPointList);
		assertFalse(actual);
		actual = MountPointListTransformer.isInList(mountPointValid, mountPointList);
		assertTrue(actual);
		actual = MountPointListTransformer.isInList(mountPointValid2, mountPointList);
		assertTrue(actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#isInList(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testIsInList_invalid_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = mountPointInvalid;
		boolean actual = MountPointListTransformer.isInList(mountPoint, mountPointList);
		assertFalse(actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#isInList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsInList_empty_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = "";
		MountPointListTransformer.isInList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#isInList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsInList_null_mountPoint()
	{
		List<String> mountPointList = new ArrayList<String>();		
		mountPointList.add(mountPointValid);
		mountPointList.add(mountPointValid2);
		String mountPoint = null;
		MountPointListTransformer.isInList(mountPoint, mountPointList);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.MountPointListTransformer#isInList(java.lang.String, java.util.List)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsInList_null_mountPointList()
	{
		List<String> mountPointList = null;
		String mountPoint = mountPointValid;
		MountPointListTransformer.isInList(mountPoint, mountPointList);
	}
	
}
