package gui.cliente.controller;

import com.jfoenix.controls.JFXButton;
import gui.cliente.thread.FXServiceConto;
import gui.cliente.utils.Clock;
import gui.cliente.utils.ManagerOrdinazioni;
import gui.cliente.general_controller.MasterController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
import prodotti.prodotto_ordinato.ProdottoOrdinato;
import prodotti.prodotto_ordinato.StatoProdottoOrdinato;
import serverCentrale.cliente.ServerCentraleEsterno;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TimerContoController extends MasterController implements Initializable {


    public JFXButton conto;
    public Label table;
    public Label time;
    public JFXButton carrello;
    public Label Tempo;
    public Text txtConto;
    private Timeline clock;

    protected ActionEvent actionEvent;

    private List<ProdottoOrdinato> ordini = new ArrayList<>();
    private ServerCentraleEsterno serverCentraleEsterno = new ServerCentraleEsterno();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setText(table.getText() + TableIdController.idTavolo);
        refresh();
    }

    private void checkConto(){
        conto.setDisable(true);
        for (ProdottoOrdinato p: ordini) {
            if (p.getStato() != StatoProdottoOrdinato.CONSEGNATO) {
                return;
            }
        }
        conto.setDisable(false);
    }


    public void refresh(){
        this.clock = new Timeline(new KeyFrame(Duration.ZERO, event1 ->{
            Clock.initClock(time);
            ordini = serverCentraleEsterno.getOrdini(TableIdController.idTavolo);
            ManagerOrdinazioni.refreshOrdinazioniButton(carrello);
            conto.setDisable(true);
            checkConto();
            checkStatoProdottoOrdinato();

        }
        ),
                new KeyFrame(Duration.seconds(1)));
        this.clock.setCycleCount(Animation.INDEFINITE);
        this.clock.play();
    }

    private void checkStatoProdottoOrdinato() {

        String temp="";
        //ProdottoOrdinato p = ordini.get(1);
        float delta;
        for(ProdottoOrdinato p : ordini) {
            if(p.getStato()==StatoProdottoOrdinato.LAVORAZIONE) {
                delta= p.getTempoElaborazioneRimanente(maxTempoPreparazione());
                if (delta >= 0) {
                    temp = "" + (int) (delta / 60) + " Minuti " + (int) (delta % 60) + " Secondi";
                } else {
                    temp = "Tempo Scaduto";
                }
                break;
            }
        }
        Tempo.setLayoutX(504);
        Tempo.setText(temp);
    }

    private int maxTempoPreparazione() {
        int max=0;
        for(ProdottoOrdinato ord : ordini){
            // Prelevo il tempo di preparazione massimo
                if(ord.getProdotto().getTempoPreparazione()>max) {
                    max = ord.getProdotto().getTempoPreparazione();
                }


        }
        return  max*60; // MAX in minuti
    }

    public void loadConto(ActionEvent event) {
        this.actionEvent = event;

        FXServiceConto fxServiceConto = new FXServiceConto(super.server, TableIdController.idTavolo);
        fxServiceConto.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                txtConto.setText(txtConto.getText() + " " + event.getSource().getValue() );
                clock.stop();
                conto.setVisible(false);
            }
        });
        fxServiceConto.start();
    }
}