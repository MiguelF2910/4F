/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DBANC;

import Proveedores.*;
import ConexionBD.Conexion;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mike
 */
public class DBProveedores extends javax.swing.JPanel {

    /**
     * Creates new form Proveedores_Principal
     */

    
    public DBProveedores() {
        initComponents();
        lblnumero.setVisible(false);
        lblnumero.setVisible(false);
    }
    
    static int confirmacion;
    public String VALORPROV; //Ya sea lo que sea escrito en el txt de busqueda se generara el query 
    public String NOMPROV;
    public String RFCPROV;
    public String NOMCALLEPROV;
    public String NIPROV;
    public String NEPROV;
    public String ESTPROV;
    public String MUNPROV;
    public String COLPROV;
    public String CPPROV;    
    public String TELPROV;
    public String NUMCUENTAPROV;
    public String CLAVEPROV;
    public String NOMCOMERCIALPROV;
    private void BDP() 
    {
        int cont=0;
        SQLServerDataSource con = new SQLServerDataSource();
        con.setServerName("localhost");
        con.setDatabaseName("Gestion4F");
        con.setUser("4FAdmin"); //CAMBIO 
        con.setPassword("CuatroF");
        con.setPortNumber(1433);
        String v=txtsearch.getText();
        try
        {
            String url="select Nombre,\n" +
"	   Nombre_Calle,\n" +
"	   Num_Int,\n" +
"	   Num_Ext,\n" +
"	   Estado,\n" +
"	   Municipio,\n" +
"	   Colonia,\n" +
"	   CP,\n" +
"	   Telefono\n" +
"	   from DomProveedores join Proveedores on Proveedores.NumProveedor=DomProveedores.Num_Proveedor where NumProveedor='"+v+"'";
            Connection cn= con.getConnection();
            PreparedStatement ps= cn.prepareStatement(url);
            ResultSet rst=ps.executeQuery();
            while(rst.next())
            {

            NOMPROV=  rst.getString(1); 
            NOMCALLEPROV=rst.getString(2);
            NIPROV=rst.getString(3);
            NEPROV=rst.getString(4);
            ESTPROV=rst.getString(5);
            MUNPROV=rst.getString(6);
            COLPROV=rst.getString(7);
            CPPROV=rst.getString(8);
            TELPROV=rst.getString(9);
            
            lblDP.setVisible(true);
            lblV.setVisible(true);
            lblnumero.setVisible(true);
            
            lblNombre.setText(NOMPROV.trim());
            lblNombreCalle.setText(NOMCALLEPROV.trim());
            lblNI.setText(NIPROV.trim());
            lblNE.setText(NEPROV.trim());
            lblEstado.setText(ESTPROV.trim());
            lblMunicipio.setText(MUNPROV.trim());
            lblColonia.setText(COLPROV.trim());
            lblCP.setText(CPPROV.trim());
            lblnumero.setText(TELPROV.trim());
                        cont++;
            }
            if(cont>=1)
            {
                lblDP.setText("EXISTENTES");
                lblDP.setForeground(Color.GREEN);
            }
        }catch(SQLException ex)
        {
            Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    }
    private void BDB()
    {
        int cont=0;
        SQLServerDataSource con = new SQLServerDataSource();
        con.setServerName("localhost");
        con.setDatabaseName("Gestion4F");
        con.setUser("4FAdmin"); //CAMBIO 
        con.setPassword("CuatroF");
        con.setPortNumber(1433);
        String v=txtsearch.getText();
        try
        {
            String url="select NombreComercial, Clave, NumCuenta from BancoProveedores where NumProveedor='"+v+"'";
            Connection cn= con.getConnection();
            PreparedStatement ps= cn.prepareStatement(url);
            ResultSet rst=ps.executeQuery();
            while(rst.next())
            {
                cont++;
                NOMCOMERCIALPROV= rst.getString(1);
                CLAVEPROV=rst.getString(2);
                NUMCUENTAPROV=rst.getString(3);

                lblNComercial.setText(NOMCOMERCIALPROV.trim());
                lblNCuenta.setText(CLAVEPROV.trim());
                lblCinterbancaria.setText(NUMCUENTAPROV.trim());

            }
            if(cont>=1)
            {
                lblV.setText("EXISTENTES");
                lblV.setForeground(Color.GREEN);
            }
            else
            {
                lblV.setText("INEXISTENTES");
                lblV.setForeground(Color.RED);            
            
            }
        }catch(SQLException ex)
        {
            Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE,null,ex);
        }    
    
    }
    private void ADB(String valor,String valor1,String valor2,String valor3) throws SQLException
    {

        Statement stm=Conexion.getConexion().createStatement();
        ResultSet rs=stm.executeQuery("insert into BancoProveedores (NumProveedor,NumCuenta,Clave,NombreComercial) values('"+valor+"','"+valor1+"','"+valor2+"','"+valor3+"'); ");
    
    }
    private void MDB(String valor,String valor1,String valor2,String valor3) throws SQLException
    {

        Statement stm=Conexion.getConexion().createStatement();
        ResultSet rs=stm.executeQuery("update BancoProveedores set NumCuenta='"+valor1+"', Clave='"+valor2+"', NombreComercial='"+valor3+"' where NumProveedor='"+valor+"';");
    
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   /* public void AnadirDomicilio() throws SQLException{
        Statement stm=Conexion.getConexion().createStatement();
        ResultSet rs=stm.executeQuery("select top(1)  NumProveedor from Proveedores order by NumProveedor desc");
        if(rs.next()){
             IDPROV=rs.getString(1);
             System.out.println(IDPROV);
             JOptionPane.showMessageDialog(null,"Su identificador es:"+IDPROV);
             rs=stm.executeQuery("insert into DomProveedores values('"+Integer.parseInt(IDPROV)+"','"+NOMCALLEPROV+"','"+NEPROV+"','"+NIPROV+"','"+ESTPROV+"','"+MUNPROV+"','"+COLPROV+"','"+CPPROV+"','"+Integer.parseInt(TELPROV)+"','"+EMAILPROV+"')");
        }
    }*/
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblDP = new javax.swing.JLabel();
        lblnumero = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblNombreCalle = new javax.swing.JLabel();
        lblNI = new javax.swing.JLabel();
        lblNE = new javax.swing.JLabel();
        lblColonia = new javax.swing.JLabel();
        lblMunicipio = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblCP = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPEditar = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPAdd = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPDelete = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pnl_limpar = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        JPCB = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPBuscar = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblNCuenta = new javax.swing.JTextField();
        lblCinterbancaria = new javax.swing.JTextField();
        lblNComercial = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblV = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(950, 550));

        jPanel1.setBackground(new java.awt.Color(140, 140, 140));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ESTADO DE CUENTA ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, 20));

        jLabel28.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel28.setText("DATOS PRINCIPALES");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        jLabel33.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jLabel33.setText("TELEFONO");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel35.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jLabel35.setText("CODIGO POSTAL");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        jLabel36.setFont(new java.awt.Font("Antique Olive", 1, 18)); // NOI18N
        jLabel36.setText("EXISTENCIA DE DATOS:");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lblDP.setFont(new java.awt.Font("Antique Olive", 1, 18)); // NOI18N
        jPanel1.add(lblDP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 200, 20));

        lblnumero.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblnumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 160, 20));

        lblNombre.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 210, 20));

        lblNombreCalle.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblNombreCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, 20));

        lblNI.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 130, 20));

        lblNE.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblNE, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 120, 20));

        lblColonia.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 20));

        lblMunicipio.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblMunicipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 20));

        lblEstado.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 130, 20));

        lblCP.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jPanel1.add(lblCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 120, 20));

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
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPEditarLayout = new javax.swing.GroupLayout(jPEditar);
        jPEditar.setLayout(jPEditarLayout);
        jPEditarLayout.setHorizontalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEditarLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPEditarLayout.setVerticalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEditarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14))
        );

        jPanel5.add(jPEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

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

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-banco-seguro-50.png"))); // NOI18N
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPAddLayout = new javax.swing.GroupLayout(jPAdd);
        jPAdd.setLayout(jPAddLayout);
        jPAddLayout.setHorizontalGroup(
            jPAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPAddLayout.setVerticalGroup(
            jPAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPAddLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(jPAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 90, -1));

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

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-borrar-para-siempre-50.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPDeleteLayout = new javax.swing.GroupLayout(jPDelete);
        jPDelete.setLayout(jPDeleteLayout);
        jPDeleteLayout.setHorizontalGroup(
            jPDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(16, 16, 16))
        );
        jPDeleteLayout.setVerticalGroup(
            jPDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDeleteLayout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(jPDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 90, 60));

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

        jLabel22.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-escoba-50.png"))); // NOI18N
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnl_limparLayout = new javax.swing.GroupLayout(pnl_limpar);
        pnl_limpar.setLayout(pnl_limparLayout);
        pnl_limparLayout.setHorizontalGroup(
            pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_limparLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnl_limparLayout.setVerticalGroup(
            pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(pnl_limparLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_limparLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel5.add(pnl_limpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 100, 50));

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

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-tabla-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(44, 44, 44))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 130, 50));

        jLabel20.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel20.setText("MODIFICAR DATOS BANCARIOS ");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel21.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel21.setText("MOSTRAR INFORMACIÓN");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, -1, -1));

        jLabel23.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel23.setText("LIMPIAR FORMULARIO");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, -1, -1));

        jLabel24.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel24.setText("INGRESAR DATOS BANCARIOS");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, -1));

        jLabel25.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel25.setText("QUITAR DATOS BANCARIOS");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel18.setText("DATOS BANCARIOS ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        JPCB.setBackground(new java.awt.Color(140, 140, 140));

        jPanel4.setBackground(new java.awt.Color(140, 140, 140));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

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

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-búsqueda-50 (1).png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPBuscarLayout = new javax.swing.GroupLayout(jPBuscar);
        jPBuscar.setLayout(jPBuscarLayout);
        jPBuscarLayout.setHorizontalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPBuscarLayout.setVerticalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout JPCBLayout = new javax.swing.GroupLayout(JPCB);
        JPCB.setLayout(JPCBLayout);
        JPCBLayout.setHorizontalGroup(
            JPCBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPCBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JPCBLayout.setVerticalGroup(
            JPCBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPCBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPCBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JPCBLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(JPCBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))))
        );

        jPanel2.setBackground(new java.awt.Color(140, 140, 140));

        lblNCuenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        lblNCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblNCuentaActionPerformed(evt);
            }
        });
        lblNCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblNCuentaKeyTyped(evt);
            }
        });

        lblCinterbancaria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        lblCinterbancaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblCinterbancariaActionPerformed(evt);
            }
        });
        lblCinterbancaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblCinterbancariaKeyTyped(evt);
            }
        });

        lblNComercial.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        lblNComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblNComercialActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel26.setText("VERIFICACIÓN");

        jLabel27.setFont(new java.awt.Font("Antique Olive", 1, 18)); // NOI18N
        jLabel27.setText("EXISTENCIA DE DATOS:");

        jLabel29.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jLabel29.setText("NUMERO DE CUENTA");

        jLabel30.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jLabel30.setText("NOMBRE COMERCIAL");

        jLabel31.setFont(new java.awt.Font("Antique Olive", 1, 12)); // NOI18N
        jLabel31.setText("CLAVE INTERBANCARIA ");

        lblV.setFont(new java.awt.Font("Antique Olive", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(274, 274, 274))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblV, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(21, 21, 21)
                                .addComponent(lblNComercial))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNCuenta)
                                    .addComponent(lblCinterbancaria))))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(lblV, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNComercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCinterbancaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(JPCB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // Busqueda de informacion mediante ID, NOMBRE O RFC

       
    }//GEN-LAST:event_txtsearchActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased
      
    }//GEN-LAST:event_txtsearchKeyReleased

    private void jPBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseExited
        jPBuscar.setBackground(new java.awt.Color(140,140,140));
    }//GEN-LAST:event_jPBuscarMouseExited

    private void jPBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseEntered
        jPBuscar.setBackground(Color.WHITE);
    }//GEN-LAST:event_jPBuscarMouseEntered

    private void jPBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseClicked

        BDB();
        BDP(); /*      try {
        String url="select Nombre,\n" +
        "	   Nombre_Calle,\n" +
        "	   Num_Int,\n" +
        "	   Num_Ext,\n" +
        "	   Estado,\n" +
        "	   Municipio,\n" +
        "	   Colonia,\n" +
        "	   CP,\n" +
        "	   Telefono,\n" +
        "	   NumCuenta,\n" +
        "	   Clave,\n" +
        "	   NombreComercial\n" +
        "FROM DomProveedores\n" +
        "join BancoProveedores on BancoProveedores.NumProveedor=DomProveedores.Num_Proveedor \n" +
        "join Proveedores on Proveedores.NumProveedor=DomProveedores.Num_Proveedor	\n" +
        "where DomProveedores.Num_Proveedor= '"+valor1+"' or NombreComercial='"+valor1+"' or Nombre='"+valor1+"'";
        //String url = "select * FROM Proveedores inner join DomProveedores on Proveedores.NumProveedor=DomProveedores.Num_Proveedor where NumProveedor ="+txtsearch.getText();
        Connection cn = con.getConnection();
        PreparedStatement ps = cn.prepareStatement(url);
        ResultSet rst = ps.executeQuery();
        while(rst.next())
        {
        NOMPROV=  rst.getString(1);
        NOMCALLEPROV=rst.getString(2);
        NIPROV=rst.getString(3);
        NEPROV=rst.getString(4);
        ESTPROV=rst.getString(5);
        MUNPROV=rst.getString(6);
        COLPROV=rst.getString(7);
        CPPROV=rst.getString(8);
        TELPROV=rst.getString(9);
        NUMCUENTAPROV=rst.getString(10);
        CLAVEPROV=rst.getString(11);
        NOMCOMERCIALPROV= rst.getString(12);
        textoTelefono.setVisible(true);
        textoCP.setVisible(true);
        txtNombre.setText(NOMPROV.trim());
        txtNombreCalle.setText(NOMCALLEPROV.trim());
        txtNI.setText(NIPROV.trim());
        txtNE.setText(NEPROV.trim());
        txtColonia.setText(ESTPROV.trim());
        txtMunicipio.setText(MUNPROV.trim());
        txtEstado.setText(COLPROV.trim());
        txtCP.setText(CPPROV.trim());
        txtnumero.setText(TELPROV.trim());
        txtNComercial.setText(NOMCOMERCIALPROV.trim());
        txtCinterbancaria.setText(CLAVEPROV.trim());
        txtNCuenta.setText(NUMCUENTAPROV.trim());
        }
        }catch (SQLException ex) {
        Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_jPBuscarMouseClicked

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        jPanel6.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setBackground(Color.red);
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        try {
            // TODO add your handling code here:

            TGeneral tg = new TGeneral();
            tg.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel6MouseClicked

    private void pnl_limparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseExited
        pnl_limpar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_pnl_limparMouseExited

    private void pnl_limparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseEntered
        pnl_limpar.setBackground(Color.red);
    }//GEN-LAST:event_pnl_limparMouseEntered

    private void pnl_limparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseClicked
        // TODO add your handling code here:
        limpiar();
        //txtID.setVisible(false);
        //lbl_id.setVisible(false);
    }//GEN-LAST:event_pnl_limparMouseClicked

    private void jPDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseExited
        jPDelete.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPDeleteMouseExited

    private void jPDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseEntered
        jPDelete.setBackground(Color.red);
    }//GEN-LAST:event_jPDeleteMouseEntered

    private void jPDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPDeleteMouseClicked
        lblNComercial.setText("");
        lblCinterbancaria.setText("");
        lblNI.setText("");
        
        NUMCUENTAPROV=lblNComercial.getText();
        CLAVEPROV=lblCinterbancaria.getText();
        NOMCOMERCIALPROV=lblNI.getText();
  try{
        Statement stm=Conexion.getConexion().createStatement();
        String valor=JOptionPane.showInputDialog("Ingrese el numero de proveedor que desea agregar los datos bancarios:");
        ResultSet rs=stm.executeQuery("select * from Proveedores where NumProveedor='"+valor+"' or Nombre='"+valor+"';");
        if(rs.next()==true)
        {
            MDB(valor,NUMCUENTAPROV,CLAVEPROV,NOMCOMERCIALPROV);
            JOptionPane.showMessageDialog(null,"Datos removidos correctamente");
            limpiar();
        }
        }catch(SQLException ex)
        {
         Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPDeleteMouseClicked

    private void jPAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseExited
        jPAdd.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPAddMouseExited

    private void jPAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseEntered
        jPAdd.setBackground(Color.red);
    }//GEN-LAST:event_jPAddMouseEntered

    private void jPAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseClicked
        //Agregar datos
        NUMCUENTAPROV=lblNComercial.getText();
        CLAVEPROV=lblCinterbancaria.getText();
        NOMCOMERCIALPROV=lblNI.getText();
        try{
        Statement stm=Conexion.getConexion().createStatement();
        String valor=JOptionPane.showInputDialog("Ingrese el numero de proveedor que desea quitar los datos bancarios:");
        
        //    Statement stm=Conexion.getConexion().createStatement();
        ResultSet rs=stm.executeQuery("select * from Proveedores where NumProveedor='"+valor+"' or Nombre='"+valor+"';");
        if(rs.next()==true)
        {
            ADB(valor,NUMCUENTAPROV,CLAVEPROV,NOMCOMERCIALPROV);
            limpiar();
            JOptionPane.showMessageDialog(null,"Datos modificador correctamente");
        }
        }catch(SQLException ex)
        {
         Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jPAddMouseClicked

    private void jPEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseExited
        jPEditar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPEditarMouseExited

    private void jPEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseEntered
        jPEditar.setBackground(Color.red);
    }//GEN-LAST:event_jPEditarMouseEntered

    private void jPEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseClicked
        NUMCUENTAPROV=lblNComercial.getText();
        CLAVEPROV=lblCinterbancaria.getText();
        NOMCOMERCIALPROV=lblNI.getText();
        try{
        Statement stm=Conexion.getConexion().createStatement();
        String valor=JOptionPane.showInputDialog("Ingrese el numero de proveedor que desea modificar los datos bancarios:");
        
        //    Statement stm=Conexion.getConexion().createStatement();
        ResultSet rs=stm.executeQuery("select * from Proveedores where NumProveedor='"+valor+"' or Nombre='"+valor+"';");
        if(rs.next()==true)
        {
            MDB(valor,NUMCUENTAPROV,CLAVEPROV,NOMCOMERCIALPROV);
            limpiar();
            JOptionPane.showMessageDialog(null,"Datos modificador correctamente");
        }
        }catch(SQLException ex)
        {
         Logger.getLogger(DBProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_jPEditarMouseClicked

    private void lblCinterbancariaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblCinterbancariaKeyTyped
        if(lblCinterbancaria.getText().length() >= 18)
        {
            evt.consume();
        }
    }//GEN-LAST:event_lblCinterbancariaKeyTyped

    private void lblCinterbancariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCinterbancariaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCinterbancariaActionPerformed

    private void lblNCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblNCuentaKeyTyped
        if(lblNCuenta.getText().length() >= 10)
        {
            evt.consume();
        }
    }//GEN-LAST:event_lblNCuentaKeyTyped

    private void lblNCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNCuentaActionPerformed

    private void lblNComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNComercialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNComercialActionPerformed
    public void limpiar()
    {
    lblDP.setText("");
    lblV.setText("");
        
    lblNComercial.setText("");
    lblNCuenta.setText("");
    lblCinterbancaria.setText("");
    
    lblNombre.setText("");
    lblNombreCalle.setText("");
    lblMunicipio.setText("");
    lblColonia.setText("");
    lblEstado.setText("");
    lblNI.setText("");
    lblNE.setText("");
    lblCP.setText("");
    lblnumero.setText("");
     
    txtsearch.setText("");

    lblnumero.setVisible(false);
    lblnumero.setVisible(false);
    lblDP.setVisible(false);
    lblV.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JPanel jPAdd;
    private javax.swing.JPanel jPBuscar;
    private javax.swing.JPanel jPDelete;
    private javax.swing.JPanel jPEditar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblCP;
    private javax.swing.JTextField lblCinterbancaria;
    private javax.swing.JLabel lblColonia;
    private javax.swing.JLabel lblDP;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JTextField lblNComercial;
    private javax.swing.JTextField lblNCuenta;
    private javax.swing.JLabel lblNE;
    private javax.swing.JLabel lblNI;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreCalle;
    private javax.swing.JLabel lblV;
    private javax.swing.JLabel lblnumero;
    private javax.swing.JPanel pnl_limpar;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
