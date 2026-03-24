package com.epam.breadth;

import java.util.ArrayList;

/**
 * @author Aleks
 *
 */
public class QueenBreadthSearch {
    private static int k = 0; // общее число решений
    private static int n = 0; // общее число порожденных вершин

    public static int getK() {
        return k;
    }

    public static int getN() {
        return n;
    }

    public static boolean queensCheck(int i, int c, ArrayList<ArrayList<Integer>> X) {
        boolean flag = true;
        for (int s = 0; s < (c - 1); s++) {
            for (int t = (s + 1); t < c; t++) {
                if ((X.get(i).get(s).equals(X.get(i).get(t))) || (s + X.get(i).get(s) == t + X.get(i).get(t))
                        || (s - X.get(i).get(s) == t - X.get(i).get(t))) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static synchronized void search(int N) {
        k = 0;
        n = 0;
        ArrayList<Integer> F = new ArrayList<>();
        ArrayList<ArrayList<Integer>> X = new ArrayList<>();
        ArrayList<ArrayList<Integer>> Y = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            F.add(i);
            n++; // порождение первых N вершин
            X.add(new ArrayList<>(F)); // преобразовать F в массив и добавить в X
            F.clear();
        }

        for (int level = 1; level < N; level++) {
            int m = X.size();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < N; j++) {
                    X.get(i).add(j); // добавляем элемент в конец X[i]
                    n++; // добавление элемента = порождение вершины
                    if (queensCheck(i, X.get(i).size(), X)) {
                        Y.add(new ArrayList<>(X.get(i)));
                    }
                    X.get(i).remove(X.get(i).size() - 1); // удаляем последний элемент из X
                }
            }
            X.clear(); // очищаем X

            for (ArrayList<Integer> yList : Y) {
                X.add(new ArrayList<>(yList)); // полное добавление из Y в X
            }

            Y.clear(); // очищаем Y
        }

        k = X.size();

        // вывод всех решений
        for (ArrayList<Integer> arrayList : X) {
            for (Integer p : arrayList) {
                System.out.print(p + 1 + " ");
            }
            System.out.println();
        }

        System.out.println("BFS Всего решений: " + X.size());
        System.out.println("BFS Порожденных вершин: " + n);
    }
}
