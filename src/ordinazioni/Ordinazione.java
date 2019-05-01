package ordinazioni;

import eccezioni.EliminaProdNonOrdException;
import eccezioni.OrdinazioneNegativaException;
import eccezioni.PrezzoNegativoException;
import prodotti.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Random;


public class Ordinazione implements OrdinazioneInterface {

    private int idTavolo;
    private String idOrdinazione;
    private ArrayList<ProdottoOrdinato> ordini;
    private LocalDateTime tempoInizioOrdinato;

    public Ordinazione(int idTavolo){
    	
    	Random rand = new Random();
    	int n=rand.nextInt(99)+1;
        this.idOrdinazione = idTavolo+":"+n;
        this.idTavolo = idTavolo;
        this.ordini  = new ArrayList<ProdottoOrdinato>();
    }
    
    public float getContoParziale(){
    	
    	float totale=0;
    	for (ProdottoOrdinato p : ordini) {
    		totale=p.getCostoParziale()+totale;
    	}
    	return totale;
    }

    @Override
    public boolean aggiungiOrdini(Prodotto prodotto, int quantita)   {

        try {

            this.ordini.add(new ProdottoOrdinato(prodotto, quantita));

        } catch (OrdinazioneNegativaException e) {
            e.printStackTrace();
        }

        return true;
    }

	public int getIdTavolo() {
		return idTavolo;
	}

	public String getIdOrdinazione() {
		return idOrdinazione;
	}



	public ArrayList<ProdottoOrdinato> getOrdini() {
		return ordini;
	}

	public void eliminaProdotto(Prodotto p) throws EliminaProdNonOrdException
    {
        for (ProdottoOrdinato prodotti : ordini)
        {
            if(prodotti.getProdotto().equals(p))
            {
                if(prodotti.getStato() == null  ) {
                    throw new EliminaProdNonOrdException();
                }
                else {
                    ordini.remove(prodotti);
                }
            }
        }
    }

    public void setTempoInizioOrdinato() {
        this.tempoInizioOrdinato = LocalDateTime.now();
    }

    public LocalDateTime getTempoInizioOrdinato() {
        return tempoInizioOrdinato;
    }

    public  LocalDateTime getTempoEffettivoOrdinato() {
        //TemporalAmount t;
        LocalDateTime temp = LocalDateTime.now();
        temp=temp.minusMinutes(tempoInizioOrdinato.getMinute());
        return  temp;
    }
}
