/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cxp;

import ConexionBD.Conexion;
import Historico_PROV.THistorico;
import Log.Login;
import static Log.Login.usuario;
import Proveedores.Proveedores_Principal;
import Proveedores.Variables;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Johan Mendoza
 */
public class Panel_CXP_Edit1 extends javax.swing.JPanel {
    static String usuario;
    Variables var = new Variables();
    String busqueda;
    int valor;
    static int NumProveedor, condpago, confirmacion;
    static double importetotal, anticipo, saldoxpagar;
    static Date date, datesum;
    static String NombreProveedor, NombreProyecto, NumFacturaAnterior, NumFactura;
    /**
     * Creates new form Panel_CXP
     */
    public Panel_CXP_Edit1(String usuario) {
        initComponents();
        this.usuario=usuario;
    }
    
    //generar historico
        public void GENH(String mensaje1, String mensaje2,String mensaje3) throws SQLException
    {
  
    //String mensaje3=log.u;
    
    Date fecha = new Date();
    long flong = fecha.getTime();
    java.sql.Date fechaing = new java.sql.Date(flong);
        
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    
    Statement stm=Conexion.getConexion().createStatement();
    
    ResultSet rs=stm.executeQuery("insert HG (NOMUSUARIO,FECHA,ACCION,NG,HORA,MOTIVO) values ('"+usuario+"','"+fechaing+"','"+mensaje1+"','"+mensaje2+"','"+dateFormat.format(date)+"','"+mensaje3+"');");
    }
        
    public void CANCELARF(String factura)throws SQLException
    {
    Statement stm=Conexion.getConexion().createStatement();
    
    ResultSet rs=stm.executeQuery("update CXP set Estado=0 where NumFactura='"+factura+"';");
    
    }
    
    public void CFP(String factura,String usuario) throws SQLException
    {
    Statement stm=Conexion.getConexion().createStatement();
    ResultSet rs=stm.executeQuery("insert into Pagos(Num_Factura,Estatus,Concepto,Solicitante,Autorizo) values('F800','0','Cancelacion','Arturo','Arturo');;");
    }

    //cancelacion por parte del administrador y permitidos 
    public  void cancelar(String motivo)
    {

    try{
        GENH("Cancelacion de factura",NumFactura,motivo); 
    } catch (SQLException ex)
    {
    Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    try {
        CFP(NumFactura, usuario);
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    try {
            CANCELARF(NumFactura);
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    //intento por cancelar una factura: no autorizados 
    public void intentocancelar(String motivo)
    {
    
        try{
        GENH("Intento de cancelación de factura",NumFactura,motivo); 
        } catch (SQLException ex)
        {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String sumarDiasAFecha(String fecha, int dias) {
    if(dias == 0){
        return fecha;
    }

    String[] f = fecha.split("-");
    Calendar calendar = Calendar.getInstance();
    //calendar.setTime(new Date(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2])));
    calendar.set(Integer.parseInt(f[0]), Integer.parseInt(f[1])-1, Integer.parseInt(f[2]));

    calendar.add(Calendar.DAY_OF_MONTH, dias);
    SimpleDateFormat fe = new SimpleDateFormat("YYYY-MM-dd");
    return fe.format(calendar.getTime());

}
    public void busquedaproveedor() throws SQLException
{
String consulta;
String b;
b=var.getGetTexto();
consulta=b.trim();
        java.sql.Statement stm=Conexion.getConexion().createStatement();
        ResultSet rst=stm.executeQuery("SELECT * from CXP where NomProveedor like '%"+b+"%'");
        while(rst.next())
        {
        NumProveedor = rst.getInt(2);
                NombreProveedor = rst.getString(3);
                importetotal = rst.getDouble(4);
                condpago = rst.getInt(5);
                NombreProyecto = rst.getString(6);
                anticipo = rst.getDouble(7);
                date = rst.getDate(8);
                datesum = rst.getDate(9);
                saldoxpagar = rst.getDouble(10);
                
                
                
                txt_numfactura.setText(busqueda.trim());
                txt_numprovedor.setText(String.valueOf(NumProveedor));
                lbl_NProveedor.setText(NombreProveedor);
                txt_total.setText(String.valueOf(importetotal).trim());
                txt_nombreproyecto.setText(NombreProyecto.trim());
                jdc_Fechaem.setDate(date);
                lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
                lbl_limpago.setText(String.valueOf(datesum));
                
                
                //Cadena de IFs para los checkbox de Condiciones de Pago
                if(condpago == 30){
                    chk_30d.setSelected(true);
                } else if(condpago == 45){
                    chk_45d.setSelected(true);
                } else if(condpago == 60){
                    chk_60d.setSelected(true);
                }
                //Cadena de IFs para los checkbox de Anticipos
                if((importetotal  * 0.30) == anticipo){
                    chk_30p.setSelected(true);
                } else if((importetotal  * 0.40) == anticipo){
                    chk_40p.setSelected(true);
                } else if((importetotal  * 0.50) == anticipo){
                    chk_50p.setSelected(true);
                }  
        }
}
    
   public int valorpago(int val)
   {
       if(val==0) //pendiente 
       {
           return 2;
       }
       else   
       {
           if(val>0) //pagado
           {
            return 1;
           }
           else //cancelado 
           { 
            return 0;
           }
           
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

        btnGroupCondPago = new javax.swing.ButtonGroup();
        btnGroupAnticipo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nombreproyecto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        chk_30d = new javax.swing.JCheckBox();
        chk_45d = new javax.swing.JCheckBox();
        chk_inmediato = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        chk_60d = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        chk_30p = new javax.swing.JCheckBox();
        chk_40p = new javax.swing.JCheckBox();
        chk_50p = new javax.swing.JCheckBox();
        lbl_limpago = new javax.swing.JLabel();
        lbl_NProveedor = new javax.swing.JLabel();
        lbl_saldoxpagar = new javax.swing.JLabel();
        txt_numfactura = new javax.swing.JTextField();
        txt_numprovedor = new javax.swing.JTextField();
        jdc_Fechaem = new com.toedter.calendar.JDateChooser();
        chk_50p1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        txtsearch = new javax.swing.JTextField();
        cmb_tipobusqueda = new javax.swing.JComboBox<>();
        jPBuscar = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPEditar = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPAdd = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPDelete = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pnl_limpar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(950, 750));

        jPanel1.setBackground(new java.awt.Color(140, 140, 140));
        jPanel1.setPreferredSize(new java.awt.Dimension(810, 442));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_id.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id.setText("Número de Factura");
        jPanel1.add(lbl_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 160, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nombre del Proveedor");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 160, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha de Emisión");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 130, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre del Proyecto");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 120, -1));

        txt_nombreproyecto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_nombreproyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreproyectoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_nombreproyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 160, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Saldo por Pagar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Condiciones de Pago");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 140, -1));

        txt_total.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_totalKeyTyped(evt);
            }
        });
        jPanel1.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 170, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha Límite de Pago");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 120, -1));

        btnGroupCondPago.add(chk_30d);
        chk_30d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_30d.setText("30 días");
        chk_30d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_30dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_30d, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        btnGroupCondPago.add(chk_45d);
        chk_45d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_45d.setText("45 días");
        chk_45d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_45dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_45d, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, -1));

        btnGroupCondPago.add(chk_inmediato);
        chk_inmediato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_inmediato.setText("Inmediato");
        chk_inmediato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_inmediatoActionPerformed(evt);
            }
        });
        jPanel1.add(chk_inmediato, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, -1, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Anticipo");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 50, -1));

        btnGroupCondPago.add(chk_60d);
        chk_60d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_60d.setText("60 días");
        chk_60d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_60dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_60d, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Número de Proveedor");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 130, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Importe");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 50, -1));

        btnGroupAnticipo.add(chk_30p);
        chk_30p.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_30p.setText("30%");
        chk_30p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_30pActionPerformed(evt);
            }
        });
        jPanel1.add(chk_30p, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 50, 30));

        btnGroupAnticipo.add(chk_40p);
        chk_40p.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_40p.setText("40%");
        chk_40p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_40pActionPerformed(evt);
            }
        });
        jPanel1.add(chk_40p, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, -1, 30));

        btnGroupAnticipo.add(chk_50p);
        chk_50p.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_50p.setText("inmediato");
        chk_50p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_50pActionPerformed(evt);
            }
        });
        jPanel1.add(chk_50p, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, 30));

        lbl_limpago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_limpago.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_limpago, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 150, 20));

        lbl_NProveedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_NProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_NProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 160, 20));

        lbl_saldoxpagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_saldoxpagar.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_saldoxpagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 160, 20));
        jPanel1.add(txt_numfactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, -1));

        txt_numprovedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numprovedorActionPerformed(evt);
            }
        });
        txt_numprovedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numprovedorKeyReleased(evt);
            }
        });
        jPanel1.add(txt_numprovedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 170, -1));
        jPanel1.add(jdc_Fechaem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 160, -1));

        btnGroupAnticipo.add(chk_50p1);
        chk_50p1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_50p1.setText("50%");
        chk_50p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_50p1ActionPerformed(evt);
            }
        });
        jPanel1.add(chk_50p1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, 30));

        jPanel4.setBackground(new java.awt.Color(140, 140, 140));

        txtsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchActionPerformed(evt);
            }
        });
        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchKeyReleased(evt);
            }
        });

        cmb_tipobusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Búsqueda por Número de Factura", "Búsqueda por Nombre del Proveedor" }));
        cmb_tipobusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_tipobusquedaActionPerformed(evt);
            }
        });

        jPBuscar.setBackground(new java.awt.Color(140, 140, 140));
        jPBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPBuscarMouseExited(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(140, 140, 140));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-búsqueda-50 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPBuscarLayout = new javax.swing.GroupLayout(jPBuscar);
        jPBuscar.setLayout(jPBuscarLayout);
        jPBuscarLayout.setHorizontalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPBuscarLayout.setVerticalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_tipobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(cmb_tipobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(59, 59, 59))
        );

        jPanel5.setBackground(new java.awt.Color(107, 190, 249));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPEditar.setBackground(new java.awt.Color(107, 190, 249));
        jPEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPEditarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPEditarMouseExited(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-editar-50.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPEditarLayout = new javax.swing.GroupLayout(jPEditar);
        jPEditar.setLayout(jPEditarLayout);
        jPEditarLayout.setHorizontalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEditarLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel14)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPEditarLayout.setVerticalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEditarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14))
        );

        jPanel5.add(jPEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jPAdd.setBackground(new java.awt.Color(107, 190, 249));
        jPAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPAddMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPAddMouseExited(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-recibo-dólar-50.png"))); // NOI18N

        javax.swing.GroupLayout jPAddLayout = new javax.swing.GroupLayout(jPAdd);
        jPAdd.setLayout(jPAddLayout);
        jPAddLayout.setHorizontalGroup(
            jPAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel16)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPAddLayout.setVerticalGroup(
            jPAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(jPAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 100, 50));

        jPDelete.setBackground(new java.awt.Color(107, 190, 249));
        jPDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPDeleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPDeleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPDeleteMouseExited(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-cancelar2-50.png"))); // NOI18N

        javax.swing.GroupLayout jPDeleteLayout = new javax.swing.GroupLayout(jPDelete);
        jPDelete.setLayout(jPDeleteLayout);
        jPDeleteLayout.setHorizontalGroup(
            jPDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(18, 18, 18))
        );
        jPDeleteLayout.setVerticalGroup(
            jPDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15))
        );

        jPanel5.add(jPDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 90, 50));

        pnl_limpar.setBackground(new java.awt.Color(107, 190, 249));
        pnl_limpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnl_limpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_limparMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_limparMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_limparMouseExited(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-escoba-50.png"))); // NOI18N

        javax.swing.GroupLayout pnl_limparLayout = new javax.swing.GroupLayout(pnl_limpar);
        pnl_limpar.setLayout(pnl_limparLayout);
        pnl_limparLayout.setHorizontalGroup(
            pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_limparLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnl_limparLayout.setVerticalGroup(
            pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_limparLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(pnl_limpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 100, 50));

        jPanel6.setBackground(new java.awt.Color(107, 190, 249));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-tabla-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 90, 40));

        jLabel22.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel22.setText("EDITAR FACTURA");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel23.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel23.setText("MOSTRAR FACTURAS GENERADAS");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        jLabel24.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel24.setText("LIMPIAR FORMULARIO");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, -1, -1));

        jLabel25.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel25.setText("GENERAR FACTURA");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        jLabel26.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel26.setText("CANCELAR FACTURA");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel18.setText("MENU PRINCIPAL DE CUENTAS POR PAGAR");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseClicked
            
           busqueda=txtsearch.getText();
           Object itembusqueda = cmb_tipobusqueda.getSelectedItem();
           String tipobusqueda = ((String) itembusqueda);
           
           if(tipobusqueda  == "Búsqueda por Número de Factura"){
           
       
        try {
            // Aqui es donde se generara la busqueda
            //txt_NumFact.setVisible(true);
            //lbl_id.setVisible(true);
            
            
            
            
                
            
            
            Statement stm=Conexion.getConexion().createStatement();
            ResultSet rst=stm.executeQuery("SELECT * from CXP where NumFactura = '"+busqueda+"'");
            if(rst.next())
            {
                NumProveedor = rst.getInt(2);
                NombreProveedor = rst.getString(3);
                importetotal = rst.getDouble(4);
                condpago = rst.getInt(5);
                NombreProyecto = rst.getString(6);
                anticipo = rst.getDouble(7);
                date = rst.getDate(8);
                datesum = rst.getDate(9);
                saldoxpagar = rst.getDouble(10);
                
                
                
                txt_numfactura.setText(busqueda.trim());
                txt_numprovedor.setText(String.valueOf(NumProveedor));
                lbl_NProveedor.setText(NombreProveedor);
                txt_total.setText(String.valueOf(importetotal).trim());
                txt_nombreproyecto.setText(NombreProyecto.trim());
                jdc_Fechaem.setDate(date);
                lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
                lbl_limpago.setText(String.valueOf(datesum));
                
                
                //Cadena de IFs para los checkbox de Condiciones de Pago
                if(condpago == 30){
                    chk_30d.setSelected(true);
                } else if(condpago == 45){
                    chk_45d.setSelected(true);
                } else if(condpago == 60){
                    chk_60d.setSelected(true);
                }
                //Cadena de IFs para los checkbox de Anticipos
                if((importetotal  * 0.30) == anticipo){
                    chk_30p.setSelected(true);
                } else if((importetotal  * 0.40) == anticipo){
                    chk_40p.setSelected(true);
                } else if((importetotal  * 0.50) == anticipo){
                    chk_50p.setSelected(true);
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "El número de Factura no existe","Número no existente",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {
               try {
                   
                   ResultadosBusqueda rb = new ResultadosBusqueda(usuario);
                   rb.ct(busqueda);
                   this.setVisible(false);
                   rb.setVisible(true);
               } catch (SQLException ex) {
                   Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
    }//GEN-LAST:event_jPBuscarMouseClicked

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // Busqueda de informacion mediante ID, NOMBRE O RFC

    }//GEN-LAST:event_txtsearchActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased

    }//GEN-LAST:event_txtsearchKeyReleased

    private void txt_nombreproyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreproyectoKeyTyped

    }//GEN-LAST:event_txt_nombreproyectoKeyTyped

    private void txt_totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyTyped
        if(txt_total.getText().length() >= 13)
        {
            evt.consume();
        }
    }//GEN-LAST:event_txt_totalKeyTyped

    private void jPEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseClicked
     date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
       NumFactura = txt_numfactura.getText();
        importetotal = Double.parseDouble(txt_total.getText());
        NombreProyecto = txt_nombreproyecto.getText();
        System.out.println(fechaem);
        
        //Ifs para calculo de la fecha de pago
        if(condpago == 30){
            fechapago = sumarDiasAFecha(fechaaux, 30);
        } else if(condpago == 45){
            fechapago = sumarDiasAFecha(fechaaux, 45);
        } else if(condpago == 60){
            fechapago = sumarDiasAFecha(fechaaux, 60);
        }
        
         try {
            
            Statement stm=Conexion.getConexion().createStatement();
            
           
             confirmacion = JOptionPane.showConfirmDialog(null,"¿Esta seguro que desea editar estos datos?");
             if(confirmacion == 0){
                  ResultSet rst=stm.executeQuery("update CXP set NumFactura = '"+NumFactura.trim()+"', NProveedor = '"+NumProveedor+"', NomProveedor ='"+NombreProveedor.trim()+"', Importe = '"+importetotal+"', CondPago = '"+condpago+"', NombreProj = '"+NombreProyecto.trim()+"', Anticipo = '"+anticipo+"', FEmision = '"+fechaem+"', FPago = '"+fechapago+"', Saldo = '"+saldoxpagar+"' where NumFactura = '"+NumFacturaAnterior+"'");            
             }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
         JOptionPane.showMessageDialog(null, "Datos editados con éxito","Datos Gauradados",JOptionPane.INFORMATION_MESSAGE);
         
         confirmacion = JOptionPane.showConfirmDialog(null,"¿Desea modificar otros datos?");
         if(confirmacion == 0){
         }
    }//GEN-LAST:event_jPEditarMouseClicked

    private void jPAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseClicked
        //prueba con boton
        date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
       NumFactura = txt_numfactura.getText();
        importetotal = Double.parseDouble(txt_total.getText());
        NombreProyecto = txt_nombreproyecto.getText();
        System.out.println(fechaem);
        
        //Ifs para calculo de la fecha de pago
        if(condpago == 30){
            fechapago = sumarDiasAFecha(fechaaux, 30);
        } else if(condpago == 45){
            fechapago = sumarDiasAFecha(fechaaux, 45);
        } else if(condpago == 60){
            fechapago = sumarDiasAFecha(fechaaux, 60);
        }
        lbl_limpago.setText(String.valueOf(fechapago));
         try 
        {
            
            int v=valorpago((int) saldoxpagar);
            Statement stm=Conexion.getConexion().createStatement();
            //Llamado a la base de datos
            ResultSet rs=stm.executeQuery("insert into CXP (NumFactura, NProveedor, NomProveedor, Importe, CondPago, NombreProj, Anticipo, FEmision, FPago, Saldo,Estado) values ('"+NumFactura.trim()+"','"+NumProveedor+"','"+NombreProveedor.trim()+"','"+importetotal+"', '"+condpago+"', '"+NombreProyecto.trim()+"','"+anticipo+"', '"+fechaem+"','"+fechapago+"', '"+saldoxpagar+"','"+v+"')");
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
           GENH("Generacion de factura",NumFactura,"-"); 
        } catch (SQLException ex)
        {
        Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }

         
    }//GEN-LAST:event_jPAddMouseClicked

    private void jPDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseClicked
    String motivo = "";
    NumFactura=txtsearch.getText();
    try
    {

        String url="select IDRol from RolUsuario inner join Usuarios on RolUsuario.IDRol=Usuarios.Rol_Usuario where Nombre_Usuario='"+usuario+"';";
        SQLServerDataSource con = new SQLServerDataSource();
        con.setServerName("localhost");
        con.setDatabaseName("Gestion4F");//nombre de mi base de datos 
        con.setUser("4FAdmin");
        con.setPassword("CuatroF");
        con.setPortNumber(1433);        
       
          Connection cn = con.getConnection();
          PreparedStatement ps = cn.prepareStatement(url);
          ResultSet rs = ps.executeQuery();
          if(rs.next())
          {
              String rol = rs.getString("IDRol");
              System.out.println(rol);
              //administrador 
              if(rol.equals("1"))
              {
                  motivo=JOptionPane.showInputDialog("Ingrese la razón de la cancelacion");
                  cancelar(motivo);
                  
              }
              //usuario con persmisos 
              if(rol.equals("3"))
              {
                  motivo=JOptionPane.showInputDialog("Ingrese la razón de la cancelación");
                  cancelar(motivo);
              }
              //usuario sin permisos 
              if(rol.equals("2"))
              {
                JOptionPane.showMessageDialog(null,"No tiene los permisos para cancelar la factura");
                intentocancelar(motivo);
              }
              
          
          }
          else 
          {
              JOptionPane.showMessageDialog(null,"su operacion no es posible ejecutarse ya que no tiene permisos");
          }
        }
    catch(SQLException ex )        
    {
     JOptionPane.showMessageDialog(null,ex.toString());
    }
    


    }//GEN-LAST:event_jPDeleteMouseClicked

    private void pnl_limparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseClicked
        // TODO add your handling code here:
        txt_numfactura.setText("");
        txt_numprovedor.setText("");
        lbl_NProveedor.setText("");
        txt_total.setText("");
        txt_nombreproyecto.setText("");
        lbl_saldoxpagar.setText("");
        lbl_limpago.setText("");
        jdc_Fechaem.setDate(null);
    }//GEN-LAST:event_pnl_limparMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        try {
            // TODO add your handling code here:

            THistorico tg = new THistorico(usuario);
            tg.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Proveedores_Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel6MouseClicked

    private void chk_30dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_30dActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        condpago = 30;
        date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
        System.out.println(fechaem);
        
        //Ifs para calculo de la fecha de pago
        if(condpago == 30){
            fechapago = sumarDiasAFecha(fechaaux, 30);
        } else if(condpago == 45){
            fechapago = sumarDiasAFecha(fechaaux, 45);
        } else if(condpago == 60){
            fechapago = sumarDiasAFecha(fechaaux, 60);
        }
        lbl_limpago.setText(String.valueOf(fechapago));
    }//GEN-LAST:event_chk_30dActionPerformed

    private void chk_45dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_45dActionPerformed
        // TODO add your handling code here:
        condpago = 45;
        date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
        System.out.println(fechaem);
        
        //Ifs para calculo de la fecha de pago
        if(condpago == 30){
            fechapago = sumarDiasAFecha(fechaaux, 30);
        } else if(condpago == 45){
            fechapago = sumarDiasAFecha(fechaaux, 45);
        } else if(condpago == 60){
            fechapago = sumarDiasAFecha(fechaaux, 60);
        }
        lbl_limpago.setText(String.valueOf(fechapago));
    }//GEN-LAST:event_chk_45dActionPerformed

    private void chk_inmediatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_inmediatoActionPerformed
        // TODO add your handling code here:
        date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
        System.out.println(fechaem);
        fechapago = fechaaux;
        lbl_limpago.setText(String.valueOf(fechapago));

    }//GEN-LAST:event_chk_inmediatoActionPerformed

    private void chk_60dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_60dActionPerformed
        // TODO add your handling code here:
        condpago = 30;
        date = jdc_Fechaem.getDate();
        long d = date.getTime();
        java.sql.Date fechaem = new java.sql.Date(d);
        String fechapago = "";
        String fechaaux = fechaem.toString();
        System.out.println(fechaem);
        
        //Ifs para calculo de la fecha de pago
        if(condpago == 30){
            fechapago = sumarDiasAFecha(fechaaux, 30);
        } else if(condpago == 45){
            fechapago = sumarDiasAFecha(fechaaux, 45);
        } else if(condpago == 60){
            fechapago = sumarDiasAFecha(fechaaux, 60);
        }
        lbl_limpago.setText(String.valueOf(fechapago));
    }//GEN-LAST:event_chk_60dActionPerformed

    private void chk_30pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_30pActionPerformed
        // TODO add your handling code here:
        importetotal = Double.parseDouble(txt_total.getText());
        anticipo = importetotal * 0.30;
        saldoxpagar = importetotal - anticipo;
        lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
    }//GEN-LAST:event_chk_30pActionPerformed

    private void chk_40pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_40pActionPerformed
        // TODO add your handling code here:
        importetotal = Double.parseDouble(txt_total.getText());
        anticipo = importetotal * 0.40;
        saldoxpagar = importetotal - anticipo;
        lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
    }//GEN-LAST:event_chk_40pActionPerformed

    private void chk_50pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_50pActionPerformed
        // TODO add your handling code here:
        importetotal = Double.parseDouble(txt_total.getText());
        saldoxpagar = importetotal;
        lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
    }//GEN-LAST:event_chk_50pActionPerformed

    private void txt_numprovedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numprovedorKeyReleased
        // TODO add your handling code here:
         NumProveedor = Integer.parseInt(txt_numprovedor.getText());
        if(txt_numprovedor.getText() != ""){
         try {
            
            Statement stm=Conexion.getConexion().createStatement();
            //Llamado a la base de datos
            ResultSet rs=stm.executeQuery("Select Nombre from Proveedores where NumProveedor = '"+NumProveedor+"' ");
            if(rs.next()){
                lbl_NProveedor.setForeground(new Color(0,0,0));
                NombreProveedor = rs.getString("Nombre");
                lbl_NProveedor.setText(NombreProveedor.trim());
            } else {
                lbl_NProveedor.setForeground(Color.red);
                lbl_NProveedor.setText("Proveedor no existente");
            } 
                
        } catch (SQLException ex) {
            Logger.getLogger(Panel_CXP_Edit1.class.getName()).log(Level.SEVERE, null, ex);
        }
       } else {
           lbl_NProveedor.setText("");
        }
    }//GEN-LAST:event_txt_numprovedorKeyReleased

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void txt_numprovedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numprovedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numprovedorActionPerformed

    private void jPBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseEntered
       jPBuscar.setBackground(Color.white);
    }//GEN-LAST:event_jPBuscarMouseEntered

    private void jPBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseExited
       jPBuscar.setBackground(new java.awt.Color(140,140,140));
    }//GEN-LAST:event_jPBuscarMouseExited

    private void jPEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseEntered
      jPEditar.setBackground(Color.red);
    }//GEN-LAST:event_jPEditarMouseEntered

    private void jPEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseExited
     jPEditar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPEditarMouseExited

    private void jPDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseEntered
      jPDelete.setBackground(Color.red);
    }//GEN-LAST:event_jPDeleteMouseEntered

    private void jPDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseExited
      jPDelete.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPDeleteMouseExited

    private void jPAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseEntered
     jPAdd.setBackground(Color.red);
    }//GEN-LAST:event_jPAddMouseEntered

    private void jPAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseExited
     jPAdd.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPAddMouseExited

    private void pnl_limparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseEntered
     pnl_limpar.setBackground(Color.red);
    }//GEN-LAST:event_pnl_limparMouseEntered

    private void pnl_limparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseExited
     pnl_limpar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_pnl_limparMouseExited

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
     jPanel6.setBackground(Color.red);
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
     jPanel6.setBackground(new java.awt.Color(107, 190, 249)); 
    }//GEN-LAST:event_jPanel6MouseExited

    private void cmb_tipobusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_tipobusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_tipobusquedaActionPerformed

    private void chk_50p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_50p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_50p1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupAnticipo;
    private javax.swing.ButtonGroup btnGroupCondPago;
    private javax.swing.JCheckBox chk_30d;
    private javax.swing.JCheckBox chk_30p;
    private javax.swing.JCheckBox chk_40p;
    private javax.swing.JCheckBox chk_45d;
    private javax.swing.JCheckBox chk_50p;
    private javax.swing.JCheckBox chk_50p1;
    private javax.swing.JCheckBox chk_60d;
    private javax.swing.JCheckBox chk_inmediato;
    private javax.swing.JComboBox<String> cmb_tipobusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAdd;
    private javax.swing.JPanel jPBuscar;
    private javax.swing.JPanel jPDelete;
    private javax.swing.JPanel jPEditar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private com.toedter.calendar.JDateChooser jdc_Fechaem;
    private javax.swing.JLabel lbl_NProveedor;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_limpago;
    private javax.swing.JLabel lbl_saldoxpagar;
    private javax.swing.JPanel pnl_limpar;
    private javax.swing.JTextField txt_nombreproyecto;
    private javax.swing.JTextField txt_numfactura;
    private javax.swing.JTextField txt_numprovedor;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
