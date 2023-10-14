
package ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Miguel Flores
 * @autor Johan Mendoza 
 */
public class Conexion 
{
 public static Connection getConexion()
    {
        //Conexion MiguelPC y PC de Presentación, descomentar cuando sea el caso
        /*
        String conexionUrl="jdbc:sqlserver://localhost:1433;" //esto se cambiará cuando la base de datos se traslade a internet
                + "database=Gestion4F;"//nombre de nuestra base de datos
                + "user=4FAdmin;"
                + "password=CuatroF;"
                + "loginTimeout=30;";
        try // tratara de hacer la conexion a la base de datos 
        {
            Connection con=DriverManager.getConnection(conexionUrl);
            return con;
        }catch(SQLException ex)
                {
                    System.out.println(ex.toString());
                    return null; 
                }*/
        //Conexion de JohanPC, descomentar cuando sea el caso
        String conexionUrl="jdbc:sqlserver://localhost:1433;" //esto se cambiará cuando la base de datos se traslade a internet
                + "database=Gestion4F;"//nombre de nuestra base de datos
                + "user=4FAdmin;"
                + "password=CuatroF;"
                + "loginTimeout=30;";
        try // tratara de hacer la conexion a la base de datos 
        {
            Connection con=DriverManager.getConnection(conexionUrl);
            return con;
        }catch(SQLException ex)
                {
                    System.out.println(ex.toString());
                    return null; 
          } 
        }
    }