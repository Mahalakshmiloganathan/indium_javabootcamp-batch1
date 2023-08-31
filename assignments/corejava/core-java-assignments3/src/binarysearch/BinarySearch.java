package binarysearch;


public class BinarySearch {

    public static int binarySearch(int target) {
        int value1 = 1;
        int value2 = 7;
        int output = -1;
        while (value1 <= value2) {
            int data = value1 + (value2 - value1) / 2;
            int midValue = getSortedDataValue(data);

            if (midValue == target) {
                output = data;
                break;
            } else if (midValue < target) {
                value1 = data + 1;
            } else {
                value2 = data - 1;
            }
        }

        return output;
    }

    public static int getSortedDataValue(int position) {
        switch (position) {
            case 1: return 50;
            case 2: return 100;
            case 3: return 200;
            case 4: return 250;
            case 5: return 300;
            case 6: return 400;
            case 7: return 500;
            default: return -1;
        }
    }

    public static void main(String[] args) {
        int target = 300;

        int output = binarySearch(target);

        if (output != -1) {
            System.out.println("Target found at position: " + output);
        } else {
            System.out.println("Target not found");
        }
    }
}
