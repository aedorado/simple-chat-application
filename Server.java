
/**
 *
 * @author Anurag
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    private static final int PORT = 7520;
    private ServerSocket listener = null;
    BufferedReader br;
    BufferedReader input;
    PrintWriter output;
    String name;

    Server() throws IOException {
        listener = new ServerSocket(PORT);
    }

    public void run() {
        System.out.println("Server Listening on port : " + listener.getLocalPort());
        try {
            Socket cliListener = listener.accept();
            System.out.println("Connected to " + cliListener.getRemoteSocketAddress());
            input = new BufferedReader(new InputStreamReader(cliListener.getInputStream()));
            output = new PrintWriter(cliListener.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(System.in));
            new Read().start();
            new Write().start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Thread t = new Server();
        t.start();
    }
    
    class Write extends Thread {
        public void run() {
            try {
                String s = "";
                while (true) {
                    s = br.readLine();
                    //System.out.println(s);
                    output.println(s);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    class Read extends Thread {
        public void run() {
            try {
                String s;
                name = input.readLine();
                System.out.println(name + " joined.");
                s = input.readLine();
                while (true) {
                    if (s.equals("exit()")) {
                        System.out.println("Client Disonnected");
                        output.println("exit()");
                        break;
                    }
                    System.out.println("Client : " + s);
                    s = input.readLine();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
