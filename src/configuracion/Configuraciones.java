/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuracion;

import ConBD.conexion2;
import Personal.altaPersona;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carmelita
 */
public class Configuraciones extends javax.swing.JDialog {

    conexion2 con = new conexion2();
    Connection c = con.conectar();
    
    private JFileChooser logo = null;
    private JFileChooser logoIzq = null;
    private JFileChooser logoDer = null;
    
    private String rutaLogo = "";
    private String rutaLogoIzq = "";
    private String rutaLogoDer = "";
    
    public Configuraciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        
        getDataConfig();
    }
    
    private void getDataConfig(){
        try {
            PreparedStatement getData = c.prepareStatement("SELECT * FROM tbl_config WHERE id=1;");
            ResultSet result = getData.executeQuery();
            
            if(result.next()){
                txtNombeEsc.setText(result.getString("nombre_escuela"));
                txtClaveEsc.setText(result.getString("clave_escuela"));
                txtNombreDir.setText(result.getString("nombre_director"));
                txtTiempoRetardo.setText(result.getString("min_retardo"));
                txtTiempoFalta.setText(result.getString("min_falta"));
                
                if(result.getBlob("logo_escuela") != null){
                    lblLogo.setText("");
                    lblLogo.setIcon(getImagen(result.getBlob("logo_escuela"), lblLogo));
                }else
                    lblLogo.setText("Logo de la Escuela");
                
                if(result.getBlob("logo_rep_izq") != null){
                    lblIzq.setText("");
                    lblIzq.setIcon(getImagen(result.getBlob("logo_rep_izq"), lblIzq));
                }else
                    lblIzq.setText("Logo Reportes Izquierda");
                
                if(result.getBlob("logo_rep_der") != null){
                    lblDer.setText("");
                    lblDer.setIcon(getImagen(result.getBlob("logo_rep_der"), lblDer));
                }else
                    lblDer.setText("Logo Reportes Derecha");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Icon getImagen(Blob bytesImg, JLabel label) {   
            Icon ico = null;
            this.repaint();
        ImageIcon imageIcon = null;
        try {
                Blob bytesImagen = bytesImg;

                byte[] bytesLeidos = bytesImagen.getBytes(1, (int) bytesImagen.length());
                imageIcon = new ImageIcon(bytesLeidos);
                
                ico = new ImageIcon(imageIcon.getImage().getScaledInstance(label.getWidth()-5,label.getHeight()-5,Image.SCALE_DEFAULT));
        } catch (SQLException ex) {
            Logger.getLogger(altaPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ico;
      }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombeEsc = new javax.swing.JTextField();
        txtClaveEsc = new javax.swing.JTextField();
        txtNombreDir = new javax.swing.JTextField();
        lblIzq = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblDer = new javax.swing.JLabel();
        btnLogoEscuela = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnLogoIzq = new javax.swing.JButton();
        btnLogoDer = new javax.swing.JButton();
        txtTiempoRetardo = new javax.swing.JTextField();
        txtTiempoFalta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre de la Escuela: ");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Clave de la Escuela: ");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nombre del Director: ");

        lblIzq.setBackground(new java.awt.Color(204, 204, 255));
        lblIzq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIzq.setOpaque(true);

        lblLogo.setBackground(new java.awt.Color(204, 204, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setOpaque(true);

        lblDer.setBackground(new java.awt.Color(204, 204, 255));
        lblDer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDer.setOpaque(true);

        btnLogoEscuela.setText("Examinar");
        btnLogoEscuela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoEscuelaActionPerformed(evt);
            }
        });

        jLabel7.setText("Logo de Escuela");

        jLabel8.setText("Logo Reporte Izquierda");

        jLabel9.setText("Logo Reporte Derecha");

        btnLogoIzq.setText("Examinar");
        btnLogoIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoIzqActionPerformed(evt);
            }
        });

        btnLogoDer.setText("Examinar");
        btnLogoDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoDerActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Minutos para retardo");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Minutos para falta");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0001_Tasks.png"))); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDer, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombeEsc)
                            .addComponent(txtClaveEsc)
                            .addComponent(txtNombreDir)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTiempoRetardo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(txtTiempoFalta))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnLogoEscuela)
                        .addGap(101, 101, 101)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAceptar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLogoIzq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLogoDer)
                                .addGap(48, 48, 48)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombeEsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClaveEsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTiempoRetardo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTiempoFalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDer, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogoEscuela)
                    .addComponent(btnLogoIzq)
                    .addComponent(btnLogoDer))
                .addGap(18, 18, 18)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoEscuelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoEscuelaActionPerformed
        if(logo != null){
            logo.setVisible(false);
            logo = null;
        }
        logo = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Fotos","jpg","png");
        logo.setFileFilter(filtro);
        if(logo.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            rutaLogo = logo.getSelectedFile().toString();
            ImageIcon icono = new ImageIcon(rutaLogo);
            Icon ico = new ImageIcon(icono.getImage().getScaledInstance(lblLogo.getWidth()-5, lblLogo.getHeight()-5, Image.SCALE_DEFAULT));
            lblLogo.setIcon(ico);
            lblLogo.setText("");
            this.repaint();
        }
        
    }//GEN-LAST:event_btnLogoEscuelaActionPerformed

    private void btnLogoIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoIzqActionPerformed
        if(logoIzq != null){
            logoIzq.setVisible(false);
            logoIzq = null;
        }
        logoIzq = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Fotos","jpg","png");
        logoIzq.setFileFilter(filtro);
        if(logoIzq.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            rutaLogoIzq = logoIzq.getSelectedFile().toString();
            ImageIcon icono = new ImageIcon(rutaLogoIzq);
            Icon ico = new ImageIcon(icono.getImage().getScaledInstance(lblIzq.getWidth()-5, lblIzq.getHeight()-5, Image.SCALE_DEFAULT));
            lblIzq.setIcon(ico);
            lblIzq.setText("");
            this.repaint();
        }
    }//GEN-LAST:event_btnLogoIzqActionPerformed

    private void btnLogoDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoDerActionPerformed
        if(logoDer != null){
            logoDer.setVisible(false);
            logoDer = null;
        }
        logoDer = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Fotos","jpg","png");
        logoDer.setFileFilter(filtro);
        if(logoDer.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            rutaLogoDer = logoDer.getSelectedFile().toString();
            ImageIcon icono = new ImageIcon(rutaLogoDer);
            Icon ico = new ImageIcon(icono.getImage().getScaledInstance(lblDer.getWidth()-5, lblDer.getHeight()-5, Image.SCALE_DEFAULT));
            lblDer.setIcon(ico);
            lblDer.setText("");
            this.repaint();
        }
    }//GEN-LAST:event_btnLogoDerActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(txtNombeEsc.getText().toString().trim().length() == 0){
            JOptionPane.showMessageDialog(this, "Debe escribir el Nombre de la Escuela", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            if(txtClaveEsc.getText().toString().trim().length() == 0){
                JOptionPane.showMessageDialog(this, "Debe escribir la Clave de la Escuela", "Error", JOptionPane.ERROR_MESSAGE);
            }else 
                if(txtNombreDir.getText().toString().trim().length() == 0)
                        JOptionPane.showMessageDialog(this, "Debe escribir el Nombre del Director", "Error", JOptionPane.ERROR_MESSAGE);
                else if(txtTiempoFalta.getText().toString().trim().length() == 0)
                    JOptionPane.showMessageDialog(this, "Debe escribir el Tiempo para considerar la Falta", "Error", JOptionPane.ERROR_MESSAGE);
                else if(Integer.parseInt(txtTiempoFalta.getText().toString()) < 0 || Integer.parseInt(txtTiempoFalta.getText().toString()) > 30)
                    JOptionPane.showMessageDialog(this, "El tiempo para las faltas debe ser de 1 - 30 minutos", "Error", JOptionPane.ERROR_MESSAGE);
                else if(txtTiempoRetardo.getText().toString().trim().length() == 0)
                    JOptionPane.showMessageDialog(this, "Debe escribir el Tiempo para considerar el Retardo", "Error", JOptionPane.ERROR_MESSAGE);
                else if(Integer.parseInt(txtTiempoRetardo.getText().toString()) < 0 || Integer.parseInt(txtTiempoRetardo.getText().toString()) > 15)
                    JOptionPane.showMessageDialog(this, "El tiempo para los retardos debe ser de 1 - 15 minutos", "Error", JOptionPane.ERROR_MESSAGE);
                else{
                    
                try {                    
                    
                    int retardo = Integer.parseInt(txtTiempoRetardo.getText().toString());
                    int falta = Integer.parseInt(txtTiempoFalta.getText().toString());
                    
                    String sql = "UPDATE tbl_config SET min_retardo="+retardo+", min_falta="+falta+", nombre_escuela='" +txtNombeEsc.getText()+"', clave_escuela='"+txtClaveEsc.getText()+"', nombre_director='"+txtNombreDir.getText()+", logo_escuela=?, logo_rep_izq=?, logo_rep_izq=?";
                                        
                    FileInputStream streamLogo =null;
                    int tamLogo = 0;
                    if(!rutaLogo.equals("")){
                        File logo = new File(rutaLogo);
                        tamLogo = (int) logo.length();
                        streamLogo = new FileInputStream(logo);
                    }
                    
                    FileInputStream streamLogoIzq =null;
                    int tamLogoIzq= 0;
                    if(!rutaLogoIzq.equals("")){
                        File logo = new File(rutaLogoIzq);
                        tamLogoIzq = (int) logo.length();
                            streamLogoIzq = new FileInputStream(logo);
                    }
                    
                    FileInputStream streamLogoDer =null;
                    int tamLogoDer = 0;
                    if(!rutaLogoDer.equals("")){
                        File logo = new File(rutaLogoDer);
                        tamLogoDer = (int) logo.length();
                        streamLogoDer = new FileInputStream(logo);
                    } 
                    
                    
                    PreparedStatement stat = c.prepareStatement(sql);
                    
                    if(streamLogo != null){
                        stat.setBinaryStream(1, streamLogo,tamLogo);
                    }else{
                        stat.setString(1, "NULL");
                    }
                    
                    if(streamLogoIzq != null){
                        stat.setBinaryStream(2, streamLogoIzq,tamLogoIzq);
                    }else{
                        stat.setString(2, "NULL");
                    }
                    
                    if(streamLogoDer != null){
                        stat.setBinaryStream(3, streamLogoDer,tamLogoDer);
                    }else{
                        stat.setString(3, "NULL");
                    }
                    stat.execute();
                    JOptionPane.showMessageDialog(this, "Datos actualizados correctamente");
                } catch (SQLException ex) {
                    Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Configuraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(Configuraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Configuraciones dialog = new Configuraciones(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnLogoDer;
    private javax.swing.JButton btnLogoEscuela;
    private javax.swing.JButton btnLogoIzq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDer;
    private javax.swing.JLabel lblIzq;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JTextField txtClaveEsc;
    private javax.swing.JTextField txtNombeEsc;
    private javax.swing.JTextField txtNombreDir;
    private javax.swing.JTextField txtTiempoFalta;
    private javax.swing.JTextField txtTiempoRetardo;
    // End of variables declaration//GEN-END:variables
}
