package it.polito.tdp.food.model;

public class PorzioneAdiacente implements Comparable <PorzioneAdiacente> {
	private String porzione;
	private Integer peso;
	/**
	 * @param porzione
	 * @param peso
	 */
	public PorzioneAdiacente(String porzione, Integer peso) {
		super();
		this.porzione = porzione;
		this.peso = peso;
	}
	public String getPorzione() {
		return porzione;
	}
	public void setPorzione(String porzione) {
		this.porzione = porzione;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((porzione == null) ? 0 : porzione.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PorzioneAdiacente other = (PorzioneAdiacente) obj;
		if (porzione == null) {
			if (other.porzione != null)
				return false;
		} else if (!porzione.equals(other.porzione))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "porzione:" + porzione + ", peso=" + peso;
	}
	@Override
	public int compareTo(PorzioneAdiacente o) {
		return this.getPorzione().compareTo(o.getPorzione());
	}
	
	

}
