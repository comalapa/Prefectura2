/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personal;

import ConBD.conexion;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_FAILED;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_READY;

import ConBD.conexion2;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Garcia
 */
public class altaPersona extends javax.swing.JDialog {

    boolean cargaFirma = false;
    boolean cargaFoto = false;
    boolean cargarHuella = false;
    
    public boolean registroOk = false;
    public boolean updateOk = false;
    
    //Capturador de Huella
    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    
    //Captura la huella a una imagen
    public DPFPFeatureSet featuresinscripcion;
    public DPFPFeatureSet featuresverificacion;
    
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    
    private DPFPTemplate template;
    
    public static String TEMPLATE_PROPERTY = "template";
    
    
    private JFileChooser buscarFoto = null;
    
    private JFileChooser buscarFirma = null;
    
    String rutaFoto ="";
    String rutaFirma = "";
    
    FileInputStream streamFoto = null;
    FileInputStream streamFirma = null;
    
    public int idPersona = 0;
    public int actualizar = 0;
    
    // Constructor principal
    public altaPersona(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        //Agregamos Listeners para activar o desactivar el Lector de Huellas
        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cierreVentana(evt);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                abrirVentana(evt);
            }
        });
        
    }
    
        
    public void getDataPersona(){
        conexion2 con = new conexion2();
        Connection c = con.conectar();
        try {
            
            
            PreparedStatement psResult = c.prepareStatement("Select curp,rfc,apellido_p, apellido_m, nombre, direccion huella, nip, estatus_id, rol_id, firma, foto, direccion, telefono from tbl_personal WHERE id = '"+idPersona+"';");
            ResultSet resultQ = psResult.executeQuery();
            
            if(resultQ.next()){
                txtCurp.setText(resultQ.getString("curp"));
                txtCurp.setEditable(false);
                txtRfc.setText(resultQ.getString("rfc"));
                txtRfc.setEditable(false);
                txtApellidoP.setText(resultQ.getString("apellido_p"));
                txtApellidoM.setText(resultQ.getString("apellido_m"));
                txtNombre.setText(resultQ.getString("nombre"));
                txtNip.setText(resultQ.getString("nip"));
                txtNip2.setText(resultQ.getString("nip"));
                
                txtDireccion.setText(resultQ.getString("direccion"));
                txtTelefono.setText(resultQ.getString("telefono"));
                
                lblFirma.setIcon(getImagen(resultQ.getBlob("firma"),lblFirma));
                cargaFirma = true;
                btnBuscarFirma.setEnabled(false);
                lblFoto.setIcon(getImagen(resultQ.getBlob("foto"),lblFoto));
                cargaFoto = true;
                btnBuscarFoto.setEnabled(false);
                
                cargarHuella = true;
                
                
            }else{
                JOptionPane.showMessageDialog(this, "No se encontro los datos de la persona...","Error",JOptionPane.ERROR_MESSAGE);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(altaPersona.class.getName()).log(Level.SEVERE, null, ex);
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
    
    // Listener para el cierre de ventanas
    private void cierreVentana(java.awt.event.WindowEvent evt) {
        // Detiene el lector de huellas
        stop();
    }
    // Listener para la apertura de la ventana
    private void abrirVentana(java.awt.event.WindowEvent evt){
        // Prepara el Lector para leer
        Iniciar();
        // Inicia el lector para detectar las lecturas.
        start();
//        EstadoHuellas();
    }
    
    // Activa el lector
    public void start(){
        Lector.startCapture();
        System.out.println("Utilizando el lector de huella");
//        EnviarTexto("Utilizando el Lector de Huella Dactilar ");
    }
    
    //Prepara el lector para detectar las lecutras
    public void Iniciar(){
        // Lectura del listener
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                //EnviarTexto("La Huella Digital ha sido Capturada");
                    System.out.println("Leyendo Huella...");
                    
                    if(cargarHuella){
                        pnlHuella.setBackground(new Color(204,204,204));
                        brAvance.setValue(0);
                        cargarHuella = false;
                    }
                    
                    if(!cargarHuella){
                        brAvance.setValue(brAvance.getValue()+25);
                        if(brAvance.getValue() == 100){
                            pnlHuella.setBackground(new Color(17,210,14));
                            pnlHuella.setOpaque(true);
                            cargarHuella = true;
                        }
                    }
                        
                    // Procesamos la huella leida
                    ProcesarCaptura(e.getSample());
                }
            });
        }
    });
    }
    
    // Procesamos la huella capturada
    public void ProcesarCaptura(DPFPSample sample){
        // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        
        // Si se da la inscripcion
        if (featuresinscripcion != null){
            try{
                
                Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear

                // Dibuja la huella dactilar capturada.
                Image image=CrearImagenHuella(sample);
                DibujarHuella(image);

            }
            catch (DPFPImageQualityException ex) {
                System.err.println("Error: "+ex.getMessage());
            }

            finally {

                // Comprueba si la plantilla se ha creado.
                switch(Reclutador.getTemplateStatus()){
                    case TEMPLATE_STATUS_READY:    // informe de éxito y detiene  la captura de huellas
                        stop();
                        setTemplate(Reclutador.getTemplate());
//                        EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Verificarla");
                        System.out.println("Plantilla de Huella ha sido creada...");
//                        btnIdentificar.setEnabled(true);
//                        btnGuardar.setEnabled(true);
//                        btnGuardar.grabFocus();
                        break;

                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                        Reclutador.clear();
                        stop();
                        //EstadoHuellas();
                        setTemplate(null);
//                        JOptionPane.showMessageDialog(CapturaHuella.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
                        System.out.println("La plantilla no pudo crearse");
                        pnlHuella.setBackground(new Color(204,204,204));
                        brAvance.setValue(0);
                        cargarHuella = false;
                        start();
                        break;
                }
            }
        }
        
    }
    
    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    
    
    public void DibujarHuella(Image image) {
        lblHuella.setIcon(new ImageIcon(
        image.getScaledInstance(170, 200, image.SCALE_DEFAULT)));
        repaint();
    }
    
    public Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    
    public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        }
        catch (DPFPImageQualityException e) {
            return null;
        }
    }
    
    public void stop(){
        Lector.stopCapture();
        System.out.println("No se esta usando el Lector de Huella");
        //EnviarTexto("No se está usando el Lector de Huella Dactilar ");
    }
    
    public DPFPTemplate getTemplate() {
        return template;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPersonales = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApellidoP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnBuscarFoto = new javax.swing.JButton();
        pnlHuella = new javax.swing.JPanel();
        lblHuella = new javax.swing.JLabel();
        brAvance = new javax.swing.JProgressBar();
        pnlOtros = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtNip = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        txtNip2 = new javax.swing.JPasswordField();
        pnlFirma = new javax.swing.JPanel();
        lblFirma = new javax.swing.JLabel();
        btnBuscarFirma = new javax.swing.JButton();
        pnlBotones = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlPersonales.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("CURP");

        txtCurp.setNextFocusableComponent(txtRfc);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("RFC");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellido Paterno");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Apellido Materno");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nombre (s)");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Direccion");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Telefono");

        lblFoto.setBackground(new java.awt.Color(255, 255, 255));
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0033_People.png"))); // NOI18N
        lblFoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblFoto.setOpaque(true);

        btnBuscarFoto.setText("Seleccionar...");
        btnBuscarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarFoto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlPersonalesLayout = new javax.swing.GroupLayout(pnlPersonales);
        pnlPersonales.setLayout(pnlPersonalesLayout);
        pnlPersonalesLayout.setHorizontalGroup(
            pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireccion)
                    .addGroup(pnlPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCurp)
                            .addComponent(txtRfc)
                            .addComponent(txtApellidoP)
                            .addComponent(txtApellidoM)
                            .addComponent(txtNombre)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPersonalesLayout.setVerticalGroup(
            pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtRfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlHuella.setBackground(new java.awt.Color(204, 204, 204));

        lblHuella.setBackground(new java.awt.Color(255, 255, 255));
        lblHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHuella.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null)));
        lblHuella.setOpaque(true);

        javax.swing.GroupLayout pnlHuellaLayout = new javax.swing.GroupLayout(pnlHuella);
        pnlHuella.setLayout(pnlHuellaLayout);
        pnlHuellaLayout.setHorizontalGroup(
            pnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHuellaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brAvance, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(lblHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlHuellaLayout.setVerticalGroup(
            pnlHuellaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHuellaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(brAvance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("NIP");

        txtNip.setText("1234");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Confirme NIP");

        txtNip2.setText("1234");

        lblFirma.setBackground(new java.awt.Color(255, 255, 255));
        lblFirma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFirma.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblFirma.setOpaque(true);

        btnBuscarFirma.setText("Seleccione...");
        btnBuscarFirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFirmaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFirmaLayout = new javax.swing.GroupLayout(pnlFirma);
        pnlFirma.setLayout(pnlFirmaLayout);
        pnlFirmaLayout.setHorizontalGroup(
            pnlFirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFirmaLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(btnBuscarFirma, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFirmaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFirma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlFirmaLayout.setVerticalGroup(
            pnlFirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFirmaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFirma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarFirma)
                .addContainerGap())
        );

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0010_Note.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/Metroid_48_0003_Trash.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlOtrosLayout = new javax.swing.GroupLayout(pnlOtros);
        pnlOtros.setLayout(pnlOtrosLayout);
        pnlOtrosLayout.setHorizontalGroup(
            pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOtrosLayout.createSequentialGroup()
                        .addComponent(pnlFirma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlOtrosLayout.createSequentialGroup()
                        .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNip)
                            .addComponent(txtNip2))))
                .addContainerGap())
        );
        pnlOtrosLayout.setVerticalGroup(
            pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtNip2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOtrosLayout.createSequentialGroup()
                        .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addComponent(pnlFirma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlHuella, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlOtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlOtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
            
        if(txtCurp.getText().toString().length() <= 0) {
            System.out.println("Verifique su curp");
        }else{
            if(txtRfc.getText().toString().length() <= 0){
                System.out.println("Verifique su RFC");
            }else {
                if(txtApellidoP.getText().toString().length() <= 0){
                    System.out.println("Verifique el Apellido Paterno");
                }else{
                    if(txtApellidoM.toString().length() <= 0){
                        System.out.println("Verifique el Apellido Materno");
                    }else{
                        if(txtNombre.getText().toString().length() <= 0){
                            System.out.println("Verifique el Nombre");
                        }else{
                            if(txtTelefono.getText().toString().length() <= 0){
                                System.out.println("Verifique el Telefono");
                            }else{
                                if(txtDireccion.getText().toString().length() <= 0){
                                    System.out.println("Verifique la Direccion");
                                }else{
                                    if(txtNip.getText().toString().length() <= 0 || txtNip2.getText().toString().length() <= 0){
                                        System.out.println("Verifique el NIP");
                                    }else{
                                        if(!txtNip.getText().toString().equals(txtNip2.getText().toString())){
                                            System.out.println("Los NIPS No coinciden");
                                        }else{
                                            if(!cargaFoto){
                                                System.out.println("No ha seleccionado la Foto");
                                            }else{
                                                if(!cargaFirma){
                                                    System.out.println("No ha seleccionado la imagen de la Firma");
                                                }else{
                                                    if(!cargarHuella){
                                                        System.out.println("No ha registrado la Huella");
                                                    }else{
                                                        System.out.println("Todos los campos completos");
                                                        
                                                        try{
                                                            
                                                            conexion2 con = new conexion2();
                                                            Connection c =  con.conectar();
                                                            if(actualizar == 0){
                                                                ByteArrayInputStream datosHuella = new ByteArrayInputStream(template.serialize());
                                                                Integer tamHuella = template.serialize().length;



                                                                PreparedStatement buscarUsuario = c.prepareStatement("SELECT huella FROM tbl_personal WHERE curp='"+txtCurp.getText()+"' OR rfc = '"+txtRfc.getText()+"';");
                                                                ResultSet result = buscarUsuario.executeQuery();

                                                                if(result.next()){
                                                                       //Lee la plantilla de la base de datos
                                                                        byte templateBuffer[] = result.getBytes("huella");
                                                                        //Crea una nueva plantilla a partir de la guardada en la base de datos
                                                                        DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                                                                        //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                                                                        setTemplate(referenceTemplate);

                                                                        // Compara las caracteriticas de la huella recientemente capturda con la
                                                                        // plantilla guardada al usuario especifico en la base de datos
                                                                        DPFPVerificationResult rsVerificacion = Verificador.verify(featuresverificacion, getTemplate());

                                                                        if(rsVerificacion.isVerified()){
                                                                            JOptionPane.showMessageDialog(this, "La CURP y/o RFC estan asociados con la Huella del usuario que intenta registrar\nVerifique", "Error al registrar la Persona", JOptionPane.ERROR_MESSAGE);
                                                                        }else{
                                                                            JOptionPane.showMessageDialog(this, "La CURP y/o RFC ya estan registrados.\nLa huella no coincide con la de la Persona que intenta registrar.\nIntente con otro dedo", "Error al registrar la Persona", JOptionPane.ERROR_MESSAGE);
                                                                        }
                                                                        registroOk = false;

                                                                }else{

                                                                    File foto = new File(rutaFoto);
                                                                    File firma = new File(rutaFirma);

                                                                    streamFoto = new FileInputStream(foto);
                                                                    streamFirma = new FileInputStream(firma);

                                                                    int tamFoto = (int) foto.length();
                                                                    int tamFirma = (int) firma.length();

                                                                    PreparedStatement stat = c.prepareStatement("INSERT INTO tbl_personal (nombre,apellido_p,apellido_m,curp,rfc,huella,nip,fecha_registro,estatus_id,rol_id,firma,foto,direccion,telefono) VALUES "
                                                                            + "('"+txtNombre.getText()+"','"+txtApellidoP.getText()+"','"+txtApellidoM.getText()+"','"+txtCurp.getText()+"','"+txtRfc.getText()+"',?,'"+txtNip.getText()+"',NOW(),1,1,?,?,'"+txtDireccion.getText()+"','"+txtTelefono.getText()+"');");

                                                                    stat.setBinaryStream(1, datosHuella,tamHuella);
                                                                    stat.setBinaryStream(2, streamFirma,tamFirma);
                                                                    stat.setBinaryStream(3, streamFoto,tamFoto);

                                                                    stat.execute();
                                                                    stat.close();

                                                                    System.out.println("Persona Guardada correctamente");
                                                                    registroOk = true;
                                                                    this.dispose();
                                                                }
                                                            }else {
                                                                if(JOptionPane.showConfirmDialog(this, "Desea actualizar los datos de la Persona?","Atencion",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                                                    String sql = "UPDATE tbl_personal SET apellido_p = '"+txtApellidoP.getText()+"', apellido_m = '"+txtApellidoM.getText()+"',nombre = '"+txtNombre.getText()+"', direccion = '"+txtDireccion.getText()+"', telefono = '"+txtTelefono.getText()+"', nip = '"+txtNip.getText()+"' WHERE id = "+idPersona+";";
                                                                    
                                                                    conexion newCon = new conexion();
                                                                    newCon.conectar();
                                                                    if(newCon.ejecutarSQL(sql)){
                                                                        JOptionPane.showMessageDialog(this, "Se actualizaron los datos correctamente");
                                                                        updateOk = true;
                                                                        this.dispose();
                                                                    }else{
                                                                        JOptionPane.showMessageDialog(this, "Problemas al actualziar");
                                                                        updateOk = false;
                                                                    }
                                                                }
                                                            }
                                                        }catch(Exception e){
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFotoActionPerformed
        if(buscarFoto != null){
            buscarFoto.setVisible(false);
            buscarFoto = null;
            cargaFoto = false;
        }
        buscarFoto = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Fotos","jpg","jpeg");
        buscarFoto.setFileFilter(filtro);
        if(buscarFoto.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            rutaFoto = buscarFoto.getSelectedFile().toString();
            ImageIcon icono = new ImageIcon(rutaFoto);
            Icon ico = new ImageIcon(icono.getImage().getScaledInstance(lblFoto.getWidth()-5, lblFoto.getHeight()-5, Image.SCALE_DEFAULT));
            lblFoto.setIcon(ico);
            this.repaint();
            cargaFoto = true;
        }
    }//GEN-LAST:event_btnBuscarFotoActionPerformed

    private void btnBuscarFirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFirmaActionPerformed
        if(buscarFirma != null){
            buscarFirma.setVisible(false);
            buscarFirma = null;
            cargaFirma = false;
        }
        buscarFirma = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes","jpg","jpeg");
        buscarFirma.setFileFilter(filtro);
        if(buscarFirma.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            rutaFirma = buscarFirma.getSelectedFile().toString();
            ImageIcon firma = new ImageIcon(rutaFirma);
            Icon ico = new ImageIcon(firma.getImage().getScaledInstance(lblFirma.getWidth()-5,lblFirma.getHeight()-5,Image.SCALE_DEFAULT));
            lblFirma.setIcon(ico);
            this.repaint();
            cargaFirma = true;
        }
    }//GEN-LAST:event_btnBuscarFirmaActionPerformed

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
            java.util.logging.Logger.getLogger(altaPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(altaPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(altaPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(altaPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                altaPersona dialog = new altaPersona(new javax.swing.JDialog(), true);
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
    private javax.swing.JProgressBar brAvance;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscarFirma;
    private javax.swing.JButton btnBuscarFoto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFirma;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblHuella;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlFirma;
    private javax.swing.JPanel pnlHuella;
    private javax.swing.JPanel pnlOtros;
    private javax.swing.JPanel pnlPersonales;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtCurp;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JPasswordField txtNip;
    private javax.swing.JPasswordField txtNip2;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
