import java.util.Scanner;

public class EvaluadorProteccion {



    //Atributos
	

    //Métodos
    public void imprimirTitulo ( ) 
    {
        System.out.println ( "==================================================" );
        System.out.println ( "ISIS 2203 - Infraestructura Computacional - 202310" );
        System.out.println ( "------------ Caso 3 - Seguridad ------------" );
        System.out.println ( "---- s.forerog2 - código 2 - código 3 -----" );
        System.out.println ( "==================================================" );
    }


    public static void main(String[] args) throws Exception 
    {
        EvaluadorProteccion evaluador = new EvaluadorProteccion();
		evaluador.imprimirTitulo();
		Scanner sc = new Scanner ( System.in );
        
		System.out.println( "\nPor favor ingrese el algoritmo (H) de generación de código criptográfico de hash (SHA256 o SHA512): ");
		String algHash = sc.nextLine();
        System.out.println( "\nPor favor ingrese la cadena (C) que representa el código criptográfico de hash de una contraseña: ");
		String cadenaCodigo = sc.nextLine();
        System.out.println( "\nPor favor ingrese una secuencia de 2 caracteres (S) que representa la sal: ");
		String sal = sc.nextLine();
        System.out.println( "\nPor favor ingrese el número de threads que implementará (1 o 2): ");
		String numThreads = sc.nextLine();



    }
}
