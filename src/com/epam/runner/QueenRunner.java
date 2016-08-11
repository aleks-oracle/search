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
	System.out.println("������� ����� ������");
	Scanner sc = new Scanner(System.in);
	N = sc.nextInt(); // ���������� ������ � ������ �����
	int[] F = new int[N];
	int m = 0; // ����� ��������� �� ��������� �����
	QueenDepthSearch.Search(m, F, N);
	System.out.println("����� ����������� ������: " + QueenDepthSearch.getN());
	System.out.println("����� ����� �������: " + QueenDepthSearch.getK());

	QueenBreadthSearch.Search(N);
    }
}
