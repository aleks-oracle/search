/**
 * 
 */
package com.epam.depth;

/**
 * @author Aleks
 * 
 */
public class QueenDepthSearch {
    private static int k = 0; // общее число решений
    private static int n = 0; // общее число порожденных вершин

    public static int getK() {
	return k;
    }

    public static int getN() {
	return n;
    }

    public static boolean QueensCheck(int m, int[] F) {
	boolean flag = true;
	// m-й ферзь не бьет (m-1) предыдущих
	for (int i = 0; i < m; i++) {
	    for (int j = (i + 1); j < (m + 1); j++) {
		if ((F[i] == F[j]) || (i + F[i] == j + F[j]) || (i - F[i] == j - F[j])) {
		    flag = false;
		    break;
		}
	    }
	}
	return flag;
    }

    public static void Search(int m, int[] F, int N) {
	if (m > (N - 1)) {
	    k++;
	    for (int i = 0; i < N; i++) {
		System.out.print((F[i] + 1) + " ");
	    }
	    System.out.println();
	} else {
	    for (int i = 0; i < N; i++) {
		F[m] = i;
		if (QueensCheck(m, F)) { // если частичная проверка прошла
					 // успешно - выставить на доску следующего ферзя
		    Search(m + 1, F, N);
		}
		n++; // порождение вершины
	    }
	}
    }

}
