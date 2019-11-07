package reversi.server;

import reversi.Reversi;
import reversi.ReversiException;
import reversi.ReversiProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The ReversiServer waits for incoming client connections and
 * pairs them off to play the game.
 *
 * @author RIT CS
 */
public class ReversiServer implements ReversiProtocol {
    /**
     * Starts a new ReversiServer. Simply creates the server and runs.
     *
     * @param args Used to specify the port on which the server should listen
     *             for incoming client connections.
     * @throws ReversiException If there is an error starting the server.
     */
    public static void main(String[] args) throws ReversiException, IOException {
        if (args.length != 2) {
            System.out.println("Usage: java ReversiServer DIM port");
            System.exit(1);
        }
        int DIM = Integer.valueOf(args[0]);
        int port = Integer.valueOf(args[1]);

        ServerSocket server = new ServerSocket(port);
        System.out.println("DIM " + DIM + " port " + port);

        System.out.println("Waiting for player one...");
        Socket client1 = server.accept();
        InputStream input1 = client1.getInputStream();
        OutputStream output1 = client1.getOutputStream();
        Scanner networkIn1 = new Scanner(input1);
        PrintStream networkOut1 = new PrintStream(output1);
        System.out.println("Player 1 connected!");

        System.out.println("Waiting for player two...");
        Socket client2 = server.accept();
        InputStream input2 = client2.getInputStream();
        OutputStream output2 = client2.getOutputStream();
        Scanner networkIn2 = new Scanner(input2);
        PrintStream networkOut2 = new PrintStream(output2);
        System.out.println("Player 2 connected!");

        System.out.println("Starting game!");

        Reversi r = new Reversi(DIM);
        int turn = 0;


        // loop while the connection is open
        while(!r.gameOver()) {

            String board = r.toString();
            networkOut1.println(board);
            networkOut2.println(board);

            if (turn%2==0){

                // read the next line of text from the client
                networkOut1.print("Your turn ! Enter row column: ");
                String line = networkIn1.nextLine();

            }else{
                // read the next line of text from the client
                networkOut2.print("Your turn ! Enter row column: ");
                String line = networkIn2.nextLine();

            }
            turn++;
        }


        // attempt to safely close the socket and server socket
        client1.shutdownInput();
        client1.shutdownOutput();
        client2.shutdownInput();
        client2.shutdownOutput();
        server.close();
    }
}
