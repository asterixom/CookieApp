package de.cookieapp.data.model;

public enum Units {

	g(1),kg(1000),Tasse(200),EL(20),TL(5);
	
	final double gramm;
	
	Units(double gramm){
		this.gramm = gramm;
	}
	
	public double get100Gramm(){
		return gramm/100;
	}
	
	public double getInX(Units X){
		return gramm/X.gramm;
	}
}
