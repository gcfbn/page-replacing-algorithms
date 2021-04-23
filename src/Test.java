import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        int frames = 20;
        int length = 10000;
        int memorySize = 1000;
        int maxRequestDistance = 3;

        ArrayList<Integer> requests = RequestGenerator.generateRequest(length, memorySize, maxRequestDistance);

//        ArrayList<Integer> requests = new ArrayList<>();
//        requests.add(1);
//        requests.add(2);
//        requests.add(3);
//        requests.add(4);
//        requests.add(1);
//        requests.add(2);
//        requests.add(5);
//        requests.add(1);
//        requests.add(2);
//        requests.add(3);
//        requests.add(4);
//        requests.add(5);

        Algorithm algorithm;

        algorithm = new FIFO();
        System.out.println("FIFO: " + algorithm.executeAndCountPageFaults(requests, frames));

        algorithm = new OPT();
        System.out.println("OPT: " + algorithm.executeAndCountPageFaults(requests, frames));

        algorithm = new LRU();
        System.out.println("LRU: " + algorithm.executeAndCountPageFaults(requests, frames));

        algorithm = new AproxLRU();
        System.out.println("AproxLRU:" + algorithm.executeAndCountPageFaults(requests, frames));

        algorithm = new RR();
        System.out.println("RR: " + algorithm.executeAndCountPageFaults(requests, frames));
    }
}
