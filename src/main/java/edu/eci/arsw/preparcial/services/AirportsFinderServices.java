package edu.eci.arsw.preparcial.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.eci.arsw.preparcial.persistencia.AirportsFinderCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class AirportsFinderServices{
    @Autowired
    HttpConnectionService httpConnectionService = null;

    @Autowired
    AirportsFinderCache airportsFinderCache = null;

    public String getAirportsByName(String name) throws AirportsFinderException {
        String data = null;
        try {
            if (airportsFinderCache.estaEnCache(name)) {
                data = airportsFinderCache.devolverCache(name).getData();
            } else {
                data = httpConnectionService.getAirportsByName(name);
                airportsFinderCache.cargarCache(name, data);
            }
        } catch(UnirestException e){
            throw new AirportsFinderException(AirportsFinderException.noSeEncontroElAeropuerto);
        }
        return data;
    }


}
