package com.bbe.xmlApi.util.xml.persist;


public enum Useful {
	DEFAULT;

	private static String prefix,tmp;
	private static long tmpSubDir = System.currentTimeMillis();

	public static String getPrefix() {
		if (prefix==null) {
			if (System.getProperty("user.dir").split("/").length>0) 
			{
				prefix = new String("/");
				tmp= new String("/tmp/");
			}
			else 
			{
				prefix = new String("\\");
				tmp= new String("C:\\tmp\\");
			}
		}
		return prefix;
	}

	public static String getPath(long l){
		prefix=getPrefix();
		
		StringBuilder sb = new StringBuilder();
		sb.append(tmp+prefix+tmpSubDir);
		
		for (char ch: Long.toString(l).toCharArray()) //for each char in string
		{
			sb.append(prefix + ch);
		}
		
		return sb.append(prefix).toString();
	}
}