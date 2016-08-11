package com.epam.runner;

import java.util.Scanner;

import com.epam.depth.BridgeDepthSearch;

/**
 * @author Aleks
 * 
 */
public class BridgeRunner {
    private static int N;

    public static void main(String[] args) {
	System.out.println("Введите число ферзей");
	Scanner sc = new Scanner(System.in);
	N = sc.nextInt(); // количество ферзей и размер доски
	int[] F = new int[N];
	int m = 0; // номер вертикали на шахматной доске
	BridgeDepthSearch.Search(m, F, N);
	System.out.println("Число порожденных вершин: " + BridgeDepthSearch.getN());
	System.out.println("Общее число решений: " + BridgeDepthSearch.getK());
	sc.close();
    }
}
