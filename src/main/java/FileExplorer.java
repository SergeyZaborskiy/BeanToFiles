import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileExplorer {

    //Convert CSV file to Java Object, write Objects to List
    public static void parseCsvToBeans(String filePath, List<Employee> employeeList) {

        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            employeeList.addAll(csv.parse());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Convert Java Object to Json File, write Objects from List
    public static <T> void parseListToJsonFile(String filePath, List<T> list) {

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(fromBeanToJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Convert Json file to Java Object, write Objects to List
    public static void parseJsonFileToBean(String filePath, List<Employee> employeeList) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type type = new TypeToken<List<Employee>>() {
        }.getType();


        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Employee> read = gson.fromJson(reader.readLine(), type);
            employeeList.addAll(read);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Method for clearing list
    public static <T> void clearObjects(List<T> list) {
        list.clear();
    }

    //Private method to convert Object to json String, return String
    private static <T> String fromBeanToJson(T obj) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }

    //Hate XMl, convert XML file to Java Object, write Objects to List, work on strong magick
    public static void parseXMLtoBeans(String filePath, List<Employee> employeeList) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(filePath);
        Node root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element elementClass = (Element) childNodes.item(i);
                employeeList.add(createEmployeeFromXml(elementClass));
            }
        }
    }

    //Private constructor for XML parse
    private static Employee createEmployeeFromXml(Element elementClass) {
        String id = elementClass.getElementsByTagName("id").item(0).getTextContent();
        String firstName = elementClass.getElementsByTagName("firstName").item(0).getTextContent();
        String lastName = elementClass.getElementsByTagName("lastName").item(0).getTextContent();
        String country = elementClass.getElementsByTagName("country").item(0).getTextContent();
        String age = elementClass.getElementsByTagName("age").item(0).getTextContent();

        return new Employee(id, firstName, lastName, country, age);
    }

}