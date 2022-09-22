package sorters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSorter {

    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        T[] array = (T[]) list.toArray();

        doQuickSort(array, 0, array.length - 1, comparator);

        list = new ArrayList<T>(List.of(array));
    }

    private static <T> void doQuickSort(T[] array, int startIndex, int endIndex, Comparator<T> comparator) {

        if (startIndex >= endIndex) {
            return;
        }

        int pivot = startIndex + (endIndex - startIndex) / 2;
        int i = startIndex, j = endIndex;

        while (i <= j) {
            while (comparator.compare(array[i], array[pivot]) < 0) {
                i++;
            }

            while (comparator.compare(array[j], array[pivot]) > 0) {
                j--;
            }

            if (i <= j) {
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }


            doQuickSort(array, startIndex, j, comparator);
            doQuickSort(array, i, endIndex, comparator);
        }
    }
}

