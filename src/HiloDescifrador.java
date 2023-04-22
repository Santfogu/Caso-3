import java.util.ArrayList;

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
		crearEspacio("abcdefghijklmnopqrstuvwxyz");
		if (modo == 1)
		{
			
			
		} 
		else if (modo == 2)
		{
			
		}
		else if (modo == 3)
		{
			
		}
		
	}


	private void crearEspacio(String cadena)
	{
		espacio.add("");
		for (int i = 0; i < cadena.length(); i++) {
			char c = cadena.charAt(i);
			espacio.add(""+c);
		}
	}
}
