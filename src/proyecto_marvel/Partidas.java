/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class Partidas implements Serializable{
    static ArrayList<Partidas>cargarPartidas;
    private static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy hh-mm-ss aaa");
    static Partidas GamePartidas[]=new Partidas[1];

    
    public int turn;
    private Calendar fecha;
    static GameStratego nuevaPartida;
    protected static int cont=1;
    Ficha [][]piezas;
    String dir;
    String  playerOne,PlayerTwo;

    public Partidas(String path,int turno, String PlayerTwo,Ficha[][] fichas) {
        this.dir=path;
        this.turn = turno;
        this.playerOne = Player.getLoggedPlayer().getUsername();
        this.PlayerTwo = PlayerTwo;
        this.piezas=fichas;
        this.fecha=Calendar.getInstance();
    }

    public Ficha[][] getPiezas() {
        return piezas;
    }

    public void setPiezas(Ficha[][] piezas) {
        this.piezas = piezas;
    }    
    public GameStratego nuevaPartida(){
        return nuevaPartida = new GameStratego();
    }
    private static String folderPlayer() {
        return "Players/"+Player.getLoggedPlayer().getUsername();
    }
    
    public static void crearFolderPlayer(String name)throws IOException{
        //Creando Folder del Player Logged
        File player = new File(folderPlayer());
        player.mkdirs();
    }
    
    public static void savePartidaForPlayer(int turn,String invitado,Ficha[][] c)throws IOException{
        String name=Player.getLoggedPlayer().getUsername();
        String fech= date.format(new Date());
        Partidas.crearFolderPlayer(name);
        String dirPadre = folderPlayer();
            //Obtengo la fecha justo en el momento que se crea el archivo     
        String path = dirPadre+"/"+cont+"_vs_"+invitado+" "+fech+".ps";
        partidaSalvada(path,turn,invitado,c);
        cont++;
    }
        
    
    private static void partidaSalvada(String path,int turno,String s,Ficha[][]c){
        try {
            FileOutputStream fo = new FileOutputStream(path);
            GamePartidas[0]=new Partidas(path,turno-1, s,c);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(GamePartidas);
            fo.close();
            oo.close();
        } catch (IOException ex) {
            
            System.out.println("Error: "+ex.getMessage());
            Logger.getLogger(Partidas.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            
        }
        JOptionPane.showMessageDialog(null,"La partida se guardo existosamente");
    }
    public static Partidas [] cargarPartida(String path)throws IOException{
        try{
            FileInputStream fi = new FileInputStream(path);
            ObjectInputStream oi = new ObjectInputStream(fi);
            GamePartidas= (Partidas[])oi.readObject();
            fi.close();
            oi.close();
        }catch (ClassNotFoundException ex) {
            return null;
        }
        return GamePartidas;
    }
    
    public static void eliminarPartida(String path){
        try{
            
            FileInputStream fi = new FileInputStream(path);
            ObjectInputStream oi = new ObjectInputStream(fi);
            GamePartidas= (Partidas[])oi.readObject();
            Player.existe(GamePartidas[0].PlayerTwo).addPuntos();
            fi.close();
            oi.close();
            File file=new File(path);
            if (!file.exists()) System.err.println("ARCHIVO NO EXISTE");
            if(file.delete()){
                JOptionPane.showMessageDialog(null,"La partida se elimino correctamente");    
            }else{
            JOptionPane.showMessageDialog(null,"Algo salio mal, partida no se elimino correctamente");
            }
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        
       
    }
      public static void eliminarPartidaActual(String path){
          File file=new File(path);
            if (!file.exists()) System.err.println("ARCHIVO NO EXISTE");
            if(file.delete()){
                JOptionPane.showMessageDialog(null,"La partida se elimino correctamente");    
            }else{
            JOptionPane.showMessageDialog(null,"Algo salio mal, partida no se elimino correctamente");
            }
      }             
  
        
    
        
       
    

    public int getCont() {
        return cont;
    }

    public final void aumenCont() {
        this.cont ++;
    }

    
}
