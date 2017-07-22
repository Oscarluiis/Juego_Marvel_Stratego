/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

/**
 *
 * @author ANDY ESCOBAR 007
 */
public class FichasVillanos extends Ficha {
    public FichasVillanos cardsVillanos[][]= new FichasVillanos[4][10];
    
    public FichasVillanos(int rango,String nombre) {
        super(TipoFicha.VILLANO, rango,nombre);

    }
}
