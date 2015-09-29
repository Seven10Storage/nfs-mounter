# nfs-mounter
This project generates a maven artifact that can be used to programmatically
 cause AutoFS to mount the specified NFS shares without the need for root privilages. The same mounter object can also be used on Windows, where the share is mounted as normal. Thus the consumer does not need to worry about platform specific code.

**First, set up AutoFS**

1. Ensure AutoFS is installed and configured for linux. See https://wiki.archlinux.org/index.php/Autofs for specifics.
2. edit the autofs master template file. This is usually something like _/etc/autofs/auto.master_ or _/etc/auto.master_. 
   * By default nfs-mounter will look for the following entry: `/mnt/hydra	/usr/local/etc/auto.hydra –timeout=60`
   * The expected fields' values can be changed by modifying a com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings object, and passing it to com.seven10.nfs_mounter.NfsMounterFactory.setMounterConfig().
3. Restart the autofs service.

**Next**, add the nfs-mounter artifact to your project's pom file. Use the following settings:
```maven
<dependency>
    <groupId>com.seven10</groupId>
    <artifactId>nfs-mounter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
Here is an example of how to use this function:

```java
	private List<NfsMountVolumesParameter> createParamObjects()
	{
		List<NfsMountVolumesParameter> rval = new ArrayList<NfsMountVolumesParameter>();
		rval.add(new NfsMountVolumesParameter("my_share","localhost", "/nfs/my_share"));
		rval.add(new NfsMountVolumesParameter("work", "isilon.corprate.interwebz", "/ifs"));
		rval.add(new NfsMountVolumesParameter("windows","192.168.55.31", "/drive_c"));
		rval.add(new NfsMountVolumesParameter("nfs_test","10.2.31.5", "/nfs_stuff"));
		return rval;
	}
	
	public void listNfsFiles() throws NfsClientException
	{
		NfsMounter mounter = NfsMounterFactory.getMounter();
		List<NfsMountVolumesParameter> parameterObjects = createParamObjects();
		List<File> files = mounter.mountVolumes(parameterObjects);
		for(File f: actualVolumes)
		{
			// do something interesting to each mounted folder
		}
	}
```
