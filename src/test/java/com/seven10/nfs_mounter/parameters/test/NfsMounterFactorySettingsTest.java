/**
 * 
 */
package com.seven10.nfs_mounter.parameters.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class NfsMounterFactorySettingsTest
{
	
	private void testInterruptableValue(boolean isReqInt, String actual)
	{
		if(isReqInt)
		{
			assertTrue(actual.contains("intr"));
		}
		else
		{
			assertFalse(actual.contains("intr"));
			assertFalse(actual.contains(",,")); //make sure that an extra comma isn't slipped in because there's no value
		}
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings#getLinuxOptionsString()}.
	 */
	@Test
	public void testGetOptionsString_fsReadOnly()
	{
		NfsMounterFactorySettings settings = new NfsMounterFactorySettings();
		boolean readOnly = settings.isFsReadOnly;
		String expected = readOnly ? "ro" : "rw";
		String actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
		
		// now change the value and see if its reflected in the string
		settings.isFsReadOnly = ! readOnly;
		expected = readOnly ? "ro" : "rw";
		actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings#getLinuxOptionsString()}.
	 */
	@Test
	public void testGetOptionsString_requestsRetriedIndefinitely()
	{
		NfsMounterFactorySettings settings = new NfsMounterFactorySettings();
		boolean reqRetryIndef = settings.isRequestsRetriedIndefinitely;
		String expected = reqRetryIndef ? "hard" : "soft";
		String actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
		
		// now change the value and see if its reflected in the string
		settings.isRequestsRetriedIndefinitely = ! reqRetryIndef;
		expected = reqRetryIndef ? "hard" : "soft";
		actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings#getLinuxOptionsString()}.
	 */
	@Test
	public void testGetOptionsString_isRequestInterruptable()
	{
		NfsMounterFactorySettings settings = new NfsMounterFactorySettings();
		boolean isReqInt = settings.isRequestInterruptable;
		String actual = settings.getLinuxOptionsString();
		testInterruptableValue(isReqInt, actual);
				
		// now change the value and see if its reflected in the string
		settings.isRequestInterruptable = ! isReqInt;
		actual = settings.getLinuxOptionsString();
		testInterruptableValue(isReqInt, actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings#getLinuxOptionsString()}.
	 */
	@Test
	public void testGetOptionsString_readDataBlockSize()
	{
		NfsMounterFactorySettings settings = new NfsMounterFactorySettings();
		int readDBSize = settings.readDataBlockSize;
		String expected = String.format("rsize=%d", readDBSize);
		String actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
		
		// now change the value and see if its reflected in the string
		settings.readDataBlockSize = readDBSize * 2;
		expected = String.format("rsize=%d", readDBSize);
		actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings#getLinuxOptionsString()}.
	 */
	@Test
	public void testGetOptionsString_writeDataBlockSize()
	{
		NfsMounterFactorySettings settings = new NfsMounterFactorySettings();
		int writeDBSize = settings.writeDataBlockSize;
		String expected = String.format("wsize=%d", writeDBSize);
		String actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
		
		// now change the value and see if its reflected in the string
		settings.writeDataBlockSize = writeDBSize * 2;
		expected = String.format("wsize=%d", writeDBSize);
		actual = settings.getLinuxOptionsString();
		assertTrue(actual.contains(expected));
	}
}
