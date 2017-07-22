/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

import java.io.Serializable;

/**
 *
 * @author Miguel Paz
 */
public class FichasHeroes extends Ficha{
    
    public FichasHeroes arrayheroes[][]= new FichasHeroes[4][10];
    String nombre;
    
    public FichasHeroes(int rango,String nom) {
        super(TipoFicha.HEROE, rango,nom);
    }
    
}