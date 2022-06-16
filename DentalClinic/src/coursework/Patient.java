package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient implements Serializable {
    private String fullName;
    private int registrationCardNumber;
    private String typeOfWork;
    private boolean paid;
    private int debtSum;

    public String getFullName() {
        return fullName;
    }
    public int getRegistrationCardNumber() {
        return registrationCardNumber;
    }
    public String getTypeOfWork() {
        return typeOfWork;
    }
    public boolean isPaid() {
        return paid;
    }
    public int getDebtSum() {
        return debtSum;
    }

    public Patient(String fullName, int registrationCardNumber, String typeOfWork,
                   boolean paid, int debtSum) throws IllegalArgumentException {
        try {
            this.setFullName(fullName);
            this.setRegistrationCardNumber(registrationCardNumber);
            this.setTypeOfWork(typeOfWork);
            this.setPaid(paid);
            this.setDebtSum(debtSum);
        } catch (Exception e) {
            throw new IllegalArgumentException("Переданы неправильные значения для создания объекта пациента");
        }

    }
    public Patient(){}

    public void setFullName(String fullName) throws IllegalArgumentException {
        if (Helpers.checkFirstLetterCapital(fullName) && fullName.length() < 90) {
            this.fullName = Helpers.cleanString(fullName);
        } else {
            throw new IllegalArgumentException("ФИО пациента должно начинаться с большой буквы и быть меньше 90 символов");
        }
    }

    public void setRegistrationCardNumber(int registrationCardNumber)
            throws IllegalArgumentException {
        if (registrationCardNumber > 0 && registrationCardNumber < 1000) {
            this.registrationCardNumber = registrationCardNumber;
        } else {
            throw new IllegalArgumentException("Допустимые границы регистрационного номера от 0 до 1000");
        }
    }

    public void setTypeOfWork(String typeOfWork) throws IllegalArgumentException {
        if (Helpers.checkFirstLetterCapital(typeOfWork) && typeOfWork.length() < 90) {
            this.typeOfWork = Helpers.cleanString(typeOfWork);
        } else {
            throw new IllegalArgumentException("Тип работы должен начинаться с большой буквы и быть меньше 90 символов");
        }
        this.typeOfWork = typeOfWork;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setDebtSum(int debtSum)
            throws IllegalArgumentException {
        if (debtSum >= 0) {
            this.debtSum = debtSum;
        } else {
            throw new IllegalArgumentException("Сумма долга должна быть неотрицательной");
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "fullName='" + fullName + '\'' +
                ", registrationCardNumber=" + registrationCardNumber +
                ", typeOfWork='" + typeOfWork + '\'' +
                ", paid=" + paid +
                ", debtSum=" + debtSum +
                '}';
    }
}
