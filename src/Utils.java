public class Utils {

    public static int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for(int i=numbers.length - 1; i >= 0; i--)
            if(numbers[i] == '1')
                result += Math.pow(2, (numbers.length - i - 1));
        return result;
    }

    public static String getNFirstDigits(String str, int numOfDigits){
        return str.substring(0, Math.min(str.length(), numOfDigits));
    }

    public static String getNfirstDifferentDigits(String[] values, String value, int numOfDigits){
        String pseudoKey = "";
        for(int i = 0; i < values.length; i++){
            if(!values[i].startsWith(getNFirstDigits(value, numOfDigits))){
                pseudoKey = values[i];
                break;
            }
        }
        return getNFirstDigits(pseudoKey, numOfDigits);
    }
}
