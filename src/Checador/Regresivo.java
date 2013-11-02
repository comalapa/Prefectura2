/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Checador;

import java.awt.Component;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

/**
 *
 * @author Carmelita
 */
public class Regresivo extends Thread{
    
    private JDialog componente;
    private int time;
    int reg = 0;
    boolean ok = true;
    
    public Regresivo(JDialog comp,int tiempo){
        
        this.componente = comp;
        this.time = tiempo;
        
    }
    
    public void run(){
        try {
            while(ok){
                sleep(1000);
                System.out.println("Tiempo: " + reg);
                reg++;
                if(reg == time){
                    componente.setVisible(false);
                    componente.dispose();
                    System.out.println("Tiempo de espera terminado");
                    ok = false;
                }
            }
            
                
        } catch (InterruptedException ex) {
            Logger.getLogger(Regresivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
