package gui.utils;

import gui.VisualizzaProdottiController;
import javafx.event.ActionEvent;
import prodotti.TipoPortata;
import serverCentrale.ServerCentraleEsterno;
import serverCentrale.ServerCentraleInterno;

import java.io.IOException;

public class MasterController {

    protected ServerCentraleEsterno server  = new ServerCentraleEsterno();

    public void loadOrdinazioni(ActionEvent event) throws IOException {
        FXMLManager.loadFXML(event, "/gui/ConfermaOrdinazioni.fxml");
    }

    public void loadHome(ActionEvent event) throws IOException {
        FXMLManager.loadFXML(event, "/gui/Home.fxml");
    }

    public void loadVisualizzaProdotti(ActionEvent event) throws IOException {
        this.loadHome(event);
    }
}
