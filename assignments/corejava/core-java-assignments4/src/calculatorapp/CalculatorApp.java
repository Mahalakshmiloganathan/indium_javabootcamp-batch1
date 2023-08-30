package calculatorapp;

public class CalculatorApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(" Welcome to Calculator App ");
            System.out.println("Enter the data , value1 and value2 ");
            return;
        }

        System.out.println(" Welcome to Calculator App ");
        String data = args[0];
        double value1 = Double.parseDouble(args[1]);
        double value2 = Double.parseDouble(args[2]);

        switch (data) {
            case "add":
                add(value1, value2);
                break;
            case "sub":
                subtract(value1, value2);
                break;
            case "mul":
                multiply(value1, value2);
                break;
            case "div":
                divide(value1, value2);
                break;
            default:
                System.out.println("Invalid operation");
        }
    }

    public static void add(double value1, double value2) {
        double output = value1 + value2;
        System.out.println(" Addition of value1 + value2 = " + output);
    }

    public static void subtract(double value1, double value2) {
        double output = value1 - value2;
        System.out.println(" Subraction of value1 + value2 = " + output);
    }

    public static void multiply(double value1, double value2) {
        double output = value1 * value2;
        System.out.println(" Multiplication of value1 + value2 = " + output);
    }

    public static void divide(double value1, double value2) {
        if (value2 == 0) {
            System.out.println("Cannot divide by zero");
        } else {
            double output = value1 / value2;
            System.out.println(" Division of value1 + value2 = " + output);
        }
    }
}

