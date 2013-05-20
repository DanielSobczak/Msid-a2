package com.example.msid_a2;

import java.util.ArrayList;

public class Liczydlo {
	
	  public float iMin(ArrayList<Point> pt) {
         float imin = Integer.MAX_VALUE;
          for(Point p : pt) {
              if(imin>p.x)
                  imin = p.x;
          }
          
          return iMin;            
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

}
