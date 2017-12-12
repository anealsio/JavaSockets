package servidor;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor{

    public static void main(String[] args) throws Exception {
 
        String fraseCliente; // Frase recebida do cliente
        String fraseMaiuscula; // Frase a ser enviada para o servidor
        int numeroLinhas; // Armazena para posteriormente enviar o numero de linhas da frase para o cliente
        String nomeDiretorio = "C:\\Prova"; // Armazena o caminho do diretorio
        File diretorio = new File(nomeDiretorio); // Armazena o arquivo
        String[]arquivos; // Arquivos no diretorio
        Scanner scanner;

        // Cabeçalho
        System.out.println("Teste de Socket - Servidor");
        System.out.println("--------------------------\n");

        // Abre um socket para escutar na porta 6789
        ServerSocket socketBoasVindas = new ServerSocket(6789);

        // Entra em um laço infinito
        while(true) {
  
            // Cada conexão de cliente é tratada como um objeto da classe Socket
            Socket socketConexao = socketBoasVindas.accept();
  
            // Prepara para receber as informações enviadas do cliente
            BufferedReader doCliente = 
                new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));

            // Prepara para enviar as informações ao cliente
            DataOutputStream paraCliente = 
                new DataOutputStream(socketConexao.getOutputStream());
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Le o comando do cliente
            fraseCliente = doCliente.readLine();
            // Verifica o comando que foi dado
            if(fraseCliente.equals("INDEX")){                
                if(diretorio.isDirectory() == false){
                    if(diretorio.exists() == false){                        
                        paraCliente.writeBytes("O diretorio nao existe!\n");
                        paraCliente.writeBytes("-1\n");
                    }else{                        
                        paraCliente.writeBytes("Esse nome nao corresponde a um diretorio.\n");
                        paraCliente.writeBytes("-1\n");;
                    }
                }else{
                    arquivos = diretorio.list();                    
                    numeroLinhas = arquivos.length + 1;                   
                    
                    paraCliente.writeBytes( "Arquivos no diretorio " + diretorio + ".\n" );
                    System.out.println("Arquivos no diretorio " + diretorio + ".");
                    for(int i = 0; i < arquivos.length; i++)
                        paraCliente.writeBytes(" " + arquivos[i]+"\n");
                    paraCliente.writeBytes( "-1\n" );
                }
            }else if(fraseCliente.charAt(0) == 'G' && fraseCliente.charAt(1) == 'E' && fraseCliente.charAt(2) == 'T'){
                String nomeArquivo[] = fraseCliente.split("GET ");
                arquivos = diretorio.list();  
                for (int i =0;i<nomeArquivo.length;i++){
                    System.out.println(i + nomeArquivo[i]);
                }
                
                //*
                int verificador = -1;
                for ( int i = 0 ; i < arquivos.length ; i++ ){
                    if( arquivos[i].equals(nomeArquivo[1]) ){
                       verificador = i;
                       break;
                    }
                }
                if ( verificador != -1){                    
                    nomeDiretorio += "\\" + nomeArquivo[1];
                    System.out.println(nomeDiretorio);
                    BufferedReader lerArquivo = new BufferedReader(new InputStreamReader(new FileInputStream(nomeDiretorio), "ISO-8859-1"));
                    String conteudo;
                    while(( conteudo = lerArquivo.readLine()) != null){                          
                        if (conteudo == null)
                            break;       
                        paraCliente.writeBytes(conteudo + "\n");
                    }
                    /*
                    String teste;
                    int count = 0;
                    while(( teste = lerArquivo.readLine()) != null){
                        //linhasArquivo[i] = lerArquivo.readLine();
                        if (teste == null)
                            break;
                        count ++;
                    }
                    
                    String linhasArquivo[] = new String[count];
                    for ( int i = 0 ; i < count ; i++ ){
                        linhasArquivo[i] = lerArquivo.readLine();
                    }
                    count ++;
                    paraCliente.writeBytes( count + "\n" );
                    paraCliente.writeBytes( "OK\n" ); 
                    for (int i = 0 ; i < linhasArquivo.length ; i++ ){
                        paraCliente.writeBytes( linhasArquivo[i] + "\n" );
                    }                     
                    ///*/
                    paraCliente.writeBytes("-1\n");
                }else{                    
                    paraCliente.writeBytes("ERROR\n");
                    paraCliente.writeBytes("-1\n");
                    //socketConexao.shutdownInput();
                    //socketConexao.shutdownOutput();
                   // socketConexao.close();
                }
                //*/
            }   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
            // Recebe a frase informada pelo cliente e passa para maiúscula
            fraseCliente = doCliente.readLine();
            fraseMaiuscula = fraseCliente.toUpperCase() + '\n';

            // Imprime os valores recebido e a ser enviado
            System.out.println("FRASE DO CLIENTE: " + fraseCliente);
            System.out.println("FRASE DO SERVIDOR: " + fraseMaiuscula);

            // Envia as informações ao cliente
            paraCliente.writeBytes(fraseMaiuscula);
            //*/
        }
    }

}