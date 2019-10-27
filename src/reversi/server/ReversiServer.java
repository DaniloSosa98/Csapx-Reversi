package reversi.server;

import reversi.ReversiException;
import reversi.ReversiProtocol;

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
    public static void main(String[] args) throws ReversiException {
        if (args.length != 2) {
            System.out.println("Usage: java ReversiServer DIM port");
            System.exit(1);
        }
    }
}
