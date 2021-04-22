import java.util.ArrayList;
import java.util.Arrays;

public class OPT implements Algorithm {
    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultsCounter = 0;
        int framesInUse = 0;

        for (int r = 0; r < requests.size(); r++) {

            int requestedPage = requests.get(r);

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
            // find frame containing page, that won't be requested for the longest time

            int highestDistance = 0;
            int indexHighestDistance = 0;

            for (int i = 0; i < framesArray.length; i++) {

                boolean foundFutureRequest = false;
                for (int j = r; j < requests.size(); j++) {
                    if (requests.get(j) == framesArray[i]) {
                        if (highestDistance < j - r)
                            highestDistance = j - r;
                        indexHighestDistance = i;
                        foundFutureRequest = true;
                        break;
                    }
                }

                // if there is no future request to frames[i], this page would be replaced
                // current highest distance does not matter
                if (!foundFutureRequest) {
                    indexHighestDistance = i;
                    break;
                }
            }

            framesArray[indexHighestDistance] = requestedPage;
            pageFaultsCounter++;
            framesInUse++;
        }

        return pageFaultsCounter;
    }
}
