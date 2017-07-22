/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ANDY ESCOBAR 007
 */
public final class GameStratego extends javax.swing.JFrame implements ActionListener{
    
    /**
     * Variables Globales
     */    
    public static Partidas partidacargada[];
    public Ficha[][] fichas=new Ficha[10][10]; 
    String PLAYER_HEROE,PLAYER_VILLANO;
    static CasillasMarvel celda[][]=new CasillasMarvel[10][10];
    Ficha heroes[][]=new FichasHeroes[4][10];
    Ficha villanos[][]=new FichasVillanos[4][10];
    CasillasMarvel primerCasilla=null,segundaCasilla=null;
    boolean turnoPlayerHeroes=true, primerclic=false;
    TipoFicha fichaContraria= turnoPlayerHeroes?TipoFicha.VILLANO:TipoFicha.HEROE;
     TipoFicha miTipoFicha = turnoPlayerHeroes? TipoFicha.HEROE:TipoFicha.VILLANO;
    int turno=0,MODO_JUEGO=Configuracion.modojuego; 
    String turnoplayer=(turno==1?"HEROES":"VILANOS");
    
    String nomHeroes[]={"Nova Blast","Nova Blast","Nova Blast","Nightcrowler","Elektra","Dr. Strange","Phoenix","Storm","Ice Man","SpiderGirl",
        "Gambit","Colossus","Beast","Giant Man","She Hulk","Emma Frost",
        "Thing","Blade","Punisher","Ghost Rider",
        "Invisible Woman","Cyclops","Human Torch","Thor",
        "Iron Man","Hulk","Silver Surfers","Daredevil",
        "Namor","Wolverine","SpiderMan",
        "Nick Fury","Profesor X",
        "Capitán América","Mr. Fantastic","Black Widow"};
    
    
    String nombVillanos[]={"Pumpkin Bomb","Pumpkin Bomb","Pumpkin Bomb","Ultron","Leader","Mr. Sinister","Electro","Sandman",
        "Sentinel 1","Sentinel 2","Viper","Carnage","Juggernaut","Lizard","Mole man","Rhino","Abomination",
        "Black Cat","Sabretooth","Thanos","Dr. Octopus","Deadpool","Mysterio","Mystique","Bullseye","Omega Red","Onslaught","Red Skull",
        "Venom","Apocalypse","Green Goblin","Kingpin","Magneto","Galactus","Dr. Doom","Black Widow mala"};




    /**
     * vARIABLE QUE ME CUENTA LOS VILLANOS QUE SE HAN COMID
     */
    final int MAX_VILLANOS=40,MAX_HEROES=40;
    
    /**
     * VARIABLE QUE CUENTA LA CANTIDA DE HEROE QUE ME HA COMIDO
     */
    int cHeroesR1=0,cHeroesR2=0,cHeroesR3=0,cHeroesR4=0,
       cHeroesR5=0,cHeroesR6=0,cHeroesR7=0,cHeroesR8=0,cHeroesR9=0,cHeroesR10=0;
    
    String turn=turnoPlayerHeroes?"HEROES":"VILLANOS";
       
    public GameStratego() { 
        initComponents();
        cerrar();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
       
       // panelTablero.setSize(500, 700);
        panelTablero.setLocation(this.getHeight()/2,this.getWidth()/2+200);
        formularioInicial();
        modoJuego();
        obtenerHeroes();
        obtenerVillanos();
        tablero(); //IMPLEMENTA EL TABLERO EN PANTALLA\
        generarAchivosdeTexto();
        pintarZonaSegura();
        cambiarTurno();
    }
    
  /**
   * FUNCION QUE IMPLEMENTA LAS CASILLAS DE TABLERO
   */  
    public void tablero(){
        if(CargarPartida.CARGARPARTIDAS==1){
            try {
               partidacargada=Partidas.cargarPartida(CargarPartida.path);
               fichas=partidacargada[0].getPiezas();
               turno=partidacargada[0].turn;
               if(partidacargada[0].turn==0){
                   PLAYER_HEROE=partidacargada[0].playerOne;
                   lblPlayerOne.setText(PLAYER_HEROE.toUpperCase());
                   PLAYER_VILLANO=partidacargada[0].PlayerTwo;
                   lblPlayerTwo.setText(PLAYER_VILLANO.toUpperCase());
               }else if(partidacargada[0].turn==1){
                   PLAYER_HEROE=partidacargada[0].PlayerTwo;
                   lblPlayerOne.setText(PLAYER_HEROE.toUpperCase());
                   PLAYER_VILLANO=partidacargada[0].playerOne;
                   lblPlayerTwo.setText(PLAYER_VILLANO.toUpperCase());
               }
            } catch (IOException ex) {
                Logger.getLogger(GameStratego.class.getName()).log(Level.SEVERE, null, ex);
            }
            panelTablero.setLayout(new GridLayout(10,10));
            for (int i=0;i<celda.length;i++ ){
                for (int e=0;e<celda[i].length;e++){
                    if (fichas[i][e] instanceof FichasHeroes || fichas[i][e] instanceof FichasVillanos){
                        celda[i][e]=new CasillasMarvel(i,e,fichas[i][e]);
                        celda[i][e].setIcon(obtenerImagen(fichas[i][e]));
                    }else{
                        celda[i][e]=new CasillasMarvel(i, e,null);
                    }
                    celda[i][e].setName(i+""+e);
                    celda[i][e].addActionListener(this);
                    panelTablero.add(celda[i][e]);
                }
            }   
        }else{
        panelTablero.setLayout(new GridLayout(10,10));
        for (int i=0;i<celda.length;i++ ){
            for (int e=0;e<celda[i].length;e++){
                if(i<heroes.length &&e<heroes[i].length){ 
                    celda[i][e]=new CasillasMarvel(i, e, heroes[i][e]);
                    celda[i][e].setText("HR"+i+" "+e);
                    celda[i][e].setIcon(obtenerImagen(heroes[i][e]));
                }else if(i>5){
                    celda[i][e]=new CasillasMarvel(i, e, villanos[i-6][e]);
                    celda[i][e].setText("VILLAINS"+i+e);
                    celda[i][e].setIcon(obtenerImagen(villanos[i-6][e]));
                }else{ 
                    celda[i][e]=new CasillasMarvel(i, e, null);
                }
                celda[i][e].setName(i+""+e);
                celda[i][e].addActionListener(this);
                panelTablero.add(celda[i][e]);
            }
        }
    }
    }
    
    public void cerrar(){
        try{
           this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
              public void windowClosing(WindowEvent e)  {
                 confirmarsalida(); 
            }});
            this.setVisible(true);
        }catch(Exception e){
            System.out.println("No debería pasar esto");
        }
    }
    
    public void confirmarsalida(){
        int valor=JOptionPane.showConfirmDialog(this, "¿Está seguro de cerrar el juego?", "Se perderán los cambios no guardados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (valor==JOptionPane.YES_OPTION){
            eliminarReportes(PLAYER_HEROE, PLAYER_VILLANO);
            this.dispose();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();
        lblPlayerOne = new javax.swing.JLabel();
        lblPlayerTwo = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblFichasPerdidasH = new javax.swing.JLabel();
        lblFichasPerdidaV = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setSize(new java.awt.Dimension(1080, 680));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelTablero.setName("panelTablero"); // NOI18N
        panelTablero.setPreferredSize(new java.awt.Dimension(530, 720));

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        lblPlayerOne.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPlayerOne.setText("Player 1");

        lblPlayerTwo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPlayerTwo.setText("Player 2");

        lblTurno.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        lblTurno.setForeground(java.awt.Color.red);
        lblTurno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTurno.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblTurnoPropertyChange(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel1.setText("Héroes:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel2.setText("Villanos:");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/redirse.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modoAleatorio.png"))); // NOI18N
        jLabel3.setText("MODO DE JUEGO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Fichas perdidas");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Fichas Perdidas");

        lblFichasPerdidasH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFichasPerdidasH.setForeground(new java.awt.Color(255, 51, 51));
        lblFichasPerdidasH.setText("   -----");

        lblFichasPerdidaV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFichasPerdidaV.setForeground(new java.awt.Color(255, 51, 51));
        lblFichasPerdidaV.setText("------");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lblFichasPerdidasH))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlayerOne))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPlayerTwo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblFichasPerdidaV)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(94, 94, 94)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jButton1)
                .addGap(398, 398, 398))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(lblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPlayerOne)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblFichasPerdidasH))
                        .addGap(138, 138, 138)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPlayerTwo)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFichasPerdidaV)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblTurnoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblTurnoPropertyChange

    }//GEN-LAST:event_lblTurnoPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int op;
        op=JOptionPane.showConfirmDialog(null, "¿Desea usted redirse?","Rendirse",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if (op==0){

            obtenerGanadorRendirse();
            this.dispose();
        }
        
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{  
            if(Player.getLoggedPlayer().getUsername().equals(PLAYER_HEROE)&&turno==1){
                Partidas.savePartidaForPlayer(turno, PLAYER_VILLANO, obtenerFichas());  
                this.dispose();
                
            }else if(Player.getLoggedPlayer().getUsername().equals(PLAYER_VILLANO)&&turno==2){
                Partidas.savePartidaForPlayer(turno,PLAYER_HEROE,obtenerFichas());
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Esta funcion no esta permitida para el usuario invitado");
            }
            
            
        }catch(IOException e){
            System.out.println("No se");
        }
        
        eliminarReportes(PLAYER_HEROE, PLAYER_VILLANO);
        
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameStratego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new RunnableImpl());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblFichasPerdidaV;
    private javax.swing.JLabel lblFichasPerdidasH;
    private javax.swing.JLabel lblPlayerOne;
    private javax.swing.JLabel lblPlayerTwo;
    public javax.swing.JLabel lblTurno;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(primerclic){
            if(e.getSource() instanceof CasillasMarvel){
                for (CasillasMarvel[] celda1 : celda) {
                    for (CasillasMarvel objeto : celda1) {
                        if (e.getSource().equals(objeto)) {
                            segundaCasilla=objeto;
                            validarSegundoClic(primerCasilla,segundaCasilla);
                            try {
                                fichasDisponibles();
                            } catch (ClassNotFoundException | IOException ex) {
                                Logger.getLogger(GameStratego.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }  
        }else{
            if(e.getSource() instanceof CasillasMarvel){
                for (CasillasMarvel[] celda1 : celda) {
                    for (CasillasMarvel objeto : celda1) {
                        if (e.getSource().equals(objeto)){
                            primerCasilla= objeto;
                            System.out.println(objeto.getWidth()+"  "+objeto.getHeight());
                            try{
                                validarPrimerClic(primerCasilla);   
                            }catch(RuntimeException ae){
                                JOptionPane.showMessageDialog(null,"No has selecionado ninguna ficha");                                
                                }                                  
                            }
                        }
                    }
                }
            }
    }

    private void validarMovimiento(CasillasMarvel primerCasilla, CasillasMarvel segundaCasilla) {
        if(segundaCasilla.ficha == null|| segundaCasilla.ficha.ficha!=miTipoFicha){
            moverPieza(primerCasilla,segundaCasilla);
        }else{
            JOptionPane.showMessageDialog(null, "Existe una ficha tuya en esa posicion");
        }        
    }

    private void moverPieza(CasillasMarvel primerCasilla, CasillasMarvel segundaCasilla) {
        int scx=segundaCasilla.x,scy=segundaCasilla.y; //COORDENADAS DE LA SEGUNDO BOTON O CASILA
        if(scx==4 ||scx==5){
            if(scy==2||scy==3||scy==6||scy==7){
               JOptionPane.showMessageDialog(null,"Movimiento no válido", "Zona Prohibida", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if(primerCasilla.ficha.rango==2){
            if(primerCasilla.x==segundaCasilla.x){
                int pos=segundaCasilla.y;
                int cmax=columMax(primerCasilla);
                int cmin=columMin(primerCasilla);
                if (pos<=cmax&& pos>=cmin){
                    if(segundaCasilla.ficha!=null){
                        primerCasilla.setIcon(obtenerImagen(primerCasilla.ficha));
                        iniciarBatalla(primerCasilla,segundaCasilla);
                    }else{
                        segundaCasilla.ficha=primerCasilla.ficha;
                        segundaCasilla.setText(primerCasilla.getText());
                        primerCasilla.setText(null);
                        //Quita la imagen de la casilla anterior
                        segundaCasilla.setIcon(primerCasilla.getIcon());
                        primerCasilla.setIcon(null);
                        primerCasilla.ficha=null;         
                        cambiarTurno();
                    }
                }else{
                        JOptionPane.showMessageDialog(null,"Tu pieza de rango "+ primerCasilla.ficha.rango+" no puede llegar hasta esa posicion");
                    }
            }else if(primerCasilla.y==segundaCasilla.y){
                int fMax=filaMax(primerCasilla);
                int fMin=filaMin(primerCasilla);
                if(segundaCasilla.x<=fMax && segundaCasilla.x>=fMin){
                    if(segundaCasilla.ficha!=null){
                        primerCasilla.setIcon(obtenerImagen(primerCasilla.ficha));
                        iniciarBatalla(primerCasilla, segundaCasilla);
                    }else{
                    segundaCasilla.ficha=primerCasilla.ficha;
                    segundaCasilla.setText(primerCasilla.getText());
                    primerCasilla.setText(null);
                    //Quita la imagen de la casilla anterior
                    segundaCasilla.setIcon(primerCasilla.getIcon());
                    primerCasilla.setIcon(null);
                    primerCasilla.ficha=null;         
                    cambiarTurno();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Tu pieza de rango "+ primerCasilla.ficha.rango+" no puede llegar hasta esa posicion");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Moviento no valido");
            }
        }else if (primerCasilla.x==segundaCasilla.x){
            int pos=primerCasilla.y;
            int s=segundaCasilla.y;
            if ((s+1)>=pos &&(s-1)<=pos){
                if(segundaCasilla.ficha!=null){
                    primerCasilla.setIcon(obtenerImagen(primerCasilla.ficha));
                    iniciarBatalla(primerCasilla, segundaCasilla);
                }else{
                segundaCasilla.ficha=primerCasilla.ficha;
                segundaCasilla.setText(primerCasilla.getText());
                primerCasilla.setText(null);
                //Quita la imagen de la casilla anterior
                segundaCasilla.setIcon(primerCasilla.getIcon());
                primerCasilla.setIcon(null);
                primerCasilla.ficha=null;         
                cambiarTurno();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Movimiento no válido, esta Ficha no se puede mover más 2 posiciones");
            }
        }
        else if(primerCasilla.y==segundaCasilla.y){
            int h=primerCasilla.x;
            int s=segundaCasilla.x;
            if ((s+1)>=h &&(s-1)<=h){
                if(segundaCasilla.ficha!=null){
                    primerCasilla.setIcon(obtenerImagen(primerCasilla.ficha));
                    iniciarBatalla(primerCasilla, segundaCasilla);
                }else{
                segundaCasilla.ficha=primerCasilla.ficha;
                segundaCasilla.setText(primerCasilla.getText());
              
                segundaCasilla.setIcon(primerCasilla.getIcon());
                primerCasilla.setIcon(null);
                primerCasilla.setText(null);
                primerCasilla.ficha=null;
                cambiarTurno();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Movimiento no válido, esta Ficha no se puede mover más 2 posiciones");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Movimiento no válido, por favor intente de nuevo");
        }   
    }
        
    private void pintarZonaSegura(){
        for(int y=4;y<=5;y++){
            for (int x=2;x<=3;x++){
                celda[y][x].setBackground(Color.YELLOW);     
            }
            for(int z=6;z<=7;z++){
                celda[y][z].setBackground(Color.magenta);
            }
        }
    }
/**
 * 
 * @param primerCasilla  Obtiene el primer clic y valida que este cumpla con las condiciones requeridas
 */
    private void validarPrimerClic(CasillasMarvel primerCasilla) {
        if(primerCasilla.ficha.ficha==fichaContraria){
            JOptionPane.showMessageDialog(null, "Selecione una ficha tuya por favor");        
        }else if(primerCasilla.ficha.rango<=0){
            JOptionPane.showMessageDialog(null, "Esta ficha no posee movimiento");
        }
        else if(primerCasilla.ficha!=null && primerCasilla.ficha.ficha==miTipoFicha){
            primerclic=true;
            System.out.println("Primer Clic");
            System.out.println("fila:"+primerCasilla.x+" columna"+primerCasilla.y+" "+infoTipoFicha(primerCasilla));
                                    
        }
        else{
            System.out.println(primerCasilla.x+""+primerCasilla.y+" "+infoTipoFicha(primerCasilla));
            JOptionPane.showMessageDialog(null, "Selecione una ficha tuya por favor");
        }
    }

    private void validarSegundoClic(CasillasMarvel primerCasilla, CasillasMarvel segundaCasilla) {
        CasillasMarvel objeto=segundaCasilla;
        if(primerCasilla.equals(objeto)){
            JOptionPane.showMessageDialog(null,"Usted a hecho clic en la misma cordenada");
        }
        else{
            System.out.println("Segundo Clic\n"+" fila "+objeto.x+" columna"+objeto.y+" "+infoTipoFicha(objeto)); //infotipoFicha retorna String segun el tipo del Objeto
            validarMovimiento(primerCasilla,segundaCasilla);
            primerclic=false;     
        }
    }

    private void obtenerVillanos() {
        int f=3,pos=0;
        int c=posicionAleatoria(1,8);
        villanos[f][c]=new FichasVillanos(-1,"Planet Earth");
        villanos[f][c+1]=new FichasVillanos(0,"Pumpkin Bomb");
        villanos[f][c-1]=new FichasVillanos(0,"Pumpkin Bomb");
        villanos[f-1][c]=new FichasVillanos(0,"Pumpkin Bomb");
        while(isEmpty(villanos)){
            int fila,columna;
            while(pos<=2){
                fila=posicionAleatoria(2,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(2,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(0,nombVillanos[pos]);
                pos+=1;
                
            }
            while(pos<=10){
                fila=posicionAleatoria(0,1);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,1);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(2,nombVillanos[pos]);
                pos+=1;   
            }
            while(pos<=15){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(3,nombVillanos[pos]);
                pos+=1;   
            }
            while(pos<=19){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(4,nombVillanos[pos]);
                pos+=1;   
            }
            while(pos<=23){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(5,nombVillanos[pos]);
                pos+=1;   
            }
             while(pos<=27){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(6,nombVillanos[pos]);
                pos+=1;   
            } while(pos<=30){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(7,nombVillanos[pos]);
                pos+=1;   
            }
            while(pos<=32){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(8,nombVillanos[pos]);
                pos+=1;   
            }
             
            while(pos<=33){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(9,nombVillanos[pos]);
                pos+=1;   
            }
             while(pos<=34){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(villanos[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                villanos[fila][columna]=new FichasVillanos(10,nombVillanos[pos]);
                pos+=1;   
            }
            for (Ficha[] villano : villanos) {
                for (int cont2 = 0; cont2 < villano.length; cont2++) {
                    if (villano[cont2] == null) {
                        villano[cont2] = new FichasVillanos(1,nombVillanos[nombVillanos.length-1]);
                    }
                }
            }
        }
    }

    private void cambiarTurno() {
        turno++;
        lblTurno.setText("TURNO "+turnoplayer);
        if (turno>2){
           turno=1;
        }  
        turnoplayer= turno==1?"HEROES":"VILLANOS";
        lblTurno.setText("TURNO "+turnoplayer);
        fichaContraria= turno==1?TipoFicha.VILLANO:TipoFicha.HEROE;
        miTipoFicha = turno==1? TipoFicha.HEROE:TipoFicha.VILLANO;
        contarFichasPerdidas();
        ocultarFichas();
    }

    private void formularioInicial() {
        try{
        if(Opciones.op==true){
            lblPlayerOne.setText(Player.getLoggedPlayer().getUsername().toUpperCase());
            PLAYER_HEROE=Player.getLoggedPlayer().getUsername();
            lblPlayerTwo.setText(User2.playerTwo.toUpperCase());
            PLAYER_VILLANO=User2.playerTwo;
        }
        else{
            PLAYER_HEROE=User2.playerTwo;
            PLAYER_VILLANO=Player.getLoggedPlayer().getUsername();
            lblPlayerOne.setText(User2.playerTwo.toUpperCase());
        
            lblPlayerTwo.setText(Player.getLoggedPlayer().getUsername().toUpperCase());
        }
        }catch (NullPointerException e){
            System.out.println("No se que pasa "+e.getMessage());
        }
       
    }

    private String infoTipoFicha(CasillasMarvel objeto) {
        String tipo=null;
        if(objeto.ficha instanceof FichasHeroes){
            tipo="HEROE"+" Rango: "+objeto.ficha.rango+" "+objeto.ficha.nombreficha;
        }else if(objeto.ficha instanceof FichasVillanos){
            tipo="VILLANOS"+" Rango: "+objeto.ficha.rango+" "+objeto.ficha.nombreficha;
        }return tipo;
    }

    private int posicionAleatoria(int min,int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private ImageIcon obtenerImagen(Ficha img) {
        String dir=(String)(img.nombreficha);
        if(img.ficha==TipoFicha.HEROE){
            ImageIcon icoimg= new ImageIcon("src/Imagenes/Heroes/"+dir+".png");
            Image image_resize = icoimg.getImage().getScaledInstance(53, 60, Image.SCALE_SMOOTH);
            return new ImageIcon(image_resize);
        }else{
            ImageIcon icoimg= new ImageIcon("src/Imagenes/Villanos/"+dir+".png");
            Image image_resize = icoimg.getImage().getScaledInstance(53, 60, Image.SCALE_SMOOTH);
            return new ImageIcon(image_resize);       
        }
    }
    
    private boolean isEmpty(Ficha[][] heroes) {
        for(Ficha i[]:heroes){
            for(Ficha e:i){
                if(e==null){
                    return true;
                }                
            }
        } 
        return false;
    }
    private void modoJuego() {
        switch (Configuracion.modojuego) {
            case 0:
                jLabel3.setText("MODO TUTORIAL");
                break;
            case 1:
                jLabel3.setText("MODO CLASICO");
                break;
            default:
                jLabel3.setText("MODO TUTORIAL");
                break;
        }
    }

    private void ocultarFichas() {
        if(MODO_JUEGO==0){
            if (turno==1){
                ImageIcon imgOcultaVillanos=new ImageIcon("src/Imagenes/cardsVillain.png");
                Icon icoVillano=new ImageIcon(imgOcultaVillanos.getImage().getScaledInstance(53,60,Image.SCALE_SMOOTH));
                for(CasillasMarvel[] tipo:celda){
                    for(CasillasMarvel villano:tipo){
                        if(villano.ficha instanceof FichasVillanos){
                            villano.setIcon(icoVillano);
                        }else if(villano.ficha instanceof FichasHeroes){
                            villano.setIcon(obtenerImagen(villano.ficha));
                       }
                    }
                }
           }else{
                ImageIcon imgOcultaHeroe=new ImageIcon("src/Imagenes/cardsHeroes.png");
                 Icon icoHeroe= new ImageIcon(imgOcultaHeroe.getImage().getScaledInstance(53,60,Image.SCALE_SMOOTH));
                 for(CasillasMarvel[] tipo:celda){
                    for(CasillasMarvel heroe:tipo){
                        if(heroe.ficha instanceof FichasHeroes){
                            heroe.setIcon(icoHeroe);
                        }else if(heroe.ficha instanceof FichasVillanos){
                            heroe.setIcon(obtenerImagen(heroe.ficha));
                       }
                    }
                }
            }
        }else{
            ImageIcon imgOcultaVillanos=new ImageIcon("src/Imagenes/cardsVillain.png");
            ImageIcon imgOcultaHeroe=new ImageIcon("src/Imagenes/cardsHeroes.png");
            Icon icoHeroe= new ImageIcon(imgOcultaHeroe.getImage().getScaledInstance(53,60,Image.SCALE_SMOOTH));
            Icon icoVillano=new ImageIcon(imgOcultaVillanos.getImage().getScaledInstance(53,60,Image.SCALE_SMOOTH));
            for(CasillasMarvel[] tipo:celda){
                for(CasillasMarvel fic:tipo){
                    if(fic.ficha instanceof FichasVillanos){
                        fic.setIcon(icoVillano);
                    }else if(fic.ficha instanceof FichasHeroes){
                        fic.setIcon(icoHeroe);
                    }                    
                }
            }
        }
            
    }

    private int filaMax(CasillasMarvel posicion) {
       for(int fmax=posicion.x+1;fmax<=9;fmax++){
           if(celda[fmax][posicion.y].ficha!=null){
               return fmax;
           }
        }
        return 9;
    }

    private int columMax(CasillasMarvel posicion) {
        for(int cmax=posicion.y+1;cmax<=9;cmax++){
           if(celda[posicion.x][cmax].ficha!=null){
                return cmax;
           }
        }
        return 9;
    }

    private int filaMin(CasillasMarvel posicion) {
        for(int fmin=posicion.x-1;fmin>=0;fmin--){
           if(celda[fmin][posicion.y].ficha!=null){
               return fmin;
           }
        }
        return 0;
    }

    private int columMin(CasillasMarvel posi) {
        for(int cMin=posi.y-1;cMin>0;cMin--){
            if(celda[posi.x][cMin].ficha!=null){
                return cMin;
            }
        }return 0;
        
    }

    private void contarFichasPerdidas() {
        int contH=0,contV=0;
        for(CasillasMarvel fichas[]:celda){
            for(CasillasMarvel ficha:fichas){
                if (ficha.ficha instanceof FichasHeroes){
                    contH+=1;
                }else if(ficha.ficha instanceof FichasVillanos){
                    contV+=1;
                }
            }
        }
        lblFichasPerdidasH.setText(""+(MAX_HEROES-contH));
        lblFichasPerdidaV.setText(""+(MAX_VILLANOS-contV));
    }

    private void fichasDisponibles() throws ClassNotFoundException, IOException {
        int Tierra=0,pconM=0;
        if (turno==1){
            for (CasillasMarvel cas[]:celda){
                for (CasillasMarvel fic:cas){
                    if(fic.ficha instanceof FichasHeroes){
                        if(fic.ficha.rango==-1){
                            Tierra=+1;
                        }
                        if(fic.ficha.rango>0){
                            pconM+=1;
                        }
                    }
                }
            }
        }else if(turno==2){
            for (CasillasMarvel cas[]:celda){
                for (CasillasMarvel fic:cas){
                    if(fic.ficha instanceof FichasVillanos){
                        if(fic.ficha.rango==-1){
                            Tierra=+1;
                        }
                        if(fic.ficha.rango>0){
                            pconM+=1;
                        }
                    }
                }
            }
        }       
        if (Tierra==0){
            obtenerGanadorTierra();
        }else if (pconM==0){
            obtenerGanadorFichasSinMovimientos();
        }
    }

    private void mostrarBatalla(CasillasMarvel primerCasilla, CasillasMarvel segundaCasilla) {
        primerCasilla.setIcon(obtenerImagen(primerCasilla.ficha));
        segundaCasilla.setIcon(obtenerImagen(segundaCasilla.ficha));
    }

    private Ficha[][] obtenerFichas() {
        for(int cont=0;cont<celda.length;cont++){
            for(int cont2=0;cont2<celda[cont].length;cont2++){
                if (celda[cont][cont2].ficha instanceof FichasHeroes ||celda[cont][cont2].ficha instanceof FichasVillanos){
                    fichas[cont][cont2]=celda[cont][cont2].ficha;
                }else{
                    fichas[cont][cont2]=null; 
                }
            }
        }
        return fichas;
    }

    private void generarAchivosdeTexto() {
        File t=new File("Players/"+PLAYER_HEROE);
        t.mkdirs();
        try(FileWriter gH=new FileWriter("Players/"+PLAYER_HEROE+"/posicionespiezas.txt")){
            String data="";//1                                    2                                    3                                    4                                    5"
                    //+ "                                    6                                    7                                    8                                    9                                    10";
            for(int cont=0;cont<celda.length;cont++){
                for (int cont2=0;cont2<celda[cont].length;cont2++){
                    if(celda[cont][cont2].ficha instanceof FichasHeroes){
                        data=data+"||pos:"+(celda[cont][cont2].x+1)+","+(celda[cont][cont2].y+1)+"  -Rango: "+celda[cont][cont2].ficha.rango+"-Nombre: "+celda[cont][cont2].ficha.nombreficha+"||  ";
                    }else{
                        data=data+"||"+"                                    "+"||   ";
                    }
                }
                data=data+"\n";
            }
            gH.write(data);
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
            File f=new File("Players/"+PLAYER_VILLANO);
            f.mkdirs();
        try(FileWriter gV=new FileWriter("Players/"+PLAYER_VILLANO+"/posicionespiezas.txt")){
            String data="";//1                                    2                                    3                                    4                                    5                                    6"
                   // + "                                    7                                    8                                    9                                    10";
            for(int cont=0;cont<celda.length;cont++){
                for (int cont2=0;cont2<celda[cont].length;cont2++){
                   if(celda[cont][cont2].ficha instanceof FichasVillanos){
                        data=data+"||pos:"+(celda[cont][cont2].x+1)+","+(celda[cont][cont2].y+1)+"  -Rango: "+celda[cont][cont2].ficha.rango+"-Nombre: "+celda[cont][cont2].ficha.nombreficha+"||  ";
                    }else{
                        data=data+"||"+"                                    "+"||  ";
                    }
                }
                data=data+"\n";
            }
            gV.write(data);
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        
    }

    private void eliminarReportes(String h, String v) {
       File r =new File("Players/"+h+"/posicionespiezas.txt");
       File rt =new File("Players/"+v+"/posicionespiezas.txt");
       r.delete();
       rt.delete();
    }

   
    private static class RunnableImpl implements Runnable {

        public RunnableImpl() {}
        @Override
        public void run() {
            new GameStratego().setVisible(true);
        }
    }
        
    public void obtenerHeroes(){
        int f=0,pos=0;
        int c=posicionAleatoria(1,8);
        heroes[f][c]=new FichasHeroes(-1,"Planet Earth");
        heroes[f][c+1]=new FichasHeroes(0,"Nova Blast");
        heroes[f][c-1]=new FichasHeroes(0,"Nova Blast");
        heroes[f+1][c]=new FichasHeroes(0,"Nova Blast");
        while(isEmpty(heroes)){
            int fila,columna;
            while(pos<=2){
                fila=posicionAleatoria(0,1);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,1);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(0,nomHeroes[pos]);
                pos+=1;
            }
            while(pos<=10){
                fila=posicionAleatoria(2,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(2,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(2,nomHeroes[pos]);
                pos+=1;   
            }
            while(pos<=15){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(3,nomHeroes[pos]);
                pos+=1;   
            }
            while(pos<=19){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(4,nomHeroes[pos]);
                pos+=1;   
            }
            while(pos<=23){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(5,nomHeroes[pos]);
                pos+=1;   
            }
             while(pos<=27){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(6,nomHeroes[pos]);
                pos+=1;   
            } while(pos<=30){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(7,nomHeroes[pos]);
                pos+=1;   
            }
            while(pos<=32){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(8,nomHeroes[pos]);
                pos+=1;   
            }
             
            while(pos<=33){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(9,nomHeroes[pos]);
                pos+=1;   
            }
             while(pos<=34){
                fila=posicionAleatoria(0,3);
                columna=posicionAleatoria(0,9);
                while(heroes[fila][columna]!=null){
                    fila=posicionAleatoria(0,3);
                    columna=posicionAleatoria(0,9);
                }
                heroes[fila][columna]=new FichasHeroes(10,nomHeroes[pos]);
                pos+=1;   
            }
            for (Ficha[] heroe : heroes) {
                for (int cont2 = 0; cont2 < heroe.length; cont2++) {
                    if (heroe[cont2] == null) {
                        heroe[cont2] = new FichasHeroes(1,nomHeroes[pos]);
                    }
                }
            }
        }
    }
    
    public void obtenerGanadorRendirse(){
        Calendar fecha=Calendar.getInstance();
        if(CargarPartida.CARGARPARTIDAS==1){
            Partidas.eliminarPartidaActual(partidacargada[0].dir);
        }
        if (turno==1){
            TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
            JOptionPane.showMessageDialog(null,lblPlayerTwo.getText().toUpperCase()+" Vencedor usando "+ fichaContraria+
                    "S ha ganado\n ya que "+lblPlayerOne.getText().toUpperCase()+ " usando "+miTipoFicha+"S se ha retirado del juego\n"+fecha.getTime());
                  
            TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
            try{
                Player.existe(PLAYER_VILLANO).addPuntos();
                Player.existe(PLAYER_VILLANO).ultimasPartidas(fichacontra, true, PLAYER_HEROE);
                Player.existe(PLAYER_HEROE).ultimasPartidas(fich, false, PLAYER_VILLANO);
            } catch (IOException ex) {
                Logger.getLogger(GameStratego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            turno=2;
            JOptionPane.showMessageDialog(null,lblPlayerOne.getText().toUpperCase()+"Vencedor usando "+fichaContraria+
                    " ha ganado ya que "+lblPlayerTwo.getText().toUpperCase()+" usando "+miTipoFicha+" se ha retirado del juego\n"+fecha.getTime());   
            TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
            TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
            try{
                Player.existe(PLAYER_HEROE).addPuntos();
                Player.existe(PLAYER_HEROE).ultimasPartidas(fichacontra, true,PLAYER_VILLANO);
                Player.existe(PLAYER_VILLANO).ultimasPartidas(fich, false, PLAYER_HEROE);
            } catch (IOException ex) {
                Logger.getLogger(GameStratego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        eliminarReportes(PLAYER_HEROE,PLAYER_VILLANO);
    }
    
    public void obtenerGanadorFichasSinMovimientos() throws IOException, ClassNotFoundException{
        Calendar fecha=Calendar.getInstance();
        if(CargarPartida.CARGARPARTIDAS==1){
            Partidas.eliminarPartidaActual(partidacargada[0].dir);
        }
            if (turno==1){
                JOptionPane.showMessageDialog(null,lblPlayerOne.getText().toUpperCase()+" Perdedor usando "+miTipoFicha+
                    " ha perdido por no tener movimientos validos disponibles ante "+lblPlayerTwo.getText().toUpperCase()+"\n"+fecha.getTime());
                TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
                
                TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
                Player.existe(PLAYER_VILLANO).addPuntos();
                Player.existe(PLAYER_VILLANO).ultimasPartidas(fichacontra, true, PLAYER_HEROE);
                Player.existe(PLAYER_HEROE).ultimasPartidas(fich, false, PLAYER_VILLANO);
            }
            else{
                turno=2;
                 TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
                TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
     
                JOptionPane.showMessageDialog(null,lblPlayerTwo.getText().toUpperCase()+" Perdedor usando "+fich+
                    " ha perdido por no tener movimientos validos disponibles ante "+lblPlayerOne.getText().toUpperCase()+"\n"+fecha.getTime());
                Player.existe(PLAYER_HEROE).addPuntos();
                Player.existe(PLAYER_HEROE).ultimasPartidas(fichacontra, true,PLAYER_VILLANO);
                Player.existe(PLAYER_VILLANO).ultimasPartidas(fich, false, PLAYER_HEROE);
            }
            eliminarReportes(PLAYER_HEROE,PLAYER_VILLANO);
            
            this.dispose();
         }
        
    
    
    public void obtenerGanadorTierra(){
        Calendar fecha=Calendar.getInstance();
        if(CargarPartida.CARGARPARTIDAS==1){
            Partidas.eliminarPartidaActual(partidacargada[0].dir);
        }
        try{
        String us;
        if (turno==1){
            TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
            JOptionPane.showMessageDialog(null,PLAYER_VILLANO.toUpperCase()+" Vencedor usando "+ fichaContraria+
            "S ha capturado la Tierra! Venciendo a "+lblPlayerOne.getText().toUpperCase()+ "\n"+fecha.getTime());
            us = lblPlayerOne.getText();
            TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
            Player.existe(PLAYER_VILLANO).addPuntos();
            Player.existe(PLAYER_VILLANO).ultimasPartidas(fichaContraria, true,PLAYER_HEROE);
            Player.existe(PLAYER_HEROE).ultimasPartidas(miTipoFicha, false,PLAYER_VILLANO);
            
        }else{
            turno=2;
            
            JOptionPane.showMessageDialog(null,PLAYER_HEROE.toUpperCase()+" Vencedor usando "+fichaContraria+
                    " ha salvado la Tierra! Venciendo a "+lblPlayerTwo.getText().toUpperCase()+"\n"+fecha.getTime());
            us = lblPlayerTwo.getText();
            Player.existe(PLAYER_HEROE).addPuntos();
            TipoFicha fich=(turno==1?TipoFicha.HEROE:TipoFicha.VILLANO);
            Player.existe(PLAYER_HEROE).ultimasPartidas(fichaContraria, true,PLAYER_VILLANO);
            TipoFicha fichacontra=(turno==1?TipoFicha.VILLANO:TipoFicha.HEROE);
            Player.existe(PLAYER_VILLANO).ultimasPartidas(miTipoFicha, false, PLAYER_HEROE);
        }
        }catch(IOException e){
            System.out.println("Error "+e.getMessage());
        }
        eliminarReportes(PLAYER_HEROE,PLAYER_VILLANO);
       // new MenuPrincipal().setVisible(true);
        this.dispose();
    }
    
     private void iniciarBatalla(CasillasMarvel pC, CasillasMarvel sC) {
        mostrarBatalla(primerCasilla,segundaCasilla);
        JOptionPane.showMessageDialog(null,pC.ficha.rango+". "+pC.ficha.nombreficha+" VS. "+sC.ficha.rango+". "+sC.ficha.nombreficha);
        if(pC.ficha.rango==sC.ficha.rango){
            sC.ficha=null;
            sC.setText(null);
            sC.setIcon(null);
            pC.ficha=null;
            pC.setText(null);
            pC.setIcon(null);
           
        }else if(pC.ficha.rango==1){
            if(sC.ficha.rango==10){
                sC.ficha=pC.ficha;
                sC.setText(pC.getText());
                pC.setText(null);
                //Quita la imagen de la casilla anterior
                sC.setIcon(pC.getIcon());
                pC.setIcon(null);
                pC.ficha=null;
            }else{
                pC.setText(null);
                pC.setIcon(null);
                pC.ficha=null;
                
            }
        }else if(sC.ficha.rango==0){
            if(pC.ficha.rango!=3){
                pC.ficha=null;
                pC.setText(null);
                pC.setIcon(null);
            
                sC.ficha=null;
                sC.setText(null);
                sC.setIcon(null);      
            }else{
                sC.ficha=pC.ficha;
                sC.setText(pC.getText());
                pC.setText(null);
                //Quita la imagen de la casilla anterior
                sC.setIcon(pC.getIcon());
                pC.setIcon(null);
                pC.ficha=null;
            }
        }else if(pC.ficha.rango==10&&sC.ficha.rango==1){
            pC.setText(null);
            pC.setIcon(null);
            pC.ficha=null;
        }else if(pC.ficha.rango>sC.ficha.rango){
            sC.ficha=pC.ficha;
                sC.setText(pC.getText());
                pC.setText(null);
                //Quita la imagen de la casilla anterior
                sC.setIcon(pC.getIcon());
                pC.setIcon(null);
                pC.ficha=null;
        }else{
                pC.setText(null);
                //Quita la imagen de la casilla anterior
                pC.setIcon(null);
                pC.ficha=null;
        }
        cambiarTurno();
    }
    
     public CasillasMarvel[][] getCasillas(){
         return celda;
     }
     public Ficha[][] getHereos(){
         return heroes;
     }
     public Ficha[][] getVillanos(){
         return villanos;
     }
     public CasillasMarvel getPrimerCasilla(){
         return primerCasilla;
     }
     public CasillasMarvel getSegundaCasilla(){
         return segundaCasilla;
     }
     public boolean getTurno(){
         return turnoPlayerHeroes;
     }
     
     
     
    
     
}