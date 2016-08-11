/**
 * 
 */
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
	System.out.println("������� ����� ������");
	Scanner sc = new Scanner(System.in);
	N = sc.nextInt(); // ���������� ������ � ������ �����
	int[] F = new int[N];
	int m = 0; // ����� ��������� �� ��������� �����
	BridgeDepthSearch.Search(m, F, N);
	System.out.println("����� ����������� ������: " + BridgeDepthSearch.getN());
	System.out.println("����� ����� �������: " + BridgeDepthSearch.getK());

    }
}
