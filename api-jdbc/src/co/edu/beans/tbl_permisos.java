package co.edu.beans;

import co.edu.crud.Columna;
import co.edu.crud.Crud;

public class tbl_permisos extends Crud{

    @Columna(AutoNumerico = false, ClavePrimaria = true)
    private java.lang.String id_usuario;

    @Columna(AutoNumerico = false, ClavePrimaria = true)
    private java.lang.Integer id_pagina;

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
}
