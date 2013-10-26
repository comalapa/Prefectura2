package Checador;

import ConBD.conexion2;
import Personal.altaPersona;
import static Personal.altaPersona.TEMPLATE_PROPERTY;
import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class checadorFull extends JFrame {
    
    
//    private final GraphicsDevice gd;
    private confirmarIdentidad nip = null;
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    
    int cierre = 1;
    
    public Timer tiempo = null;
    
    public checadorFull() {
        super("Nada");
        this.setUndecorated(true);
        initComponents();
        
        
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Hilo hiloReloj = new Hilo(lblHora);
        hiloReloj.start();
        
        
        lblFecha.setText(armarFecha());       
        
        this.repaint();
        
        this.addComponentListener(new ComponentAdapter() {
            @Override public void componentShown(ComponentEvent e) {
                    addListener();
                    start();
            }
            @Override public void componentHidden(ComponentEvent e) {
                    stop();
            }

    });
    }
    
   protected void start()
    {
            capturer.startCapture();
            System.out.println("Usando el lector de Huellas, ya puede escanear.");
    } 
    
   protected void stop()
    {
            capturer.stopCapture();
    }
   protected void process(DPFPSample sample){
//       drawPicture(convertSampleToBitmap(sample));
       
       // Process the sample and create a feature set for the enrollment purpose.
		DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
                
                conexion2 con = new conexion2();
                Connection c = con.conectar();
        try {
            System.out.println("SELECT id, nombre, apellido_p, apellido_m, huella, estatus_id, rol_id,foto,nip FROM tbl_personal;");
            PreparedStatement buscarUsuario = c.prepareStatement("SELECT id, nombre, apellido_p, apellido_m, huella, estatus_id, rol_id,foto,nip FROM tbl_personal;");
            ResultSet result = buscarUsuario.executeQuery();
            
            
            while(result.next()){
                
                //Lee la plantilla de la base de datos
                byte templateBuffer[] = result.getBytes("huella");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate);
            
                
                
//             Check quality of the sample and start verification if it's good
		if (features != null)
		{
			// Compare the feature set with our template
			DPFPVerificationResult resultVer = verificator.verify(features, getTemplate());
//			updateStatus(result.getFalseAcceptRate());
			if (resultVer.isVerified()){
                            System.out.println("La huella fue verificada OK");
                            if(nip != null){
                                nip.setVisible(false);
                                nip.dispose();
                                nip = null;
                                System.gc();
                            }
                            nip = new confirmarIdentidad(this,true);
                            nip.setFoto(result.getBlob("foto"));
                            nip.pass = result.getString("nip");
                            nip.setNombre(result.getString("nombre") + " " + result.getString("apellido_p") + " " + result.getString("apellido_m"));
                            nip.setVisible(true);
                            break;                            
                        }else
				System.out.println("Huella NO verificada.");
		}
            }
        } catch (SQLException ex) {
            Logger.getLogger(checadorFull.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   
   
   public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
   
   protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
   
   public void drawPicture(Image image) {
            lblLogo.setIcon(new ImageIcon(image.getScaledInstance(200, lblLogo.getHeight(), Image.SCALE_DEFAULT)));
            
            
            
    }
   
   public DPFPTemplate getTemplate() {
        return template;
    }
   
   protected Image convertSampleToBitmap(DPFPSample sample) {
            return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
   
    public void addListener(){
        capturer.addDataListener(new DPFPDataAdapter() {
			@Override public void dataAcquired(final DPFPDataEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("La huella fue capturada.");
					System.out.println("Coloque el mismo dedo otra vez.");
					process(e.getSample());
				}});
			}
		});
		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
		 			System.out.println("El lector de Huellas se ha conectado.");
				}});
			}
			@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("El lector de hiellas se ha desconectado.");
				}});
			}
		});
		capturer.addSensorListener(new DPFPSensorAdapter() {
			@Override public void fingerTouched(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("El lector de huellas ha sido tocado.");
				}});
			}
			@Override public void fingerGone(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("El dedo ha sido removido del lector de huellas.");
				}});
			}
		});
		capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
			@Override public void onImageQuality(final DPFPImageQualityEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
						System.out.println("La calidad de la huella es buena.");
					else
						System.out.println("La calidad de la huella es mala.");
				}});
			}
		});
    }
    
    
    //Conseguir Fecha para el sistema.
    
    public String nombreDia(int noDia){
        switch(noDia){
            case 1: return "Domingo";
            case 2: return "Lunes";
            case 3: return "Martes";
            case 4: return "Miercoles";
            case 5: return "Jueves";
            case 6: return "Viernes";
            case 7: return "Sabado";
        }
        return "Error";
    }
    
    
    public String nombreMes(int noMes){
        switch(noMes){
            case 0: return "Enero";
            case 1: return "Febrero";
            case 2: return "Marzo";
            case 3: return "Abril";
            case 4: return "Mayo";
            case 5: return "Junio";
            case 6: return "Julio";
            case 7: return "Agosto";
            case 8: return "Septiembre";
            case 9: return "Octubre";
            case 10: return "Noviembre";
            case 11: return "Diciembre";
            
        }
        return "Error";
    }
    
    public String armarFecha(){
        GregorianCalendar fecha = new GregorianCalendar();
        int dia = fecha.get(fecha.DAY_OF_MONTH);
        int mes = fecha.get(fecha.MONTH);
        int anio = fecha.get(fecha.YEAR);
        int diaSemana = fecha.get(fecha.DAY_OF_WEEK);
        
        System.out.println(diaSemana);
        
        String miFecha = nombreDia(diaSemana) + " " + String.valueOf(dia) + " de " + nombreMes(mes) + " del " + String.valueOf(anio);
        return miFecha;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Escuela Secundaria Tecnica No. 32");
        lblTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTituloMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblTituloMouseReleased(evt);
            }
        });

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/est32Logo.jpg"))); // NOI18N
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLogoMouseReleased(evt);
            }
        });

        lblHora.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("00:00");
        lblHora.setName("lblFecha"); // NOI18N

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText("00:00");
        lblFecha.setName("lblFecha"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(92, 92, 92)
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTituloMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTituloMouseReleased
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_lblTituloMouseReleased

    private void lblLogoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseReleased

    }//GEN-LAST:event_lblLogoMouseReleased

    private void lblTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTituloMouseClicked

    }//GEN-LAST:event_lblTituloMouseClicked

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
            java.util.logging.Logger.getLogger(checadorFull.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(checadorFull.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(checadorFull.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(checadorFull.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                checadorFull dialog = new checadorFull();
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
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
