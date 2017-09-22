package WorkOrderTested;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.*;

public class Processing {

    Map<Status, Set<WorkOrderPOJO>> workMap = new HashMap<>();



    public static void main(String args[]) {
        Processing processing = new Processing();
        processing.defaultWork();
        processing.processWorkOrders();
    }


    public void defaultWork() {
        workMap.put(Status.INITIAL, new HashSet<>());
        workMap.put(Status.ASSIGNED, new HashSet<>());
        workMap.put(Status.IN_PROGRESS, new HashSet<>());
        workMap.put(Status.DONE, new HashSet<>());
    }

    public void processWorkOrders() {
        try {
            readIt();
            moveIt();
            Thread.sleep(5000L);
            processWorkOrders();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void readIt() {
        File TheWorkOrder = new File(".");
        File files[] = TheWorkOrder.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrderPOJO working = mapper.readValue(new File(f.getName()), WorkOrderPOJO.class);
                    Status orderStatus = working.getStatus();
                    putWorkOrderInMap(orderStatus, working);
                    f.delete();
                    System.out.println(workMap);
                } catch (IOException e) {
                    e.fillInStackTrace();
                }

            }
        }

    }

    private void moveIt() {

        Set<WorkOrderPOJO> Step1 = workMap.get(Status.INITIAL);
        Set<WorkOrderPOJO> Step2 = workMap.get(Status.ASSIGNED);
        Set<WorkOrderPOJO> Step3 = workMap.get(Status.IN_PROGRESS);


        defaultWork();

        workMap.put(Status.ASSIGNED, Step1);
        workMap.put(Status.IN_PROGRESS, Step2);
        workMap.put(Status.DONE, Step3);


        System.out.println(workMap);


    }


    private void putWorkOrderInMap(Status status, WorkOrderPOJO workOrder) {
        Set<WorkOrderPOJO> workMapSet = workMap.get(status);
        workMapSet.add(workOrder);
        workMap.put(status, workMapSet);

    }
}

