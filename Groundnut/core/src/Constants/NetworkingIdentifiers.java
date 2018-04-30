package Constants;

public class NetworkingIdentifiers {

    public static final int IDENTIFIER_LENGTH = 3;

    //client-packet identifiers
    public static final byte[] CONNECT_REQUEST_IDENTIFIER = {0, 0, 0};
    public static final byte[] MOVEMENT_PACKET_IDENTIFIER = {1, 0, 0};

    //Server-packet identifiers
    public static final byte[] REJECTION_IDENTIFIER = {0, 1, 0};
    public static final byte[] ACCEPT_IDENTIFIER = {1, 1, 0};
    public static final byte[] SIMULATION_STATE_IDENTIFIER = {0, 0, 1};

}
