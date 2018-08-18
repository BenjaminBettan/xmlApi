package com.bbe.xmlapi.utilxml.persist;


public class PersistConfigurator {

	private static String prefix,tmp;
	private static long tmpSubDir;
	private final static String STR_1 = "/";
	private final static String STR_2 = "\\";
	private final static String STR_3 = "/tmp/";
	private final static String STR_4 = "C:\\tmp\\";
	
	private PersistConfigurator() {}

	public static String getPrefix() {
		if (prefix==null) {
			if (System.getProperty("user.dir").split(STR_1).length>0) 
			{//linux
				prefix = STR_1;
				tmp= STR_3;
			}
			else 
			{//windows
				prefix = STR_2;
				tmp= STR_4;
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

	public static void setTmpSubDir(long tmpSubDir_) {
		tmpSubDir = tmpSubDir_;
	}
	
}