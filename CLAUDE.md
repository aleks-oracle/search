# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

This is an IntelliJ IDEA Java project with no build tool (Maven/Gradle). Compilation uses the IntelliJ compiler with output to `out/production/search/`.

To compile and run from the command line:

```bash
# Compile
javac -d out/production/search src/com/epam/breadth/QueenBreadthSearch.java src/com/epam/depth/QueenDepthSearch.java src/com/epam/depth/BridgeDepthSearch.java src/com/epam/runner/QueenRunner.java src/com/epam/runner/BridgeRunner.java

# Run N-Queens solver (prompts for board size N via stdin)
java -cp out/production/search com.epam.runner.QueenRunner

# Run Bridge solver (prompts for N via stdin)
java -cp out/production/search com.epam.runner.BridgeRunner
```

## Architecture

Two independent problems, each solved with search algorithms:

**N-Queens Problem** (`com.epam.runner.QueenRunner`):
- Runs both algorithms in sequence on the same input N
- `com.epam.depth.QueenDepthSearch` — recursive depth-first search (backtracking); uses static counters `k` (solutions) and `n` (nodes generated)
- `com.epam.breadth.QueenBreadthSearch` — iterative breadth-first search using two `ArrayList<ArrayList<Integer>>` buffers (`X` active, `Y` valid candidates), expanded level by level

**Bridge Problem** (`com.epam.runner.BridgeRunner`):
- `com.epam.depth.BridgeDepthSearch` — structurally identical to `QueenDepthSearch` (same `QueensCheck` logic); intended to be adapted for a different problem domain

**Shared constraint check (`QueensCheck`)**: validates that no two queens in a partial placement share a row, left diagonal, or right diagonal. Exists in all three search classes independently.

All output is in Russian. Board positions are printed as 1-indexed.
