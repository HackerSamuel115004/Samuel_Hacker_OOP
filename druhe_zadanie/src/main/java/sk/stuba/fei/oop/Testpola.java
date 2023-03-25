package sk.stuba.fei.oop;
import java.util.Scanner;
public class Testpola
{
    public static void main(String args[])
    {
        Scanner reader = new Scanner(System.in);
        System.out.print("Vloz cislo: ");
        int number = reader.nextInt();
        int a[]=new int[5];


        for(int i=0;i<a.length;i++)
            System.out.println(a[i]);
    }

}