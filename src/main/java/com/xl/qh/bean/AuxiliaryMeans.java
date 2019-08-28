package com.xl.qh.bean;

public class AuxiliaryMeans {
	
	public static BaseAuxiliaryMean maAuxiliaryMean;
	public static BaseAuxiliaryMean ktAuxiliaryMean;
	
	static {
		maAuxiliaryMean = new MaAuxiliaryMean();
		ktAuxiliaryMean = new KtAuxiliaryMean();
	}
	
	public static BaseAuxiliaryMean newMaAuxiliaryMean(){
		return maAuxiliaryMean;
	}
	
	public static BaseAuxiliaryMean newKtAuxiliaryMean(){
		return ktAuxiliaryMean;
	}
}
