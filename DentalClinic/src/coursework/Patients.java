package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Patients implements Serializable {
    private ArrayList<Patient> patientsList;

    public Patients() {
        patientsList = new ArrayList<>();
    }
    public ArrayList<Patient> getPatientsList() {
        return patientsList;
    }

    public void setPatientsList(ArrayList<Patient> patientsList){
        this.patientsList = patientsList;
    }

    /**
     * Добавить нового пациента в список
     * @param patient объект пациента
     */
    public void add(Patient patient){
        patientsList.add(patient);
    }

    /**
     * Изменить пациента в списке
     * @param id порядковый номер пациента
     * @param patient пациент
     */
    public void set(int id, Patient patient){
        patientsList.set(id, patient);
    }

    /**
     * Удалить пациента из списка
     * @param patient пациент
     */
    public void remove(Patient patient){
        patientsList.remove(patient);
    }

    /**
     * Удаление пациента из списка по номеру
     * @param num номер пациента в списке
     */
    public void remove(int num) throws IllegalArgumentException {
        if (num < patientsList.size() && num >= 0){
            patientsList.remove(num);
        } else {
            throw new IllegalArgumentException("Некорректный индекс пациента к удалению");
        }
    }

    @Override
    public String toString() {
        return "Patients{" +
                "patientsList=" + patientsList +
                '}';
    }
}
