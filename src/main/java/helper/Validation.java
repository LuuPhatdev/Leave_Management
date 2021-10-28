package helper;

import javax.swing.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
//    public static boolean checkInput(String str, String regex, String message) {
//        str = str.trim();
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.matches()) {
//            return true;
//        } else {
//            JOptionPane.showMessageDialog(null, message);
//            return false;
//        }
//    }

    public static boolean checkInput(String str, String regex) {
        str = str.trim();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

//    public static boolean checkInput(String str, String regex, int minLen, int maxLen, String message) {
//        str = str.trim();
//        if (str.length() > maxLen || str.length() < minLen) {
//            JOptionPane.showMessageDialog(null, message);
//            return false;
//        }
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.matches()) {
//            return true;
//        } else {
//            JOptionPane.showMessageDialog(null, message);
//            return false;
//        }
//    }

    public static boolean checkInput(String str, String regex, int minLen, int maxLen) {
        str = str.trim();
        if (str.length() > maxLen || str.length() < minLen) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkInputAmount(String str, String regex, String message) {
        str = str.trim();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (str.length()==0) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        if(Integer.parseInt(str) <= 0) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        return true;
    }

}
