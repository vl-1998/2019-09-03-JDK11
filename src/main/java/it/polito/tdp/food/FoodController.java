/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
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
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	String passi = txtPassi.getText();
    	Integer N;
    	String scelta = this.boxPorzioni.getValue();
    	
    	if(passi == null) {
    		txtResult.appendText("Inserire un valore numerico per il numero di passi.");
    	}
    	if(scelta==null) {
    		txtResult.appendText("Scegliere una porzione!");
    	}
    	
    	try {
    		N = Integer.parseInt(passi);
    		
    	} catch (IllegalArgumentException e) {
        	txtResult.appendText("Inserire valore numerico!");
        	return;
        }
    	
    	for (String s : this.model.cammino(scelta, N)) {
    		txtResult.appendText(s+"\n");
    	}
    	txtResult.appendText("Il cammino ha peso = "+this.model.getPesoMax()+"\n");
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate..."+"\n");
    	
    	String scelta = this.boxPorzioni.getValue();
    	
    	if(scelta==null) {
    		txtResult.appendText("Scegliere una porzione!");
    	}
    	
    	for(PorzioneAdiacente p : this.model.direttamenteConnessi(scelta)) {
    		txtResult.appendText(p.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	String i = this.txtCalorie.getText();
    	Integer calorie;
    	
    	if(i==null) {
    		txtResult.appendText("Inserire un valore numerico per le calorie.");
    	}
    	
    	try {
    		calorie = Integer.parseInt(i);
    		
    	} catch (IllegalArgumentException e) {
        	txtResult.appendText("Inserire valore numerico!");
        	return;
        }
    	
    	
    	
    	this.model.creaGrafo(calorie);
    	
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(this.model.getVertex());
    	
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#Vertici = "+this.model.getVertex().size()+" #Archi = "+this.model.getEdge().size());
    	
    	
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
