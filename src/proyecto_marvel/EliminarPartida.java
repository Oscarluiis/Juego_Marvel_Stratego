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
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Paz
 */
public class EliminarPartida extends javax.swing.JFrame {
    

    /**
     * Creates new form EliminarPartida
     */
    public EliminarPartida() {
        initComponents();
       
        mostrarPartidas();
        
         ImageIcon imagen= new ImageIcon("src\\Imagenes\\Para Frames\\Hulk.jpg");
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

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistEliminar = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText("Eliminar Partida");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(270, 300, 330, 30);

        jlistEliminar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jlistEliminar);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(270, 120, 330, 160);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SELECCIONA LA PARTIDA QUE QUIERES ELIMINAR");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(210, 90, 480, 22);

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 840, 490);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try{
        int index=jlistEliminar.getSelectedIndex();
        String dir=jlistEliminar.getModel().getElementAt(index);
        String path=getpathPartidaEliminar(dir);
        System.out.println(path);
        int op;
        op=JOptionPane.showConfirmDialog(null, "Estas seguro que deseas elminar la Partida?","Partida",JOptionPane.OK_CANCEL_OPTION);
        if(op==0){
            Partidas.eliminarPartida(path); 
        }else{
            dispose();
        }
         
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Porfavor seleccione un elemento a eliminar");
        }
        
        mostrarPartidas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(EliminarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EliminarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EliminarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EliminarPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EliminarPartida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jlistEliminar;
    // End of variables declaration//GEN-END:variables

        public void mostrarPartidas(){
            DefaultListModel<String>model2=new DefaultListModel<>();
            jlistEliminar.setModel(model2);
            File file=new File("Players/"+Player.getLoggedPlayer().getUsername());
            for(File child : file.listFiles()){
                if(!child.isHidden()){
                //Ultima modif
                    Date ultima = new Date(child.lastModified());
                    String dir=child.getName();
                    System.out.println(ultima+"   "+child.getName());
                    model2.addElement(dir);

                }
            }
        }
        
    public String getpathPartidaEliminar(String url){
       // String dir ="";
       // for(int cont=31;cont<url.length();cont++){
       //     dir=dir+url.charAt(cont);
      //  }
        return "Players/"+Player.getLoggedPlayer().getUsername()+"/"+url;
    }
      
    }