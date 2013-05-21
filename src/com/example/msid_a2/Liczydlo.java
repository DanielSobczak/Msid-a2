package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;



public class Liczydlo {
	

	float iMin,iMax,jMin,jMax = Integer.MIN_VALUE;

	
	
	 public List<Point> getHi(float a, float b, List<Point> points){
		// if(iMin<0 || iMax<0 || iMin<0 || jMax<0) policzIJ(points);
		 ArrayList<Point> result = new ArrayList<Point>();
		 for(Point point : points){
			 if(point.x > ((1.0-a)*iMin+a*jMax) && point.x < (1-b)*iMin + b*iMax) 
				 result.add(point);
		 }
		 return result;
	 }
	 
	 public List<Point> getHj(float a, float b, List<Point> points){
		// if(iMin<0 || iMax<0 || jMin<0 || jMax<0) policzIJ(points);
		 ArrayList<Point> result = new ArrayList<Point>();
		 for(Point point : points){
			 if(point.x > ((1.0-a)*jMin+a*jMax) && point.x < (1-b)*jMin + b*jMax) 
				 result.add(point);
		 }
		 return result;
	 }
	
	  public float getiMin(List<Point> pt) {
         float imin = Integer.MAX_VALUE;
          for(Point p : pt) {
              if(imin>p.x)
                  imin = p.x;
          }
          
          return imin;            
      }
      
      public float getiMax(List<Point> pt) {
          
         float imax = Integer.MIN_VALUE;
          
          for(Point p : pt) {
              if(imax<p.x)
                  imax = p.x;
          }
          
          return imax;
          
      }
      
      public float getjMax(List<Point> pt) {
          
         float jmax = Integer.MIN_VALUE;
          for(Point p : pt) {
              if(jmax<p.y)
                  jmax = p.y;
          }
          
          return jmax;   
      }
      
      public float getjMin(List<Point> pt) {
          
         float jmin = Integer.MAX_VALUE;
          
          for(Point p : pt) {
              if(jmin>p.y)
                  jmin = p.y;
          }
          
          return jmin;            
      }
      
      public void policzIJ(List<Point> points){
    	  iMin = getiMin(points);
    	  jMin = getjMin(points);
    	  iMax = getiMax(points);
    	  jMax = getjMax(points);
      }
      
      public double cechaQ1(List<Point> points){
    	  List<Point> hI = getHi(0, 0.3f,points);
    	  if(iMin<0 || iMax<0 || iMin<0 || jMax<0) policzIJ(points);
    	  double result = (getjMax(hI) - getjMin(hI))/(jMax - jMin);
    	  return result;
      }
      
      public double cechaQ2(List<Point> points){
    	  List<Point> hJ = getHi(0.4f, 0.6f,points);
    	  if(iMin<0 || iMax<0 || iMin<0 || jMax<0) policzIJ(points);
    	  double result = (getiMax(hJ) - getiMin(hJ))/(iMax - iMin);
    	  return result;
      }
      
      public double modelStat(Litera l, double D){
    	  l.liczOdchylenie();
    	  double wynik = (1/(Math.pow((2*Math.PI),D/2.0)*)*Math.exp(-(1/2)*()));
      }
      


}
