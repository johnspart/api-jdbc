/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John
 */
public class Crud implements ICrud{
    
    private int posicion;
    private int cantidad;
    private Connection conexion;

    @Override
    public int insert() {
        int Res = 0;
        try{
            Columna Col;
            int ind = 1;
            String Sq = getSqlInsert();
            PreparedStatement St = get_Conexion().prepareStatement(Sq, PreparedStatement.RETURN_GENERATED_KEYS);
            
            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                Campo.setAccessible(true);
                
                if(Col == null || Col.AutoNumerico() || Campo.get(this) == null)
                    continue;
                
                Campo.setAccessible(true);
                St.setObject(ind++, Campo.get(this));
            } 
            System.out.println(St.toString());
            
            Res = St.executeUpdate();
            
            ResultSet rs = St.getGeneratedKeys();
            while(rs.next()){
                for (Field Campo : this.getClass().getDeclaredFields()) {
                    Col = Campo.getAnnotation(Columna.class);

                    if(Col == null || !Col.AutoNumerico())
                        continue;

                    Campo.setAccessible(true);
                    Campo.set(this, rs.getObject(Campo.getName()));
                } 
            }
        }catch(SQLException | SecurityException | IllegalArgumentException | IllegalAccessException ex){
            ex.printStackTrace();
        }finally{
            return Res;
        }
    }
    
    @Override
    public int update() {
        int res = 0;
        try{
            Columna Col;
            int ind = 1;
            String Sq = getSqlUpdate();
            PreparedStatement St =get_Conexion().prepareStatement(Sq);

            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                if(Col == null || Col.ClavePrimaria())
                    continue;
                
                if (!Col.AutoNumerico() && Campo.get(this) != null){
                    Campo.setAccessible(true);
                    St.setObject(ind++, Campo.get(this));
                }
            }
            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                if(Col == null)
                    continue;
                
                if (Col.ClavePrimaria()){
                    Campo.setAccessible(true);
                    St.setObject(ind++, Campo.get(this));
                }
            }
            
            res = St.executeUpdate();
            
        }catch(SQLException | SecurityException | IllegalArgumentException | IllegalAccessException ex){
            ex.printStackTrace();
        }finally{
            return res;
        }
    
    }

    @Override
    public int delete() {
        int res = 0;
        try{
            Columna Col;
            int ind = 1;
            
            String Sq = getSqlDelete();
            PreparedStatement St = this.get_Conexion().prepareStatement(Sq);

            for(Field Campo : this.getClass().getDeclaredFields()){
                Col = Campo.getAnnotation(Columna.class);
                
                if(Col == null)
                    continue;
                
                if(Col.ClavePrimaria()){ 
                    Campo.setAccessible(true);
                    St.setObject(ind++, Campo.get(this));
                }
                
            }
            res = St.executeUpdate();

        }catch(SQLException | SecurityException | IllegalArgumentException | IllegalAccessException ex){
            ex.printStackTrace();
        }finally{
            return res;
        }
    }

    @Override
    public int execute(String sql, Object... parametros) {
        int res = 0;
        int j = 1;
        try{
            PreparedStatement st = get_Conexion().prepareStatement(sql);
            for(int i= 0; i < parametros.length; i++){
                st.setObject(j++, parametros[i]);
            }
            
            res = st.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public int save() {
        int res = 0;
        try{
            Columna columna;
            boolean actualizar = false;
            boolean guardar = false;
            for(Field campo : this.getClass().getFields()){
                columna = campo.getAnnotation(Columna.class);

                if(columna == null)
                    continue;
                
                campo.setAccessible(true);
                if(columna.ClavePrimaria()){
                    if(columna.AutoNumerico() || campo.get(this) != null){
                        actualizar = false;
                        guardar = true;
                    }else{
                        actualizar = false;
                        guardar = false;
                        break;
                    }
                }else{
                    actualizar &= true;
                }
            }
            
            if(guardar){
                insert();
            }else if(actualizar){
                update();
            }
        
        }catch ( IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List<? extends ICrud> select() {
        ArrayList<Crud> Lst = new ArrayList<Crud>();
        
        Connection Con = this.get_Conexion();
        PreparedStatement St = null;
        ResultSet Rst = null;
        String SqlSelect = getSqlSelect();
        Integer contador = 0;
        
        boolean Sw = false;
        Columna Col;
        Crud Reg;
        try{
            
            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                if(Col == null)
                    continue;

                Campo.setAccessible(true);
                if(Campo.get(this) != null){
                    if(!Sw){
                        Sw = true;
                        SqlSelect += "WHERE ";
                    }
                    SqlSelect += ""+Campo.getName()+" = ? AND ";
                }
            }
            
            if(Sw){
                SqlSelect = SqlSelect.substring(0, SqlSelect.length()-5);
            }
            
            if(cantidad != 0)
                St = Con.prepareStatement(SqlSelect+";",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            else
                St = Con.prepareStatement(SqlSelect+";");
            
            int i = 1;
            for (Field Campo : this.getClass().getDeclaredFields()) {

                Col = Campo.getAnnotation(Columna.class);
                if(Col == null)
                    continue;

                Campo.setAccessible(true);
                if(Campo.get(this) != null)
                    St.setObject(i++, Campo.get(this));
            }

            Rst = St.executeQuery();
            
            if(cantidad != 0){
                Rst.setFetchSize(cantidad);
                Rst.absolute(posicion);
            }
            
            while(Rst.next()){
                Reg = this.getClass().newInstance();
                
                for (Field Campo : Reg.getClass().getDeclaredFields()) {
                    
                    Col = Campo.getAnnotation(Columna.class);
                    if(Col == null)
                        continue;
                    
                    Campo.setAccessible(true);
                    Campo.set(Reg, Rst.getObject(Campo.getName()));
                }
                if(cantidad != 0){
                    if(cantidad > 0 && ++contador > cantidad)
                        break;
                }
                Lst.add(Reg);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(InstantiationException ex){
            ex.printStackTrace();
        }catch(IllegalAccessException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                St.close();
                Rst.close();
                Con.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return Lst;
        }
    }

    @Override
    public List<? extends ICrud> select(String sql, Object... params) {
        ArrayList<Crud> Lst = new ArrayList<Crud>();
        
        Connection Con = this.get_Conexion();
        PreparedStatement St = null;
        ResultSet Rst = null;
        Integer contador = 0;
        String sqlSelect = "SELECT ";
        Columna Col;
        Crud Reg;
        try{
            boolean swPrimero = true;
            for(Field campo : this.getClass().getDeclaredFields()){
                Col = campo.getAnnotation(Columna.class);
                
                if(Col == null)
                    continue;
                
                if(swPrimero)
                    sqlSelect+=campo.getClass().getSimpleName();
                else
                    sqlSelect+=", "+campo.getClass().getSimpleName();
                
                swPrimero = false;
            }
            
            sqlSelect += " FROM "+get_Tabla()+" "+sql;
            
            if(cantidad != 0)
                St = Con.prepareStatement(sqlSelect,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            else
                St = Con.prepareStatement(sqlSelect);
            
            int i = 1;
            for (int j = 0; j < params.length; j++) {
                St.setObject(i++, params[j]);
            }

            Rst = St.executeQuery();
            
            if(cantidad != 0){
                Rst.setFetchSize(cantidad);
                Rst.absolute(posicion);
            }
            
            while(Rst.next()){
                Reg = this.getClass().newInstance();
                
                for (Field Campo : Reg.getClass().getDeclaredFields()) {
                    
                    Col = Campo.getAnnotation(Columna.class);
                    if(Col == null)
                        continue;
                    
                    Campo.setAccessible(true);
                    Campo.set(Reg, Rst.getObject(Campo.getName()));
                }
                if(cantidad != 0){
                    if(cantidad > 0 && ++contador > cantidad)
                        break;
                }
                Lst.add(Reg);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(InstantiationException ex){
            ex.printStackTrace();
        }catch(IllegalAccessException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                St.close();
                Rst.close();
                Con.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return Lst;
        }
    }

    @Override
    public String get_Tabla() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Connection get_Conexion() {
        return conexion;
    }

    @Override
    public void set_Conexion(Connection Conexion) {
        this.conexion = Conexion;
    }

    @Override
    public boolean isConexionExterna() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int get_Posicion() {
        return this.posicion;
    }

    @Override
    public void set_Posicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public int get_Cantidad() {
        return this.cantidad;
    }

    @Override
    public void set_Cantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int get_Filas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public String getSqlInsert(){
        String Consulta="";
        String Interrogantes = " (";
        Columna Col;
        try{
            
            Consulta+="INSERT INTO "+get_Tabla()+" (";
            
            int n = 0;
            
            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                Campo.setAccessible(true);
                
                n++;
                if(Col == null || Campo.get(this) == null)
                    continue;
                
               if (!Col.AutoNumerico()){
                   if(n == this.getClass().getDeclaredFields().length){
                        Consulta+=Campo.getName();
                        Interrogantes +="?";
                    }else{
                        Consulta+=Campo.getName()+", ";
                        Interrogantes +="?, ";
                    }
                }
            }
        }catch(SecurityException | IllegalArgumentException | IllegalAccessException ex){
            ex.printStackTrace();
        }
        
        Consulta += ") VALUES "+Interrogantes+");";
        
        return Consulta;
    }
        
    public String getSqlUpdate(){
        String Consulta="";
        String WHERE = "";
        boolean SwPrimeraComa = true;
        

        try {
            WHERE += " WHERE ";
            Consulta += "UPDATE "+this.get_Tabla()+" SET ";
            Columna Col;
            for (Field Campo : this.getClass().getDeclaredFields()) {
                boolean SwCont = true;
                
                Col = Campo.getAnnotation(Columna.class);
                Campo.setAccessible(true);
                
                if(Col == null)
                    continue;
                
                if(Col.ClavePrimaria()){
                        WHERE += Campo.getName()+" = ?";
                        SwCont = false;
                        WHERE +=" AND ";
                }
                if(SwCont && Campo.get(this) != null){
                    if (!Col.AutoNumerico()){
                        if(SwPrimeraComa){
                            Consulta+=Campo.getName()+" = ?";
                            SwPrimeraComa = false;
                        }else{
                            Consulta+= ", "+Campo.getName()+" = ?";
                        }
                    }
                }
               
            }
            
            
        }catch(SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        WHERE = WHERE.substring(0, WHERE.length()-4);
        return Consulta+WHERE+";";
    }
    
    public String getSqlDelete(){
        String clase="";
     

        try {
            clase+="DELETE FROM "+get_Tabla()+" WHERE ";

            Columna Col;
            for (Field Campo : this.getClass().getDeclaredFields()) {
                Col = Campo.getAnnotation(Columna.class);
                if(Col == null)
                    continue;
                
                if(Col.ClavePrimaria()){
                    clase += Campo.getName()+" = ? AND ";
                }
            }
            
            
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        
        clase = clase.substring(0, clase.length()-4);
        return clase+";";
    }
    
    public String getSqlSelect(){
        String Consulta = "";
        String WHERE = "FROM "+get_Tabla()+" ";
        boolean SwPrimeraComa = true;
        

        try {
            Consulta += "SELECT ";
            Columna Col;
            for (Field Campo : this.getClass().getDeclaredFields()) {
                boolean SwCont = true;
                Col = Campo.getAnnotation(Columna.class);
                if(Col == null)
                    continue;

                if(SwPrimeraComa){
                    Consulta+=Campo.getName();
                    SwPrimeraComa = false;
                }else{
                    Consulta+= ", "+Campo.getName();
                }
               
            }
            
            
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return Consulta+" "+WHERE+"";
    }
}
