package com.jxxhwl.wx.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

public class Test4 {
	
	@Test
	public void test(){
		Set<Double> set = new HashSet<Double>();
		List<Zhuzi> zl = new ArrayList<Zhuzi>(); 
		Zhuzi mubiao = null;
		double dou = 0d;
		Queue<Integer> Perque = new LinkedList<Integer>();
		Perque.add(0);        
		Perque.add(1);
		Perque.add(2);
		Perque.add(3);
		Perque.add(4);
		Perque.add(5);
		Perque.add(6);
		Perque.add(7);
		for(int i=8;i <= 100; i++){
			for(int j = 8;j<=i;j++){
				List<Zhuzi> list = new ArrayList<Zhuzi>();
				for(int k = 0; k < 8; k++){
					Queue<Integer> q = new LinkedList<Integer>();
					for(Integer in : Perque){
						q.add(in);
					}
					q.add(7);
					Test2 t = new Test2();
					Zhuzi zz = t.test(q);
					list.add(zz);
				}
				double q = 0d;
				Zhuzi zzzz = null;
				for(Zhuzi zhu : list){
					if(q == 0d){
						q = zhu.getQuotient();
						zzzz = zhu;
					}else{
						if(zhu.getQuotient() < q ){
							q = zhu.getQuotient();
							zzzz = zhu;
						}
					}
				}
//				System.out.println(zzzz.getPerque());
				Perque = zzzz.getPerque();
				zl.add(zzzz);
			}
		}
		for (Zhuzi z : zl) {
			if(dou == 0d){
				dou = z.getQuotient();
				mubiao = z;
			}else{
				if(z.getQuotient() < dou){
					dou = z.getQuotient();
					mubiao = z;
				}
			}
		}
		System.out.println(mubiao.getPerque());
		System.out.println(dou);
	}
}
