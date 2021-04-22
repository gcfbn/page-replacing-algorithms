import java.util.ArrayList;

public interface Algorithm {

    int EMPTY_FRAME_CODE = -1;

    int executeAndCountPageFaults(ArrayList<Integer> requests, int frames);
}
