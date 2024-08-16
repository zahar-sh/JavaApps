import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

class Speed {

    private static final Random random = new Random();

    public Speed() {
        int[] array = new int[10_000];
        generateArray(array);

        long start = System.currentTimeMillis();
        bubbleSort(array);
        System.out.println("BubbleSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        bubbleSort2(array);
        System.out.println("BubbleSort2\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        bubbleSort3(array);
        System.out.println("BubbleSort3\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        gnomeSort(array);
        System.out.println("GnomeSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        shakerSort(array);
        System.out.println("ShakerSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        shellSort(array);
        System.out.println("ShellSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        quickSort(array);
        System.out.println("QuickSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        nonRecursiveQuickSort(array);
        System.out.println("nonRecursiveQuickSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        oddEvenSorting(array);
        System.out.println("OddEvenSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        selectionSort(array);
        System.out.println("SelectionSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        heapSort(array);
        System.out.println("HeapSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        mergeSort(array);
        System.out.println("MergeSort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        Arrays.sort(array);
        System.out.println("Arrays.sort\t" + (System.currentTimeMillis() - start));

        generateArray(array);
        start = System.currentTimeMillis();
        randomSort(array);
        System.out.println("randomSort\t" + (System.currentTimeMillis() - start));

        /*generateArray(array);
        start = System.currentTimeMillis();
        MySort(array);
        System.out.println("MySort\t" + (System.currentTimeMillis() - start));*/
    }


    private static void generateArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(array.length);
        }
    }

    private static void bubbleSort(int[] array) {// пузырь
        int i = 0, tmp;
        boolean found = false;
        while (i < array.length - 1) {
            if (array[i] > array[i + 1]) {
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

        int middle = low + (high - low) / 2;
        int opora = array[middle];

        int i = low, j = high;
        while (i <= j) {
            while (array[i] < opora) i++;
            while (array[j] > opora) j--;

            if (i <= j) {
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

    private static void nonRecursiveQuickSort(int[] array) {
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
        int right = array.length;
        int temp;
        while (found) {
            found = false;
            for (int i = left; i < right - 1; i++) {
                if (array[i] > array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    found = true;
                }
            }
            if (!found)
                break;
            right--;
            found = false;
            for (int i = right - 1; i >= left; i--) {
                if (array[i] > array[i + 1]) {
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
                if (array[least] > array[j]) {
                    least = j;
                }
            }
            int tmp = array[min];
            array[min] = array[least];
            array[least] = tmp;
        }
    }

    private static void siftDown(int[] array, int root, int bottom) {
        int maxChild;
        boolean done = false;
        while (root * 2 <= bottom && !done) {
            if (root * 2 == bottom) maxChild = root * 2;
            else if (array[root * 2] > array[root * 2 + 1]) maxChild = root * 2;
            else maxChild = root * 2 + 1;
            if (array[root] < array[maxChild]) {
                int temp = array[root];
                array[root] = array[maxChild];
                array[maxChild] = temp;
                root = maxChild;
            } else done = true;
        }
    }

    private static void heapSort(int[] array) {//пирамидальная
        int temp;
        for (int i = (array.length / 2) - 1; i >= 0; i--)
            siftDown(array, i, array.length - 1);
        for (int i = array.length - 1; i >= 1; i--) {
            temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            siftDown(array, 0, i - 1);
        }
    }

    private static void mergeSort(int[] array) {//слияние
        int[] down = new int[array.length];
        merge_sort(array, down, 0, array.length - 1);
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

    private void MySort(int[] array) {
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
                    current++;
                    tree[index] = -1;
                }
                if (found_R) i = 2 * i + 2;
                else {
                    if (i % 2 == 1) i = (i - 1) / 2;
                    else i = (i - 2) / 2;
                }
            }
        }
    }

    private static void randomSort(int[] array) {
        int a, b;
        while (!isSort(array)) {
            a = random.nextInt(array.length);
            b = random.nextInt(array.length);
            if (array[Math.min(a, b)] > array[Math.max(a, b)]) {
                int temp = array[a];
                array[a] = array[b];
                array[b] = temp;
            }
        }
    }

    private static boolean isSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }
}