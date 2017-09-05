package com.jxxhwl.wx.test;

import org.apache.log4j.Logger;


public class Test {
	public static Logger log = Logger.getLogger(TestLog.class);
	@org.junit.Test
	public void test(){
		int[] GrowthRate;//生长速度
		GrowthRate = new int[8];
		GrowthRate[0] = 1;
		GrowthRate[1] = 2;	  
		GrowthRate[2] = 3;	 
		GrowthRate[3] = 4;
		GrowthRate[4] = 5;
		GrowthRate[5] = 6;
		GrowthRate[6] = 7;
		GrowthRate[7] = 8;
		
		
		//H:当天所有竹子生长长度的和
	       int H=GrowthRate[0]+GrowthRate[1]+GrowthRate[2]+GrowthRate[3]+GrowthRate[4]+GrowthRate[5]+GrowthRate[6]+GrowthRate[7]; 
	     //DH：当天最高的竹子高度
		   Double DH = 8d;
		   //zhuzi:存放竹子对象
		   int[] zhuzi = new int[]{0,0,0,0,0,0,0,0};
		   
		   double[] dh_h = new double[]{0,0,0,0,0,0,0,0};
		   //dh_h最小值
		   double max_dh_h = 0d;
		 //dh_h最小值的索引
		   int max_dh_h_index = 0;
		   
		   for(int i = 0;i < 1000; i++ ){
			   max_dh_h = 0d;
			   max_dh_h_index = 0;
			   //第i+1天每个竹子1-8生长
			   for(int j = 0; j < 8; j ++){
				   zhuzi[j] +=  GrowthRate[j];
			   }
			   System.out.println("第" + (i+1) + "天 ：" + zhuzi[0] + "," +zhuzi[1] + "," +zhuzi[2] + "," +zhuzi[3] + "," +zhuzi[4] + "," +zhuzi[5] + "," +zhuzi[6] + "," +zhuzi[7]);
			   //竹子生长后计算DH/h
//			   if(i == 0){
				   //计算dh_h
				   for(int b = 0; b < 8; b++){
					   dh_h[b] = (double)zhuzi[b]/H;
				   }
				   System.out.println("第" + i + "天 ：" + dh_h[0] + "," +dh_h[1] + "," +dh_h[2] + "," +dh_h[3] + "," +dh_h[4] + "," +dh_h[5] + "," +dh_h[6] + "," +dh_h[7]);
				   //比较dh_h,得到最小值
				   max_dh_h = dh_h[0];
				   max_dh_h_index = 0;
				   for(int c = 0; c < 8; c++){
					   if(max_dh_h < dh_h[c]){
						   max_dh_h = dh_h[c];
						   max_dh_h_index = c;
					   }
				   }
				   //得到应该砍掉的竹子，将其高度变为0
				   zhuzi[max_dh_h_index] = 0;
				   
				   System.out.println("应该砍的竹子是第：" + (max_dh_h_index+ 1) + "颗");
				   
				   log.warn(max_dh_h_index+ 1);
				   //打印当前竹子的高度
				   System.out.println("第" + (i+1) + "天 ：" + zhuzi[0] + "," +zhuzi[1] + "," +zhuzi[2] + "," +zhuzi[3] + "," +zhuzi[4] + "," +zhuzi[5] + "," +zhuzi[6] + "," +zhuzi[7]);
//			   }
			   
		   }
		   
		      
	       
	       
	}
}
