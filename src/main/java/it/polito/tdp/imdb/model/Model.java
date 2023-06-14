package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Map<Integer,Director> idMApDirectors;
	private Graph<Director, DefaultWeightedEdge> grafo;
	
	//variabili ricorsione
	private int c;
	List<Director> listaMassima;
	List<Director> rimanenti;
	int concatenati;
	int totCondivisi;
	
	public Model() {
		this.dao=new ImdbDAO();
	}
	
	public List<Director> creaGrafo(int anno) {
		this.idMApDirectors=new HashMap<>();
		this.grafo=new SimpleWeightedGraph<Director,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Director> vertici=this.dao.directorsForYear(anno);
		for(Director d: vertici) {
			this.idMApDirectors.put(d.id, d);
		}
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Pair> coppie=this.dao.getPairs(anno,this.idMApDirectors);
		
		for (Pair p:coppie) {
			Graphs.addEdge(this.grafo, p.getD1(), p.getD2(), p.getPeso());
		}
		Collections.sort(vertici);
		
		System.out.println(this.grafo.vertexSet().size());
		System.out.println(this.grafo.edgeSet().size());
		return vertici;
		
	}

	public List<Adiacente> getAdiacenti(Director regista) {
		List<Adiacente> adiacenti=new ArrayList<>();
		for(Director d: Graphs.neighborListOf(this.grafo, regista)) {
			DefaultWeightedEdge edge=this.grafo.getEdge(d, regista);
			Adiacente adiacente=new Adiacente(d, (int) this.grafo.getEdgeWeight(edge));
			adiacenti.add(adiacente);
		}
		
		Collections.sort(adiacenti);
		return (adiacenti);
		
		
	}

	public List<Director> doRicorsione(int c, Director d) {
		this.totCondivisi=0;
		this.c=c;
		this.concatenati=0;
		this.listaMassima=new ArrayList<>();
		List<Director> parziale=new ArrayList<>();
		cerca(parziale, d,0);
		return this.listaMassima;
	}
	
	public void cerca(List<Director> parziale, Director last,double sommaPesi) {
		
		
		List<Director> adiacenti=Graphs.neighborListOf(this.grafo,last);
		for(Director d1: adiacenti) {
			if(!parziale.contains(d1) && sommaPesi+this.grafo.getEdgeWeight(this.grafo.getEdge(last, d1))<=this.c) {
				parziale.add(d1);
				cerca(parziale,d1,sommaPesi+this.grafo.getEdgeWeight(this.grafo.getEdge(last, d1)));
				parziale.remove(d1);
			}
		}
		
		if(parziale.size()>this.concatenati) {
			this.totCondivisi=(int)sommaPesi;
			this.concatenati=parziale.size();
			this.listaMassima=new ArrayList<Director>(parziale);
		}
		
		
		
		
	}
	public int getCondivisi() {
		return this.totCondivisi;
	}

	
}
