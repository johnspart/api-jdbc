package co.edu.crud;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface ICrud {

    /**
     * Ingresa la informacion del bean utilizando reflection en la tabla
     *
     * @return numero de registros insertados
     */
    public int insert();

    /**
     * Actualiza los campos de la tabla que no son primarios teniendo como
     * criterio (where) los campos primarios en el bean que NO son nulos
     *
     * @return numero de registros actualizados
     */
    public int update();

    /**
     * Elimina de la tabla los registros bajo los criterios especificados por
     * los valores de la clave primaria en el bean
     *
     * @return numero de registros eliminados
     */
    public int delete();

    /**
     * Ejecuta una sentencia en la base de datos esta sentencias deben ser de
     * tipo update, insert, delete
     *
     * @param _comando sentencia a ejecutar, especificar parametros con ?
     * @param _parametros los valores de los parametros de la sentencia
     * @return
     */
    public int execute(String sql, Object... parametros);

    /**
     * Inserta o actualiza la informacion del bean
     *
     * @return Numero de registros guardados
     */
    public int save();

    /**
     * Ejecuta una consulta en la base de datos basada en los campos no nulos
     * del bean y toma los valores del bean para generar el WHERE. Se puede
     * parametrizar el comportamiento de la consulta a traves de los metodos:
     * <br> {@linkplain ICrud#set_Cantidad(int) }
     * <br> {@linkplain ICrud#set_Posicion(int) }
     *
     * @see #set_Cantidad(int)
     * @see #set_Posicion(int)
     * @return
     */
    public List<? extends ICrud> select();

    /**
     * Ejecuta una consulta en la base de datos se puede parametrizar el
     * comportamiento de la consulta a traves de los metodos: 
     * <br> {@linkplain ICrud#set_Cantidad(int) }
     * <br> {@linkplain ICrud#set_Posicion(int) }
     *
     * @see #set_Cantidad(int)
     * @see #set_Posicion(int)
     * @param sql
     * @param params
     * @return
     */
    public List<? extends ICrud> select(String sql, Object... params);

    /**
     * Tabla sobre la cual se realizan las operaciones en la base de datos por
     * defecto retorna el nombre de la clase
     *
     * @return nombre de la tabla
     */
    public String get_Tabla();

    /**
     * Cuando la conexion NO externa y es nula, genera una conexion a la base de
     * datos y la retorna en un objeto Connection
     *
     * @return Conexion a la base de datos
     */
    public Connection get_Conexion();

    /**
     * Asigna la conexion a la base de datos, automaticamente la marca como una 
     * conexion externa, con el fin de que no la cierren desde los métodos de esta clase
     * @param Conexion 
     */
    public void set_Conexion(Connection Conexion);

    /**
     * Indica si la conexion es externa o no.
     * Cuando es una conexion externa, desde la clase que implemente ICrud no se 
     * puede cerrar la conexion a la base de datos ya que esto es tarea de la clase que envia la conexion.
     * @return 
     */
    public boolean isConexionExterna();

    /**
     * Obtiene la Fila desde la cual se comienzan a traer registros en el Select
     * @return Posicion desde la cual se deben traer los registros
     */
    public int get_Posicion();

    /**
     * Asigna la Fila desde la cual se comienzan a traer registros en el Select
     * @see #select() (int)
     * @see #select(java.lang.String, java.lang.Object[])
     * @param posicion
     */
    public void set_Posicion(int posicion);

    /**
     * Obtiene la cantidad de registros que se deben traer a partir de la
     * Posicion dada
     * @see #select() (int)
     * @see #select(java.lang.String, java.lang.Object[])
     * @return Cantidada de registro a traer
     */
    public int get_Cantidad();

    /**
     * Asigna la cantidad de registros que se deben traer a partir de la
     * Posicion dada
     * @see #select() (int)
     * @see #select(java.lang.String, java.lang.Object[])
     * @param cantidad, entero indicando la cantidad de registros a traer
     */
    public void set_Cantidad(int cantidad);

    /**
     * Obtiene el número de filas que trae la consulta. <br/> Solo trae
     * información cuando los métodos
     * {@linkplain ICrud#set_Cantidad(int) } y {@linkplain ICrud#set_Posicion(int)
     * } devuelven valores mayores a cero
     *
     * @return Un número entero con la cantidad total de registros que trae la
     * consulta
     * @see ICrud#get_Cantidad()
     * @see ICrud#get_Posicion()
     * @see #select() (int)
     * @see #select(java.lang.String, java.lang.Object[])
     */
    public int get_Filas();
}
