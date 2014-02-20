package co.edu.beans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.jdbc.SQL;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

/**
 *
 * @author John
 */
public class GeneradorDiagrama {
	
    public static boolean CrearClase(String NombreTabla) throws SQLException{
        boolean Sw = true;
        
        
        Connection Con = SQL.getConexionEs();
        Statement Stm = Con.createStatement();
        ResultSet Rst = null;
        ResultSet RstPk = null;
        ResultSetMetaData MtdTabla;
        
        try{
            Rst = Stm.executeQuery("select * from "+NombreTabla);
            MtdTabla = Rst.getMetaData();

            String Clase = "public class "+NombreTabla+" extends Crud{\n" ;
            String ClaseMetodos = "";
            
            boolean ContPk;
            boolean SwCont;
            for(int i = 1; i <= MtdTabla.getColumnCount(); i++){
                Clase += "\n";
                SwCont = true;
                
                RstPk = Con.getMetaData().getPrimaryKeys(null, null, NombreTabla);

                ContPk = RstPk.next();
                while(ContPk){
                    if(RstPk.getString(4).equals(MtdTabla.getColumnName(i)) & MtdTabla.isAutoIncrement(i)){
                        Clase += "    @Columna(Autonumerico = true, ClavePrimaria = true)\n";
                        SwCont = false;
                        break;
                    }else if(RstPk.getString(4).equals(MtdTabla.getColumnName(i))){
                        Clase += "    @Columna(Autonumerico = false, ClavePrimaria = true)\n";
                        SwCont = false;
                        break;
                    }

                    ContPk = RstPk.next();

                }
                if(SwCont){
                    if (MtdTabla.isAutoIncrement(i))
                        Clase += "    @Columna(Autonumerico = true, ClavePrimaria = false)\n";
                    else
                        Clase += "    @Columna()\n";
                }

                Clase += "    private "+MtdTabla.getColumnClassName(i)+" "+MtdTabla.getColumnName(i)+";\n";
                
                ClaseMetodos += "\n";
                ClaseMetodos += "    /*\n";
                ClaseMetodos += "    *Método encargado de retornar el valor que posee el campo "+MtdTabla.getColumnName(i)+"\n";
                ClaseMetodos += "    */\n";
                ClaseMetodos += "    public "+MtdTabla.getColumnClassName(i)+" get"+MtdTabla.getColumnName(i)+"(){\n";
                ClaseMetodos += "        return "+MtdTabla.getColumnName(i)+";\n";
                ClaseMetodos += "    }\n";
                ClaseMetodos += "\n";
                ClaseMetodos += "    /*\n";
                ClaseMetodos += "    *Método encargado de asignar el valor enviado al campo "+MtdTabla.getColumnName(i)+"\n";
                ClaseMetodos += "    */\n";
                ClaseMetodos += "    public void set"+MtdTabla.getColumnName(i)+"("+MtdTabla.getColumnClassName(i) +" "+MtdTabla.getColumnName(i) +"){\n";
                ClaseMetodos += "        this."+MtdTabla.getColumnName(i)+" = "+MtdTabla.getColumnName(i)+";\n";
                ClaseMetodos += "    }\n";
            }

            Clase += ClaseMetodos;
                
            Clase+="}\n";

            //Guardar el archivo;
            try {

                File java = new File("d:\\"+NombreTabla+".java");
                if(java.exists()){
                    System.out.println("Ya existe un archivo con ese nombre");
                }
                else
                {
                    java.createNewFile();
                    PrintWriter k = new PrintWriter(new FileWriter(java));
                    k.print(Clase);
                    k.flush();
                    k.close();

                    System.out.println("Se ha creado con exito el archivo "+NombreTabla+".java");
                }
            }
            catch(Exception ex) {
                System.out.println("Error en la creacion del archivo "+NombreTabla+".java");
                Sw = false;
            }
        }finally{
            try {
                Rst.close();
                RstPk.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                Stm.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            try {
                Con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return Sw;
        }
    }
}
