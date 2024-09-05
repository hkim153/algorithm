import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <a href="https://algospot.com/judge/problem/read/LIS">링크</a>
 * <p>
 * 문제
 * 어떤 정수 수열에서 0개 이상의 숫자를 지우면 이 수열의 부분 수열 (subsequence) 를 얻을 수 있다.
 * 예를 들어 10 7 4 9 의 부분 수열에는 7 4 9, 10 4, 10 9 등이 있다.
 * 단, 10 4 7 은 원래 수열의 순서와 다르므로 10 7 4 9 의 부분 수열이 아니다.
 * <p>
 * 어떤 부분 수열이 순증가할 때 이 부분 수열을 증가 부분 수열 (increasing subsequence) 라고 한다.
 * 주어진 수열의 증가 부분 수열 중 가장 긴 것의 길이를 계산하는 프로그램을 작성하라.
 * <p>
 * 어떤 수열의 각 수가 이전의 수보다 클 때, 이 수열을 순증가 한다고 한다.
 * <p>
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C (<= 50) 가 주어진다.
 * 각 테스트 케이스의 첫 줄에는 수열에 포함된 원소의 수 N (<= 500) 이 주어진다.
 * 그 다음 줄에 수열이 N개의 정수가 주어진다. 각 정수는 1 이상 100,000 이하의 자연수이다.
 * <p>
 * 출력
 * 각 테스트케이스마다 한 줄씩, 주어진 수열의 가장 긴 증가 부분 수열의 길이를 출력한다.
 */
/*
예제 입력
4
4
1 2 3 4
8
5 4 3 2 1 6 7 8
8
5 6 7 8 1 2 3 4
15
10 9 8 7 6 5 6 4 7 3 8 2 9 1 10

예제 출력
4
4
4
6
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());
        while(testCases-- > 0)
        {
            int n = Integer.parseInt(br.readLine());
            int[] num = new int[n];
            int[] memoization = new int[n];
            StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
            int i = 0;
            while(stringTokenizer.hasMoreTokens())
            {
                num[i++] = Integer.parseInt(stringTokenizer.nextToken());
            }

            System.out.println(mySolve1(n, num, memoization));
        }
    }

    public static int mySolve1(int n, int[] num, int[] memoization)
    {
        int max = 0;
        for(int off = n - 1; off >= 0; off--)
        {
            max = Math.max(max, mySolve2(off, num, memoization));
        }

        return max;
    }
    public static int mySolve2(int off, int[] num, int[] memoization)
    {
        if(off == num.length -1)
        {
            return 1;
        }

        int max = 0;
        for(int i = off + 1; i < num.length; i++)
        {
            if(num[off] < num[i])
            {
                int sum;
                if(memoization[i] != 0)
                {
                    sum = 1 + memoization[i];
                }
                else
                {
                    sum = 2;
                }
                max = Math.max(max, sum);
            }
        }
        memoization[off] = max;
        return max;
    }
}
