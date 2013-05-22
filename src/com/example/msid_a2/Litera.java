package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;

import Matrix.IllegalDimensionException;
import Matrix.Matrix;
import Matrix.MatrixMathematics;
import Matrix.NoSquareException;


public class Litera {
	Matrix srednia;
	Matrix macierzKowariancji;
	double theta;
	double prawdopodobienstwo;
	char name;

	List<WektorCech2> przykladyDoNauki;

	public Litera(char name){
		this.name = name;
		theta = Integer.MIN_VALUE;
		przykladyDoNauki = new ArrayList<WektorCech2>();
	}

	/**
	 * 
	 * @param cecha
	 * @do uruchamia liczSrednia() oraz liczMacierzKowariancji()
	 * @throws IllegalDimensionException
	 */
	public Litera(List<WektorCech2> cecha) throws IllegalDimensionException{
		przykladyDoNauki = cecha;
		theta = Integer.MIN_VALUE;
		liczSrednia();
		liczMacierzKowariancji(srednia);
	}

	public void add(WektorCech2 cecha, double N){
		przykladyDoNauki.add(cecha);
		doucz(cecha.getMatrix(),N);
	}

	public int size(){
		return przykladyDoNauki.size();
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

	/**
	 * 
	 * @param D - liczba wymiarów
	 * @param cechy - macierz wyliczonych cech zczytanych z obrazka
	 * @return model statystyczny dla danej litery
	 * @throws IllegalDimensionException
	 * @throws NoSquareException
	 */
	public double modelStat(int D,Matrix cechy) throws IllegalDimensionException, NoSquareException{
		if(cechy.getNrows() != D) throw new ArrayIndexOutOfBoundsException(cechy.getNrows());
		double[] wynik = new double[D];
		//Liczenie wykladnika; m3=(phi-mi)  m2=Ek^-1 m1=m3^T ; T-transponowane
		Matrix m3 = MatrixMathematics.subtract(cechy, this.liczSrednia());
		Matrix m2 = MatrixMathematics.inverse(this.liczMacierzKowariancji(this.srednia));
		Matrix m1 = MatrixMathematics.transpose(m3);
		double wyznacznik = MatrixMathematics.multiply(MatrixMathematics.multiply(m1, m2),m3).multiplyByConstant(-0.5).getValueAt(0, 0); // {-1/2 *m1*m2*m3}
		//Policzenie modelu
		double p = 1/(Math.pow(2*Math.PI,(double)D/2.0)*Math.sqrt(MatrixMathematics.determinant(this.macierzKowariancji))) * Math.exp(wyznacznik);
		return p;
	}


	public void doucz(Matrix cecha, double N) {
		try {
			if(srednia==null || macierzKowariancji == null) {
				liczSrednia();
				liczMacierzKowariancji(srednia);
				douczTheta(N);
			} else {
			douczSrednia(cecha);
			douczTheta(N);
			liczMacierzKowariancji(srednia);
			}
		} catch (IllegalDimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	private void douczSrednia(Matrix cecha) throws IllegalDimensionException{
		int size = size()<1?0:size()-1;

		srednia = srednia.multiplyByConstant(size);
		srednia = MatrixMathematics.add(srednia, cecha);
		srednia = srednia.multiplyByConstant(size());

	}

	private void douczTheta(double N){
		theta = size()/N;
	}
}
