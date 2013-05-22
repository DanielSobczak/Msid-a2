package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.Matrix;
import Matrix.MatrixMathematics;
import Matrix.NoSquareException;



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
	
	  public float getiMin(List<Point> points) {
         float imin = Integer.MAX_VALUE; 
          for(Point p : points) {
              if(imin>p.x)
                  imin = p.x;
          }
          
          return imin;            
      }
      
      public float getiMax(List<Point> points) {
          
         float imax = Integer.MIN_VALUE;
          
          for(Point p : points) {
              if(imax<p.x)
                  imax = p.x;
          }
          
          return imax;
          
      }
      
      public float getjMax(List<Point> points) {
          
         float jmax = Integer.MIN_VALUE;
          for(Point p : points) {
              if(jmax<p.y)
                  jmax = p.y;
          }
          
          return jmax;   
      }
      
      public float getjMin(List<Point> points) {
          
         float jmin = Integer.MAX_VALUE;
          
          for(Point p : points) {
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
      
      public double modelStat(Litera litera,int D,Matrix cechy) throws IllegalDimensionException, NoSquareException{
    	 if(cechy.getNrows() != D) throw new ArrayIndexOutOfBoundsException(cechy.getNrows());
    	 double[] wynik = new double[D];
    	 //Liczenie wykladnika; m3=(phi-mi)  m2=Ek^-1 m1=m3^T ; T-transponowane
    	 Matrix m3 = MatrixMathematics.subtract(cechy, litera.liczSrednia());
    	 Matrix m2 = MatrixMathematics.inverse(litera.liczMacierzKowariancji(litera.srednia));
    	 Matrix m1 = MatrixMathematics.transpose(m3);
    	 double wyznacznik = MatrixMathematics.multiply(MatrixMathematics.multiply(m1, m2),m3).multiplyByConstant(-0.5).getValueAt(0, 0); // {-1/2 *m1*m2*m3}
    	//Policzenie modelu
    	 double p = 1/(Math.pow(2*Math.PI,(double)D/2.0)*Math.sqrt(MatrixMathematics.determinant(litera.macierzKowariancji))) * Math.exp(wyznacznik);
    	 return p;
      }
      
}
