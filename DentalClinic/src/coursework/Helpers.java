package coursework;

public class Helpers {
    /**
     * Проверить заглавная ли первая бука
     * @param str строка
     * @return заглавная ли первая буква
     */
    public static boolean checkFirstLetterCapital(String str) {
        if (str.length() < 1)
            return false;
        if (Character.isUpperCase(str.charAt(0)))
            return true;
        return false;
    }

    /**
     * Почистить строку от двойных пробелов
     * @param string строка
     * @return очищенная строка
     */
    public static String cleanString(String string){
        return string.trim().replaceAll(" +", " ");
    }

    public static boolean correctCardNumber(Patient patient){
        boolean correctRegistrationCardNumber = true;
        for(int i = 0; i< Main.getPatients().getPatientsList().size(); i++){
            if(patient.getRegistrationCardNumber()==Main.getPatients().getPatientsList().get(i).getRegistrationCardNumber()){
                correctRegistrationCardNumber = false;
            }
        }
        return correctRegistrationCardNumber;
    }
}
