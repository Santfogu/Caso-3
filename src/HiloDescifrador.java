import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    if (modo == 2) {
      indexFinal = 10460353203L / 2;
    }
    if (modo == 3) {
      indexInicio = 10460353203L / 2;
    }

    
    StringBuilder sb;
    int indice;
    int[] indices = {387420489, 14348907, 531441, 19683, 729, 27, 1};


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

      if (cadenaCodigo.equals(convertirHash(V + sal))) 
      {
        encontrado = true;
        threadGanador = true;
        password = V ;
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        this.tiempoRespuesta = duration + " ms";
      }
      indexInicio++;
      
    }

  }

  private String convertirHash(String cadena) {
    String hashStr = "";
    try {
      // Aplicar Hash a los bytes
      MessageDigest digest;
      digest = MessageDigest.getInstance(algHash);
      byte[] hashBytes = digest.digest(cadena.getBytes());

      // Convertir el valor hash a una cadena hexadecimal
      StringBuilder sb = new StringBuilder();
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b));
      }
      hashStr = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashStr;
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
