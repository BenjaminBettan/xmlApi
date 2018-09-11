package com.bbe.xmlapi.util.persist;

public class PersistConfigurator {

	private static String prefix,tmp;
	private static String tmpSubDir;
	private static final String LINUX_PREFIX = "/";
	private static final String WINDOWS_PREFIX = "\\";
	private static final String LINUX_TMP = "/tmp";
	private static final String WINDOWS_TMP = "C:\\tmp";

	private PersistConfigurator() {}

	static   {
		PersistConfigurator.setTmpSubDir(""+System.currentTimeMillis());	
	}

	public static String getPrefix() {
		if (prefix==null) {
			if (System.getProperty("user.dir").split(LINUX_PREFIX).length>0) 
			{//linux
				prefix = LINUX_PREFIX;
				tmp= LINUX_TMP;
			}
			else 
			{//windows
				prefix = WINDOWS_PREFIX;
				tmp= WINDOWS_TMP;
			}
		}
		return prefix;
	}

	public static String convertToFilePath(long l){ //long l=123456 / OS=linux : will return -> /tmp/1/2/3/4/5/6/ 
		prefix=getPrefix();

		StringBuilder sb = new StringBuilder();
		sb.append(tmp+prefix+tmpSubDir);

		for (char ch: Long.toString(l).toCharArray()) //for each char in string
		{
			sb.append(prefix + "_" +ch);
		}

		return sb.append(prefix).toString();
	}

	public static void setTmpSubDir(String tmpSubDir_) {
		tmpSubDir = tmpSubDir_;
	}

	public static String getTmpSubDir() {
		return tmpSubDir;
	}

	public static String getTmp() {
		return tmp;
	}

	public static void setTmp(String tmp) {
		PersistConfigurator.tmp = tmp;
	}
}