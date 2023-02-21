import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Lab {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Inte tillräckligt med argument");
            System.exit(1);
        }
        String infilename = args[0];
        String outfilename = args[1];
        int N = Integer.parseInt(args[2]);
        
        LinkedList<Integer> data = readDataFromFile(infilename);
        
        try (PrintWriter writer = new PrintWriter(new File(outfilename))) {
            writer.println("Iteration,Time (ns)");
            for (int i = 1; i <= N; i++) {
                LinkedList<Integer> copy = new LinkedList<>(data);
                long startTime = System.nanoTime();
                ListSorter.mergeSort(copy);
                //Collections.sort(copy);
                long endTime = System.nanoTime();
                long time = endTime - startTime;
                writer.println((i) + "," + time);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static LinkedList<Integer> readDataFromFile(String filename) {
        LinkedList<Integer> data = new LinkedList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextInt()) {
                int x = scanner.nextInt();
                data.add(x);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        return data;
    }
}

class ListSorter {
    public static LinkedList<Integer> mergeSort(LinkedList<Integer> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        
        // Divide the list into two sublists
        int mid = list.size() / 2;
        LinkedList<Integer> left = new LinkedList<>(list.subList(0, mid));
        LinkedList<Integer> right = new LinkedList<>(list.subList(mid, list.size()));
        
        // Recursively sort the two sublists
        left = mergeSort(left);
        right = mergeSort(right);
        
        // Merge the two sorted sublists
        LinkedList<Integer> mergedList = merge(left, right);
        return mergedList;
    }

    private static LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right) {
        LinkedList<Integer> mergedList = new LinkedList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.getFirst() <= right.getFirst()) {
                mergedList.add(left.removeFirst());
            } else {
                mergedList.add(right.removeFirst());
            }
        }
        mergedList.addAll(left);
        mergedList.addAll(right);
        return mergedList;
    }
}
