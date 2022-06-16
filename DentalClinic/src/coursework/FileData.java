package coursework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileData {

    /**
     * Сохранить в файл-хранище всех пациентов
     * @param patients объект пациентов
     * @throws IOException файл недоступен
     */
    public static void SaveInFile(List<Patient> patients) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("fileData/fileData.json"), patients);
    }

    /**
     * Считать с файла-хранилища всех пациентов
     * @return список пациентов
     * @throws IOException файл недоступен
     */
    public static ArrayList<Patient> LoadFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("fileData/fileData.json"), ArrayList.class);
    }
}
