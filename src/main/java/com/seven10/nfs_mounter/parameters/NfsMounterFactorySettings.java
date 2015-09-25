/**
 * 
 */
package com.seven10.nfs_mounter.parameters;

/**
 * @author kmm
 *
 */
public class NfsMounterFactorySettings
{
	public boolean isFsReadOnly = false;
	
	/**
	 * If true, NFS requests are retried indefinitely
	 * Otherwise, the NFS client fails an NFS request after retrans retransmissions have been sent, causing the NFS client
	 * to return an error to the calling application
	 * Note: A so-called "soft" timeout can cause silent data corruption in certain cases. As such, use the soft
	 * option only when client responsiveness is more important than data integrity. Using NFS over TCP or increasing
	 *  the value of the retrans option may mitigate some of the risks of using the soft option. 
	 */
	public boolean isRequestsRetriedIndefinitely = true;
	/**
	 * If set, allows NFS requests to be interrupted if the server goes down or cannot be reached. 
	 */
	public boolean isRequestInterruptable = true;

	/**
	 * This setting speeds up NFS communication for reads by setting a larger data block size (num, in bytes), to be transferred at one time.
	 * Be careful when changing these values; some older Linux kernels and network cards do not work well with larger block sizes. 
	 */
	public int readDataBlockSize = 8192;

	/**
	 * This setting speeds up NFS communication for writes by setting a larger data block size (num, in bytes), to be transferred at one time. 
	 * Be careful when changing these values; some older Linux kernels and network cards do not work well with larger block sizes. 
	 */
	public int writeDataBlockSize = 8192;
	
	/**
	 * Linux mounts the NFS volume to a special folder
	 */
	public String linuxBaseMntDir = "/mnt/hydra";
	
	/**
	 * Linux autofs uses a special template file to mount volumes. We create this file on the fly.
	 * This parameter is the location of the template file.
	 */
	public String linuxAutoFsTemplatePath = "/usr/local/etc/auto.hydra";
	
	/**
	 * Generates a string to be passed into the linux mount command containing relevant settings
	 * @return
	 */
	public String getLinuxOptionsString()
	{
		StringBuilder rval = new StringBuilder();
		rval.append((isFsReadOnly)? "ro," : "rw,");
		rval.append((isRequestsRetriedIndefinitely) ? "hard," : "soft,");
		rval.append((isRequestInterruptable) ? "intr," : "");
		rval.append(String.format("rsize=%d,", readDataBlockSize));
		rval.append(String.format("wsize=%d,", writeDataBlockSize));
		return rval.toString();
	}
}
