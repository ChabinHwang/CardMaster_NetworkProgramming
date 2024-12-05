package src.cardmaster;


import src.cardmaster.main_signup_login.HomeScreen;

import java.io.BufferedReader;
import java.net.Socket;

public class User extends Thread{
    private Socket sock = null;
    private BufferedReader br = null;

    public User(Socket sock, BufferedReader br) {
        this.sock = sock;
        this.br = br;
    }

    public void run() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new HomeScreen();
        });
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            // Handle exception
        } finally {
            try {
                if (br != null) br.close();
                if (sock != null) sock.close();
            } catch (Exception ex) {
                // Handle exception
            }
        }
    }
}
