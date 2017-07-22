/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

import java.io.Serializable;

/**
 *
 * @author David
 */
public abstract class Ficha implements Serializable{
    protected int rango;
    protected TipoFicha ficha;
    String nombreficha;
    
    public Ficha(TipoFicha ficha, int rango,String nom){
        this.ficha= ficha;
        this.rango= rango;
        this.nombreficha=nom;
 
      
        
    }

    public String getNombreficha() {
        return nombreficha;
    }

    public int getRango() {
        return rango;
    }

    public TipoFicha getFicha() {
        return ficha;
    }
    
}
