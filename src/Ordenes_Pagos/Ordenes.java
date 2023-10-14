/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Ordenes_Pagos;

import HISTORICOS.*;
import ConexionBD.Conexion;
import MU.MAdministrador;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Proveedores.Vista;
import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import Proveedores.*;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
/**
 *
 * @author Johan Mendoza
 */
public class Ordenes extends javax.swing.JFrame {

    /**
     * Creates new form TablaGeneral_1
     */
    static String usuario;
    String SIT="";
    int p,dif, confirmacion;
    int xMouse, yMouse; 
    DefaultTableModel dtm=new DefaultTableModel();
    DefaultTableModel dtm1=new DefaultTableModel();    
    public Ordenes(String usuario) throws SQLException {
        initComponents();
        this.usuario=usuario;
        this.setLocationRelativeTo(null);
        //tabla para las operaciones
        String[] Titulos=new String []{"FACTURA","ORDEN DE PAGO","MONTO A PAGAR","SOLICITANTE","SITUACIÓN","SELECCIONAR"};
        dtm.setColumnIdentifiers(Titulos);
        jDatos1.setModel(dtm);
        
       //tabla para las operaciones 
        String[] Titulos2=new String []{"NÚMERO DE ORDEN DE PAGO","SITUACIÓN"};
        dtm1.setColumnIdentifiers(Titulos2);
        jDatos2.setModel(dtm1);
               
        ct();
        ct1();
    }
         public void addCheckBox(int column, JTable table)
    {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public boolean IsSelected(int row, int column, JTable table)
    {    
        boolean a;
        a=table.getValueAt(row, column) != null;
        System.out.println("valor boleano"+a);
        return a;  
        
    }
    private void ct() throws SQLException 
    {

        java.sql.Statement stm=Conexion.getConexion().createStatement();
        ResultSet rst=stm.executeQuery(" select NumFactura,NumPartida,Saldo,Solicitante,SitOrdenPago from Pagos inner join CXP on CXP.NumFactura=Pagos.Num_Factura where SitOrdenPago='0';");   
       
        while(rst.next())
        {
            p=rst.getInt(5);
            System.out.println(p);
            if(p==2) 
            {
                SIT="NO APROBADO";
            }
            if(p==1) 
            {
                SIT="APROBADO";
            }
            if(p==0)
            { 
                SIT="PENDIENTE";
            }  
            
            dtm.addRow(new Object[]{
            rst.getString(1),
            rst.getString(2),
            rst.getString(3),
            rst.getString(4),
            SIT
            });
            
        }
       addCheckBox(5,jDatos1);
    }
    private void ct1() throws SQLException
    {
        java.sql.Statement stm=Conexion.getConexion().createStatement();
        ResultSet rst=stm.executeQuery(" select NumPartida, SitOrdenPago from Pagos inner join CXP on CXP.NumFactura=Pagos.Num_Factura where SitOrdenPago='1' and SitOrdenPago='2'");
   
       
        while(rst.next())
        {
            p=rst.getInt(2);
            System.out.println(p);
            if(p==2) 
            {
                SIT="NO APROBADO";
            }
            if(p==1) 
            {
                SIT="APROBADO";
            }
            if(p==0)
            { 
                SIT="PENDIENTE";
            }  
            
            dtm.addRow(new Object[]{
            rst.getString(1),
            rst.getString(2),
            rst.getString(3),
            rst.getString(4),
            SIT
            });
            
        }   
    }   
    
    public void AutorizarOrden(String numfact) throws SQLException{
        confirmacion = JOptionPane.showConfirmDialog(null,"¿Está seguro que desea autorizar el pago correspondiente a la factura: " + numfact.trim() + " ?");
        
        if(confirmacion == 0){
            JOptionPane.showMessageDialog(null,"Ha autorizado esta factura con éxito");
            java.sql.Statement stm=Conexion.getConexion().createStatement();
            stm.executeQuery("UPDATE Pagos set SitOrdenPago = 1 WHERE Num_Factura = '"+numfact+"'");
        } else {
            java.sql.Statement stm=Conexion.getConexion().createStatement();
            stm.executeQuery("UPDATE Pagos set SitOrdenPago = 2 WHERE Num_Factura = '"+numfact+"'");
            JOptionPane.showMessageDialog(null,"Usted ha rechazado la solicitud de pago");
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbtnclose = new javax.swing.JButton();
        jbtnclose2 = new javax.swing.JButton();
        jBACK2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jBACK4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDatos2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jBACK5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jBACK6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDatos1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(107, 190, 249));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(140, 140, 140));
        jPanel2.setForeground(new java.awt.Color(6, 187, 233));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(6, 187, 233));
        jLabel1.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SITUACIÓN DE ORDENES DE PAGO");

        jbtnclose.setBackground(new java.awt.Color(0, 0, 0));
        jbtnclose.setFont(new java.awt.Font("Rockwell Condensed", 0, 14)); // NOI18N
        jbtnclose.setForeground(new java.awt.Color(255, 255, 255));
        jbtnclose.setText("X");
        jbtnclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtncloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtncloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtncloseMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbtncloseMousePressed(evt);
            }
        });
        jbtnclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtncloseActionPerformed(evt);
            }
        });

        jbtnclose2.setBackground(new java.awt.Color(0, 0, 0));
        jbtnclose2.setFont(new java.awt.Font("Rockwell Condensed", 0, 14)); // NOI18N
        jbtnclose2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnclose2.setText("-");
        jbtnclose2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnclose2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnclose2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtnclose2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtnclose2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbtnclose2MousePressed(evt);
            }
        });
        jbtnclose2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnclose2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 824, Short.MAX_VALUE)
                .addComponent(jbtnclose2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnclose, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(230, 230, 230))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnclose, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnclose2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 70));

        jBACK2.setBackground(new java.awt.Color(107, 190, 249));
        jBACK2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBACK2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBACK2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBACK2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBACK2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBACK2MouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel9.setText("AUTORIZAR ORDEN ");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jBACK2Layout = new javax.swing.GroupLayout(jBACK2);
        jBACK2.setLayout(jBACK2Layout);
        jBACK2Layout.setHorizontalGroup(
            jBACK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
            .addGroup(jBACK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jBACK2Layout.setVerticalGroup(
            jBACK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
            .addGroup(jBACK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK2Layout.createSequentialGroup()
                    .addGap(0, 3, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(0, 4, Short.MAX_VALUE)))
        );

        getContentPane().add(jBACK2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(140, 140, 140));

        jBACK4.setBackground(new java.awt.Color(107, 190, 249));
        jBACK4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBACK4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBACK4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBACK4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBACK4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBACK4MouseExited(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel11.setText("DESHACER ORDEN");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jBACK4Layout = new javax.swing.GroupLayout(jBACK4);
        jBACK4.setLayout(jBACK4Layout);
        jBACK4Layout.setHorizontalGroup(
            jBACK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBACK4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBACK4Layout.setVerticalGroup(
            jBACK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBACK4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDatos2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jDatos2.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jDatos2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "-", "-", "-"
            }
        ));
        jDatos2.setGridColor(new java.awt.Color(0, 0, 0));
        jDatos2.setSelectionBackground(new java.awt.Color(107, 190, 249));
        jDatos2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jDatos2.setShowGrid(false);
        jDatos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDatos2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jDatos2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jBACK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBACK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(140, 140, 140));

        jBACK5.setBackground(new java.awt.Color(107, 190, 249));
        jBACK5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBACK5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBACK5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBACK5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBACK5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBACK5MouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel12.setText("RECHAZAR ORDEN ");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jBACK5Layout = new javax.swing.GroupLayout(jBACK5);
        jBACK5.setLayout(jBACK5Layout);
        jBACK5Layout.setHorizontalGroup(
            jBACK5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
            .addGroup(jBACK5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK5Layout.createSequentialGroup()
                    .addGap(0, 8, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );
        jBACK5Layout.setVerticalGroup(
            jBACK5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jBACK5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK5Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        jBACK6.setBackground(new java.awt.Color(107, 190, 249));
        jBACK6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBACK6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBACK6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBACK6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBACK6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBACK6MouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel13.setText("AUTORIZAR ORDEN ");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jBACK6Layout = new javax.swing.GroupLayout(jBACK6);
        jBACK6.setLayout(jBACK6Layout);
        jBACK6Layout.setHorizontalGroup(
            jBACK6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
            .addGroup(jBACK6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK6Layout.createSequentialGroup()
                    .addGap(0, 9, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );
        jBACK6Layout.setVerticalGroup(
            jBACK6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
            .addGroup(jBACK6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBACK6Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        jDatos1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jDatos1.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jDatos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "-", "-", "-"
            }
        ));
        jDatos1.setGridColor(new java.awt.Color(0, 0, 0));
        jDatos1.setSelectionBackground(new java.awt.Color(107, 190, 249));
        jDatos1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jDatos1.setShowGrid(false);
        jDatos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDatos1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jDatos1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jBACK6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBACK5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBACK6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBACK5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 940, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtncloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtncloseMouseClicked
     /*   MPE MD=new MPE();
        System.exit(0);
        MD.setVisible(false);*/
        this.setVisible(false);
        MAdministrador ma=new MAdministrador(usuario);
        ma.setVisible(true);
    }//GEN-LAST:event_jbtncloseMouseClicked

    private void jbtncloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtncloseMouseEntered
        jbtnclose.setBackground(Color.red);
    }//GEN-LAST:event_jbtncloseMouseEntered

    private void jbtncloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtncloseMouseExited
        jbtnclose.setBackground(Color.black);
    }//GEN-LAST:event_jbtncloseMouseExited

    private void jbtncloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtncloseMousePressed

    }//GEN-LAST:event_jbtncloseMousePressed

    private void jbtncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtncloseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtncloseActionPerformed

    private void jbtnclose2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnclose2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnclose2MouseClicked

    private void jbtnclose2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnclose2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnclose2MouseEntered

    private void jbtnclose2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnclose2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnclose2MouseExited

    private void jbtnclose2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnclose2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnclose2MousePressed

    private void jbtnclose2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnclose2ActionPerformed
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jbtnclose2ActionPerformed

    private void jDatos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDatos2MouseClicked

       /* Variables var= new Variables();
        int fs=jDatos.getSelectedRow();
        if(fs>=0)
        {//obtencion mediante un objeto
            String obj[]= new String[3];
            obj[1]=jDatos.getValueAt(fs, 0).toString();
            var.setGetTexto(obj[1]);
            /*sacar(obj[1]);
            //ingresados a un txt
            a=obj[1];
            c=Integer.parseInt(a);
            JOptionPane.showMessageDialog(null,"dato obtenido ID-"+c);
            var.setIDselected(c);
            JOptionPane.showMessageDialog(null,"dato obtenido ID-"+var.getIDselected());
            //////////// aqui es donde se termino de comentar anteriormente 

            Vista V1 = null;
            try {
                V1 = new Vista();
            } catch (SQLException ex) {
                Logger.getLogger(TGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
            V1.setVisible(true);

        }*/
    }//GEN-LAST:event_jDatos2MouseClicked

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        int x= evt.getXOnScreen();
        int y= evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        xMouse= evt.getX();
        yMouse= evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jBACK2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jBACK2MouseClicked

    private void jBACK2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBACK2MouseEntered

    private void jBACK2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jBACK2MouseExited

    private void jBACK4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jBACK4MouseClicked

    private void jBACK4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK4MouseEntered
    jBACK4.setBackground(Color.red);
    }//GEN-LAST:event_jBACK4MouseEntered

    private void jBACK4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK4MouseExited
    jBACK4.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jBACK4MouseExited

    private void jBACK5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jBACK5MouseClicked

    private void jBACK5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK5MouseEntered
    jBACK5.setBackground(Color.red);
    }//GEN-LAST:event_jBACK5MouseEntered

    private void jBACK5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK5MouseExited
    jBACK5.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jBACK5MouseExited

    private void jBACK6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK6MouseClicked
       String numfact;
        for(int i = 0; i < dtm.getRowCount(); i++){
            if(IsSelected(i, 5, jDatos1)==true)
            {                        
                    numfact = dtm.getValueAt(i,0).toString();
                try {
                    //System.out.println(numfact);
                    AutorizarOrden(numfact);
                } catch (SQLException ex) {
                    Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jBACK6MouseClicked

    private void jBACK6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK6MouseEntered
    jBACK6.setBackground(Color.red);
    }//GEN-LAST:event_jBACK6MouseEntered

    private void jBACK6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBACK6MouseExited
    jBACK6.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jBACK6MouseExited

    private void jDatos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDatos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jDatos1MouseClicked

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
            java.util.logging.Logger.getLogger(Ordenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ordenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ordenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ordenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Ordenes(usuario).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jBACK2;
    private javax.swing.JPanel jBACK4;
    private javax.swing.JPanel jBACK5;
    private javax.swing.JPanel jBACK6;
    protected javax.swing.JTable jDatos1;
    protected javax.swing.JTable jDatos2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnclose;
    private javax.swing.JButton jbtnclose2;
    // End of variables declaration//GEN-END:variables
}
