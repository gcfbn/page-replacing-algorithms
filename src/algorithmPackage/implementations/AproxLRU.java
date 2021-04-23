package algorithmPackage.implementations;

import algorithmPackage.otherClasses.Algorithm;
import algorithmPackage.otherClasses.ArrayHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class AproxLRU implements Algorithm {

    private static class QueueElement {
        private final int index;
        private final int frame;
        private boolean sign;

        public QueueElement(int index, int frame, boolean sign) {
            this.index = index;
            this.frame = frame;
            this.sign = sign;
        }

        public int getIndex() {
            return index;
        }

        public int getFrame(){
            return frame;
        }

        public boolean getSign() {
            return sign;
        }

        public void setSign(boolean sign) {
            this.sign = sign;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof QueueElement)) return false;
            QueueElement objElement = (QueueElement) obj;
            return objElement.getIndex() == this.getIndex();
        }
    }

    @Override
    public int executeAndCountPageFaults(ArrayList<Integer> requests, int frames) {

        int[] framesArray = new int[frames];
        ArrayList<QueueElement> queue = new ArrayList<>();

        // fill array with values which mark frame as empty
        Arrays.fill(framesArray, EMPTY_FRAME_CODE);

        int pageFaultsCounter = 0;
        int framesInUse = 0;

        for (int requestedPage : requests) {

            // if framesArray already contains requested page, go to the next request
            int requestedPageIndex = ArrayHelper.indexOf(requestedPage, framesArray);
            if (requestedPageIndex != -1) {
                // try to find in queue frame containing requestedPageIndex
                for (QueueElement e : queue) {
                    if (e.getIndex() == requestedPageIndex) {
                        e.setSign(true);
                        break;
                    }
                }
                continue;
            }

            // if there are empty frames, try to find one
            // then, write requested page into this frame and go to the next request
            if (framesInUse < framesArray.length) {
                int emptyFrameIndex = ArrayHelper.indexOf(EMPTY_FRAME_CODE, framesArray);
                framesArray[emptyFrameIndex] = requestedPage;
                framesInUse++;
                pageFaultsCounter++;
                queue.add(new QueueElement(requestedPage, emptyFrameIndex, true));
                continue;
            }

            // if all frames are in use and any frame does not contain requested page,
            // remove first element with sign equal 0
            boolean removedElementFromQueue = false;
            while (!removedElementFromQueue) {
                QueueElement firstElement = queue.get(0);
                if (firstElement.getSign()) {
                    firstElement.setSign(false);
                    queue.add(firstElement);
                } else {
                    framesArray[firstElement.getFrame()] = requestedPage;
                    queue.add(new QueueElement(requestedPage, firstElement.getFrame(), true));
                    removedElementFromQueue = true;
                }
                queue.remove(0);
            }
            pageFaultsCounter++;

        }

        return pageFaultsCounter;
    }
}
