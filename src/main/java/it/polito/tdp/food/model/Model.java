package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> best;
	private int pesoMassimo=0;
	private int pesoTemp=0;
	
	
		
	public int getPesoMassimo() {
		return pesoMassimo;
	}

	public void creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph <>(DefaultWeightedEdge.class);
		FoodDao dao = new FoodDao();
		
		//vertici
		Graphs.addAllVertices(grafo, dao.portionsCalorie(calorie));
		
		
		//aggiungo gli archi
		for (Adiacenti a: dao.getAdiacenti()) {
			//se il grafo contiene i due vertici
			if (this.grafo.containsVertex(a.getPrimo()) && this.grafo.containsVertex(a.getSecondo())) {
				Graphs.addEdge(grafo, a.getPrimo(), a.getSecondo(), a.getPeso());
			}
		}
		
	}
	
	public int vertexNumber() {
		return grafo.vertexSet().size();
	}
	public int edgeNumber() {
		return grafo.edgeSet().size();
	}

	public Collection<String> getPorzioni() {
		List <String> res = new ArrayList<>();
		res.addAll(this.grafo.vertexSet());
		Collections.sort(res);
		return res;
	}
	
	//QUESTO NON FUNZIONA
	/*public String direttamenteConnessi(String scelta) {
		String res="";
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			for (String s: this.grafo.vertexSet()) {
				if (s.compareTo(scelta)==0) {
					for (String ss: Graphs.neighborListOf(grafo, s)) {
						if (res==null) {
							res = "Vertice: "+ss+" Peso: "+this.grafo.getEdgeWeight(e);
						}else {
							res = res +"\n" + "Vertice: "+ss+" Peso: "+this.grafo.getEdgeWeight(e);
						}
					}
				}
			}
		}
		return res;
	}*/
	
	public List<PorzioneAdiacente> direttamenteConnessi(String scelta){
		List<String> vicini = Graphs.neighborListOf(this.grafo, scelta);
		List<PorzioneAdiacente> result = new ArrayList <>();
		for (String s: vicini) {
			PorzioneAdiacente pTemp = new PorzioneAdiacente(s, this.grafo.getEdgeWeight(this.grafo.getEdge(scelta, s)));
			result.add(pTemp);
		}
		
		Collections.sort(result);
		return result;
	}
	
	public List <String> trovaPercorso(int lunghezza, String verticeIniziale){
		List <String> parziale = new ArrayList<>();
		this.best= new ArrayList<>();
		parziale.add(verticeIniziale);
		
		trovaRicorsivo(lunghezza,parziale, verticeIniziale);
		return this.best;
		
	}
	private void trovaRicorsivo(int lunghezza, List<String> parziale, String verticePrecedente) {	
		//caso terminale, ho raggiunto il numero di iterazioni
		if (parziale.size()==lunghezza+1) {//se voglio avere N archi, avrò N+1 vertici
			if (pesoTemp>pesoMassimo) {//se il peso 
				this.best= new ArrayList<>(parziale);
				this.pesoMassimo=pesoTemp;
			}
			return;
		}
		
		//scorro 
		for (String vicino : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			//controllo che il cammino non contenga già il vertice
			if (!parziale.contains(vicino)) {
				DefaultWeightedEdge arco = this.grafo.getEdge(parziale.get(parziale.size()-1), vicino);
				parziale.add(vicino);
				pesoTemp += this.grafo.getEdgeWeight(arco);
				this.trovaRicorsivo(lunghezza, parziale, vicino);
				parziale.remove(parziale.size()-1);
				pesoTemp = (int) (pesoTemp - this.grafo.getEdgeWeight(arco));
			}
		}
		
	}

	public List<String> getVrticiGrafo() {
		List<String> result = new ArrayList<>();
		for (String s: this.grafo.vertexSet()) {
			result.add(s);
		}
		Collections.sort(result);
		return result;
	}
	
}
