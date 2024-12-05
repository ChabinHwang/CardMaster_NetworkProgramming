package src.cardmaster.main_signup_login;


import src.cardmaster.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static Socket sock = null;
    public static BufferedReader br = null;
    public static PrintWriter pw = null;
    public static void main(String[] args) {
        boolean endflag = false;
        try{
            sock = new Socket("localhost", 10000);
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            //System.out.print("ID를 입력하세요: ");
            //pw.println(keyboard.readLine());
            //pw.flush();
            User user=new User(sock, br);
            user.start();
            String line = null;

            /**
             * 아래 while문처럼 종료문 작성해야함
             */
//            while ((line = keyboard.readLine()) != null) {
//                pw.println(line);
//                pw.flush();
//                if (line.equals("/quit")) {
//                    endflag = true;
//                    break;
//                }
//            }



        } catch (Exception ex){
            if (!endflag)
                System.out.println(ex);
        }

    }
}
