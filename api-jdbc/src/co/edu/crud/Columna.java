package co.edu.crud;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author thomas
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Columna {

    boolean ClavePrimaria() default false;
    boolean AutoNumerico() default false;

}
