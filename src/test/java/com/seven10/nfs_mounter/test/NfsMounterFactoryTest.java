/**
 * 
 */
package com.seven10.nfs_mounter.test;

import static org.junit.Assert.*;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.NfsMounterFactory;
import com.seven10.nfs_mounter.exceptions.NfsClientException;
import com.seven10.nfs_mounter.linux.LinuxNfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;
import com.seven10.nfs_mounter.windows.WindowsNfsMounter;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * @author kmm
 *
 */
public class NfsMounterFactoryTest
{

	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.NfsMounterFactory#getMounter()}.
	 * 
	 * @throws NfsClientException
	 */
	@Test
	public void testGetMounter_valid()
	{
		try
		{
			NfsMounter mounter = NfsMounterFactory.getMounter();
			if (SystemUtils.IS_OS_LINUX)
			{
				assertThat(mounter, instanceOf(LinuxNfsMounter.class));
			}
			else if (SystemUtils.IS_OS_WINDOWS)
			{
				assertThat(mounter, instanceOf(WindowsNfsMounter.class));
			}
		}
		catch (NfsClientException ex)
		{
			fail("Unknown OS type");
		}
	}

	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.NfsMounterFactory#setMounterConfig(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}
	 * .
	 */
	@Test
	public void testSetAndGetMounterConfig_valid()
	{
		NfsMounterFactorySettings expected = new NfsMounterFactorySettings();
		expected.isFsReadOnly = !expected.isFsReadOnly;
		expected.isRequestInterruptable = !expected.isRequestInterruptable;

		NfsMounterFactory.setMounterConfig(expected);
		NfsMounterFactorySettings actual = NfsMounterFactory.getMounterConfig();

		assertEquals(expected, actual);
	}

	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.NfsMounterFactory#setMounterConfig(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMounterConfig_nullParam()
	{
		NfsMounterFactorySettings expected = null;
		NfsMounterFactory.setMounterConfig(expected);
	}
}
