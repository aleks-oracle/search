⏺ Algorithm Analysis

1. QueenDepthSearch — Recursive DFS with Backtracking

How it works: Places queens column by column. At column m, tries all N rows, calls QueensCheck before recursing deeper.

QueensCheck complexity: O(m²) — checks all pairs (i,j) among m+1 placed queens.

Time complexity: Much better than brute-force O(N^N) due to pruning. The worst-case is closer to O(N! · N²) — the backtracker explores at most N! leaf-paths (since
valid placements approximate permutations), each requiring O(N²) validation along the path. In practice, pruning eliminates the vast majority of branches early.

Space complexity: O(N) — the F array and recursion stack depth are both bounded by N.

Critical bug — static state: k and n are static and never reset. Running Search a second time within the same JVM accumulates counts from the previous run, producing
wrong output. QueenRunner uses this once per execution so it doesn't manifest, but it's a latent defect.

  ---
2. QueenBreadthSearch — Iterative BFS

How it works: Builds placements level by level. X holds all valid partial placements of current length; for each, tries all N row extensions; valid ones go into Y; Y
replaces X for the next level.

QueensCheck complexity: O(c²) — checks all pairs in the partial placement, not just the newly added queen against previous ones. Only O(c) work is necessary for the
incremental check, so this is a factor-of-c redundancy.

Time complexity: O(Σₖ₌₁ᴺ |Xₖ| · N · k²), where |Xₖ| is the count of valid k-queen partial placements. This is significantly worse than DFS because:
- All valid partial placements at every level are materialized and revisited
- The quadratic check is applied in full at each extension

Space complexity — the real problem: O(|Xₖ| · k) at each level. For N=8 there are 2,057 valid partial placements at the widest level. For N=12 this becomes hundreds
of thousands. BFS must hold the entire frontier in memory simultaneously, making it exponentially more memory-hungry than DFS for large N.

Additional inefficiency: Each extension does add(j) / QueensCheck / remove(last) in-place on the ArrayList, and valid candidates are copied into new ArrayLists. This
generates heavy GC pressure from O(|Xₖ| · N) object allocations per level.

  ---
3. BridgeDepthSearch — Identical to QueenDepthSearch

The implementation is byte-for-byte identical to QueenDepthSearch — same QueensCheck, same Search logic, same static k/n counters, even the same comment "m-й ферзь
не бьет (m-1) предыдущих" (m-th queen doesn't attack previous ones). The name implies a "bridge crossing" problem, but no adaptation was made. Same complexity: O(N!
· N²) time, O(N) space, same static state bug.

  ---
Comparison Summary

┌─────────────────────┬───────────────────────────┬───────────────────────────┬───────────────────┐
│      Property       │  DFS (QueenDepthSearch)   │ BFS (QueenBreadthSearch)  │ BridgeDepthSearch │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Time complexity     │ O(N! · N²) practical      │ O(Σ|Xₖ| · N · k²)         │ Same as DFS       │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Space complexity    │ O(N)                      │ O(|Xₖ| · k) — exponential │ O(N)              │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Finds all solutions │ Yes                       │ Yes                       │ Yes               │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Static state bug    │ Yes                       │ No                        │ Yes               │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Check efficiency    │ Redundant (O(m²) vs O(m)) │ Doubly redundant          │ Same as DFS       │
├─────────────────────┼───────────────────────────┼───────────────────────────┼───────────────────┤
│ Scales to large N   │ Reasonably                │ Poorly (memory-bound)     │ Same as DFS       │
└─────────────────────┴───────────────────────────┴───────────────────────────┴───────────────────┘

Bottom line: DFS is far superior for this problem. BFS is inherently ill-suited for constraint satisfaction problems like N-Queens because it cannot prune early — it
must fully expand all partial placements at each depth before moving to the next level, while DFS abandons a branch the moment a constraint is violated.

⏺ DFS — Depth-First Search. Explores one branch of the solution tree as deep as possible before backtracking. QueenDepthSearch does this recursively: it places a queen
in column 0, then column 1, then column 2... going deep until either a full solution is found or a conflict forces it to backtrack.

BFS — Breadth-First Search. Explores all partial solutions at the same depth before going deeper. QueenBreadthSearch does this iteratively: it first generates all   
valid 1-queen placements, then all valid 2-queen placements from those, and so on level by level.

The "Depth" and "Breadth" in the class names directly refer to these two fundamental graph/tree traversal strategies — so the naming in the code already hints at    
this, just written out in full rather than abbreviated.

❯ Let's say I want to write tests for these 2 algorithms. How do I know that particular combination that an algorith generates is real solution? Do I need another     
algorithm?

⏺ No, you don't need another algorithm. The validity of a solution can be verified with a simple independent checker — much simpler than the search itself.

For the N-Queens problem, a valid solution is an array F of length N where:

1. No two queens share a row: all values in F are distinct
2. No two queens share a left diagonal: i + F[i] != j + F[j] for all pairs
3. No two queens share a right diagonal: i - F[i] != j - F[j] for all pairs

(Column conflicts are impossible by construction since each index i represents a distinct column.)

So your test verifier is just:

boolean isValidSolution(int[] F, int N) {                                                                                                                            
for (int i = 0; i < N; i++) {                                                                                                                                    
for (int j = i + 1; j < N; j++) {
if (F[i] == F[j]) return false;                    // same row                                                                                           
if (i + F[i] == j + F[j]) return false;           // same left diagonal
if (i - F[i] == j - F[j]) return false;           // same right diagonal                                                                                 
}       
}                                                                                                                                                                
return true;
}

This is independent of both algorithms — it's just the definition of a valid placement. Then your tests verify:

- Every solution output by the algorithm passes this checker
- The count of solutions matches known values (e.g. N=4→2, N=5→10, N=6→4, N=7→40, N=8→92) — these are well-known and published in mathematical literature

The known solution counts give you a strong correctness guarantee without needing a reference algorithm.

❯ I did not fully understand the necessity of introducing 'isAllNumeric' method. How is it connected to simple printing? What are those 3 tokens you are talking about?

⏺ The BFS Search method prints two things to stdout — the solutions, and a summary at the end:

System.out.println("Всего решений: " + X.size());                                                                                                                                                                      
System.out.println("Порожденных вершин: " + n);

So for N=3 (zero solutions), the full output captured is just:                                                                                                                                                         
Всего решений: 0                                                                                                                                                                                                       
Порожденных вершин: 0

The parser splits each line by whitespace. "Всего решений: 0" splits into:                                                                                                                                             
["Всего", "решений:", "0"]

That's exactly 3 tokens. Since N=3, the condition tokens.length == N was true, so the parser tried to do Integer.parseInt("Всего") — and crashed.

For N=2 the same summary line also produces 3 tokens, but tokens.length == 2 is false so it was skipped safely. The collision only happens when N equals the number of words in the summary line, which is 3.

isAllNumeric simply rejects any line that contains non-digit tokens before attempting to parse, making the filter robust regardless of what summary text the algorithm prints.                                         

QueenDepthSearch — added a public Search(int N) entry point that resets k/n, calls the recursive method, then prints the summary. The recursive method became
private.

QueenRunner — simplified to just QueenDepthSearch.Search(N) since the summary is now printed inside the class, same as BFS.

QueenDepthSearchTest — dropped the @BeforeEach reflection hack (no longer needed since Search(N) resets state itself), added isAllNumeric to filter the new summary
lines when parsing stdout.

IDEA files

┌───────────────────────────┬─────────────────────────────────────────────────────────┐                                                                              
│           File            │                         Reason                          │                                                                              
├───────────────────────────┼─────────────────────────────────────────────────────────┤
│ .idea/.gitignore          │ The one we just created — excludes workspace.xml/shelf/ │                                                                              
├───────────────────────────┼─────────────────────────────────────────────────────────┤
│ .idea/compiler.xml        │ Compiler output path — project-level                    │
├───────────────────────────┼─────────────────────────────────────────────────────────┤                                                                              
│ .idea/encodings.xml       │ File encoding settings — project-level                  │
├───────────────────────────┼─────────────────────────────────────────────────────────┤                                                                              
│ .idea/jarRepositories.xml │ Maven repo URLs — project-level                         │
├───────────────────────────┼─────────────────────────────────────────────────────────┤                                                                              
│ .idea/misc.xml            │ JDK version, project SDK — project-level                │
├───────────────────────────┼─────────────────────────────────────────────────────────┤                                                                              
│ .idea/modules.xml         │ Module definition — project-level                       │
├───────────────────────────┼─────────────────────────────────────────────────────────┤                                                                              
│ .idea/vcs.xml             │ Git VCS mapping — project-level                         │
└───────────────────────────┴─────────────────────────────────────────────────────────┘

⏺ Done. The Claude Code hook is now configured in .claude/settings.json.

Here's how it differs from the git hook:

┌─────────────────┬─────────────────────┬───────────────────────┐                                                                                                    
│                 │      Git hook       │   Claude Code hook    │                                                                                                    
├─────────────────┼─────────────────────┼───────────────────────┤                                                                                                    
│ Triggers when   │ you run git push    │ Claude runs git push  │                                                                                                    
├─────────────────┼─────────────────────┼───────────────────────┤                                                                                                    
│ Location        │ .git/hooks/pre-push │ .claude/settings.json │                                                                                                    
├─────────────────┼─────────────────────┼───────────────────────┤                                                                                                    
│ Requires Claude │ No                  │ Yes                   │                                                                                                    
└─────────────────┴─────────────────────┴───────────────────────┘

To activate the Claude hook in this session, open /hooks in Claude Code to reload the config.

⏺ Here's a practical breakdown for your Java/Maven project:

  ---
Code Formatting (PostToolUse on Write|Edit)
- Runs a formatter automatically after Claude edits a file
- Example: run Google Java Formatter or prettier after every file write
- When to use: when you want consistent formatting without thinking about it — Claude edits a file, it gets formatted immediately

  ---
Code Quality (PostToolUse on Write|Edit)
- Run a linter or static analysis after Claude changes code
- Example: run checkstyle or pmd after editing Java files
- When to use: when you want instant feedback on quality issues as Claude writes code, rather than finding them later in CI

  ---
Logging (PreToolUse on Bash)
- Record every command Claude runs to a log file
- Example: echo "$(date): $cmd" >> ~/.claude/bash-log.txt
- When to use: when you want an audit trail of what Claude executed — useful for learning, debugging, or security awareness

  ---
Validation (PreToolUse on Write|Edit)
- Check something before Claude writes a file
- Example: block Claude from editing production files on certain branches, or warn if editing a file above a certain size
- When to use: as a safety guardrail — "before Claude touches this, verify X"

All three hooks are set up. Here's a summary of what each does:

┌────────────┬───────────────────────────┬───────────────────────────────────────────────────────────────────────────┐
│    Hook    │           Event           │                               What it does                                │                                               
├────────────┼───────────────────────────┼───────────────────────────────────────────────────────────────────────────┤                                               
│ Logging    │ PreToolUse on Bash        │ Logs every command Claude runs to ~/.claude/bash-log.txt with a timestamp │                                             
├────────────┼───────────────────────────┼───────────────────────────────────────────────────────────────────────────┤                                               
│ Formatting │ PostToolUse on Write|Edit │ Runs mvn formatter:format after Claude edits any .java file               │                                               
├────────────┼───────────────────────────┼───────────────────────────────────────────────────────────────────────────┤                                               
│ Quality    │ PostToolUse on Write|Edit │ Runs mvn checkstyle:check and prints warnings/errors (non-blocking)       │                                               
└────────────┴───────────────────────────┴───────────────────────────────────────────────────────────────────────────┘

Open /hooks in Claude Code to reload the config and activate them.


❯ explain this line "mvn test -q 2>&1 | grep -E "Tests run|BUILD" to me

⏺ Breaking it down piece by piece:
mvn test — runs Maven tests (you know this one)
-q — "quiet" mode, suppresses most of Maven's output so it only shows errors and test results
2>&1 — Maven prints some output to stderr (error stream) and some to stdout (normal stream). This redirects stderr into stdout so both streams go into the pipe
together
| — pipe: takes the output of the left command and feeds it as input to the right command
grep -E "Tests run|BUILD" — filters lines, keeping only those that contain "Tests run" or "BUILD". -E enables extended regex so the | means "or"
The result: instead of seeing thousands of lines of solution output, you only see the summary lines like:
Tests run: 42, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS