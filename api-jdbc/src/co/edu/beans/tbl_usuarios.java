package co.edu.beans;

import co.edu.crud.Columna;
import co.edu.crud.Crud;
import co.edu.crud.ICrud;

import java.util.List;

public class tbl_usuarios extends Crud{

    @Columna(AutoNumerico = false, ClavePrimaria = true)
    private java.lang.String id_usuario;

    @Columna()
    private java.lang.String nombre;

    @Columna()
    private java.lang.String apellido;

    @Columna()
    private java.lang.String password;

    /*
    *Método encargado de retornar el valor que posee el campo id_usuario
    */
    public java.lang.String getid_usuario(){
        return id_usuario;
    }

    /*
    *Método encargado de asignar el valor enviado al campo id_usuario
    */
    public void setid_usuario(java.lang.String id_usuario){
        this.id_usuario = id_usuario;
    }

    /*
    *Método encargado de retornar el valor que posee el campo nombre
    */
    public java.lang.String getnombre(){
        return nombre;
    }

    /*
    *Método encargado de asignar el valor enviado al campo nombre
    */
    public void setnombre(java.lang.String nombre){
        this.nombre = nombre;
    }

    /*
    *Método encargado de retornar el valor que posee el campo apellido
    */
    public java.lang.String getapellido(){
        return apellido;
    }

    /*
    *Método encargado de asignar el valor enviado al campo apellido
    */
    public void setapellido(java.lang.String apellido){
        this.apellido = apellido;
    }

    /*
    *Método encargado de retornar el valor que posee el campo password
    */
    public java.lang.String getpassword(){
        return password;
    }

    /*
    *Método encargado de asignar el valor enviado al campo password
    */
    public void setpassword(java.lang.String password){
        this.password = password;
    }

    @Override
    public List<tbl_usuarios> select(String sql, Object... params) {
        return (List<tbl_usuarios>) super.select(sql, params);
    }

    @Override
    public List<tbl_usuarios> select() {
        return (List<tbl_usuarios>) super.select();
    }

    
}
