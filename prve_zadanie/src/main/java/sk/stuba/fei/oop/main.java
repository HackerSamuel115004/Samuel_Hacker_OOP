package sk.stuba.fei.oop;
import java.util.*;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello, World!\n");

        Scanner sc=new Scanner(System.in);
        System.out.println("enter first number");
        int x = sc.nextInt();
        System.out.println("enter second number");
        int y = sc.nextInt();
        if(x == y) {
            System.out.println("Both x and y are equal");

        }else {
            System.out.println("x and y are not equal");
        }
if (x > y){
    return "1";}
if (x < y){
            return "-1";}
else {return "0"; }

}