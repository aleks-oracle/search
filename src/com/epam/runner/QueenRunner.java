/**
 * 
 */
package com.epam.runner;

import java.util.Scanner;

import com.epam.breadth.QueenBreadthSearch;
import com.epam.depth.QueenDepthSearch;

/**
 * @author Aleks
 * 
 */
public class QueenRunner {
    private static int N;

    public static void main(String[] args) {
	System.out.println("Введите число ферзей");
	Scanner sc = new Scanner(System.in);
	N = sc.nextInt(); // количество ферзей и размер доски
	int[] F = new int[N];
	int m = 0; // номер вертикали на шахматной доске
	QueenDepthSearch.Search(m, F, N);
	System.out.println("Число порожденных вершин: " + QueenDepthSearch.getN());
	System.out.println("Общее число решений: " + QueenDepthSearch.getK());

	QueenBreadthSearch.Search(N);
    }
}
