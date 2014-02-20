/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.jdbc;

/**
 *
 * @author John
 */
import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.NoInitialContextException;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;

public class SQL {
    /*
     * Retorna la connexion a la base de datos
     * @return con
     */
    //private static ;
    //private static Statement Cmd;
    @SuppressWarnings("CallToThreadDumpStack")
    public static Connection getConexionEs(){
        Connection Con = null;
        try{
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String Usuario = "postgres";
            String Clave = "123456";
            Class.forName("org.postgresql.Driver");
            Con = DriverManager.getConnection(url, Usuario, Clave);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            return Con;
        }
        
    }
    
    @SuppressWarnings("CallToThreadDumpStack")
    public static Connection getConexion(){
        Connection Con = null;
        Context conTxt;
        DataSource dS;
        try{
            conTxt = new InitialDirContext();
            dS = (DataSource) conTxt.lookup("jdbc/ces2");
            Con = dS.getConnection();
        }catch(NoInitialContextException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            return Con;
        }
        
    }
    /*
    public static Statement getStatemnt(){
        try{
            
            Cmd = getConexion().createStatement();
            
        }catch(Exception ex){
            
        }finally{
            return Cmd;
        }
    }*/
}
