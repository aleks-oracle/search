package com.epam.breadth;

import java.util.ArrayList;

/**
 * @author Aleks
 * 
 */
public class QueenBreadthSearch {

    public static boolean QueensCheck(int i, int c, ArrayList<ArrayList<Integer>> X) {
	boolean flag = true;
	for (int s = 0; s < (c - 1); s++) {
	    for (int t = (s + 1); t < c; t++) {
		if ((X.get(i).get(s) == X.get(i).get(t)) || (s + X.get(i).get(s) == t + X.get(i).get(t))
			|| (s - X.get(i).get(s) == t - X.get(i).get(t))) {
		    flag = false;
		    break;
		}
	    }
	}
	return flag;
    }

    public static void Search(int N) {
	int n = 0;
	ArrayList<Integer> F = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> X = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> Y = new ArrayList<ArrayList<Integer>>();

	for (int i = 0; i < N; i++) {
	    F.add(i);
	    n++; // порождение первых N вершин
	    X.add(new ArrayList<Integer>(F)); // преобразовать F в массив и
					      // добавить в X
	    F.clear();
	}

	for (int k = 1; k < N; k++) {
	    int m = X.size();
	    for (int i = 0; i < m; i++) {
		for (int j = 0; j < N; j++) {
		    X.get(i).add(j); // добавляем элемент в конец X[i]
		    n++; // добавление элемента = порождение вершины
		    if (QueensCheck(i, X.get(i).size(), X)) {
			Y.add(new ArrayList<Integer>(X.get(i)));
		    }
		    X.get(i).remove(X.get(i).size() - 1); // удаляем последний
							  // элемент из X
		}
	    }
	    X.clear(); // очищаем X

	    for (ArrayList<Integer> yList : Y) {
		X.add(new ArrayList<Integer>(yList)); // полное добавление из Y
						      // в X
	    }

	    Y.clear(); // очищаем Y
	}

	// вывод всех решений
	for (ArrayList<Integer> arrayList : X) {
	    for (Integer p : arrayList) {
		System.out.print(p + 1 + " ");
	    }
	    System.out.println();
	}

	System.out.println("Всего решений: " + X.size());
	System.out.println("Порожденных вершин: " + n);
    }
}