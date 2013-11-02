
package Checador;

import ConBD.conexion;
import Personal.altaPersona;
import java.awt.Color;
import java.awt.Image;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class confirmarIdentidad extends javax.swing.JDialog {

    public String pass = "";
    public int minRetardo = 0;
    public int minFalta = 0;
    public int dia = -1;
    public int idPersona = 0;
    
    public conexion con = new conexion();
    public Statement stat = con.conectar();
    
        
    public confirmarIdentidad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(this);
    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNip = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNombre.setText("jLabel2");

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lock.png"))); // NOI18N

        lblInfo.setBackground(new java.awt.Color(204, 204, 255));
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setText("Ingrese su NIP");
        lblInfo.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nombre:");
        jLabel1.setToolTipText("");

        txtNip.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        txtNip.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNipKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNipKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtNip.getAccessibleContext().setAccessibleDescription("null");

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

    private void txtNipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNipKeyReleased
        if(txtNip.getText().toString().length() == 4){
            txtNip.setEnabled(false);
            System.out.println("Confirmando nip");
            
            if(txtNip.getText().toString().equals(pass)){
                lblInfo.setBackground(new Color(0,254,102));
                lblInfo.setText("Bienvenid@");
                registrarEntrada();
            }else{
                lblInfo.setBackground(new Color(255,51,51));
                lblInfo.setText("Su NIP no es correcto");
            }
            Regresivo reg = new Regresivo(this,3);
            reg.start();
        }
    }//GEN-LAST:event_txtNipKeyReleased

    private void registrarEntrada(){
        
        
        Date ahora = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        String horaActual = sdf.format(ahora);
        
        System.out.println("Hora Actual = " + horaActual);
        
        ResultSet result = con.ejecutarSQLSelect("SELECT * FROM tbl_horario_personal WHERE dia_semana =" + this.dia + " AND personal_id =" + idPersona + " ORDER BY entrada ASC LIMIT 1;");
        try {
            
            if(result.next()){
                String horaEntrada = result.getString("entrada");
                String horaRetardo = horaEntrada.substring(0,3)+minRetardo+":00";
                String horaFalta = horaEntrada.substring(0,3)+minFalta+":00";
                
                System.out.println("Hora de Entrada: " + horaEntrada + "| Hora con Retardo: " + horaRetardo + "| Hora con Falta: " + horaFalta);
                
                try {
                    Date hActual,hEntrada,hRetardo,hFalta;
                    hActual = sdf.parse(horaActual);
                    hEntrada = sdf.parse(horaEntrada);
                    hRetardo = sdf.parse(horaRetardo);
                    hFalta = sdf.parse(horaFalta);
                    
                    if(hActual.compareTo(hEntrada)<=0){
                        System.out.println("Asistencia");
                    }else if(hActual.compareTo(hEntrada)>0 && hActual.compareTo(hRetardo)<=0){
                        System.out.println("Retardo");
                    }else if(hActual.compareTo(hFalta) > 0 ){
                        System.out.println("Falta");
                    }
                    
                } catch (ParseException ex) {
                    Logger.getLogger(confirmarIdentidad.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }else{
                JOptionPane.showMessageDialog(this, "Usted no tiene Horarios Asignados, Reporte este incidente con el Administrador del Sistema");
            }
        } catch (SQLException ex) {
            Logger.getLogger(confirmarIdentidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void txtNipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNipKeyTyped
        if(txtNip.getText().toString().length() == 4){
            evt.consume();
        }
    }//GEN-LAST:event_txtNipKeyTyped
    
    public void setNombre(String nom){
        lblNombre.setText(nom);
    }
    
    public void setFoto(Blob imagenByte){
        lblImagen.setIcon(getImagen(imagenByte,lblImagen));
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
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(confirmarIdentidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(confirmarIdentidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(confirmarIdentidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(confirmarIdentidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                confirmarIdentidad dialog = new confirmarIdentidad(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPasswordField txtNip;
    // End of variables declaration//GEN-END:variables
}
