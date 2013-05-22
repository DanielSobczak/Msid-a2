package com.example.msid_a2;

import Matrix.Matrix;

public class Estymator {
	double srednia=0,odchylenie=0,theta=0;

	public void setSrednia(double srednia) {
		this.srednia = srednia;
	}

	public void setOdchylenie(double odchylenie) {
		this.odchylenie = odchylenie;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	public Matrix getMatrix(){
		double[][] m={{srednia},{odchylenie},{theta}};
		return new Matrix(m);
		}

	public Estymator(){
	} 

}
