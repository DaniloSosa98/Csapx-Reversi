package reversi.client;

import reversi.ReversiException;
import reversi.ReversiProtocol;

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
    public static void main(String[] args) throws ReversiException {
        if(args.length != 2) {
            System.out.println(
                    "Usage: java ReversiClient " + "<hostname> <port>");
            System.exit(1);
        }
    }
}
