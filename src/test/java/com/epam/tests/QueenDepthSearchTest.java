package com.epam.tests;

import com.epam.depth.QueenDepthSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueenDepthSearchTest {

    private static boolean isValidSolution(int[] f, int n) {
        if (f.length != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (f[i] == f[j]) {
                    return false; // same row
                }
                if (i + f[i] == j + f[j]) {
                    return false; // same left diagonal
                }
                if (i - f[i] == j - f[j]) {
                    return false; // same right diagonal
                }
            }
        }
        return true;
    }

    private List<int[]> captureSolutions(int N) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer));
        try {
            QueenDepthSearch.search(N);
        } finally {
            System.setOut(original);
        }

        List<int[]> solutions = new ArrayList<>();
        for (String line : buffer.toString().trim().split("\n")) {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length == N) {
                try {
                    int[] sol = new int[N];
                    for (int i = 0; i < N; i++) {
                        sol[i] = Integer.parseInt(tokens[i]) - 1;
                    }
                    solutions.add(sol);
                } catch (NumberFormatException e) {
                    // skip summary lines (e.g. "DFS Всего решений: 2")
                }
            }
        }
        return solutions;
    }

    @ParameterizedTest(name = "N={0}: solution count should be {1}")
    @CsvSource({ "1,1", "4,2", "5,10", "6,4", "7,40", "8,92" })
    void solutionCountMatchesKnownValues(int N, int expected) {
        QueenDepthSearch.search(N);
        assertEquals(expected, QueenDepthSearch.getK(), "Wrong solution count for N=" + N);
    }

    @ParameterizedTest(name = "N={0}: every printed solution must be valid")
    @CsvSource({ "1", "4", "5", "6", "7", "8" })
    void allPrintedSolutionsAreValid(int N) {
        List<int[]> solutions = captureSolutions(N);
        assertFalse(solutions.isEmpty(), "Expected at least one solution for N=" + N);
        for (int[] sol : solutions) {
            assertTrue(isValidSolution(sol, N), "Invalid solution printed: " + Arrays.toString(sol));
        }
    }

    @ParameterizedTest(name = "N={0}: printed solution count should be {1}")
    @CsvSource({ "1,1", "4,2", "5,10", "6,4", "7,40", "8,92" })
    void printedSolutionCountMatchesKnownValues(int N, int expected) {
        List<int[]> solutions = captureSolutions(N);
        assertEquals(expected, solutions.size(), "Wrong number of printed solutions for N=" + N);
    }

    @Test
    void noSolutionsForN2() {
        QueenDepthSearch.search(2);
        assertEquals(0, QueenDepthSearch.getK());
    }

    @Test
    void noSolutionsForN3() {
        QueenDepthSearch.search(3);
        assertEquals(0, QueenDepthSearch.getK());
    }

    @Test
    void singleQueenOnSingleSquareIsValid() {
        List<int[]> solutions = captureSolutions(1);
        assertEquals(1, solutions.size());
        assertArrayEquals(new int[] { 0 }, solutions.get(0));
    }
}
