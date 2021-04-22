import java.util.ArrayList;
import java.util.Random;

public class RequestGenerator {


    public static ArrayList<Integer> generateRequest(int length, int memorySize, int maxRequestDistance){

        Random randomGenerator = new Random();
        int startIndex = randomGenerator.nextInt(memorySize);

        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(startIndex);

        int previousRequest = startIndex;

        for (int i=1; i<length; i++){
            // next requested page will have index in range
            // [previousRequest - maxRequestDistance, previousRequest + maxRequestDistance]
            int nextRequest = previousRequest + randomGenerator.nextInt(2 * maxRequestDistance + 1) - maxRequestDistance;

            // if index is out of bounds (index >= memorySize),
            // set it to [memorySize - maxRequestDistance, memorySize - 1]
            if (nextRequest >= memorySize)
                nextRequest = memorySize - (randomGenerator.nextInt(maxRequestDistance) + 1);

            // if index is out of bounds (index < 0), set it to [0,4]
            else if (nextRequest < 0) nextRequest = randomGenerator.nextInt(maxRequestDistance);

            requests.add(nextRequest);
            previousRequest = nextRequest;
        }

        return requests;
    }
}
