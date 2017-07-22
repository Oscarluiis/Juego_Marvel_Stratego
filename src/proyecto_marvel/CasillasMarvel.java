/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

import java.io.Serializable;
import javax.swing.JButton;

/**
 *
 * @author ANDY ESCOBAR 007
 */
public class CasillasMarvel extends JButton implements Serializable{
    
    boolean turnplayerHeroe;
    public int x,y;
    Ficha ficha;
   
    public CasillasMarvel(int x, int y, Ficha ficha) {
        this.x = x;
        this.y = y;
        this.ficha = ficha;
        this.setOpaque(true);
        this.setBorderPainted(false);
       // this.addActionListener(this);
  
    }
 
}
    

   

