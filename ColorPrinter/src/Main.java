import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[30];
        for (int i = 0; i < array.length; i++)
            array[i] = (int) (Math.random() * 50);

        ColorPrinter<Integer> printer = new ColorPrinter<>(array);
        printer.format(ColorPrinter.YELLOW_BOLD_BRIGHT, ColorPrinter.BLUE_BOLD_BRIGHT, " ");
        printer.colorPrint();
        int i = 0;
        boolean found = false;
        while (i < array.length - 1) {
            if (array[i] > array[i + 1]) {
                printer.colorPrint(i, i + 1);
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                found = true;
            }
            i++;
            if (i == array.length - 1 && found) {
                found = false;
                i = 0;
            }
        }
        printer.colorPrint(ColorPrinter.GREEN_BOLD_BRIGHT);
    }
}
