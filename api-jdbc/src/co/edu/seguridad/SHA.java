package co.edu.seguridad;

import java.security.MessageDigest;

/**
 *
 * @author thomas
 */
public class SHA {

    public static String hashSHA1(String clear) {
        StringBuffer hash = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            byte[] b = md.digest(clear.getBytes());
            int size = b.length;
            hash = new StringBuffer(size);
            for (int i = 0; i < size; i++) {
                int u = b[i] & 255; // unsigned conversion
                if (u < 16) {
                    hash.append("0" + Integer.toHexString(u));
                } else {
                    hash.append(Integer.toHexString(u));
                }
            }
            return hash.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return hash.toString();
        }
    }

    public static void main(String[] args) {
            String password = "1234";
            String password_cifrado = hashSHA1(password);
            System.out.println(password_cifrado);

            //La salida en consola para el password 1234 es
            //7110eda4d09e062aa5e4a390b0a572ac0d2c0220
    }
}
