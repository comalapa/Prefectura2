/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Garcia
 */
public class renderTabla extends JLabel implements TableCellRenderer{
    
    String path = "";
    
    public renderTabla(String path)
    {
        this.path = path;
     }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        JLabel etiqueta = new JLabel();
        
        etiqueta.setBackground(new Color(255,66,6));
        etiqueta.setOpaque(true);
        
        ImageIcon icono = new ImageIcon(path);
        Icon ico = new ImageIcon(icono.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
        etiqueta.setIcon(ico);
        
        
//        etiqueta.setIcon (new ImageIcon(path));
        
        return etiqueta;
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    
}
