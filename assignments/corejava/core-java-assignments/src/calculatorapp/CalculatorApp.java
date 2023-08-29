package calculatorapp;

import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Calculator App");
        System.out.println("Select one operation");
        System.out.println(" 1. Addition \n 2. Subtraction \n 3. Multiply \n 4. Divided \n 5. Exit" );
        int output = sc.nextInt();
        System.out.print("Enter the first number: ");
        double a = sc.nextDouble();
        System.out.print("Enter the second number: ");
        double b = sc.nextDouble();
        switch (output)
        {
            case 1: {
                double sum = a + b;
                System.out.println(" Addition of a + b = " + sum);
                break;
            }
            case 2: {
                double prod = a - b;
                System.out.println(" Subtraction of a - b = " + prod);
                break;
            }
            case 3: {
                double mul = a * b;
                System.out.println(" Multiplication of a - b = " + mul);
                break;
            }
            case 4: {
                if (b!=0) {
                    double div = a / b;
                    System.out.println(" Division of a - b = " + div);
                }else{
                    System.out.println(" Cannot divide by zero ");
                }

                break;
            }
            case 5: {
                System.out.println(" Exiting the program ");
                break;
            }
            default:
                System.out.println(" Invalid operation number ");
                break;
        }
    }
}
