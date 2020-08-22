package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph <String ,DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<String> best;
	private Integer pesoMax;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(Integer calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.listAllPortions(calorie));
		
		for (Adiacenza a : this.dao.getAdiacenze()) {
			if(this.grafo.containsVertex(a.getP1()) && this.grafo.containsVertex(a.getP2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
	}
	
	public List<String> getVertex(){
		List<String> vertici = new ArrayList<>(this.grafo.vertexSet()); 
		Collections.sort(vertici);
		return vertici;
	}
	public List<DefaultWeightedEdge> getEdge(){
		List<DefaultWeightedEdge> archi = new ArrayList<>(this.grafo.edgeSet()); 
		return archi;
	}
	
	public List<PorzioneAdiacente> direttamenteConnessi(String scelta){
		List<String> vicini = Graphs.neighborListOf(this.grafo, scelta);
		List<PorzioneAdiacente> result = new ArrayList <>();
		for (String s: vicini) {
			PorzioneAdiacente pTemp = new PorzioneAdiacente(s,(int)this.grafo.getEdgeWeight(this.grafo.getEdge(scelta, s)));
			result.add(pTemp);
		}
		
		Collections.sort(result);
		return result;
	}
	
	
	
	public Integer getPesoMax() {
		return pesoMax;
	}

	public List<String> cammino (String scelta, Integer N){
		this.best = new ArrayList<>();
		this.pesoMax = 0;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(scelta);
		Integer pTemp=0;
		
		cerca(parziale, N, pTemp);
		
		return best;
		
	}

	private void cerca(List<String> parziale, Integer n, Integer p) {
		List<String> successivi = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		
		if(parziale.size() == n+1) {
			if(p>pesoMax) {
				this.pesoMax = p;
				this.best = new ArrayList<>(parziale);
			}
			return;
		}
		
		for(String s : successivi) {
			if(!parziale.contains(s)) {
				DefaultWeightedEdge arco = this.grafo.getEdge(parziale.get(parziale.size()-1), s);
				parziale.add(s);
				p += (int)this.grafo.getEdgeWeight(arco);
				cerca(parziale,n, p);
				parziale.remove(s);
				p = (int) (p - this.grafo.getEdgeWeight(arco));
			}
		}
		
	}

}
