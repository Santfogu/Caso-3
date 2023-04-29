import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HiloDescifrador extends Thread {

  //Modo 1 = todo el espacio; modo 2 = primera mitad espacio busqueda; modo 3 = segunda mitad espacio busqueda
  private int modo;
  private String algHash;
  private String cadenaCodigo;
  private String sal;
  private char[] espacio;
  public static Boolean encontrado = false;
  public String password;
  public String tiempoRespuesta;
  private Boolean threadGanador ;

  public HiloDescifrador(
    int modo,
    String algHash,
    String cadenaCodigo,
    String sal,
    char[] espacio
  ) {
    this.modo = modo;
    this.algHash = algHash;
    this.cadenaCodigo = cadenaCodigo;
    this.sal = sal;
    this.espacio = espacio;

	this.threadGanador = false;
	this.tiempoRespuesta = "";
	this.password = "";
  }

  public void run() {

    long startTime = System.currentTimeMillis();
    
    String V = "";

	// Por defecto en modo 1
    long indexInicio = 0;
    long indexFinal = 10460353203L;
    int aumento = 1;

    if (modo == 2) 
    {
      aumento = 2;
    }
    if (modo == 3) 
    {
      aumento = 2;
      indexInicio = 1;
    }


    StringBuilder sb;
    int indice;
    int[] indices = {387420489, 14348907, 531441, 19683, 729, 27, 1};
    
    int len = cadenaCodigo.length();
    byte[] byteAct = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        byteAct[i / 2] = (byte) ((Character.digit(cadenaCodigo.charAt(i), 16) << 4)
                              + Character.digit(cadenaCodigo.charAt(i+1), 16)); 
      }

    while (indexInicio <= indexFinal && !(encontrado)) 
    {
      sb = new StringBuilder();

      for (int i = 0; i < 7; i++) {
        indice = (int) (indexInicio/indices[i]) % 27;
        if (espacio[indice] != '\0') {
          sb.append(espacio[indice]);
        }
      }
      V = sb.toString();

      if (Arrays.equals(convertirHash(V+sal),byteAct))
      {
        encontrado = true;
        threadGanador = true;
        password = V ;
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        this.tiempoRespuesta = duration + " ms";
      }
      indexInicio += aumento;
      
    }

  }

  private byte[] convertirHash(String cadena) {
    byte[] hashBytes = {};
    try {
      // Aplicar Hash a los bytes
      MessageDigest digest;
      digest = MessageDigest.getInstance(algHash);
      hashBytes = digest.digest(cadena.getBytes());

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashBytes;
  }

  public String darTiempoRespuesta(){
	return this.tiempoRespuesta;
  }

  public String darPassword(){
	return this.password;
  }

  public Boolean encontro(){
	return this.threadGanador;
  }

  public static void reiniciarEncontrado(){
	HiloDescifrador.encontrado = false;
  }

}
