import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <a href="https://algospot.com/judge/problem/read/FENCE">링크</a>
 * <p>
 * 문제
 * 너비가 같은 N개의 나무 판자를 붙여 세운 울타리가 있습니다.
 * 시간이 지남에 따라 판자들이 부러지거나 망가져 높이가 다 달라진 관계로 울타리를 통째로 교체하기로 했습니다.
 * 이 때 버리는 울타리의 일부를 직사각형으로 잘라내 재활용하고 싶습니다.
 * 그림 (b)는 (a)의 울타리에서 잘라낼 수 있는 많은 직사각형 중 가장 넓은 직사각형을 보여줍니다.
 * 울타리를 구성하는 각 판자의 높이가 주어질 때, 잘라낼 수 있는 직사각형의 최대 크기를 계산하는 프로그램을 작성하세요.
 * 단 (c)처럼 직사각형을 비스듬히 잘라낼 수는 없습니다.
 * 판자의 너비는 모두 1이라고 가정합니다.
 * <p>
 * 입력
 * 첫 줄에 테스트 케이스의 개수 C (C≤50)가 주어집니다.
 * 각 테스트 케이스의 첫 줄에는 판자의 수 N (1≤N≤20000)이 주어집니다.
 * 그 다음 줄에는 N개의 정수로 왼쪽부터 각 판자의 높이가 순서대로 주어집니다.
 * 높이는 모두 10,000 이하의 음이 아닌 정수입니다.
 * <p>
 * 출력
 * 각 테스트 케이스당 정수 하나를 한 줄에 출력합니다.
 * 이 정수는 주어진 울타리에서 잘라낼 수 있는 최대 직사각형의 크기를 나타내야 합니다.
 */

/*
예제 입력
3
7
7 1 5 9 6 7 3
7
1 4 4 4 4 1 1
4
1 8 2 2

예제 출력
20
16
8
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        while (testCase-- > 0)
        {
            int length = Integer.parseInt(br.readLine());
            int[] h = new int[length];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < length; i++)
            {
                h[i] = Integer.parseInt(st.nextToken());
            }

            System.out.println(solve(0, length - 1, h));
        }
    }

    private static int solve(int left, int right, int[] h)
    {
        if(left == right) return h[left];

        int mid = (left + right) / 2;
        int ret = Math.max(solve(left, mid, h), solve(mid + 1, right, h));

        //***mid를 겹치는 부분***
        //처음에는 가운데를 인접한 두개의 판자만.
        int lo = mid, hi = mid + 1;
        int height = Math.min(h[lo], h[hi]);

        ret = Math.max(ret, 2*height);
        while(left < lo || hi < right)
        {
            if(left == lo)
            {
                hi++;
                height = Math.min(height, h[hi]);
            }
            else if(right == hi)
            {
                lo--;
                height = Math.min(height, h[lo]);
            }
            else
            {
                //항상 높이가 큰 것을 골라야 함
                if(h[lo-1] < h[hi+1])
                {
                    hi++;
                    height = Math.min(height, h[hi]);
                }
                else
                {
                    lo--;
                    height = Math.min(height, h[lo]);
                }
            }
            ret = Math.max(ret, height * (hi - lo + 1));
        }

        return ret;
    }
}
