package hw09;

import java.util.*;

public class SequenceAlignment {
    private int gapCost = 1;
    private int mismatchCost = 2;
    private String str1 = null, str2 = null;
    private HashSet<Coordinate> arrowPath;

    public SequenceAlignment() {
        arrowPath = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("String1 : ");
        str1 = sc.nextLine();
        System.out.print("String2 : ");
        str2 = sc.nextLine();
    }


    public void doSequenceAlignment() {
//        sequenceAlignment(str2, str1);
        sequenceAlignment(str2, 0, str1, 0);
    }

    private void sequenceAlignment(String x, int xSpace, String y, int ySpace) {
        int n = x.length();
        int m = y.length();
        if (n <= 2 || m <= 2) {
            int M[][] = standardAlignment(y, x);
            getItem(M, xSpace, ySpace);
            return;
        }

        int[] YPrefix = allYPrefixCosts(x, (int) Math.ceil((double) n / 2), y);
        int[] YSuffix = allYSuffixCosts(x, (int) Math.ceil((double) n / 2), y);
//            int[] YPrefix = allYPrefixCosts(x, n/2, y);
//            int[] YSuffix = allYSuffixCosts(x, n/2, y);

        int best = Integer.MAX_VALUE;
        int bestq = Integer.MAX_VALUE;
        for (int q = 1; q <= m; q++) {
            int cost = YPrefix[q] + YSuffix[q - 1]; // Suffix는 reverse 상태이다.
            if (cost < best) {
                bestq = q;
                best = cost;
            }
        }

        // 무한 재귀 호출 변경
        arrowPath.add(new Coordinate(bestq + ySpace, (int) Math.ceil((double) n / 2) + xSpace));
//            arrowPath.add(new Coordinate(bestq + ySpace, n/2 + xSpace));

        // 다 포함
        sequenceAlignment(x.substring(0, (int) Math.ceil((double) n / 2)), xSpace, y.substring(0, bestq), ySpace); // 스트링 자르기 주의할것, 스트링 0부터 시작
        sequenceAlignment(x.substring((int) Math.ceil((double) n / 2) - 1, n), (int) Math.ceil((double) n / 2) + xSpace - 1, y.substring(bestq - 1, m), bestq + ySpace - 1);

        // 다 포함 2 ====> 기존 것
//            sequenceAlignment(x.substring(0, n/2), xSpace, y.substring(0, bestq), ySpace); // 스트링 자르기 주의할것, 스트링 0부터 시작
////        sequenceAlignment(x.substring(n/2 - 1, n), Math.abs(0 - n/2 - 1), y.substring(bestq - 1, m), Math.abs(0 - bestq - 1));
////        sequenceAlignment(x.substring(n/2 - 1 , n), n/2 - 0 - 1 + xSpace , y.substring(bestq - 1 , m), bestq - 0 - 1 + ySpace);
//            sequenceAlignment(x.substring(n/2 - 1 , n), n/2 - 0 + xSpace  , y.substring(bestq - 1 , m), bestq - 0   + ySpace);
    }

    private int[][] standardAlignment(String str1, String str2) {
        int[][] M = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i < str2.length() + 1; i++) {
            M[0][i] = i * gapCost;
        }

        for (int i = 0; i < str1.length() + 1; i++) {
            M[i][0] = i * gapCost;
        }

        for (int i = 1; i < str1.length() + 1; i++) {
            for (int j = 1; j < str2.length() + 1; j++) {
                M[i][j] = Math.min(Math.min(calcMismatchCost(i - 1, j - 1, str1, str2) + M[i - 1][j - 1], gapCost + M[i - 1][j]), gapCost + M[i][j - 1]);
            }
        }

        return M;
    }

    private void getItem(int[][] M, int xSpace, int ySpace) {
        int lastRow = M.length - 1;
        int lastCol = M[0].length - 1;
        int rowPtr = lastRow;
        int colPtr = lastCol;

        while (true) {
            // 중복 제거
//            Coordinate c = new Coordinate(rowPtr + ySpace , colPtr +xSpace);
//            if(!arrowPath.contains(c)) arrowPath.add(c);

//            if(!(rowPtr == 0 && colPtr == 0) || !(lastRow == rowPtr && lastCol == colPtr)){
            arrowPath.add(new Coordinate(rowPtr + ySpace, colPtr + xSpace));
//                arrowPath2.put(new Coordinate(rowPtr + ySpace , colPtr +xSpace), M[rowPtr][colPtr]);
//            }
            if (rowPtr == 0 && colPtr == 0 || rowPtr == 1 && colPtr == 1) {
                return;
            } else if (rowPtr == 0) {
                colPtr = colPtr - 1;
            } else if (colPtr == 0) {
                rowPtr = rowPtr - 1;
            } else if (M[rowPtr - 1][colPtr] + gapCost == M[rowPtr][colPtr]) {
                rowPtr = rowPtr - 1;
            } else if (M[rowPtr][colPtr - 1] + gapCost == M[rowPtr][colPtr]) {
                colPtr = colPtr - 1;
            } else {
                rowPtr = rowPtr - 1;
                colPtr = colPtr - 1;
            }
        }
    }

    private int calcMismatchCost(int x, int y, String str1, String str2) {
        if (str1.charAt(x) == str2.charAt(y)) {
            return 0;
        }
        return mismatchCost;
    }

    private int revCalcMismatchCost(int x, int y, String str1, String str2) {
        if (str1.charAt(x) == str2.charAt(y)) {
            return 0;
        }
        return mismatchCost;
    }

    private int[] allYPrefixCosts(String x, int mid, String y) {
        int[] firstColumn = new int[y.length() + 1];
        int[] secondColumn = new int[y.length() + 1];

        for (int i = 0; i < firstColumn.length; i++) {
            firstColumn[i] = i * gapCost;
        }

        for (int i = 1; i <= mid; i++) {
            for (int j = 0; j < secondColumn.length; j++) {
                if (j == 0) {
                    secondColumn[j] = i * gapCost;
                    continue;
                }
                secondColumn[j] = Math.min(Math.min(calcMismatchCost(j - 1, i - 1, y, x) + firstColumn[j - 1], gapCost + firstColumn[j]), gapCost + secondColumn[j - 1]);
            }
            System.arraycopy(secondColumn, 0, firstColumn, 0, secondColumn.length);
        }

        return secondColumn;
    }

    private int[] allYSuffixCosts(String x, int mid, String y) { // 같은 위치로 가야함
        String xReverse = new StringBuilder(x).reverse().toString();
        String yReverse = new StringBuilder(y).reverse().toString();

        int[] firstColumn = new int[y.length() + 1];
        int[] secondColumn = new int[y.length() + 1];

        for (int i = 0; i < firstColumn.length; i++) {
            firstColumn[i] = i * gapCost;
        }

        for (int i = 1; i <= xReverse.length() - mid + 1; i++) {
            for (int j = 0; j < secondColumn.length; j++) {
                if (j == 0) {
                    secondColumn[j] = i * gapCost;
                    continue;
                }
                // calcMismatch index 서로 바꿨음
                secondColumn[j] = Math.min(Math.min(revCalcMismatchCost(j - 1, i - 1, yReverse, xReverse) + firstColumn[j - 1], gapCost + firstColumn[j]), gapCost + secondColumn[j - 1]);
            }
            System.arraycopy(secondColumn, 0, firstColumn, 0, secondColumn.length);

        }

        return reverseArrayInt(secondColumn);

    }

    public void print() {
        List list = new ArrayList(arrowPath);
        Collections.sort(list, new CoordinateComparator());
        for (Object s : list) {
            Coordinate temp = (Coordinate) s;
            System.out.println("[" + temp.getRow() + "][" + temp.getCol() + "]");
        }
//        Iterator<Coordinate> i = arrowPath.iterator();
//        while(i.hasNext()){
//            Coordinate temp = i.next();
//            System.out.println("[" + temp.getRow() + "][" + temp.getCol() + "]");
//        }
    }

    private int[] reverseArrayInt(int[] n) {
        int left = 0;
        int right = n.length - 1;

        while (left < right) {
            int temp = n[left];
            n[left] = n[right];
            n[right] = temp;

            left++;
            right--;
        }

        return n;
    }

    private class Coordinate {
        private int row, col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }


        // HashSet에 추가시 중복 제거 위해 override
        @Override
        public int hashCode() {
            return Integer.toString(row).hashCode() + Integer.toString(col).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            Coordinate test = (Coordinate) obj;
            if (obj instanceof Coordinate) {
                if (row == test.row && col == test.col) return true;
                return false;
            }
            return false;
        }
    }

    // Comparator를 이용한 정렬
    class CoordinateComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            int row1 = ((Coordinate) o1).row;
            int row2 = ((Coordinate) o2).row;
            if (row1 == row2) return 0;
            if (row1 < row2) return -1;
            return 1;
//            return row1 - row2;
        }
    }

}
