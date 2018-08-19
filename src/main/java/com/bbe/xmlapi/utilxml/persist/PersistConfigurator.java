package com.bbe.xmlapi.utilxml.persist;


public class PersistConfigurator {

	private static String prefix,tmp;
	private static String tmpSubDir;
	private final static String PREFIX_LINUX = "/";
	private final static String PREFIX_WINDOWS = "\\";
	private final static String TMP_LINUX = "/tmp";
	private final static String TMP_WINDOWS = "C:\\tmp";
	
	private PersistConfigurator() {}

	public static String getPrefix() {
		if (prefix==null) {
			if (System.getProperty("user.dir").split(PREFIX_LINUX).length>0) 
			{//linux
				prefix = PREFIX_LINUX;
				tmp= TMP_LINUX;
			}
			else 
			{//windows
				prefix = PREFIX_WINDOWS;
				tmp= TMP_WINDOWS;
			}
		}
		return prefix;
	}

	public static String convertToFilePath(long l){
		prefix=getPrefix();
		
		StringBuilder sb = new StringBuilder();
		sb.append(tmp+prefix+tmpSubDir);
		
		for (char ch: Long.toString(l).toCharArray()) //for each char in string
		{
			sb.append(prefix + ch);
		}
		
		return sb.append(prefix).toString();
	}

	public static void setTmpSubDir(String tmpSubDir_) {
		tmpSubDir = tmpSubDir_;
	}

	public static String getTmpSubDir() {
		return tmpSubDir;
	}
	
}