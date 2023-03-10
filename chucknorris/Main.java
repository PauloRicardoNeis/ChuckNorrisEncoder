package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String choice = "";

        loop:
        while(true) {
            System.out.println("Please input operation (encode/decode/exit):");
            choice = scan.nextLine();
            switch(choice) {
                case "encode":
                    System.out.println("Input string: ");
                    System.out.println("Encoded string:\n" + textToChuck(scan.nextLine()));
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String code = scan.nextLine();
                    System.out.println(codeIsValid(code) ?
                            "Decoded string:\n" + chuckToText(code) : "Encoded string is not valid.");
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break loop;
                default:
                    System.out.println("There is no '" + choice + "' operation");
            }

        }

    }

    private static String chuckToText(String s) {
        return binaryToText(chuckToBinary(s));
    }

    private static String textToChuck(String s) {
        return binaryToChuck(textToBinary(s));
    }

    private static String binaryToText(String binary) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < binary.length(); i += 7 ) {
            sb.append(binaryToChar(binary.substring(i, i + 7)));
        }
        return sb.toString();
    }

    private static char binaryToChar(String binary) {
        int j = Integer.parseInt(binary, 2);
        return (char)j;
    }

    private static String charToBinary(char c) {
        StringBuilder str = new StringBuilder(Integer.toBinaryString(c));
        while (str.length() < 7) {
            str.insert(0, "0");
        }
        return str.toString();
    }

    private static String textToBinary(String text) {
        char[] charArr = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c: charArr) {
            sb.append(charToBinary(c));
        }

        return sb.toString();
    }

    private static String binaryToChuck(String s) {
        StringBuilder sb = new StringBuilder();

        String[] arr = s.split("(?<=(.))(?!\\1)");
        s = "";

        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].toCharArray()[0] == '0' ? "00 " : "0 ");
            sb.append("0".repeat(arr[i].length()) + " ");
        }

        return sb.toString();
    }

    private static String chuckToBinary(String s) {
        StringBuilder sb = new StringBuilder();
        String[] code = s.split(" ");

        for (int i = 0; i < code.length - 1; i += 2) {
            sb.append(code[i].equals("0") ? "1".repeat(code[i + 1].length()) : code[i + 1]);
        }

        return sb.toString();
    }

    private static boolean codeIsValid(String code) {
        if(!code.matches("[0 ]*")) return false;
        String[] list = code.split(" ");

        int digits = 0;
        for(int i = 1; i < list.length; i += 2) {
            digits += list[i].length();
        }

        if(digits % 7 != 0) return false;

        for(int i = 0; i < list.length; i += 2) {
            if(list[i].length() > 2) return false;
        }
        return true;
    }

}