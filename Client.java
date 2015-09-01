/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anurag
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

    private static String serverName;
    private static int PORT;
    private static String name;
    BufferedReader input;
    BufferedReader br;
    PrintWriter output;
    Socket server;
    boolean readContinue;

    public Client() {
        this.serverName = "localhost";
        this.PORT = 7520;
        this.name = "";
    }

    public void run() {
        try {
            System.out.println("Connecting to " + serverName + " on port " + PORT + "...");
            server = new Socket(serverName, PORT);
            System.out.println("Connected to " + server.getRemoteSocketAddress());
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter name : ");
            while (name.equals("")) {
                name = br.readLine();
            }
            output.println(name);
            System.out.println("Welcome " + name);
            Thread r = new Read();
            Thread w = new Write();
            r.start();
            w.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    class Read extends Thread {
        public void run() {
            String s;
            while (true) {
                try {
                    s = input.readLine();
                    if (s.equals("exit()")) {
                        break;
                    }
                    System.out.println("Server : " + s);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
    
    class Write extends Thread {
        public void run() {
            try {
                String s = "";
                while (true) {
                    s = br.readLine();
                    output.println(s);
                    if (s.equals("exit()")) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
}