package hw03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class LoopInvariant {
    int arr[];

    LoopInvariant(String filePath) {
        String data = this.readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " ");
        arr = new int[str.countTokens()];
        for (int i = 0; str.hasMoreTokens(); i++) {
            arr[i] = Integer.parseInt(str.nextToken());
        }
    }


    public int binarySearch(int value) {
        int start = 0;
        int end = this.arr.length - 1;
        while (end != start) {
            int mid = (start + end) / 2;
            if (this.arr[mid] >= value) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        if (this.arr[start] == value) return start;
        return -1;
    }



    private String readFile(String filePath) {
        BufferedReader br = null;
        StringTokenizer str = null;
        StringBuilder sb = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(filePath));
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

}
