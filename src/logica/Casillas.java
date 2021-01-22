package logica;

import Paquete.Fin;
import Paquete.Gameover;
import Paquete.Tablero;
import java.awt.Color;
import java.awt.Dimension;
import java.security.Principal;
import javax.swing.JButton;

public class Casillas extends JButton{
    
    //VARIABLES DE LA CLASE
    private int x;
    
    private int y;
    
    private int tipocelda;
    
    private boolean mina;
    
    private Color numeros[];

    //CONSTRUCTOR
    public Casillas(int x, int y) {
        
        this.x = x;
        
        this.y = y;
        
        this.mina = false;
        
        this.numeros = new Color[]{Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.DARK_GRAY, Color.BLACK, Color.GRAY, Color.PINK}; //ARRAY DE LOS NUMEROS, DONDE SE LE ASIGAN UN COLOR A CADA POSICION

        this.setBackground(new java.awt.Color(0, 0, 204)); //ASIGNANDO COLOR A LAS CASILLAS
        
        this.setFont(new java.awt.Font("Tahoma", 1, 12)); //ASIGNANDO TIPO DE LETRA A LAS CASILLAS
        
        this.setMinimumSize(new Dimension(30,10)); //DIMENSION MINIMA DE LAS CASILLAS
        
        this.addActionListener(new java.awt.event.ActionListener(){
            
            public void actionPerformed(java.awt.event.ActionEvent evt){
                
                mostrarCasilla(evt);
            }
        });
    }

    public void setTipocelda(int tipocelda) {
        this.tipocelda = tipocelda;
    }
    
    public int getTipocelda() {
        return tipocelda;
    }

    public boolean getMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
    
    //METODO QUE PERMITE MOSTRAR LAS CASILLAS
    public void mostrar(){
            
        if(!mina && Tablero.ganar == false){
            
            this.mina = true;
            
            this.setBackground(new java.awt.Color(240,240,240)); //SE CAMBIA EL COLOR DE LA CASILLA QUE SE PRESIONO
            
            //IMPRIME LO QUE SE ENCUENTRA EN LA CASILLA: 0 = MINA 2 = VACIO
            switch(this.tipocelda){
                
                case 0:
                    
                    //SI ES 0, SE MUESTRAN TODAS LAS MINAS DEL TABLERO
                    for (int i = 0; i < Tablero.filas; i++) {
                        
                        for (int j = 0; j < Tablero.columnas; j++) {
                            
                            if(Tablero.casillas[i][j].getTipocelda() == 0){
                                Tablero.casillas[i][j].setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mina.png")));
                                
                                Tablero.casillas[i][j].setBackground(new java.awt.Color(240,240,240));
                            }
                        }
                    }
                    
                    //SE CAMBIA LA VARIABLE A TRUE, DEBIDO A QUE EL JUGADOR HA PERDIDO
                    Tablero.ganar = true; 
                    
                    //METODO QUE DESPLIEGA EL EJECUTABLE GAMEOVER
                    Gameover gameover = new Gameover(Tablero.jDesktopPane1);
                    
                    Tablero.jDesktopPane1.add(gameover);
                    
                    gameover.show();
                    
                    gameover.setVisible(true);
                    
                    break;
                    
                case 1:
                    
                    //SI ES 1, SE RECORRE EL ARRAY, COMPROBANDO SI EN ALGUNA CASILLA CERCANA SE ENCUENTRA ALGUNA MINA, PARA PROCEDER A MOSTRAR LA CASILLA DE NUMEROS
                    int cont = 0;
                    
                    this.setBackground(new java.awt.Color(240,240,240)); //ASIGNANDO COLOR A LAS CASILLAS QUE SE SELECCIONAN

                    for (int i = -1; i <= 1; i++) {
                        
                        if(x+i >= 0 && x+i < Tablero.filas){
                            
                            for (int j = -1; j <= 1; j++) {
                                
                                if((y+j >= 0 && (y+j) < Tablero.columnas) && Tablero.casillas[x+i][y+j].getTipocelda() == 0){
                                    
                                    cont++;
                                }
                            }
                        }
                    }
                    
                    this.setText(""+cont); //SE COLOCA EL NUMERO EN LA CASILLA CORRESPONDIENTE
                    this.setForeground(this.numeros[cont]); //DEPENDIENDO DEL NUMERO, SE LE ASIGNA UN COLOR (DEFINIDOS EN EL ARRAY NUMEROS)
                    
                    break;
                    
                default: 
                    
                    //SI LA CASILLA ESTA VACIA, RECORRE EL ARRAY Y MUESTRA LA CASILLA Y LAS QUE TIENE ALREDEDOR, SIEMPRE Y CUANDO NO CONTENGAN UNA MINA
                    for (int i = -1; i <= 1; i++) {
                        
                        if(x+i >= 0 && x+i < Tablero.filas){
                            
                            for (int j = -1; j <= 1; j++) {
                                
                                if((y+j >= 0 && (y+j) < Tablero.columnas) && Tablero.casillas[x+i][y+j].getTipocelda() != 0){
                                    
                                    if(!Tablero.casillas[x+i][y+j].getMina()){
                                        
                                        Tablero.casillas[x+i][y+j].mostrar();
                                    }
                                }
                                
                            }
                        }
                    }
                    
                    break;
            }
        }
    }
    
    //METODO QUE VERIFICAR SI EL USUARIO GANO LA PARTIDA
    public void ganarPartida(){
        
        int cont = 0;
        
        for (int i = 0; i < Tablero.filas; i++) {
            
            for (int j = 0; j < Tablero.columnas; j++) {
                
                if(Tablero.casillas[i][j].getMina()){
                    
                    cont++;
                }
            }
        }
        if (!Tablero.ganar && cont == (Tablero.filas * Tablero.columnas - Tablero.minas)) {
            
            Tablero.ganar = true;
            
            //METODO QUE DESPLIEGA EL EJECUTABLE FINAL
            Fin fin = new Fin(Tablero.jDesktopPane1);
            
            Tablero.jDesktopPane1.add(fin);
            
            fin.show();
            
            fin.setVisible(true);
        }
    }
    
    //METODO QUE PERMITE MOSTRAR LAS CASILLAS
    private void mostrarCasilla(java.awt.event.ActionEvent evt){
        
        mostrar();
        
        ganarPartida();
    }


    
    
    
}
