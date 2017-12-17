import java.util.Arrays;

import static java.lang.System.out;

/**
 * Created by karine on 3/11/2017.
 */
public class Golay {
    public static void main(String[] args) {
        out.println("Insert vector r");
        java.util.Scanner in = new java.util.Scanner(System.in);
        int[] test = {1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        out.println(Arrays.toString(decode(test)));
        int[] r = new int[24];
        for (int i = 0; i < 24; i++) {
            r[i] = in.nextInt();
        }
        out.println(Arrays.toString(decode(r)));
    }


    private static int[] multiply(int[] vector, int[][] matrix) {
        int length = vector.length;
        int newlength = matrix[0].length;
        int[] result = new int[newlength];
        for (int i = 0; i < newlength; i++) {
            for (int j = 0; j < length; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
            result[i] %= 2;
        }
        return result;
    }

    private static int getWeight(int[] vector) {
        int result = 0;
        for (int lmnt : vector) {
            if (lmnt == 1)
                result++;
        }
        return result;
    }

    private static int[] concat(int[] a, int[] b) {
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static int[] sum(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = a[i] + b[i];
            result[i] %= 2;
        }
        return result;
    }

    private static int[] getUI(int i) {
        int[] result = new int[12];
        result[i] = 1;
        return result;
    }

    private static int[] decode(int[] r) {
        int[] failReturn = {0};

        final int[][] H_T = {
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1},
                {0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1},
                {1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1},
                {0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
                {1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1},
                {0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };

        int[][] p = new int[12][12];
        System.arraycopy(H_T, 12, p, 0, 12);

        int[] s = multiply(r, H_T);
        int[] e = new int[r.length];
        int[] zero = new int[e.length / 2];
        int[] v;
        if (getWeight(s) <= 3) {
            e = concat(s, zero);
            v = sum(e, r);
            return v;
        } else {
            for (int i = 0; i < 12; i++) {
                int[] pRow = sum(s, p[i]);
                if (getWeight(pRow) <= 2) {
                    e = concat(pRow, getUI(i));
                    v = sum(e, r);
                    return v;
                }
            }
        }
        int[] sP = multiply(s, p);
        if (getWeight(sP) == 2 || getWeight(sP) == 3) {
            e = concat(zero, sP);
            v = sum(r, e);
            return v;
        } else {
            for (int i = 0; i < 12; i++) {
                if (getWeight(sum(sP, p[i])) == 2) {
                    e = concat(getUI(i), sum(sP, p[i]));
                    v = sum(r, e);
                    return v;
                }
            }
        }
        out.println("This represents a decoding failure");
        return failReturn;
    }
}
