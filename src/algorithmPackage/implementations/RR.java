package algorithmPackage.implementations;

import algorithmPackage.otherClasses.Algorithm;
import algorithmPackage.otherClasses.ArrayHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RR implements Algorithm {

    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultsCounter = 0;
        int framesInUse = 0;

        Random randomGenerator = new Random();

        for (int requestedPage : requests) {

            // if framesArray already contains requested page, go to the next request
            if (ArrayHelper.indexOf(requestedPage, framesArray) != -1)
                continue;

            // if there are empty frames, try to find one
            // then, write requested page into this frame and go to the next request
            if (framesInUse < framesArray.length) {
                int emptyFrameIndex = ArrayHelper.indexOf(EMPTY_FRAME_CODE, framesArray);
                framesArray[emptyFrameIndex] = requestedPage;
                framesInUse++;
                pageFaultsCounter++;
                continue;
            }

            // if all frames are in use and any frame does not contain requested page,
            // generate random index and replace frame with this index
            int randomIndex = randomGenerator.nextInt(framesArray.length);
            framesArray[randomIndex] = requestedPage;
            pageFaultsCounter++;
        }

        return pageFaultsCounter;
    }

}
