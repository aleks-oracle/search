package com.epam.runner;

import java.util.Scanner;

import com.epam.breadth.QueenBreadthSearch;
import com.epam.depth.QueenDepthSearch;

/**
 * @author Aleks
 *
 */
public class QueenRunner {

    public static void main(String[] args) {
        System.out.println("Введите число ферзей");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // количество ферзей и размер доски
        sc.close();
        if (N <= 0) {
            System.out.println("Число ферзей должно быть больше нуля");
            return;
        }
        QueenDepthSearch.search(N);
        System.out.println("Всего решений: " + QueenDepthSearch.getK());
        System.out.println("Порожденных вершин: " + QueenDepthSearch.getN());
        QueenBreadthSearch.search(N);
        System.out.println("Всего решений: " + QueenBreadthSearch.getK());
        System.out.println("Порожденных вершин: " + QueenBreadthSearch.getN());
    }
}
