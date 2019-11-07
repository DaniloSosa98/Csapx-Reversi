package reversi.client;

import reversi.Reversi;
import reversi.ReversiException;
import reversi.ReversiProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Represents the client side of a Connect Four game. Establishes a connection
 * with the reversi.server and then responds to requests from the reversi.server (often by
 * prompting the real user).
 *
 * @author RIT CS
 */
public class ReversiClient implements ReversiProtocol {
    /**
     * Starts a new ReversiClient.
     *
     * @param args Used to specify the hostname and port of the ReversiServer
     *             through which the client will play.
     *
     * @throws ReversiException If there is a problem creating the client
     * or connecting to the server.
     */
    public static void main(String[] args) throws ReversiException, IOException {
        if(args.length != 2) {
            System.out.println(
                    "Usage: java ReversiClient " + "<hostname> <port>");
            System.exit(1);
        }
        String hostname = args[0];
        int port = Integer.valueOf(args[1]);

        Socket socket = new Socket(hostname, port);
        InputStream input = socket.getInputStream();
        Scanner networkIn = new Scanner(input);

        OutputStream output = socket.getOutputStream();
        PrintStream networkOut = new PrintStream(output);

        Scanner prompt = new Scanner(System.in);
        int i = 0;

        while(networkIn.hasNextLine()) {

            String response = networkIn.nextLine();

            if(response.contains("[") || response.contains("0") || response.contains("move")){
                System.out.println(response);

            }else if(response.contains("You ")){
                System.out.println(response);
                break;
            }else{
                System.out.println(response);
                //System.out.println(networkIn.nextLine());
                String text = prompt.nextLine();
                networkOut.println(text);
            }

        }

        // close the connection safely; insure all written data is sent
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();

    }

}
