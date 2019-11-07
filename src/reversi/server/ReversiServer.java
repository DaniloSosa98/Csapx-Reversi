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
            networkOut1.print(board);
            networkOut2.print(board);

            if (turn%2==0){

                // read the next line of text from the client
                networkOut1.println("Your turn ! Enter row column: ");
                String move = networkIn1.nextLine();
                r.makeMove(Character.getNumericValue(move.charAt(0)), Character.getNumericValue(move.charAt(2)));

                networkOut1.println("A move has been made in row " + move.charAt(0) + " column " + move.charAt(2));
                networkOut2.println("A move has been made in row " + move.charAt(0) + " column " + move.charAt(2));

            }else{
                // read the next line of text from the client
                networkOut2.println("Your turn ! Enter row column: ");
                String move = networkIn2.nextLine();
                r.makeMove(Character.getNumericValue(move.charAt(0)), Character.getNumericValue(move.charAt(2)));

                networkOut2.println("A move has been made in row " + move.charAt(0) + " column " + move.charAt(2));
                networkOut1.println("A move has been made in row " + move.charAt(0) + " column " + move.charAt(2));
            }
            turn++;
        }

        if(r.getWinner()== Reversi.Move.PLAYER_ONE){
            networkOut1.print("You won! Yay!");
            networkOut2.print("You lost! Boo!");
        }else if(r.getWinner()== Reversi.Move.PLAYER_TWO){
            networkOut1.print("You lost! Boo!");
            networkOut2.print("You won! Yay!");
        }else{
            networkOut1.print("You tied! Meh!");
            networkOut2.print("You tied! Meh!");
        }

        // attempt to safely close the socket and server socket
        client1.shutdownInput();
        client1.shutdownOutput();
        client2.shutdownInput();
        client2.shutdownOutput();
        server.close();
    }
}
