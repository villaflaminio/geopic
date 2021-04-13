package com.flaminiovilla.geris.component.geocode;

import com.flaminiovilla.geris.model.Place;
import com.flaminiovilla.geris.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Questa classe si occupa di gestire la localizzazione della citta'
 * piu' vicina rispetto le mie coordinate geografiche.
 * I places sono una lista statica presenti del dabase, e contengono
 * latitudine e longitudine e nome di tutti i comuni italiani , quindi la retrive verso
 * il database verra' eseguita una sola volta,e instanziando la classe tramite Singleton
 * riesco a tenere in memoria questa lista di place, diminuendo il carico verso il DB.
 * Dopo che ho la lista di tutti i comuni andro' a trasformarla in un albero, i cui nodi
 * conterranno latidudine , longitudine ed un nome univoco.
 * grazie al metodo nearestPlace scorro l'albero binario fino ad ottenere il nodo che ha
 * la distanza minima rispetto a la posizione di ricerca.
 *
 * Se un albero di ricerca non è destinato a essere modificato, ed è noto esattamente quanto spesso
 * si accederà a ciascun elemento, è possibile costruire un albero di ricerca binario ottimale ,
 * che è un albero di ricerca in cui il costo medio di ricerca un articolo (il costo di ricerca previsto )
 * è ridotto al minimo.
 * Anche se disponiamo solo di stime dei costi di ricerca, un tale sistema può velocizzare notevolmente le ricerche in media.
 * */
@Component
public class ReverseGeoCoderExecute {
    static ReverseGeoCode reverseGeoCode;
    @Autowired
    private  PlaceRepository placeRepository;

    public String revgeocode(Double latitude, Double longitude){
        if(reverseGeoCode ==null){
            List<Place> places = placeRepository.findAll();
            reverseGeoCode = new ReverseGeoCode(places);
        }
        return reverseGeoCode.nearestPlace(latitude, longitude).name;
    }
}
