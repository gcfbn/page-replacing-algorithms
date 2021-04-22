import java.util.ArrayList;
import java.util.Random;

public class RequestGenerator {

    public static ArrayList<Integer> generateRequest(int length, int memorySize){

        Random randomGenerator = new Random();
        int startIndex = randomGenerator.nextInt(memorySize);

        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(startIndex);

        int previousRequest = startIndex;

        for (int i=1; i<length; i++){
            // next requested page will have index in range [previousRequest - 10, previousRequest + 10]
            int nextRequest = previousRequest + randomGenerator.nextInt(21) - 10;

            // if index is out of bounds (index >= memorySize), set it to [memorySize - 10, memorySize - 1]
            if (nextRequest >= memorySize) nextRequest = memorySize - randomGenerator.nextInt(10) + 1;

            // if index is out of bounds (index < 0), set it to [0,9]
            else if (nextRequest < 0) nextRequest = randomGenerator.nextInt(10);

            requests.add(nextRequest);
            previousRequest = nextRequest;
        }

        return requests;
    }
}
