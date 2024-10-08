import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제
 * 6
 * 1  2
 * 3  7  4
 * 9  4  1  7
 * 2  7  5  9  4
 * 위 형태와 같이 삼각형 모양으로 배치된 자연수들이 있습니다.
 * 맨 위의 숫자에서 시작해, 한 번에 한 칸씩 아래로 내려가 맨 아래 줄로 내려가는 경로를 만들려고 합니다.
 * 경로는 아래 줄로 내려갈 때마다 바로 아래 숫자, 혹은 오른쪽 아래 숫자로 내려갈 수 있습니다.
 * 이 때 모든 경로 중 포함된 숫자의 최대 합을 찾는 프로그램을 작성하세요.
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C(C <= 50)가 주어집니다.
 * 각 테스트 케이스의 첫 줄에는 삼각형의 크기 n(2 <= n <= 100)이 주어지고,
 * 그 후 n줄에는 각 1개~n개의 숫자로 삼각형 각 가로줄에 있는 숫자가 왼쪽부터 주어집니다.
 * 각 숫자는 1 이상 100000 이하의 자연수입니다.
 * 출력
 * 각 테스트 케이스마다 한 줄에 최대 경로의 숫자 합을 출력합니다.
 */
/*
예제 입력
2
5
6
1  2
3  7  4
9  4  1  7
2  7  5  9  4
5
1
2 4
8 16 8
32 64 32 64
128 256 128 256 128

예제 출력
28
341
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        while(testCase-- > 0)
        {
            int triangleSize = Integer.parseInt(br.readLine());

            int[][] map = new int[triangleSize][triangleSize];
            int[][] memoization = new int[triangleSize][triangleSize];
            for(int i = 0; i < triangleSize; i++)
            {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = 0;
                while(st.hasMoreTokens())
                {
                    int value = Integer.parseInt(st.nextToken());
                    map[i][x++] = value;
                }
            }

            System.out.println(mySolve(0, 0, map, memoization));
        }
    }

    public static int mySolve(int cy, int cx, int[][] map, int[][] memoization)
    {
        if(cy == map.length - 1)
        {
            return map[cy][cx];
        }

        if(memoization[cy][cx] != 0)
            return memoization[cy][cx];


        int max = Math.max(mySolve(cy + 1, cx, map, memoization), mySolve(cy + 1, cx + 1, map, memoization));

        int sum = map[cy][cx] + max;
        memoization[cy][cx] = sum;

        return sum;
    }
}
