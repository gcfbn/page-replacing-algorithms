import java.util.ArrayList;
import java.util.Arrays;

public class LRU implements Algorithm {

    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];
        int[] timestamps = new int[frames];

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultsCounter = 0;
        int framesInUse = 0;

        for (int r = 0; r < requests.size(); r++) {

            int requestedPage = requests.get(r);

            // if framesArray already contains requested page, go to the next request
            int requestedPageIndex = ArrayHelper.indexOf(requestedPage, framesArray);
            if (requestedPageIndex != -1) {
                // set timestamp of this frame
                timestamps[requestedPageIndex] = r;
                continue;
            }

            // if there are empty frames, try to find one
            // then, write requested page into this frame and go to the next request
            if (framesInUse < framesArray.length) {
                int emptyFrameIndex = ArrayHelper.indexOf(EMPTY_FRAME_CODE, framesArray);
                framesArray[emptyFrameIndex] = requestedPage;
                framesInUse++;
                pageFaultsCounter++;
                timestamps[emptyFrameIndex] = r;
                continue;
            }

            // if all frames are in use and any frame does not contain requested page,
            // find frame with lowest timestamp and replace it with requested page

            int minimumTimestamp = requests.size();
            int minimumTimestampIndex = -1;

            for (int i = 0; i < timestamps.length; i++) {
                if (timestamps[i] < minimumTimestamp) {
                    minimumTimestamp = timestamps[i];
                    minimumTimestampIndex = i;
                }
            }

            pageFaultsCounter++;
            timestamps[minimumTimestampIndex] = r;
            framesArray[minimumTimestampIndex] = requestedPage;
            framesInUse++;
        }

        return pageFaultsCounter;
    }
}
