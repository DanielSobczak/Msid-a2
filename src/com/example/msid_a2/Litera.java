package com.example.msid_a2;

import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.Matrix;
import Matrix.MatrixMathematics;


public class Litera {
	Matrix srednia;
	Matrix macierzKowariancji;
	double theta;

	List<WektorCech2> przykladyDoNauki;
	
	Litera(List<WektorCech2> cechy){
		przykladyDoNauki = cechy;
		theta = Integer.MIN_VALUE;
	}

	/**
	 * 
	 * @Macierz- u("mi") = Macierz Nx1 (N ilosc wystapien danej litery podczas nauki)
	 * @throws IllegalDimensionException
	 */
	public Matrix liczSrednia() throws IllegalDimensionException{
		Matrix suma = new Matrix(2, 1);
		for(WektorCech2 w:przykladyDoNauki){
			suma=MatrixMathematics.add(w.getMatrix(), suma);
		}
		Matrix result = suma.multiplyByConstant(1.0/przykladyDoNauki.size());
		srednia = result;
		return result;
	}
	
  	public Matrix liczMacierzKowariancji(Matrix u) throws IllegalDimensionException{
  		Matrix suma = new Matrix(u.getNrows(), u.getNrows());
  		for(WektorCech2 w:przykladyDoNauki){
  			Matrix m1 = MatrixMathematics.subtract(w.getMatrix(), u);
  			Matrix m2 = MatrixMathematics.transpose(m1);
  			Matrix iloczyn = MatrixMathematics.multiply(m1, m2);
  			suma = MatrixMathematics.add(suma,iloczyn);
  		}
  		suma = suma.multiplyByConstant(1.0/przykladyDoNauki.size());
  		macierzKowariancji=suma;
  		return suma;
  	}
  	
  	/**
  	 * 
  	 * @param iloscWystapien - liczba wystapien danej litery podczas nauki
  	 * @param calkowitaIlosc - liczba wszystich par w nauce (suma iloscWystapien z kazdych liter)
  	 */
  	public void setTheta(double iloscWystapien,double calkowitaIlosc){
  		theta = iloscWystapien/calkowitaIlosc;
  	}
  	
  	public double getTheta(){
  		return theta;
  	}
  	
}
