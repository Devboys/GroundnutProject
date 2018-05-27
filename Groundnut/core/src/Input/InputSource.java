package Input;

/**Marks the concrete object as a source of input, that can be used whenever input is needed.*/
public interface InputSource {
    PlayerInput getInput();
}
