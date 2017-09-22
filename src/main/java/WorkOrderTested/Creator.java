package WorkOrderTested;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

    public class Creator {

        public static void main(String args[]) {
            Creator creator = new Creator();
            creator.NewOrder();
        }

        public void NewOrder() {
            WorkOrderPOJO workorderpojo = new WorkOrderPOJO();
            Scanner scanner = new Scanner(System.in);


            workorderpojo.setStatus(Status.INITIAL);

            System.out.println("ID: ");
            workorderpojo.setId(scanner.nextInt());

            System.out.println("Person entering new order: ");
            workorderpojo.setSenderName(scanner.next());

            System.out.println("Description: ");
            workorderpojo.setDescription(scanner.next());

            ObjectMapper mapper = new ObjectMapper();

            File file = new File(workorderpojo.getId() + ".json");

            try {
                mapper.writeValue(file, workorderpojo);
                String consoleJson = mapper.writeValueAsString(workorderpojo);
                System.out.println(consoleJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (true) {
                    NewOrder();
                    Thread.sleep(4500);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

