package gui.utils;

import javafx.event.ActionEvent;
import serverCentrale.ServerCentraleEsterno;

import java.io.IOException;

public class MasterController {

    protected ServerCentraleEsterno server  = new ServerCentraleEsterno();

    public void loadOrdinazioni(ActionEvent event) throws IOException {
        FXMLManager.loadFXML(event, "/gui/cliente/resources/ConfermaOrdinazioni.fxml");
    }

    public void loadHome(ActionEvent event) throws IOException {
        FXMLManager.loadFXML(event, "/gui/cliente/resources/Home.fxml");
    }

    public void loadVisualizzaProdotti(ActionEvent event) throws IOException {
        this.loadHome(event);
    }


}
