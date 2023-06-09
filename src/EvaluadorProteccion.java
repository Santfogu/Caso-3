import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EvaluadorProteccion {



    //Atributos
    String algHash;
	String cadenaCodigo;
    String sal;
    String numThreads;
    public static char[] espacio;
    
    //Métodos
    private void imprimirTitulo ( ) 
    {
        System.out.println ( "==================================================" );
        System.out.println ( "ISIS 2203 - Infraestructura Computacional - 202310" );
        System.out.println ( "--------------- Caso 3 - Seguridad ---------------" );
        System.out.println ( "---- s.forerog2 - jo.cabanzo - s.tenjov -----" );
        System.out.println ( "==================================================" );
    }


    private void modoPrueba()
    {
        String archivo = "datosPrueba.txt";
        //Estructura archivo de prueba->H,C,S,T
        //(H) un algoritmo de generación de código criptográfico de hash
        //(C) una cadena que representa el código criptográfico de hash de una contraseña
        //(S) una secuencia de 2 caracteres que representa la sal
        //(T) número de threads que implementará

        int numeroPrueba = 0;    

        File file = new File("Resultados.txt");
		FileWriter fr = null;
        try {
			fr = new FileWriter(file);
		} 
		catch (IOException e) {
			System.out.println("Ocurrió un error al crear el archivo.");
			e.printStackTrace();
		}

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                
                String[] campos = linea.split(",");
                algHash = campos[0].trim();
                cadenaCodigo = campos[1].trim();
                sal = campos[2].trim();
                numThreads = campos[3].trim();
                
                numeroPrueba ++;
                String tiempoRespuestaThread = "";
                String password = "";


                if (numThreads.equals("1"))
                {
                    HiloDescifrador hilo = new HiloDescifrador(1, algHash, cadenaCodigo, sal, espacio); 
                    hilo.start();
                    hilo.join();

                    if (hilo.encontro()){
                        tiempoRespuestaThread = hilo.darTiempoRespuesta();
                        password = hilo.darPassword();
                    }


                }
                else if (numThreads.equals("2"))
                {
                    HiloDescifrador hilo1 = new HiloDescifrador(2, algHash, cadenaCodigo, sal, espacio);
                    HiloDescifrador hilo2 = new HiloDescifrador(3, algHash, cadenaCodigo, sal, espacio);
                    hilo1.start();
                    hilo2.start();
                    hilo1.join();
                    hilo2.join();

                    if (hilo1.encontro()){
                        tiempoRespuestaThread = hilo1.darTiempoRespuesta();
                        password = hilo1.darPassword();
                    }
                    else if (hilo2.encontro()){
                        tiempoRespuestaThread = hilo2.darTiempoRespuesta();
                        password = hilo2.darPassword();
                    }

                }

                HiloDescifrador.reiniciarEncontrado();



                System.out.println("\n-------------------------------------");
                System.out.println("Terminó prueba numero " + numeroPrueba);
                System.out.println("-------------------------------------\n");

                
                if (tiempoRespuestaThread != ""){
                    System.out.println("Tiempo de respuesta: " + tiempoRespuestaThread);
                }
                System.out.println("\n");

                System.out.println ("Algoritmo de hash: " + algHash );
                System.out.println ("Codigo hash: " + cadenaCodigo );
                System.out.println ("Sal: " + sal );
                System.out.println("Numero Threads: " + numThreads + "\n");
                System.out.println("Contraseña Encontrada: " + password);

                
                System.out.println("\n");


                //Escibir resultados por ciclo  
                fr.write("V: " + password +
                        " T: " + tiempoRespuestaThread + 
                        " H: " + algHash +
                        " S: " + sal +
                        " NumT: " + numThreads + "\n");
                fr.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        finally{
			try {
				fr.close();
			} 
			catch (IOException e) {
				System.out.println("Ocurrió un error al crear el archivo.");
				e.printStackTrace();
			}
		}

    }
    
	

    public static void main(String[] args) throws Exception 
    {

        espacio = new char[27];
        espacio[0] = '\0';  // cadena vacía
        for (int i = 1; i <= 26; i++) {
            espacio[i] = (char) (i + 96);  // caracteres de 'a' a 'z'
        }
        


        EvaluadorProteccion evaluador = new EvaluadorProteccion();
		evaluador.imprimirTitulo();
		Scanner sc = new Scanner ( System.in );
        System.out.println( "\nPor favor ingrese 1 para ingresar datos por consola y 2 para correr archivo de prueba:");
		String modo = sc.nextLine();
        if (modo.equals("1"))
        {
            System.out.println( "\nPor favor ingrese el algoritmo (H) de generación de código criptográfico de hash (SHA-256 o SHA-512): ");
            String algHash = sc.nextLine();
            System.out.println( "\nPor favor ingrese la cadena (C) que representa el código criptográfico de hash de una contraseña: ");
            String cadenaCodigo = sc.nextLine();
            System.out.println( "\nPor favor ingrese una secuencia de 2 caracteres (S) que representa la sal: ");
            String sal = sc.nextLine();
            System.out.println( "\nPor favor ingrese el número de threads que implementará (1 o 2): ");
            String numThreads = sc.nextLine();
            
            String tiempoRespuestaThread = "";
            String password = "";


            if (numThreads.equals("1"))
            {
                HiloDescifrador hilo = new HiloDescifrador(1, algHash, cadenaCodigo, sal, espacio); 
                hilo.start();
                hilo.join();

                if (hilo.encontro()){
                    tiempoRespuestaThread = hilo.darTiempoRespuesta();
                    password = hilo.darPassword();
                }


            }
            else if (numThreads.equals("2"))
            {
                HiloDescifrador hilo1 = new HiloDescifrador(2, algHash, cadenaCodigo, sal, espacio);
                HiloDescifrador hilo2 = new HiloDescifrador(3, algHash, cadenaCodigo, sal, espacio);
                hilo1.start();
                hilo2.start();
                hilo1.join();
                hilo2.join();

                if (hilo1.encontro()){
                    tiempoRespuestaThread = hilo1.darTiempoRespuesta();
                    password = hilo1.darPassword();
                }
                else if (hilo2.encontro()){
                    tiempoRespuestaThread = hilo2.darTiempoRespuesta();
                    password = hilo2.darPassword();
                }

            }

            HiloDescifrador.reiniciarEncontrado();



            System.out.println("\n-------------------------------------");
            System.out.println("Prueba Terminada");
            System.out.println("-------------------------------------\n");

            
            if (tiempoRespuestaThread != ""){
                System.out.println("Tiempo de respuesta: " + tiempoRespuestaThread);
            }
            System.out.println("\n");

            System.out.println ("Algoritmo de hash: " + algHash );
            System.out.println ("Codigo hash: " + cadenaCodigo );
            System.out.println ("Sal: " + sal );
            System.out.println("Numero Threads: " + numThreads + "\n");
            System.out.println("Contraseña Encontrada: " + password);

            
            System.out.println("\n");



        }
        else if (modo.equals("2"))
        {
            evaluador.modoPrueba();
        
        }

    }

   
}
