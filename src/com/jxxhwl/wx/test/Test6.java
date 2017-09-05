package com.jxxhwl.wx.test;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;


public class Test6 {
	public static Logger log = Logger.getLogger(TestLog.class);
	@org.junit.Test
	public void test(){
		int[] GrowthRate;//生长速度
		GrowthRate = new int[3];
		GrowthRate[0] = 2;
		GrowthRate[1] = 3;	  
		GrowthRate[2] = 5;	
		
		int[] Count;
		Count = new int[3];
		Count[0]=1;
		Count[1]=1;
		Count[2]=1;
		
		
		   
		for(int i = 0;i < 4; i++ ){
			  for(int j = 0;j<4;j++){
				   if(j == i ){
					   continue;
				   }
				   for(int k = 0;k < 4;k++){
					   if(k == j || k == i){
						   continue;
					   }
					   for(int l = 0;l <4;l++){
						   Queue<Integer> q = new LinkedList<Integer>();
						   q.add(i);
						   q.add(j);
						   q.add(k);
						   q.add(l);
						   System.out.println(q);
					   }
				   }
			   }
		   }
//		   int index = 0;
//		   for(int x = 3;x<5;x++){
//			   for(int i = 0;i < 3; i++ ){
//				   if(i == 0) {
//					   index++;
//				   }
//				   for(int j = 0;j<3;j++){
//					   if(j == 0){
//						   
//					   }
//					   if(j == i ){
//						   continue;
//					   }
//					   for(int k = 0;k < 3;k++){
//						   if(k == j || k == i){
//							   continue;
//						   }
//						   Queue<Integer> q = new LinkedList<Integer>();
//						   q.add(i);
//						   q.add(j);
//						   q.add(k);
//						   System.out.println(q);
//					   }
//				   }
//			   }
//		   }
		   
		      
	       
	       
	}
}
