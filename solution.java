import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'climbingLeaderboard' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY ranked
     *  2. INTEGER_ARRAY player
     */

    public static List<Integer> climbingLeaderboard(List<Integer> ranked, List<Integer> player) {
    // Write your code here
        List<Integer> result = new ArrayList<>();
    
        int n = ranked.size();
        int m = player.size();

        int[] rank = new int[n];

        rank[0] = 1;

        for (int i = 1; i < n; i++) {
            if (ranked.get(i).equals(ranked.get(i - 1))) {
                rank[i] = rank[i - 1];
            } else {
                rank[i] = rank[i - 1] + 1;
            }
        }

        for (int i = 0; i < m; i++) {
            int aliceScore = player.get(i);
            if (aliceScore > ranked.get(0)) {
                result.add(1);
            } else if (aliceScore < ranked.get(n - 1)) {
                result.add(rank[n - 1] + 1);
            } else {
                int index = binarySearch(ranked, aliceScore);
                result.add(rank[index]);
            }
        }
    
        return result;
    }

    private static int binarySearch(List<Integer> a, int key) {
    
        int lo = 0;
        int hi = a.size() - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a.get(mid) == key) {
                return mid;
            } else if (a.get(mid) < key && key < a.get(mid - 1)) {
                return mid;
            } else if (a.get(mid) > key && key >= a.get(mid + 1)) {
                return mid + 1;
            } else if (a.get(mid) < key) {
                hi = mid - 1;
            } else if (a.get(mid) > key) {
                lo = mid + 1;
            }
        }
        
        return -1;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int rankedCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> ranked = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int playerCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> player = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = Result.climbingLeaderboard(ranked, player);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
