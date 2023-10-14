/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ModuloPagos;


import ConexionBD.Conexion;
import Log.Login;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Johan Mendoza
 */
public class Panel_Pagos_Edit extends javax.swing.JPanel {
    static String usuario;
    String numfact;
    static int NumProveedor, confirmacion, Estatus, condpago;
    static double importetotal, anticipo, saldoxpagar, cantidadapagar,monto;
    static Date date, datesum;
    static String NombreProveedor, NombreProyecto, NumFacturaAnterior, NumFactura, Solicitante, Concepto, Autorizante;
    DefaultTableModel dtm = new DefaultTableModel();
    
        
    /**
     * Creates new form Panel_CXP
     */
    public Panel_Pagos_Edit(String usuario) throws SQLException {
        this.usuario = usuario;
        initComponents();
        String[] a=new String []{"Número de Partida", "Número de Factura", "Proveedor","Fecha de Emisión","Fecha Límite de Pago","Estatus","Projecto","Concepto","Importe","Anticipo","Saldo por Pagar","Solicito","Seleccionar"};
        dtm.setColumnIdentifiers(a);
        jDatos.setModel(dtm);
        cmb_autorizar.setModel(obtenerUsuarios());
        //addCheckBox(0, jDatos);
        ct();
    }
    
    public int VERIFICARMONTO(String NumFact,float monto) throws SQLException 
    {
            Double montototal=0.0;
            Double anticipo=0.0;
            Statement stm = Conexion.getConexion().createStatement();
            ResultSet rs = stm.executeQuery("select Importe, Anticipo from CXP where NumFactura='"+NumFact+"';");
            while(rs.next())
            {
             montototal=rs.getDouble(1);
             anticipo=rs.getDouble(2);
             
             System.out.println("TOTAL:"+montototal);
             System.out.println("Anticipo"+anticipo);
             System.out.println("pago"+monto);
            }
            montototal=montototal-(monto+anticipo);
            System.out.println(montototal);
            if(montototal>=0)
            {
                    return 0;
            }
            else
                    return 1;
    }
    public int VERIFICARUSUARIO(int numpartida) throws SQLServerException, SQLException
    {
        int valor=0;
    String vsit1="1";
    String vsit0="0";
    
    //String solicitante="";

    Statement stm = Conexion.getConexion().createStatement();
    ResultSet rs = stm.executeQuery("select solicitante, SitOrdenPago from Pagos where NumPartida='"+numpartida+"' and Solicitante='"+usuario+"';");
    if(rs.next())
        {
             String solicitante=rs.getString(1);
             Integer sitordenpago = rs.getInt(2);
             System.out.println("SOLICITO"+solicitante);
             System.out.println("SITUACION DE ORDEN DE PAGO"+sitordenpago);
             
            /* System.out.println("TOTAL:"+montototal);
             System.out.println("Anticipo"+anticipo);
             System.out.println("pago"+monto);*/ 
              System.out.println("Entrando a la condicion");
   /* if(usuario.equals(solicitante))
    {
        System.out.println("condicion de usuario");
        //if(vsit1.equals(sitordenpago))
        if(sitordenpago==1)
        {
        System.out.println("vas a pagar");
        valor= 1; //tener el permiso 
        }
        if(sitordenpago==0)
        {
        System.out.println("vas a pagar");
        valor= 0; //no tener permisos
        }
    }*/     valor=1;
        }
    else
        {
            valor= -1; //no encontrar nada
        }
        System.out.println(valor);
        return valor ;
        
    }
    public int VERIFICARPERMISO(int numpartida) throws SQLException
    {
        Integer sitordenpago; 
        int val=0;
        Statement stm = Conexion.getConexion().createStatement();
        ResultSet rs = stm.executeQuery("select SitOrdenPago from Pagos where NumPartida='"+numpartida+"';");
        if(rs.next())
            {
            sitordenpago = rs.getInt(1);
            val=sitordenpago;
            }
        else 
            {
            //val=-1;
            } 
    //return 0;
             System.out.println(val);
             return val;
    }
    public int VERIFICARUSUARIO(String usuario) throws SQLServerException, SQLException
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
                  return 1; //aprobado 
              }
              //usuario con persmisos 
              if(rol.equals("3"))
              {
                  return 0;
              }
              //usuario sin permisos 
              if(rol.equals("2"))
              {
                  return 2;
              }
              
          
          }
          return -1;
    
    }
    public int VERIFICAROP(String valor) throws SQLException
    {
              Statement stm = Conexion.getConexion().createStatement();
            ResultSet rs = stm.executeQuery("select * from Pagos where NumPartida='"+valor+"';");
            if(rs.next()){
                    return 1;
            } else {
                    return 0;
            }  
    }
    public void GENOP(String NumFactura, int Estatus, String Concepto, String Solicitante,String Autorizante, int val, float monto) throws SQLException
    {
            Statement stm = Conexion.getConexion().createStatement();
            ResultSet rs = stm.executeQuery("Insert into Pagos (Num_Factura, Estatus, Concepto, Solicitante, Autorizo, SitOrdenPago,Monto) values ('"+NumFactura.trim()+"', '"+Estatus+"', '"+Concepto.trim()+"', '"+Solicitante.trim()+"','"+Autorizante.trim()+"','"+val+"','"+monto+"')");
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Orden de Pago generada con éxito");
                stm.close();
            } else {
                JOptionPane.showMessageDialog(null,"No se pudo generar su orden de pago");
            }
    
    }
    
    public void GENH(String mensaje1, String mensaje2) throws SQLException
    {
    Login log= new Login();
    //String mensaje3=log.u;
    
    Date fecha = new Date();
    long flong = fecha.getTime();
    java.sql.Date fechaing = new java.sql.Date(flong);
        
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    
    Statement stm=Conexion.getConexion().createStatement();
    
    ResultSet rs=stm.executeQuery("insert HG (NOMUSUARIO,FECHA,ACCION,NG,HORA,MOTIVO) values ('"+usuario+"','"+fechaing+"','"+mensaje1+"','"+mensaje2+"','"+dateFormat.format(date)+"','-');");
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

    private void ct() throws SQLException
    {
        Statement stm=Conexion.getConexion().createStatement();
        ResultSet rst=stm.executeQuery("SELECT NumPartida, NumFactura, NomProveedor, FEmision, FPago, Estatus, NombreProj, Concepto, Importe, Anticipo, Saldo, Solicitante FROM CXP inner join Pagos on CXP.NumFactura=Pagos.Num_Factura");
        while(rst.next())
        {
            dtm.addRow(new Object[]{
            
            rst.getInt(1),
            rst.getString(2),
            rst.getString(3),
            rst.getDate(4),
            rst.getDate(5),
            rst.getInt(6),
            rst.getString(7),
            rst.getString(8),
            rst.getFloat(9),
            rst.getFloat(10),
            rst.getFloat(11),
            rst.getString(12) //El numero de columna aca es 12 por que empieza del 1, en cambio en el checkbox también es 12 por que comienza la cuenta desde el 0.
            });
            
        }
        addCheckBox(12, jDatos);
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
    public DefaultComboBoxModel obtenerUsuarios(){
        DefaultComboBoxModel cmbmodelo = new DefaultComboBoxModel();
        try {
            Statement stm=Conexion.getConexion().createStatement();
            ResultSet rst = stm.executeQuery("SELECT Nombre_Usuario from Usuarios where Rol_Usuario = 1");
            while(rst.next()){
                cmbmodelo.addElement(rst.getString(1));
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cmbmodelo;
    }
    
    public void PagarFactura(String numfactura) throws SQLException{
        double nuevototal;
        int continuar = 0;
        Date fecha = new Date();
        long flong = fecha.getTime();
        java.sql.Date fechaliq = new java.sql.Date(flong);
        java.sql.Statement stm=Conexion.getConexion().createStatement();
        ResultSet rst=stm.executeQuery("SELECT NomProveedor, Importe, Saldo, Concepto, Solicitante, FPago, NombreProj, Monto FROM CXP inner join Pagos on CXP.NumFactura=Pagos.Num_Factura WHERE NumFactura ='"+numfactura+"'");
        while(rst.next())
        {
          NombreProveedor = rst.getString(1);
          importetotal = rst.getFloat(2);
          saldoxpagar = rst.getFloat(3); //este es nuestro total 
          Concepto = rst.getString(4);
          Solicitante = rst.getString(5);
          date = rst.getDate(6);
          NombreProyecto = rst.getString(7);
          cantidadapagar=rst.getDouble(8);
          
            try{                
        confirmacion=JOptionPane.showConfirmDialog(null,"Usted ha decidido pagar la factura:"+numfactura + " Que tiene los siguientes datos:"
                +"\nA Nombre del Proveedor:"+NombreProveedor
                +"\nCon importe total de:"+importetotal
                //+"\nCon importe total de:"+monto
                +"\nCon un Saldo Pendiente de:"+saldoxpagar
                +"\nCon el concepto:"+Concepto
                +"\nFactura Solicitada por:"+Solicitante
                +"\nCon una fecha límite de:"+date
                +"\nPerteneciente al Proyecto:"+NombreProyecto
                +"\n¿Está seguro que desea pagar esta factura?");
               
                if(confirmacion == 0){
                    do{
                    //cantidadapagar = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la cantidad que desea pagar","Cantidad a Pagar"));
                    nuevototal = saldoxpagar - cantidadapagar;
                    
                    if(nuevototal > 0){
                        JOptionPane.showMessageDialog(null,"La cantidad que falta por pagar es: " + nuevototal);
                         stm.executeQuery("UPDATE CXP set Saldo ='"+nuevototal+"', Estado = 2 where NumFactura = '"+numfactura+"'");
                         stm.executeQuery("UPDATE Pagos set Estatus = 2");
                         continuar = 1;
                    } else if(nuevototal == 0){
                        JOptionPane.showMessageDialog(null, """
                                                            La factura se ha liquidado satisfactoraimente.
                                                            No se olvide de reflejar los cambios en el banco""");
                         stm.executeQuery("UPDATE CXP set Saldo ='"+nuevototal+"', Estado = 1 where NumFactura = '"+numfactura+"'");
                         stm.executeQuery("UPDATE Pagos set Estatus = 1, FechaLiquidacion = '"+fechaliq+"'");
                         continuar = 1;
                     
                    } else {
                        JOptionPane.showMessageDialog(null,"Por favor, ingrese una cantidad válida");
                    }
                    }while(continuar != 1);
                }
          }catch(SQLException ex){
              
          }
    }
    }
    
    private int consultarusuario()
    {
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
                  return 1;
                  
              }
              //usuario con persmisos 
              if(rol.equals("3"))
              {
                 return 3;
              }
              //usuario sin permisos 
              if(rol.equals("2"))
              {
                  return 2;
              } 
          }
        }
    catch(SQLException ex )        
    {
     JOptionPane.showMessageDialog(null,ex.toString());
    }
    return 0;
 }
   /* public void PF()
    {
        String numfact = "";
        int value;
        for(int i = 0; i <= dtm.getRowCount(); i++){
            if(IsSelected(i, 12, jDatos)){
                try 
                {             
                    numfact = dtm.getValueAt(i, 0).toString();
                    System.out.println(numfact);
                    
                    value=VERIFICARUSUARIO(numfact);
                    if(value==1)
                    {
                    System.out.println(numfact);
                    PagarFactura(numfact);                   
                    
                    }
                    else
                    {
                    JOptionPane.showMessageDialog(null,"La operación no puede realizarse");
                    
                    }    
                    

                } catch (SQLException ex) {
                    Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }*/
    private int consulta()
    {
     //Statement stm=Conexion.getConexion().createStatement();
    
   // ResultSet rs=stm.executeQuery("insert HG (NOMUSUARIO,FECHA,ACCION,NG,HORA,MOTIVO) values ('"+usuario+"','"+fechaing+"','"+mensaje1+"','"+mensaje2+"','"+dateFormat.format(date)+"','-');");
    return 0;
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
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtsearch = new javax.swing.JTextField();
        cmb_tipobusqueda = new javax.swing.JComboBox<>();
        lbl_aviso = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPBuscar = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nombreproyecto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_concepto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_NProveedor = new javax.swing.JLabel();
        lbl_fliquidacion = new javax.swing.JLabel();
        txt_numpartida = new javax.swing.JTextField();
        txt_nomsolicitante = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_numproveedor = new javax.swing.JTextField();
        lbl_estatustitulo = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbl_estatus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        chk_cancelado = new javax.swing.JCheckBox();
        txt_total = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        chk_30d = new javax.swing.JCheckBox();
        chk_45d = new javax.swing.JCheckBox();
        chk_60d = new javax.swing.JCheckBox();
        chk_inmediato = new javax.swing.JCheckBox();
        lbl_saldoxpagar = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_numfactura = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_total3 = new javax.swing.JTextField();
        txt_anticipo = new javax.swing.JTextField();
        lbl_femision = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cmb_autorizar = new javax.swing.JComboBox<>();
        lbl_limpago = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDatos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPEditar = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPAdd = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        pnl_limpar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(950, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(950, 750));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel18.setText("GENERAR ORDENES DE PAGO");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel18)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 13, -1, -1));

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

        cmb_tipobusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Búsqueda por Número de Partida", "Búsqueda por Número de Proveedor", "Búsqueda por Fecha de Emisión", "Búsqueda por Fecha Límite de Pago" }));

        lbl_aviso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_aviso.setForeground(new java.awt.Color(255, 0, 0));
        lbl_aviso.setText("Por favor, ingrese un número de factura para generar su pago.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsearch)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cmb_tipobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lbl_aviso, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 79, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_tipobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_aviso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 51, 850, 70));

        jPanel2.setBackground(new java.awt.Color(140, 140, 140));

        jPBuscar.setBackground(new java.awt.Color(140, 140, 140));
        jPBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPBuscarMouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-búsqueda-50 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPBuscarLayout = new javax.swing.GroupLayout(jPBuscar);
        jPBuscar.setLayout(jPBuscarLayout);
        jPBuscarLayout.setHorizontalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPBuscarLayout.setVerticalGroup(
            jPBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBuscarLayout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 51, -1, 70));

        jPanel1.setBackground(new java.awt.Color(140, 140, 140));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_id.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id.setText("Número de Partida");
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

        txt_nombreproyecto.setEditable(false);
        txt_nombreproyecto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_nombreproyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreproyectoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_nombreproyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 160, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Condiciones de Pago");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, 120, -1));

        txt_concepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_concepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_conceptoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_concepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 460, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Anticipo");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 50, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Número de Proveedor");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 130, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Autorizó");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 60, -1));

        lbl_NProveedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_NProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_NProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 160, 20));

        lbl_fliquidacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_fliquidacion.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_fliquidacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 120, 20));
        jPanel1.add(txt_numpartida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, -1));

        txt_nomsolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomsolicitanteActionPerformed(evt);
            }
        });
        txt_nomsolicitante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nomsolicitanteKeyReleased(evt);
            }
        });
        jPanel1.add(txt_nomsolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 280, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Número de Factura");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 130, -1));

        txt_numproveedor.setEditable(false);
        txt_numproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numproveedorActionPerformed(evt);
            }
        });
        txt_numproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numproveedorKeyReleased(evt);
            }
        });
        jPanel1.add(txt_numproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, 170, -1));

        lbl_estatustitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_estatustitulo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_estatustitulo.setText("Estatus");
        jPanel1.add(lbl_estatustitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 80, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Importe Total");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 80, -1));

        lbl_estatus.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel1.add(lbl_estatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 220, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha Límite de Pago");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 210, -1));

        chk_cancelado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_cancelado.setForeground(new java.awt.Color(255, 0, 0));
        chk_cancelado.setText("Factura Cancelada");
        chk_cancelado.setEnabled(false);
        chk_cancelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_canceladoActionPerformed(evt);
            }
        });
        jPanel1.add(chk_cancelado, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 20, -1));

        txt_total.setEditable(false);
        txt_total.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_totalKeyTyped(evt);
            }
        });
        jPanel1.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 170, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Fecha de Liquidación");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 120, -1));

        btnGroupCondPago.add(chk_30d);
        chk_30d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_30d.setText("30 días");
        chk_30d.setEnabled(false);
        chk_30d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_30dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_30d, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 20, -1));

        btnGroupCondPago.add(chk_45d);
        chk_45d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_45d.setText("45 dias");
        chk_45d.setEnabled(false);
        chk_45d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_45dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_45d, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 20, -1));

        btnGroupCondPago.add(chk_60d);
        chk_60d.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_60d.setText("60 días");
        chk_60d.setEnabled(false);
        chk_60d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_60dActionPerformed(evt);
            }
        });
        jPanel1.add(chk_60d, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, 20, -1));

        btnGroupCondPago.add(chk_inmediato);
        chk_inmediato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_inmediato.setText("Inmediato");
        chk_inmediato.setEnabled(false);
        chk_inmediato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_inmediatoActionPerformed(evt);
            }
        });
        jPanel1.add(chk_inmediato, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 20, -1));

        lbl_saldoxpagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_saldoxpagar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_saldoxpagar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(lbl_saldoxpagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 20));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Nombre del Solicitante");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 130, -1));

        txt_numfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numfacturaActionPerformed(evt);
            }
        });
        txt_numfactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numfacturaKeyReleased(evt);
            }
        });
        jPanel1.add(txt_numfactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 170, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Saldo por Pagar");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

        txt_total3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_total3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_total3KeyTyped(evt);
            }
        });
        jPanel1.add(txt_total3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 170, -1));

        txt_anticipo.setEditable(false);
        txt_anticipo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_anticipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_anticipoActionPerformed(evt);
            }
        });
        txt_anticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_anticipoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_anticipoKeyTyped(evt);
            }
        });
        jPanel1.add(txt_anticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 170, -1));

        lbl_femision.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_femision.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_femision, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 150, 20));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Concepto");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, -1));

        jPanel1.add(cmb_autorizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 240, 170, 20));

        lbl_limpago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_limpago.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_limpago, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 120, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Factura Cancelada");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 190, 110, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("30 días");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 50, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("60 días");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, 50, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("90 días");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 50, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("120 días");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 190, 50, -1));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 134, 930, 276));

        jDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Partida", "N° Factura", "Proveedor", "Fecha de Emisión", "Fecha Límite de Pago", "Fecha de Liquidación", "Estatus", "Nombre de Proyecto", "Concepto", "Importe Total", "Anticipo", "Saldo por Pagar", "Solicitó", "Autorizó"
            }
        ));
        jScrollPane1.setViewportView(jDatos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 930, 120));

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

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-editar-48.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPEditarLayout = new javax.swing.GroupLayout(jPEditar);
        jPEditar.setLayout(jPEditarLayout);
        jPEditarLayout.setHorizontalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEditarLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPEditarLayout.setVerticalGroup(
            jPEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEditarLayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(jLabel14))
        );

        jPanel5.add(jPEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 50, 50));

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
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.add(jPAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 100, 50));

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
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
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
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-tabla-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 90, 40));

        jLabel22.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel22.setText("EDITAR ORDEN");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        jLabel23.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel23.setText("GENERACION DE REPORTES");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        jLabel24.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel24.setText("LIMPIAR FORMULARIO");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, -1, -1));

        jLabel25.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel25.setText("PAGAR FACTURA");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, -1, -1));

        jLabel30.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jLabel30.setText("GENERAR ORDEN DE PAGO");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, -1, -1));

        jPanel8.setBackground(new java.awt.Color(107, 190, 249));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconografias/icons8-tarjeta-en-uso-50.png"))); // NOI18N
        jPanel8.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jPanel5.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 90, 50));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 930, -1));

        jLabel26.setFont(new java.awt.Font("Antique Olive", 1, 24)); // NOI18N
        jLabel26.setText("PAGO DE FACTURAS");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, -1, 30));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 670));
    }// </editor-fold>//GEN-END:initComponents

    private void jPBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPBuscarMouseClicked
        
    }//GEN-LAST:event_jPBuscarMouseClicked

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // Busqueda de informacion mediante ID, NOMBRE O RFC

    }//GEN-LAST:event_txtsearchActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased

    }//GEN-LAST:event_txtsearchKeyReleased

    private void txt_nombreproyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreproyectoKeyTyped
     /*   if(txt_nombreproyecto.getText().length() >= 3)
        {
            evt.consume();
        }*/
    }//GEN-LAST:event_txt_nombreproyectoKeyTyped

    private void txt_conceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_conceptoKeyTyped
        /*if(txt_concepto.getText().length() >= 13)
        {
            evt.consume();
        }*/
    }//GEN-LAST:event_txt_conceptoKeyTyped

    private void jPEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseClicked
        
    }//GEN-LAST:event_jPEditarMouseClicked

    private void jPAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseClicked
            NumFactura = txt_numfactura.getText();
            Concepto = txt_concepto.getText();
            Solicitante = txt_nomsolicitante.getText();
            Autorizante = cmb_autorizar.getSelectedItem().toString();
            int vvop=-1; //valor de verificacion de orden de pago 
            float monto=(float) Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto que desea pagar"));
            //verificar usuario;
            try {
            vvop=VERIFICARUSUARIO(usuario);
                } catch (SQLException ex) {
            Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                 }
            //verificar que mi pago es menor al de mi monto principal 
        try {            
            if(VERIFICARMONTO(NumFactura,monto)==0)
            {
                try {
                    GENOP(NumFactura,Estatus,Concepto,Solicitante,Autorizante,vvop,monto);
                } catch (SQLException ex) {
                    Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                try {
                    // Vacía la tabla eliminando todas las filas una por una
                    dtm.setRowCount(0);
                    ct();
                } catch (SQLException ex) {
                    Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                }
                //generacion de orden de pago en el historico
                try{
                    GENH("Generación de Orden de Pago",Concepto);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null,"Ocurrió un error");
                }
                
            }
            else if(VERIFICARMONTO(NumFactura,monto)==1)
            {
             JOptionPane.showMessageDialog(null,"El monto que desea pagar sobrepasa el monto final");   
                
            }
            //generacion de situacion de orden de pago: ADMINISTRADOR
            
            //}
            /* if(vvop==1)
            {
            JOptionPane.showMessageDialog(null,"Su operacion no puede ejecutarse...");
            }*/
        } catch (SQLException ex) {
            Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        


    }//GEN-LAST:event_jPAddMouseClicked

    private void pnl_limparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseClicked
        // TODO add your handling code here:
        txt_numpartida.setText("");
        txt_nomsolicitante.setText("");
        lbl_NProveedor.setText("");
        txt_concepto.setText("");
        txt_nombreproyecto.setText("");
        lbl_fliquidacion.setText("");
        lbl_NProveedor.setText("");
        txt_total.setText("");
        lbl_estatus.setText("");
        chk_cancelado.setEnabled(false);
        lbl_fliquidacion.setText("");
        lbl_femision.setText("");
    }//GEN-LAST:event_pnl_limparMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
      /*  try {
            // TODO add your handling code here:

            TGeneral tg = new TGeneral();
            tg.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Proveedores_Principal.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txt_nomsolicitanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomsolicitanteKeyReleased

    }//GEN-LAST:event_txt_nomsolicitanteKeyReleased

    private void txt_nomsolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomsolicitanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomsolicitanteActionPerformed

    private void txt_numproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numproveedorActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_numproveedorActionPerformed

    private void txt_numproveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numproveedorKeyReleased
        
    }//GEN-LAST:event_txt_numproveedorKeyReleased

    private void txt_totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalKeyTyped

    private void chk_canceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_canceladoActionPerformed
        // TODO add your handling code here:
        Estatus = 3;
        lbl_estatus.setForeground(Color.red);
        lbl_estatus.setText("CANCELADO");
    }//GEN-LAST:event_chk_canceladoActionPerformed

    private void chk_30dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_30dActionPerformed
        
    }//GEN-LAST:event_chk_30dActionPerformed

    private void chk_45dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_45dActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_chk_45dActionPerformed

    private void chk_60dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_60dActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_chk_60dActionPerformed

    private void chk_inmediatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_inmediatoActionPerformed
       
    }//GEN-LAST:event_chk_inmediatoActionPerformed

    private void txt_numfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numfacturaActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txt_numfacturaActionPerformed

    private void txt_numfacturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numfacturaKeyReleased
        // TODO add your handling code here:
         if(txt_numfactura.getText()== ""){
            lbl_aviso.setVisible(true);
        } else {
             try {
                 lbl_aviso.setVisible(false);
                 
                 numfact=txt_numfactura.getText();
                 
                 Statement stm=Conexion.getConexion().createStatement();
                 ResultSet rst=stm.executeQuery("SELECT * from CXP where NumFactura = '"+numfact+"'");
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
                     
                     
                     
                     txt_numfactura.setText(numfact.trim());
                     txt_numproveedor.setText(String.valueOf(NumProveedor));
                     lbl_NProveedor.setText(NombreProveedor);
                     txt_total.setText(String.valueOf(importetotal).trim());
                     txt_nombreproyecto.setText(NombreProyecto.trim());
                     lbl_femision.setText(String.valueOf(date));
                     lbl_saldoxpagar.setText(String.valueOf(saldoxpagar));
                     lbl_limpago.setText(String.valueOf(datesum));
                     txt_anticipo.setText(String.valueOf(anticipo));
                     
                     
                     //Cadena de IFs para los checkbox de Condiciones de Pago
                     if(condpago == 30){
                         chk_30d.setSelected(true);
                     } else if(condpago == 45){
                         chk_45d.setSelected(true);
                     } else if(condpago == 60){
                         chk_60d.setSelected(true);
                     }
                     //Cadena de IFs para el estatus
                     if(saldoxpagar == 0){
                         Estatus = 1;
                         lbl_estatus.setForeground(Color.green);
                         lbl_estatus.setText("PAGADO");
                     } else {
                         Estatus = 2;
                         lbl_estatus.setForeground(Color.yellow);
                         lbl_estatus.setText("PENDIENTE");
                     }
                     
                 }    } catch (SQLException ex) {
                 Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
             }
         }    
    }//GEN-LAST:event_txt_numfacturaKeyReleased

    private void txt_total3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_total3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total3KeyTyped

    private void txt_anticipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_anticipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_anticipoActionPerformed

    private void txt_anticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_anticipoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_anticipoKeyReleased

    private void txt_anticipoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_anticipoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_anticipoKeyTyped

    private void jPEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseEntered
        // TODO add your handling code here:
        jPEditar.setBackground(Color.red);
    }//GEN-LAST:event_jPEditarMouseEntered

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        //For para detectar el número de registros seleccionados en la tabla
       // int val1=-1;
       // PF();
       /* try 
        {
            val1=VERIFICARUSUARIO(usuario);
        } catch (SQLException ex) 
        {
            Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       /* switch (val1)
        {
            case 1:*/

        /*        break;
            case 0:
                JOptionPane.showMessageDialog(null,"operacion pendiente: Necesita permiso del administrador");
                break;                
            case 2:
                JOptionPane.showMessageDialog(null,"operacion no permitida: usuario sin permisos");
                break;                
            case -1:
                JOptionPane.showMessageDialog(null,"operacion no permitida: usuario desconocido");
                break;
        }
                    /* if(val1==1)
            {
            PF();
            }
            if(val1==3)
            {
            val2=consulta();
            
            
            }*/
        int numpart;
        String numfact;
        int value,value2;
        try{
        for(int i = 0; i < dtm.getRowCount(); i++){
            if(IsSelected(i, 12, jDatos)==true)
            {
              /*  try 
                {*/             
                    numpart = (int)dtm.getValueAt(i, 0);
                    numfact = dtm.getValueAt(i, 1).toString();
                    System.out.println(numfact);
                   // PagarFactura(numfact);
                   //value=VERIFICARUSUARIO(numfact);
                   value2=VERIFICARPERMISO(numpart);
                   
                    //if(value==1) //mismo usuario; 
                    //{
                        if(value2==0)
                        {
                            JOptionPane.showMessageDialog(null,"SOLICITUD PENDIENTE: A la espera de ser autorizada por el admin");
                        }
                        if(value2==1)
                        {
                            System.out.println(numfact);
                            PagarFactura(numfact);   
                        }
                        if(value2==2)
                        {
                            JOptionPane.showMessageDialog(null,"SOLICITUD RECHAZADA: fuiste rechazado por el admin");
                        }
                        if(value2==-1)
                        {
                            JOptionPane.showMessageDialog(null,"ERROR: No se pudo realizar la operación");
                        }                        
                    //}
                   /* if(value==0)
                    {
                   JOptionPane.showMessageDialog(null,"Su solicitud fue enviada para ser aprobada por el administrador");
                    
                    }*/    
                   /* if(value==-1)
                    {
                        JOptionPane.showMessageDialog(null,"ORDEN DE PAGO NO AUTORIZADO PARA ESTE USUARIO:operacion fallida");
                    
                    }      */   

              /*  } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            else
            {
               // JOptionPane.showMessageDialog(null,"selecciona algo");
            
            }
            
        }
         } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(Panel_Pagos_Edit.class.getName()).log(Level.SEVERE, null, ex);
                }
        
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        jPanel8.setBackground(Color.red);
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        jPanel8.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPEditarMouseExited
        jPEditar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPEditarMouseExited

    private void jPAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseExited
        // TODO add your handling code here:
        jPAdd.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_jPAddMouseExited

    private void jPAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPAddMouseEntered
        // TODO add your handling code here:
        jPAdd.setBackground(Color.red);
    }//GEN-LAST:event_jPAddMouseEntered

    private void pnl_limparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseEntered
        // TODO add your handling code here:
        pnl_limpar.setBackground(Color.red);
    }//GEN-LAST:event_pnl_limparMouseEntered

    private void pnl_limparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_limparMouseExited
        // TODO add your handling code here:
        pnl_limpar.setBackground(new java.awt.Color(107, 190, 249));
    }//GEN-LAST:event_pnl_limparMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupAnticipo;
    private javax.swing.ButtonGroup btnGroupCondPago;
    private javax.swing.JCheckBox chk_30d;
    private javax.swing.JCheckBox chk_45d;
    private javax.swing.JCheckBox chk_60d;
    private javax.swing.JCheckBox chk_cancelado;
    private javax.swing.JCheckBox chk_inmediato;
    private javax.swing.JComboBox<String> cmb_autorizar;
    private javax.swing.JComboBox<String> cmb_tipobusqueda;
    protected javax.swing.JTable jDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAdd;
    private javax.swing.JPanel jPBuscar;
    private javax.swing.JPanel jPEditar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_NProveedor;
    private javax.swing.JLabel lbl_aviso;
    private javax.swing.JLabel lbl_estatus;
    private javax.swing.JLabel lbl_estatustitulo;
    private javax.swing.JLabel lbl_femision;
    private javax.swing.JLabel lbl_fliquidacion;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_limpago;
    private javax.swing.JLabel lbl_saldoxpagar;
    private javax.swing.JPanel pnl_limpar;
    private javax.swing.JTextField txt_anticipo;
    private javax.swing.JTextField txt_concepto;
    private javax.swing.JTextField txt_nombreproyecto;
    private javax.swing.JTextField txt_nomsolicitante;
    private javax.swing.JTextField txt_numfactura;
    private javax.swing.JTextField txt_numpartida;
    private javax.swing.JTextField txt_numproveedor;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total3;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables


}
