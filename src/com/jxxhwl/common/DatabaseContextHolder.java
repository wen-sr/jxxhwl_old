package com.jxxhwl.common;

public class DatabaseContextHolder {

	public static final String DATA_SOURCE_1 = "dataSource1";  
    public static final String DATA_SOURCE_2 = "dataSource2";  
    public static final String DATA_SOURCE_3 = "dataSource3";  
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() {  
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}
