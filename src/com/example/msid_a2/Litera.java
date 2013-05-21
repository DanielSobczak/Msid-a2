package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;


public class Litera {

	Estymator Q1,Q2;
	List<WektorCech> cechy;
	
	
	Litera(List<WektorCech> cechy){
		this.cechy = cechy;
	}
	
	
	public void liczSrednia(){
		
		for(WektorCech x:cechy){
			Q1.srednia+=x.cechaQ1;
			Q2.srednia+=x.cechaQ2;
		}
		Q1.setSrednia(Q1.srednia/cechy.size());
		Q2.setSrednia(Q2.srednia/cechy.size());
	}
	
  	public void liczOdchylenie(){
		double suma1=0,suma2=0;
		
		for(WektorCech x:cechy){
			suma1+=(x.cechaQ1-Q1.srednia)*(x.cechaQ1-Q1.srednia);
			suma2+=(x.cechaQ2-Q2.srednia)*(x.cechaQ2-Q2.srednia);
		}
		
		Q1.odchylenie=Math.sqrt(suma1/cechy.size());
		Q2.odchylenie=Math.sqrt(suma2/cechy.size());
	}
}
