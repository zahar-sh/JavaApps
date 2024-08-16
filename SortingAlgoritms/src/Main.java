import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

public class Main {
    private static final String RESET = "\033[0m";
    private static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    private static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    private static final String BLUE_BOLD_BRIGHT = "\033[1;94m";

    public static void main(String[] args) {
        int n = 11;
        int[] array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "BubbleSort");
        bubbleSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "GnomeSort");
        gnomeSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "ShakerSort");
        shakerSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "ShellSort");
        shellSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "QuickSort");
        quickSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "OddEvenSort");
        oddEvenSorting(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "SelectionSort");
        selectionSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "HeapSort");
        heapSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "MergeSort");
        mergeSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "mySort");
        mySort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(GREEN_BOLD_BRIGHT + "randomSort");
        randomSort(array);
        System.out.println();

        array = generateArray(n);
        System.out.println(Arrays.toString(array));
        NonRecursiveQuickSort(array);
        System.out.println(Arrays.toString(array));

        new Speed();
    }

    private static int[] generateArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * 10) + 10;
        }
        return array;
    }

    private static void bubbleSort(int[] array) {// пузырь
        int i = 0, tmp;
        boolean found = false;
        while (i < array.length - 1) {
            if (array[i] > array[i + 1]) {
                int[] ints = {i, i + 1};
                colorPrint(array, ints);
                tmp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = tmp;
                found = true;
            }
            i++;
            if (i == array.length - 1 && found) {
                found = false;
                i = 0;
            }
        }
    }

    private static void bubbleSort2(int[] array) {// пузырь
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int[] ints = {i, i + 1};
                    colorPrint(array, ints);
                    int tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    found = true;
                }
            }
        }
    }

    private static void bubbleSort3(int[] array) {// пузырь
        for (int j = array.length - 1; j >= 0; j--) {
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    int[] ints = {i, i + 1};
                    colorPrint(array, ints);
                    int tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                }
            }
        }
    }

    private static void gnomeSort(int[] array) {//гномья
        int i = 1;
        int j = 2;
        while (i < array.length) {
            if (i <= 0 || array[i - 1] <= array[i]) {
                i = j;
                j++;
            } else {
                int[] ints = {i - 1, i};
                colorPrint(array, ints);
                int tmp = array[i];
                array[i] = array[i - 1];
                array[i - 1] = tmp;
                i--;
            }
        }
    }

    private static void quickSort(int[] array) {
        quick(array, 0, array.length - 1);
    }

    private static void quick(int[] array, int low, int high) {//быстрая сортировка
        if (array.length == 0 || low >= high)
            return;

        int opora = array[low + (high - low) / 2];

        int i = low, j = high;
        while (i <= j) {
            while (array[i] < opora) i++;
            while (array[j] > opora) j--;

            if (i <= j) {
                int[] ints = {i, j};
                colorPrint(array, ints);
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
            quick(array, low, j);
        if (high > i)
            quick(array, i, high);
    }


    private static void NonRecursiveQuickSort(int[] array) {
        Stack<Integer> low = new Stack<>();
        Stack<Integer> high = new Stack<>();
        low.push(0);
        high.push(array.length - 1);

        do {
            int left = low.pop();
            int right = high.pop();

            int i = left;
            int j = right;
            int middle = array[(left + right) / 2];

            while (i <= j) {
                while (array[i] < middle) i++;
                while (array[j] > middle) j--;
                if (i <= j) {
                    int b = array[i];
                    array[i] = array[j];
                    array[j] = b;
                    i++;
                    j--;
                }
            }

            if (i < right) {
                low.push(i);
                high.push(right);
            }
            if (j > left) {
                low.push(left);
                high.push(j);
            }

        } while (!low.empty() && !high.empty());
    }


    private static void shakerSort(int[] array) {//перемешивание
        boolean found = true;
        int left = 0;
        int right = array.length - 1;
        int temp;
        while (found) {
            found = false;
            for (int i = left; i < right; i++) {
                if (array[i] > array[i + 1]) {
                    int[] ints = {i, i + 1};
                    colorPrint(array, ints);
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    found = true;
                }
            }
            if (!found) break;
            right--;
            found = false;
            for (int i = right; i >= left; i--) {
                if (array[i] > array[i + 1]) {
                    int[] ints = {i, i + 1};
                    colorPrint(array, ints);
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    found = true;
                }
            }
            left++;
        }
    }

    private static void shellSort(int[] array) {//сортировка Шелла
        int temp;
        for (int inc = array.length / 2; inc >= 1; inc /= 2) {
            for (int step = 0; step < array.length; step++) {
                for (int k = 0; k < array.length - 1; k += inc) {
                    for (int i = Math.min(k + inc, array.length - 1); i >= inc; i -= inc) {
                        if (array[i - inc] > array[i]) {
                            int[] ints = {i - inc, i};
                            colorPrint(array, ints);
                            temp = array[i];
                            array[i] = array[i - inc];
                            array[i - inc] = temp;
                        } else break;
                    }
                }
            }
        }
    }

    private static void oddEvenSorting(int[] array) {//четная нечетная
        int buff;
        for (var i = 0; i < array.length; i++) {
            for (var j = (i % 2) == 0 ? 0 : 1; j < array.length - 1; j += 2) {
                if (array[j] > array[j + 1]) {
                    int[] ints = {j, j + 1};
                    colorPrint(array, ints);
                    buff = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = buff;
                }
            }
        }
    }

    private static void selectionSort(int[] array) {//выбором
        for (int min = 0; min < array.length - 1; min++) {
            int least = min;
            for (int j = min + 1; j < array.length; j++) {
                if (array[j] < array[least]) {
                    least = j;
                }
            }
            int[] ints = {min, least};
            colorPrint(array, ints);
            int tmp = array[min];
            array[min] = array[least];
            array[least] = tmp;
        }
    }

    private static void siftDown(int[] array, int root, int bottom) {
        int maxChild;
        boolean done = true;
        while (root * 2 <= bottom && done) {
            if (root * 2 == bottom)
                maxChild = root * 2;
            else if (array[root * 2] > array[root * 2 + 1])
                maxChild = root * 2;
            else
                maxChild = root * 2 + 1;
            if (array[root] < array[maxChild]) {
                int[] ints = {root, maxChild};
                colorPrint(array, ints);
                int temp = array[root];
                array[root] = array[maxChild];
                array[maxChild] = temp;
                root = maxChild;
            } else done = false;
        }
    }

    private static void heapSort(int[] array) {//пирамидальная
        colorPrint(array);
        int temp;
        for (int i = (array.length / 2) - 1; i >= 0; i--)
            siftDown(array, i, array.length - 1);
        for (int i = array.length - 1; i >= 1; i--) {
            int[] ints = {0, i};
            colorPrint(array, ints);
            temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            siftDown(array, 0, i - 1);
        }
        colorPrint(array);
    }

    private static void mergeSort(int[] array) {//слияние
        int[] down = new int[array.length];
        colorPrint(array);
        colorPrint(merge_sort(array, down, 0, array.length - 1));
    }

    private static int[] merge_sort(int[] up, int[] down, int left, int right) {
        if (left == right) {
            down[left] = up[left];
            return down;
        }

        int middle = (left + right) / 2;

        int[] l_buff = merge_sort(up, down, left, middle);
        int[] r_buff = merge_sort(up, down, middle + 1, right);
        int[] target = l_buff == up ? down : up;

        int l_cur = left, r_cur = middle + 1;
        for (int i = left; i <= right; i++) {
            if (l_cur <= middle && r_cur <= right) {
                if (l_buff[l_cur] < r_buff[r_cur]) {
                    target[i] = l_buff[l_cur];
                    l_cur++;
                } else {
                    target[i] = r_buff[r_cur];
                    r_cur++;
                }
            } else if (l_cur <= middle) {
                target[i] = l_buff[l_cur];
                l_cur++;
            } else {
                target[i] = r_buff[r_cur];
                r_cur++;
            }
        }
        return target;
    }

    private static void mySort(int[] array) {
        colorPrint(array);
        int[] tree = new int[array.length];
        for (int x = 1; x < array.length; x++) {
            int key = array[0];
            int Tree = array[x];
            int i = 0;
            boolean found = true;
            while (found) {
                int left = 2 * i + 1;
                int right = left + 1;
                boolean flag1 = false;
                boolean flag2 = false;
                for (int k = 0; k < array.length; k++) {
                    if (tree[k] == left) flag1 = true;
                    if (tree[k] == right) flag2 = true;
                }
                if (Tree < key) {
                    if (tree[x] == 0 && !flag1) {
                        tree[x] = left;
                        found = false;
                    } else i = 2 * i + 1;
                } else {
                    if (tree[x] == 0 && !flag2) {
                        tree[x] = right;
                        found = false;
                    } else i = 2 * i + 2;
                }
                for (int k = 0; k < array.length; k++) {
                    if (tree[k] == i) {
                        key = array[k];
                        break;
                    }
                }
            }
        }
        colorPrint(tree);
        int i = 0;
        int index = 0;
        int current = 0;
        while (current < array.length) {
            boolean found_L = false;
            boolean found_R = false;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            for (int k = 0; k < array.length; k++) {
                if (tree[k] == left) found_L = true;
                if (tree[k] == right) found_R = true;
                if (tree[k] == i) index = k;
            }
            if (found_L) i = 2 * i + 1;
            else {
                if (tree[index] != -1) {
                    System.out.print(BLUE_BOLD_BRIGHT + array[index] + "\t");

                    current++;
                    tree[index] = -1;
                }
                if (found_R) i = 2 * i + 2;
                else i = (i - 1) / 2;
            }
        }
        System.out.println(RESET);
    }

    private static Random random = new Random();

    private static void randomSort(int[] array) {
        int a, b;
        colorPrint(array);
        while (!isSort(array)) {
            a = random.nextInt(array.length);
            b = random.nextInt(array.length);
            if (array[Math.min(a, b)] > array[Math.max(a, b)]) {
                int[] ints = {a, b};
                colorPrint(array, ints);
                int temp = array[a];
                array[a] = array[b];
                array[b] = temp;
            }
        }
        colorPrint(array);
    }

    private static boolean isSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

    private static void colorPrint(int[] array) {
        printFon(array, 0, array.length);
        System.out.println();
    }

    private static void colorPrint(int[] array, int[] index) {
        int previous = 0;
        TreeSet<Integer> sort = new TreeSet<>();
        for (int i : index)
            sort.add(i);
        for (int i : sort) {
            printFon(array, previous, i);
            printElement(array, i);
            previous = i + 1;
        }
        printFon(array, previous, array.length);
        System.out.println();
    }

    private static void printFon(int[] array, int a, int b) {
        while (a < b)
            System.out.print(Main.BLUE_BOLD_BRIGHT + array[a++] + RESET + "\t");
    }

    private static void printElement(int[] array, int index) {
        System.out.print(Main.YELLOW_BOLD_BRIGHT + array[index] + RESET + "\t");
    }
}