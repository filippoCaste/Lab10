/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulator;
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

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void handleSelectedRiver(ActionEvent event) {
    	this.txtResult.clear();
    	
    	River r = this.boxRiver.getValue();
    	if(r!=null) {
    		this.model.calculateParameters(r);
    		this.txtEndDate.setText(r.getLast().toString());
    		this.txtFMed.setText(r.getFlowAvg()+"");
    		this.txtNumMeasurements.setText(r.getN()+"");
    		this.txtStartDate.setText(r.getFirst().toString());
    		return;
    	} else {	
    		this.txtResult.setText("Errore imprevisto, il fiume selezionato non è al momento presente");
    	}
    }
    
    @FXML
    void doSimulate(ActionEvent event) {
    	this.txtResult.clear();

    	River r = this.boxRiver.getValue();
    	Double k = null;
    	try {
    		k = Double.parseDouble(this.txtK.getText());
    	} catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.setText("Inserisci un valore di k valido!");
    		return;
    	}
    	if(r!=null && k!=null && k>0) {

    		Simulator s = new Simulator();
    		s.initialize(r, k);
    		s.run();
  
    		txtResult.appendText("Fail days: " + s.getFail_days() + "\n"
    				+ "C_med: " + s.getC_med());
    	}
    	else {
    		txtResult.setText("i valori inseriti non sono validi!");
    	}
    		
    	return;
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxRiver.getItems().addAll(model.getAllRivers());
    }
}
