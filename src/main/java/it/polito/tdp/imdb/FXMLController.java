/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.imdb.model.Adiacente;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Integer anno=this.boxAnno.getValue();
    	if(anno!=null) {
    		List<Director> registi=this.model.creaGrafo(anno);
    		this.boxRegista.getItems().addAll(registi);
    	}else {
    		this.txtResult.setText("inserire un anno");
    	}
    
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	Director regista=this.boxRegista.getValue();
    	if(regista!=null) {
    		List<Adiacente> adiacenti=model.getAdiacenti(regista);
    		for (Adiacente a: adiacenti) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    	}else {
    		
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	String cString=this.txtAttoriCondivisi.getText();
    	Director d=this.boxRegista.getValue();
        if(cString!="" || d==null) {
        	try {
        		int c=Integer.parseInt(cString);
        		List<Director> risultato=this.model.doRicorsione(c,d);
        		int totale=this.model.getCondivisi();
        		this.txtResult.setText("totale attori condivisi:"+totale+"\n" );
        		for(Director regista: risultato) {
        			this.txtResult.appendText(regista.toString());
        		}
        		
        	}catch(NumberFormatException e) {
        		this.txtResult.setText("inserire un numero intero");
        	}
        	
        }else {
        	this.txtResult.setText("inserire un numero intero e un regista");
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	List<Integer> anni=new LinkedList<>();
    	anni.add(2004);
    	anni.add(2005);
    	anni.add(2006);
    	this.boxAnno.getItems().addAll(anni);
    	
    }
    
}
