package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.Matrix;
import Matrix.NoSquareException;
import android.util.Log;



public class Liczydlo {


	float iMin,iMax,jMin,jMax = Integer.MIN_VALUE;

	public Liczydlo(){
	}

	/**
	 * @param a - Alpha ze wzoru na Hi
	 * @param b - Beta ze wzoru na Hi
	 * @param points - Zbior punktow nalezacych do przedzialu
	 * @return
	 */
	public List<Point> getHi(float a, float b, List<Point> points){
		// if(iMin<0 || iMax<0 || iMin<0 || jMax<0) policzIJ(points);
		ArrayList<Point> result = new ArrayList<Point>();
		for(Point point : points){
			if(point.x > ((1.0-a)*iMin+a*iMax) && point.x < (1-b)*iMin + b*iMax) 
				result.add(point);
		}
		Log.d("H","xxxResult Hi.size: "+result.size());
		return result;
	}

	/**
	 * @param a - Alpha ze wzoru na Hj
	 * @param b - Beta ze wzoru na Hj
	 * @param points - Zbior punktow nalezacych do przedzialu
	 * @return
	 */
	public List<Point> getHj(float a, float b, List<Point> points){
		// if(iMin<0 || iMax<0 || jMin<0 || jMax<0) policzIJ(points);
		ArrayList<Point> result = new ArrayList<Point>();
		for(Point point : points){
			if(point.y >= ((1.0-a)*jMin+a*jMax) && point.y < (1-b)*jMin + b*jMax) 
				result.add(point);
		}
		Log.d("H","xxxResult Hj.size: "+result.size());
		return result;
	}

	/**
	 * 
	 * @param points Narysowane punkty
	 * @returnn minialny x z narysowanych pktow
	 */
	public float getiMin(List<Point> points) {
		float imin = Integer.MAX_VALUE;  
		for(Point p : points) {
			if(imin>p.x)
				imin = p.x;
		}

		return imin;            
	}
	
	/**
	 * 
	 * @param points Narysowane punkty
	 * @returnn max x z narysowanych pktow
	 */

	public float getiMax(List<Point> points) {

		float imax = Integer.MIN_VALUE;

		for(Point p : points) {
			if(imax<p.x)
				imax = p.x;
		}

		return imax;

	}
	
	/**
	 * 
	 * @param points Narysowane punkty
	 * @returnn max y z narysowanych pktow
	 */

	public float getjMax(List<Point> points) {

		float jmax = Integer.MIN_VALUE;
		for(Point p : points) {
			if(jmax<p.y)
				jmax = p.y;
		}

		return jmax;   
	}
	
	/**
	 * 
	 * @param points Narysowane punkty
	 * @returnn min y z narysowanych pktow
	 */

	public float getjMin(List<Point> points) {

		float jmin = Integer.MAX_VALUE;

		for(Point p : points) {
			if(jmin>p.y)
				jmin = p.y;
		}

		return jmin;            
	}
	
	/**
	 * Liczy maxI,minI,maxJ,minJ
	 * @param points
	 */

	public void policzIJ(List<Point> points){
		iMin = getiMin(points);
		jMin = getjMin(points);
		iMax = getiMax(points);
		jMax = getjMax(points);
		Log.d("kupa",String.format("imin %s   jMin %s   iMax %s  jMax %s", iMin, jMin, iMax, jMax));
	}
	
	/**
	 * 
	 * @param points - Punkty, ktore tworza litere
	 * @return result - Cecha1, opisana w tresci zadania
	 */

	public double cechaQ1(List<Point> points){
		policzIJ(points);
		List<Point> hI = getHi(0, 0.3f,points);
		double result = (getjMax(hI) - getjMin(hI))/(jMax - jMin);
		return result;
	}

	
/**
 * 
 * @param points - Punkty, ktore tworza litere
 * @return result - Cecha2 opisana w tresci zadania
 */
	public double cechaQ2(List<Point> points){
		policzIJ(points);
		List<Point> hJ = getHj(0.4f, 0.6f,points);
		double result = (getiMax(hJ) - getiMin(hJ))/(iMax - iMin);
		return result;
	}



/**
 * 
 * @param litery - lista liter
 * @param D  - wymiar
 * @param cechy - cechy do modelu
 * @return
 * @throws IllegalDimensionException
 * @throws NoSquareException
 */
	public Litera klasyfikacja(List<Litera> litery, int D, Matrix cechy) throws IllegalDimensionException, NoSquareException{
		List<Double> modele = new ArrayList<Double>();
		List<Double> liczniki = new ArrayList<Double>();
		

		for (int i = 0; i < litery.size(); i++) {
			double modelStat = (litery.get(i).modelStat(D, cechy));
			modele.add(modelStat);
			liczniki.add(modelStat*litery.get(i).getTheta());
			litery.get(i).prawdopodobienstwo = liczniki.get(i);
		}
		Litera max = litery.get(0);
		for(Litera l:litery){
			Log.d("kupa",String.format("Litera %s = %s",l.name,l.prawdopodobienstwo));
			if(l.prawdopodobienstwo>max.prawdopodobienstwo) max = l;
		}
		
		return max;
		
	}

}
