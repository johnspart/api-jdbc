package co.edu.beans;

import co.edu.crud.Columna;
import co.edu.crud.Crud;

import java.util.List;

public class Instituciones extends Crud{

    @Columna(AutoNumerico= true, ClavePrimaria = true)
    private java.lang.Long id_institucion;

    @Columna()
    private java.lang.String nombre;

    @Columna()
    private java.lang.String direccion;

    @Columna()
    private java.lang.String comentario;

    @Columna()
    private java.lang.Boolean estado;

    /*
    *Método encargado de retornar el valor que posee el campo id_institucion
    */
    public java.lang.Long getid_institucion(){
        return id_institucion;
    }

    /*
    *Método encargado de asignar el valor enviado al campo id_institucion
    */
    public void setid_institucion(java.lang.Long id_institucion){
        this.id_institucion = id_institucion;
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
    *Método encargado de retornar el valor que posee el campo direccion
    */
    public java.lang.String getdireccion(){
        return direccion;
    }

    /*
    *Método encargado de asignar el valor enviado al campo direccion
    */
    public void setdireccion(java.lang.String direccion){
        this.direccion = direccion;
    }

    /*
    *Método encargado de retornar el valor que posee el campo comentario
    */
    public java.lang.String getcomentario(){
        return comentario;
    }

    /*
    *Método encargado de asignar el valor enviado al campo comentario
    */
    public void setcomentario(java.lang.String comentario){
        this.comentario = comentario;
    }

    /*
    *Método encargado de retornar el valor que posee el campo estado
    */
    public java.lang.Boolean getestado(){
        return estado;
    }

    /*
    *Método encargado de asignar el valor enviado al campo estado
    */
    public void setestado(java.lang.Boolean estado){
        this.estado = estado;
    }
    
    @Override
    public List<Instituciones> select(){
        return (List<Instituciones>) super.select();
    }
}
