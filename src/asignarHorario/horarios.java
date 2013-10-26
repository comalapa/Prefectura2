/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asignarHorario;

import ConBD.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Garcia
 */
public class horarios extends javax.swing.JDialog {
    
    private String[] titulos = new String[]{ "Id", "Hora Entrada", "Hora Salida"};
    public DefaultTableModel model=new DefaultTableModel(titulos,0);
    
    
    public horarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        getPersonal();
        getHorarios();
        getDias();
        
        actualizarTabla();
        
        
    }

    public void getModel(){
        tblHorarios.setModel(model);
        
        tblHorarios.getColumnModel().getColumn(0).setMinWidth(0);
        tblHorarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHorarios.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblHorarios.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblHorarios.getColumnModel().getColumn(2).setPreferredWidth(100);
        
                
    }
    
    private void deleteRowModelTable(){
        // Limpiamos el Modelo de la Tabla
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
    }
    
    private void getHorarios(){
        conexion con = new conexion();
        Statement stat = con.conectar();
        
        cmbHorario.removeAllItems();
        
        if(stat != null){
            ResultSet result = con.ejecutarSQLSelect("SELECT * FROM tbl_horario");
            
            try {
                while(result.next()){
                    cmbHorario.addItem(result.getString("hora"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void getPersonal(){
        conexion con = new conexion();
        Statement stat = con.conectar();
        
        cmbPersonal.removeAllItems();
        
        if(stat != null){
            ResultSet result = con.ejecutarSQLSelect("SELECT id, nombre, apellido_p, apellido_m, curp FROM tbl_personal;");
            
            try {
                while(result.next()){
                    cmbPersonal.addItem(result.getString("id") + "-" + result.getString("curp") + "-" + result.getString("nombre") + " " + result.getString("apellido_p") + " " + result.getString("apellido_m"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void getDias(){
        cmbDiaSemana.removeAllItems();
        cmbDiaSemana.addItem("Lunes");
        cmbDiaSemana.addItem("Martes");
        cmbDiaSemana.addItem("Miercoles");
        cmbDiaSemana.addItem("Jueves");
        cmbDiaSemana.addItem("Viernes");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbPersonal = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbDiaSemana = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbHorario = new javax.swing.JComboBox();
        btnAgregar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Personal");

        cmbPersonal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPersonal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPersonalItemStateChanged(evt);
            }
        });
        cmbPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPersonalActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dia de la semana");

        cmbDiaSemana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDiaSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDiaSemanaActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Horario");

        cmbHorario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbHorarioActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0044_Alarm.png"))); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0003_Trash.png"))); // NOI18N

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0001_Tasks.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbDiaSemana, 0, 233, Short.MAX_VALUE)
                            .addComponent(cmbHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbPersonal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAgregar)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbDiaSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cmbHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAceptar)
                            .addComponent(btnRemover))))
                .addContainerGap())
        );

        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblHorarios);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDiaSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDiaSemanaActionPerformed
        actualizarTabla();
    }//GEN-LAST:event_cmbDiaSemanaActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String[] splitPersona = cmbPersonal.getSelectedItem().toString().split("-");
        int idPersona = Integer.parseInt(splitPersona[0].toString());
        
        String[] horas = cmbHorario.getSelectedItem().toString().split("-");
        
        String horaEntrada = horas[0].toString().trim();
        String horaSalida = horas[1].toString().trim();
        
        System.out.println("idPersona = " + idPersona + " Hora Entrada: " + horaEntrada + " Hora Salida: " + horaSalida + " Numero de Dia = " + getNoDia());
        
        conexion con = new conexion();
        Statement stat = con.conectar();
        
        if(stat != null){
            try {
                
                ResultSet result = con.ejecutarSQLSelect("SELECT * from tbl_horario_personal WHERE personal_id = " + idPersona + " AND entrada = '"+horaEntrada+"' AND salida = '"+horaSalida+"' AND dia_semana = " + getNoDia());
                if(result.next()){
                    JOptionPane.showMessageDialog(this,"Ya se ha registrado la hora en el dia " + cmbDiaSemana.getSelectedItem().toString(), "Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    System.out.println("INSERT INTO tbl_horario_personal (personal_id,entrada,salida,dia_semana)VALUES("+idPersona+",'"+horaEntrada+"', '"+horaSalida+"',"+getNoDia()+");");
                    con.ejecutarSQL("INSERT INTO tbl_horario_personal (personal_id,entrada,salida,dia_semana)VALUES("+idPersona+",'"+horaEntrada+"', '"+horaSalida+"',"+getNoDia()+");");
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        actualizarTabla();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cmbHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbHorarioActionPerformed

    }//GEN-LAST:event_cmbHorarioActionPerformed

    private void cmbPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPersonalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPersonalActionPerformed

    private void cmbPersonalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPersonalItemStateChanged
        actualizarTabla();
    }//GEN-LAST:event_cmbPersonalItemStateChanged

    private void actualizarTabla(){
        getModel();
        deleteRowModelTable();
        
        String[] splitPersona = cmbPersonal.getSelectedItem().toString().split("-");
        int idPersona = Integer.parseInt(splitPersona[0].toString());
        
        conexion con = new conexion();
        Statement stat = con.conectar();
        
       if(stat != null){
           ResultSet result= con.ejecutarSQLSelect("SELECT * from tbl_horario_personal WHERE personal_id = " + idPersona  + " AND dia_semana = " + getNoDia() + ";");
            try {
                
                while(result.next()){
                    model.addRow(new Object[]{result.getInt("id"),result.getString("entrada"),result.getString("salida")});
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
    
    private int getNoDia(){
        int diaSemana  = 0;
        if(cmbDiaSemana.getSelectedItem().equals("Lunes")){
            diaSemana = 1;
        }
        if(cmbDiaSemana.getSelectedItem().equals("Martes")){
            diaSemana = 2;
        }
        
        if(cmbDiaSemana.getSelectedItem().equals("Miercoles")){
            diaSemana = 3;
        }
        
        if(cmbDiaSemana.getSelectedItem().equals("Jueves")){
            diaSemana = 4;
        }
        
        if(cmbDiaSemana.getSelectedItem().equals("Viernes")){
            diaSemana = 5;
        }
        
        return diaSemana;
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
            java.util.logging.Logger.getLogger(horarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(horarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(horarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(horarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                horarios dialog = new horarios(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox cmbDiaSemana;
    private javax.swing.JComboBox cmbHorario;
    private javax.swing.JComboBox cmbPersonal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHorarios;
    // End of variables declaration//GEN-END:variables
}
