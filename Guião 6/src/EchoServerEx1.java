import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class EchoServerEx1 {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                String line;
                int soma = 0;
                int numTot = 0;
                while ((line = in.readLine()) != null) {
                    int n = Integer.parseInt(line);
                    soma += n;
                    numTot++;
                    out.println(soma); //o printWriter converte o int em String
                    out.flush();
                }
                numTot = numTot > 0 ? numTot : 1; //para a exceção do numTotal ser 0. Divide by 0.
                out.println(soma/numTot);
                out.flush(); //se nao tivessemos isto o print da media dava null, porque o que estava no buffer do printwriter não era escrito

                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}