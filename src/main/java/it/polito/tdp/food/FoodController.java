/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	int lunghezza;  
    	try {
    		lunghezza = Integer.parseInt(txtPassi.getText());
    	} catch (Throwable t) {
    		txtResult.appendText("Inserire un input valido");
    		return;
    	}
    	
    	List <String> res = this.model.trovaPercorso(lunghezza, boxPorzioni.getValue());
    	
    	for (String s : res) {
    		txtResult.appendText(s+"\n");
    	}
    	
    	txtResult.appendText("\nPeso del cammino " + this.model.getPesoMassimo());
    	
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	String vertice = boxPorzioni.getValue();
    	txtResult.appendText("Porzioni adiacenti a: "+vertice+"\n");
    	for (PorzioneAdiacente p: this.model.direttamenteConnessi(vertice)) {
    		txtResult.appendText(p.toString()+"\n");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	int calorie;  
    	try {
    		calorie = Integer.parseInt(txtCalorie.getText());
    	} catch (Throwable t) {
    		txtResult.appendText("Inserire un input valido");
    		return;
    	}
    	
    	this.model.creaGrafo(calorie);
    	
    	boxPorzioni.getItems().clear();
    	boxPorzioni.getItems().addAll((model.getVrticiGrafo()));
    	
    	txtResult.appendText("Grafo creato. #VERTCIC: "+this.model.vertexNumber()+" #ARCHI: "+this.model.edgeNumber());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
