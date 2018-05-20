package testsocket.clientTest; /*
 * create by weikunpeng
 * 2018/5/19 19:07
 */

import testsocket.Talk;

import java.io.*;
import java.net.Socket;

public class clientTest {

    public static void main(String[] args) throws IOException {

        Socket socket=new Socket("127.0.0.1",9999);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pw.println("11 wei 11 22");
        pw.flush();

        String line = is.readLine();

        System.out.println(line);

        pw.close();
        is.close();
        socket.close();


    }
}
