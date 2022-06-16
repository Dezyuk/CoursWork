package coursework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Processing {
    /**
     * Считать пациентов с файла в виде строки JSON
     * @return JSON-строка с пациентами
     * @throws IOException файл недоступен
     */
    public static String show() throws IOException {
        try {
            return new BufferedReader(new FileReader(new File("fileData/fileData.json"))).readLine();
        } catch (IOException e) {
            System.out.println("Файл хранилища недоступен");
        }
        return "{\"showed_successfully\": false}";
    }

    /**
     * Добавление нового пациента в базу данных
     * @param patientJSON
     * @return успешность выполнения запроса
     * @throws IOException файл хранилища недоступен
     */
    public static String add(String patientJSON) throws IOException, IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Patient patient = objectMapper.readValue(patientJSON, Patient.class);
            if(Helpers.correctCardNumber(patient)){
                Main.getPatients().add(patient);
                FileData.SaveInFile(Main.getPatients().getPatientsList());
                return "{\"added_successfully\": true}";
            }else {
                System.out.println("Заданы неправильные данные пациента");
                return "{\"added_successfully\": false}";
            }
        } catch (Exception e) {
            System.out.println("Заданы неправильные данные пациента");
            return "{\"added_successfully\": false}";
        }
    }

    public static String edit(String patientJSON) throws IOException, IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode patientJsonNode = objectMapper.readTree(patientJSON);
            int id = Integer.parseInt(patientJsonNode.get("id").asText());
            Patient patient = objectMapper.treeToValue(patientJsonNode.get("patient"), Patient.class);
            Main.getPatients().set(id, patient);
            FileData.SaveInFile(Main.getPatients().getPatientsList());
            return "{\"edited_successfully\": true}";
        } catch (Exception e) {
            System.out.println("Заданы неправильные данные пациента для редактирования");
            return "{\"added_successfully\": false}";
        }
    }


    public static String delete(String ListJSON) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Main.getPatients().setPatientsList(objectMapper.readValue(ListJSON, ArrayList.class));
            FileData.SaveInFile(Main.getPatients().getPatientsList());
            return "{\"deleted_successfully\": true}";
        } catch (IOException e) {
            System.out.println("Файл хранилища недоступен");
            return "{\"deleted_successfully\": false}";
        }
    }
}
