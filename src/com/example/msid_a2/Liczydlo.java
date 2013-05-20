package com.example.msid_a2;

import java.util.ArrayList;

public class Liczydlo {
	
	  
    float imin, imax, jmin, jmax;
    ArrayList<Point> hi, hj;
	private ArrayList<Point> pts;
    
    public Liczydlo(ArrayList<Point> pts) {
        this.pts = pts;
        imin = iMin(pts);
        imax = iMax(pts);
        jmin = jMin(pts);
        jmax = jMax(pts);
        
        hi = new ArrayList<Point>();
        hj = new ArrayList<Point>();
        
    }
	
	
	  public float iMin(ArrayList<Point> pt) {
         float imin = Integer.MAX_VALUE;
          for(Point p : pt) {
              if(imin>p.x)
                  imin = p.x;
          }
          
          return imin;            
      }
      
      public float iMax(ArrayList<Point> pt) {
          
         float imax = Integer.MIN_VALUE;
          
          for(Point p : pt) {
              if(imax<p.x)
                  imax = p.x;
          }
          
          return imax;
          
      }
      
      public float jMax(ArrayList<Point> pt) {
          
         float jmax = Integer.MIN_VALUE;
          
          for(Point p : pt) {
              if(jmax<p.y)
                  jmax = p.y;
          }
          
          return jmax;
          
      }
      
      public float jMin(ArrayList<Point> pt) {
          
         float jmin = Integer.MAX_VALUE;
          
          for(Point p : pt) {
              if(jmin>p.y)
                  jmin = p.y;
          }
          
          return jmin;            
      }
      
      public ArrayList<Point> fillHi(double a, double b) {
          
          for(Point p : pts) {
              if((1-a)*imin+a*imax <= p.x && (1-b)*imin+b*imax >= p.x) {
                  hi.add(p);
              }
          }
          
          return hi;
          
      }
      
      public ArrayList<Point> fillHj(double a, double b) {
          
          for(Point p : pts) {
              if((1-a)*jmin+a*jmax <= p.x && (1-b)*jmin+b*jmax >= p.x) {
                  hj.add(p);
              }
          }
          
          return hj;
          
      }

}
