package sk.stuba.fei.oop;
import java.util.Scanner;
public class ScanfPrint {

        public static void main(String[] args) {


            Scanner reader = new Scanner(System.in);
            System.out.print("Vloz cislo: ");

            // nextInt() reads the next integer from the keyboard
            int number = reader.nextInt();

            // println() prints the following line to the output screen
            System.out.println("Zadal si: " + number);
        }
    }

