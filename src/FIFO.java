import java.util.ArrayList;
import java.util.Arrays;

public class FIFO implements Algorithm{

    private static final int EMPTY_FRAME_CODE = -1;

    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultCounter = 0;
        int removeAsNext = 0;
        int framesInUse = 0;

        for (int requestedPage : requests){

            // check if framesArray contains requested page
            boolean pageFound = false;
            for (int frameValues : framesArray){
                if (frameValues == requestedPage){
                    pageFound = true;
                    break;
                }
            }

            if (pageFound) continue;

            // if there are empty frames, try to find one
            if (framesInUse < framesArray.length){
                for (int i=0; i<framesArray.length; i++){

                    // if frame with index i is empty
                    if (framesArray[i] == EMPTY_FRAME_CODE){
                        framesArray[i] = requestedPage;
                        pageFaultCounter++;
                        framesInUse++;
                    }
                }
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
