/**
 * Write a program that will listen on port 4242. When a client connects print
 "Welcome to Little Brother..." to the client and then repeat the information it
 received until the input is "Stop".  Then disconnect the client and stop listening.
 Below is a sample of how to setup a server with opening a port and listening on it
 and printing out to it. Note in the example below the connection to the client is closed
 after each line which is not the desired functionality.
 Also the server needs to be stopped when the word Stop is typed.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LittleBrother {

    public static void main(String[] args) {
        final int PORT_NUMBER = 4242;


            try (

                ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
                /**
                 *  Declaration, final or effectively final variable required ??
                 */
                //System.out.println("Listening on port " + PORT_NUMBER);
                Socket clientSock = serverSock.accept();
                PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
                //System.out.println("Connected client");
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

        ){
                System.out.println("Connected to asdf" + PORT_NUMBER);
                String outputText, inputText;
                LittleBrotherProtocol lbp = new LittleBrotherProtocol();
                outputText = lbp.processInput("Welcome little brother...");
                out.println(outputText);

               while((inputText = br.readLine()) != null){
                   outputText = lbp.processInput(inputText);
                   out.println(outputText);
                   if(outputText.toLowerCase().equals("stop")){
                       outputText = lbp.processInput("Disconnected client...");
                       out.println(outputText);

                       /**
                        * Q: Since this is in a loop and is written after each iteration, is .flush() needed
                        * since .close() flushes and closes?
                        */
                       br.close();
                       serverSock.close();
                       clientSock.close();

                       break;
                   }


                }


              } catch(Exception e) {
                e.printStackTrace();
            }

    }
}