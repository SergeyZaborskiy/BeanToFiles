import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        //List for saving Objects Employee
        List<Employee> employeeList = new ArrayList<>();

        System.out.println("Hello!");
        System.out.println("We're read file from .csv file and fill Employee structure");
        FileExplorer.parseCsvToBeans("data.csv", employeeList);
        System.out.println("Done!");
        System.out.println("Now we printing our Employee list from structure");
        employeeList.forEach(System.out::println);
        System.out.println("Done!");
        System.out.println("Now writing employeeList to JSON File");
        FileExplorer.parseListToJsonFile("data.json", employeeList);
        System.out.println("Done!");
        System.out.println("Now clearing Employee from structure");
        FileExplorer.clearObjects(employeeList);
        System.out.println("Done!");
        System.out.println("Now we read file from .json file and fill Employee structure");
        FileExplorer.parseJsonFileToBean("data.json", employeeList);
        System.out.println("Done!");
        System.out.println("Now we printing our Employee list from structure");
        employeeList.forEach(System.out::println);
        System.out.println("Done!");
        System.out.println("Now we clearing inner structure");
        FileExplorer.clearObjects(employeeList);
        System.out.println("Done!");
        System.out.println("We're read file from .xml file and fill Employee structure");
        FileExplorer.parseXMLtoBeans("data.xml", employeeList);
        System.out.println("Done!");
        System.out.println("Now we printing our Employee list from structure");
        employeeList.forEach(System.out::println);
        System.out.println("Done!");
        System.out.println("Now writing employeeList to JSON File");
        FileExplorer.parseListToJsonFile("data2.json", employeeList);
        System.out.println("Done!");

    }
}
