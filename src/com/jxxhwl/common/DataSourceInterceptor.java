package com.jxxhwl.common;

import org.aspectj.lang.JoinPoint;

public class DataSourceInterceptor {
	public void setdataSourceOne(JoinPoint jp) {  
        DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_1);  
    }  
      
    public void setdataSourceTwo(JoinPoint jp) {  
        DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_2);  
    }  
    public void setdataSourceThree(JoinPoint jp) {  
    	DatabaseContextHolder.setDBType(DatabaseContextHolder.DATA_SOURCE_3);  
    }  
}
