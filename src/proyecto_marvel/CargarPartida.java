/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Paz
 */
public class CargarPartida extends javax.swing.JFrame{
    static int CARGARPARTIDAS=0;
    public static String path="";
    /**
     * Creates new form CargarPartida
     */
    public CargarPartida() {
        initComponents();
        mostrarPartidas();
           ImageIcon imagen= new ImageIcon("src\\Imagenes\\Para Frames\\Daredevil.jpg");
        //OBTENER TAMAÑO DEL FRAME
        Toolkit tk= Toolkit.getDefaultToolkit();
        Dimension tamaño= tk.getScreenSize();
        //Se le asigna el tamaño del frame al label
        jLabel1.setSize(tamaño);
     
        ImageIcon icono= new ImageIcon(imagen.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(),Image.SCALE_DEFAULT));
        jLabel1.setIcon(icono);
       
     
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocation(0,0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistCargarPartidas = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ELIJA UNA PARTIDA");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(620, 70, 420, 22);

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText("¡INICIAR LA PARTIDA SELECCIONADA!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(620, 310, 420, 25);

        jScrollPane1.setViewportView(jlistCargarPartidas);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(620, 100, 423, 190);

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1080, 470);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            CARGARPARTIDAS=1;
            int index=jlistCargarPartidas.getSelectedIndex();
            String dir=jlistCargarPartidas.getModel().getElementAt(index);
            path=getpathPartida(dir);
            System.out.println(getpathPartida(dir));
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Por favor se leccione una partida");       
        }
        new GameStratego().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public String getpathPartida(String url){
        String dir ="";
        Calendar f=Calendar.getInstance();
        for(int cont=31;cont<url.length();cont++){
            dir=dir+url.charAt(cont);
        }
        return "Players/"+Player.getLoggedPlayer().getUsername()+"/"+dir;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CargarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargarPartida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jlistCargarPartidas;
    // End of variables declaration//GEN-END:variables

    public void mostrarPartidas(){
        DefaultListModel<String>model1=new DefaultListModel<>();
        jlistCargarPartidas.setModel(model1);
        File file=new File("Players/"+Player.getLoggedPlayer().getUsername());
        try{
            for(File child : file.listFiles()){
                if(!child.isHidden()){
                //Ultima modif
                    Date ultima = new Date(child.lastModified());
                    String dir=ultima+"   "+child.getName();
                    System.out.print(ultima+"   "+child.getName());
                    model1.addElement(dir);
                }
              
            }
        }catch(NullPointerException r){
            JOptionPane.showMessageDialog(null,"No hay partida disponibles para cargar");
        }
        }
      
    

   
}
  
