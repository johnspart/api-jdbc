package co.edu.beans;

import co.edu.crud.Columna;
import co.edu.crud.Crud;

public class tbl_paginas extends Crud{

    @Columna(AutoNumerico = true, ClavePrimaria = true)
    private java.lang.Integer id_pagina;

    @Columna()
    private java.lang.String nombre;

    @Columna()
    private java.lang.String url;

    /*
    *Método encargado de retornar el valor que posee el campo id_pagina
    */
    public java.lang.Integer getid_pagina(){
        return id_pagina;
    }

    /*
    *Método encargado de asignar el valor enviado al campo id_pagina
    */
    public void setid_pagina(java.lang.Integer id_pagina){
        this.id_pagina = id_pagina;
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
    *Método encargado de retornar el valor que posee el campo url
    */
    public java.lang.String geturl(){
        return url;
    }

    /*
    *Método encargado de asignar el valor enviado al campo url
    */
    public void seturl(java.lang.String url){
        this.url = url;
    }
}
