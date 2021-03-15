import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClientEx1 {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = systemIn.readLine()) != null) {
                out.println(userInput);
                out.flush();

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

            socket.shutdownOutput();
            String media = in.readLine();
            out.println("A média é" +media);

            socket.close(); //alguns programas nao dão nem close e/ou nem shutdownInput porque o cliente ao terminar basta não necessita

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}