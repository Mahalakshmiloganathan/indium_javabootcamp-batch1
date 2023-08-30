package calculatorapp;

public class CalculatorApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Welcome to Calculator App");
            System.out.println("Enter the data , value1 and value2 ");
            System.exit(1);
        }

        System.out.println(" Welcome to Calculator App ");

        String data = args[0];
        double value1 = Double.parseDouble(args[1]);
        double value2 = Double.parseDouble(args[2]);
        double output;

        switch (data) {
            case "add":
                output = value1 + value2;
                System.out.println(" Addition of value1 + value2 = " + output);
                break;
            case "subtract":
                output = value1 - value2;
                System.out.println(" Subtraction of value1 + value2 = " + output);
                break;
            case "multiply":
                output = value1 * value2;
                System.out.println("Multiplication  of value1 + value2 = " + output);
                break;
            case "divide":
                if (value2 != 0) {
                    output = value1 / value2;
                    System.out.println("Division  of value1 + value2 = " + output);
                } else {
                    System.out.println(" Cannot divide by zero ");
                    System.exit(1);
                }
                break;
            default:
                System.out.println(" Invalid operation number ");
                System.exit(1);
        }

    }
}
