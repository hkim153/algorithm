import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * <a href="https://algospot.com/judge/problem/read/PI">링크</a>
 * 문제
 * (주의: 이 문제는 TopCoder 의 번역 문제입니다.)
 * <p>
 * 가끔 TV 에 보면 원주율을 몇만 자리까지 줄줄 외우는 신동들이 등장하곤 합니다.
 * 이들이 이 수를 외우기 위해 사용하는 방법 중 하나로, 숫자를 몇 자리 이상 끊어 외우는 것이 있습니다.
 * 이들은 숫자를 세 자리에서 다섯 자리까지로 끊어서 외우는데, 가능하면 55555 나 123 같이 외우기 쉬운 조각들이 많이 등장하는 방법을 택하곤 합니다.
 * <p>
 * 이 때, 각 조각들의 난이도는 다음과 같이 정해집니다:
 * <p>
 * 모든 숫자가 같을 때 (예: 333, 5555) 난이도: 1
 * 숫자가 1씩 단조 증가하거나 단조 감소할 때 (예: 23456, 3210) 난이도: 2
 * 두 개의 숫자가 번갈아 가며 출현할 때 (예: 323, 54545) 난이도: 4
 * 숫자가 등차 수열을 이룰 때 (예: 147, 8642) 난이도: 5
 * 그 외의 경우 난이도: 10
 * 원주율의 일부가 입력으로 주어질 때, 난이도의 합을 최소화하도록 숫자들을 3자리에서 5자리까지 끊어 읽고 싶습니다. 최소의 난이도를 계산하는 프로그램을 작성하세요.
 * <p>
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C (<= 50) 가 주어집니다. 각 테스트 케이스는 8글자 이상 10000글자 이하의 숫자로 주어집니다.
 * <p>
 * 출력
 * 각 테스트 케이스마다 한 줄에 최소의 난이도를 출력합니다.
 */
/*
예제 입력
5
12341234
11111222
12122222
22222222
12673939

예제 출력
4
2
5
2
14
*/
/*
    끊어 읽을 수 있는 모든 방법은?
    8 = 3 + 5, 4 + 4, 5 + 3
    9 = 3 + 3 + 3, 4 + 5, 5 + 4,
    위에서 3 + 5 하면 불가능하다는 것을 알려주면 될 것 같다.
*/
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());
        while(testCases-- > 0)
        {
            String num = br.readLine();
            int length = num.length();
            int[] nums = new int[length];
            int[] memoization = new int[length];
            for(int i = 0; i < length; i++)
            {
                nums[i] = num.charAt(i) - '0';
            }

            System.out.println(mySolve(0, nums, memoization));
            System.out.println(Arrays.toString(memoization));
        }
    }

    public static int mySolve(int off, int[] nums, int[] memoization)
    {
        if(off == nums.length)
            return 0;
        else if(off + 2 >= nums.length)
            return -1; //불가능

        if(memoization[off] != 0)
            return memoization[off];


        int forThree = Integer.MAX_VALUE;
        int forFour = Integer.MAX_VALUE;
        int forFive = Integer.MAX_VALUE;
        if(off + 2 < nums.length)
        {
            int next = mySolve(off + 3, nums, memoization);
            if(next != -1)
            {
                forThree = mySolveFindDifficulty(off, off + 2, nums) + next;
            }
        }
        if(off + 3 < nums.length)
        {
            int next = mySolve(off + 4, nums, memoization);
            if(next != -1)
            {
                forFour = mySolveFindDifficulty(off, off + 3, nums) + next;
            }
        }
        if(off + 4 < nums.length)
        {
            int next = mySolve(off + 5, nums, memoization);
            if(next != -1)
            {
                forFive = mySolveFindDifficulty(off, off + 4, nums) + next;
            }
        }

        int min = Math.min(forThree, forFour);
        min = Math.min(min, forFive);

        if(min == Integer.MAX_VALUE)
        {
            memoization[off] = -1;
            return -1;
        }

        memoization[off] = min;
        return min;
    }
    public static int mySolveFindDifficulty(int off, int limit, int[] nums)
    {
        if(ifDifficultyOne(off, limit, nums))
            return 1;
        else if(ifDifficultyTwo(off, limit, nums))
            return 2;
        else if(ifDifficultyFour(off, limit, nums))
            return 4;
        else if(ifDifficultyFive(off, limit, nums))
            return 5;
        else
            return 10;
    }
    public static boolean ifDifficultyOne(int off, int limit, int[] nums)
    {
        int prev = nums[off];
        for(int i = off + 1; i <= limit; i++)
        {
            if(prev != nums[i])
                return false;
        }
        return true;
    }
    public static boolean ifDifficultyTwo(int off, int limit, int[] nums)
    {
        int prev = nums[off];
        int isSum = 0;
        for(int i = off + 1; i <= limit; i++)
        {
            if(prev + 1 == nums[i])
            {
                if(isSum == 0)
                    isSum = 1;
                else if(isSum == -1)
                    return false;
            }
            else if(prev - 1 == nums[i])
            {
                if(isSum == 0)
                    isSum = -1;
                else if(isSum == 1)
                    return false;
            }
            else
            {
                return false;
            }

            prev = nums[i];
        }
        return true;
    }
    public static boolean ifDifficultyFour(int off, int limit, int[] nums)
    {
        int pprev = nums[off];
        int prev = nums[off + 1];
        for(int i = off + 2; i <= limit; i++)
        {
            if(pprev != nums[i])
                return false;

            pprev = prev;
            prev = nums[i];
        }
        return true;
    }
    public static boolean ifDifficultyFive(int off, int limit, int[] nums)
    {
        int prev = nums[off + 1];
        int diff = prev - nums[off];
        for(int i = off + 2; i <= limit; i++)
        {
            if(nums[i] - prev != diff)
                return false;

            prev = nums[i];
        }
        return true;
    }
}
