package com.example.msid_a2;

import Matrix.Matrix;

public class WektorCech2 {

	private double[][] cecha;
	
	public WektorCech2(double Q1,double Q2){
		cecha = new double[2][1];
		cecha[0][0] = Q1;
		cecha[0][1] = Q2;
	}
	
	/**
	 * 
	 * @param i - numer wiersza
	 * @param j - numer kolumny
	 * @param value - warotsc
	 */
	public void setAt(int i,int j,double value){
		cecha[i][j] = value;
	}
	
	/**
	 * 
	 * @param i numer wiersza
	 * @param j numer kolumny
	 * @return wartosc w danej komorce
	 */
	public double getFrom(int i, int j){
		return cecha[i][j];
	}
	
	public Matrix getMatrix(){
		return new Matrix(cecha);
	}
}
