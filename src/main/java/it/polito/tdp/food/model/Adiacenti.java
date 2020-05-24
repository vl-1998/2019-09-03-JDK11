package it.polito.tdp.food.model;

public class Adiacenti {
	private String primo;
	private String secondo;
	private int peso;
	/**
	 * @param primo
	 * @param secondo
	 * @param peso
	 */
	public Adiacenti(String primo, String secondo, int peso) {
		super();
		this.primo = primo;
		this.secondo = secondo;
		this.peso = peso;
	}
	public String getPrimo() {
		return primo;
	}
	public void setPrimo(String primo) {
		this.primo = primo;
	}
	public String getSecondo() {
		return secondo;
	}
	public void setSecondo(String secondo) {
		this.secondo = secondo;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Adiacenti [primo=" + primo + ", secondo=" + secondo + ", peso=" + peso + "]";
	}
	
	

}
