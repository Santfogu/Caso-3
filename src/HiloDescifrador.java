import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.sql.rowset.spi.SyncResolver;

public class HiloDescifrador extends Thread
{

	//Modo 1 = todo el espacio; modo 2 = primera mitad espacio busqueda; modo 3 = segunda mitad espacio busqueda
	private int modo;
	private String algHash;
	private String cadenaCodigo;
    private String sal;
	private ArrayList<String> espacio = new ArrayList<String>();
	

    public HiloDescifrador(int modo, String algHash, String cadenaCodigo, String sal) 
	{
		this.modo = modo;
		this.algHash = algHash;
		this.cadenaCodigo = cadenaCodigo;
		this.sal = sal;
	}
	
	public void run() 
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Comenzó");
		crearEspacio("abcdefghijklmnopqrstuvwxyz");
		long iterador = 0;
		String V = "";
		boolean encontrado = false;
		if (modo == 1)
		{
			while (iterador <= 10460353203L && !(encontrado))
			{
				int pos1 = (int) (iterador/(27*27*27*27*27*27))%27;
				int pos2 = (int) (iterador/(27*27*27*27*27))%27;
				int pos3 = (int) (iterador/(27*27*27*27))%27;
				int pos4 = (int) (iterador/(27*27*27))%27;
				int pos5 = (int) (iterador/(27*27))%27;
				int pos6 = (int) (iterador/27)%27;
				int pos7 = (int) (iterador%27);
				
				V = espacio.get(pos1)+espacio.get(pos2)+espacio.get(pos3)+espacio.get(pos4)+espacio.get(pos5)+espacio.get(pos6)+espacio.get(pos7);
				System.out.println(V);
				if (cadenaCodigo.equals(convertirHash(V+sal)))
				{
					encontrado = true;
					System.out.println(V+"---"+sal);
				}

				iterador++;
				
			}

			
			
		} 
		else if (modo == 2)
		{
			
		}
		else if (modo == 3)
		{
			
		}


		long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

		System.out.println(duration);
		System.out.println("Terminó");
	}

	private String convertirHash(String cadena)
	{
		String hashStr = "";
		try 
		{	
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



	private void crearEspacio(String cadena)
	{
		espacio.add("");
		for (int i = 0; i < cadena.length(); i++) 
		{
			char c = cadena.charAt(i);
			espacio.add(""+c);
		}
	}

	
}
