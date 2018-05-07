package ClientNetworking;

public interface NetworkingHandler {
    /**Closes both the input- and output-threads of the handler, as well as any socket-connections(if applicable)*/
    void close();
}
