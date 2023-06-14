package it.polito.tdp.imdb.model;

public class Adiacente implements Comparable<Adiacente> {
	
	private Director d;
	private int condivisi;
	
	public Adiacente(Director d, int condivisi) {
		super();
		this.d = d;
		this.condivisi = condivisi;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public int getCondivisi() {
		return condivisi;
	}
	public void setCondivisi(int condivisi) {
		this.condivisi = condivisi;
	}
	@Override
	public int compareTo(Adiacente o) {
		return o.getCondivisi()-this.condivisi;
	}
	@Override
	public String toString() {
		return d.getFirstName()+" "+d.getLastName()+": "+condivisi;
	}
	
	
	

}
