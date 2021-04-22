import java.util.ArrayList;
import java.util.Arrays;

public class FIFO implements Algorithm {

    private static final int EMPTY_FRAME_CODE = -1;

    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultCounter = 0;
        int removeAsNext = 0;
        int framesInUse = 0;

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
                pageFaultCounter++;
                continue;
            }

            // if all frames are in use and any frame does not contain requested page,
            // replace frame with removeAsNext index by requested page
            pageFaultCounter++;
            framesArray[removeAsNext] = requestedPage;

            // set removeAsNext to next frame or 0, if current index is last index in array
            removeAsNext = (removeAsNext + 1) % framesArray.length;
        }

        return pageFaultCounter;
    }
}
