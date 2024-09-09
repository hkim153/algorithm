import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <a href="https://algospot.com/judge/problem/read/CLOCKSYNC">링크</a>
 * <p>
 * 문제
 * judge-attachments/d3428bd7a9a425b85c9d3c042b674728/clocks.PNG
 * <p>
 * 그림과 같이 4 x 4 개의 격자 형태로 배치된 16개의 시계가 있다.
 * 이 시계들은 모두 12시, 3시, 6시, 혹은 9시를 가리키고 있다.
 * 이 시계들이 모두 12시를 가리키도록 바꾸고 싶다.
 * <p>
 * 시계의 시간을 조작하는 유일한 방법은 모두 10개 있는 스위치들을 조작하는 것으로,
 * 각 스위치들은 모두 적게는 3개에서 많게는 5개의 시계에 연결되어 있다.
 * 한 스위치를 누를 때마다, 해당 스위치와 연결된 시계들의 시간은 3시간씩 앞으로 움직인다.
 * 스위치들과 그들이 연결된 시계들의 목록은 다음과 같다.
 * <p>
 * 0	0, 1, 2
 * 1	3, 7, 9, 11
 * 2	4, 10, 14, 15
 * 3	0, 4, 5, 6, 7
 * 4	6, 7, 8, 10, 12
 * 5	0, 2, 14, 15
 * 6	3, 14, 15
 * 7	4, 5, 7, 14, 15
 * 8	1, 2, 3, 4, 5
 * 9	3, 4, 5, 9, 13
 * 시계들은 맨 윗줄부터, 왼쪽에서 오른쪽으로 순서대로 번호가 매겨졌다고 가정하자.
 * 시계들이 현재 가리키는 시간들이 주어졌을 때, 모든 시계를 12시로 돌리기 위해 최소한 눌러야 할 스위치의 수를 계산하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫 줄에 테스트 케이스의 개수 C (<= 30) 가 주어진다.
 * 각 테스트 케이스는 한 줄에 16개의 정수로 주어지며, 각 정수는 0번부터 15번까지 각 시계가 가리키고 있는 시간을 12, 3, 6, 9 중 하나로 표현한다.
 * <p>
 * 출력
 * 각 테스트 케이스당 한 줄을 출력한다.
 * 시계들을 모두 12시로 돌려놓기 위해 눌러야 할 스위치의 최소 수를 출력한다.
 * 만약 이것이 불가능할 경우 -1 을 출력한다.
 */
/*
예제 입력
2
12 6 6 6 6 6 12 12 12 12 12 12 12 12 12 12
12 9 3 12 6 6 9 3 12 9 12 9 12 12 6 6

예제 출력
2
9
 */
public class Main
{
    public static int MAX_VALUE = (int) Math.pow(4, 10) + 1;
    public static String SWITCHES =
                    "0, 1, 2\n" +
                    "3, 7, 9, 11\n" +
                    "4, 10, 14, 15\n" +
                    "0, 4, 5, 6, 7\n" +
                    "6, 7, 8, 10, 12\n" +
                    "0, 2, 14, 15\n" +
                    "3, 14, 15\n" +
                    "4, 5, 7, 14, 15\n" +
                    "1, 2, 3, 4, 5\n" +
                    "3, 4, 5, 9, 13\n";

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(br.readLine());
        while(testCases-- > 0)
        {
            int[] clocks = new int[16];
            StringTokenizer st = new StringTokenizer(br.readLine());
            int index = 0;
            while(st.hasMoreTokens())
            {
                clocks[index++] = Integer.parseInt(st.nextToken());
            }
            int[][] switches = new int[10][16];
            StringTokenizer st1 = new StringTokenizer(SWITCHES, "\n");
            index = 0;
            while(st1.hasMoreTokens())
            {
                StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ",");
                while(st2.hasMoreTokens())
                {
                    int clock = Integer.parseInt(st2.nextToken().trim());
                    switches[index][clock] = 1;
                }
                index++;
            }

            /*System.out.println(Arrays.toString(clocks));
            for(int i = 0; i < switches.length; i++)
            {
                System.out.println(Arrays.toString(switches[i]));
            }*/

            System.out.println(mySolution(clocks, switches));
        }
    }

    //bookSolution이랑 같음
    //중요한 점!
    //1. 스위치는 4번 돌리면 제자리로 돌아오는 것이기 때문에 0~3번까지만 누른다.
    //2. 스위츠를 누르는 순서는 상관없다. 0,1 누르나 1,0 누르나 똑같다.
    // 그렇기 때문에 0번 부터 시작해서 10번 스위치까지 0~3번 눌러서 완탐으로 최저 switch를 선택할 수 있다.
    // 경우의 수 4^10 = 1048576;
    private static int mySolution(int[] clocks, int[][] switches)
    {
        int ret = mySolution(clocks, switches, 0);
        if(ret == MAX_VALUE){
            return -1;
        }

        return ret;
    }
    private static int mySolution(int[] clocks, int[][] switches, int curSwitch)
    {
        if(curSwitch == switches.length)
        {
            if(checkClocks(clocks))
                return 0; //이게 왜 0인지 생각.
            return MAX_VALUE;
        }

        int ret = MAX_VALUE;
        for(int cnt = 0; cnt < 4; cnt++)
        {
            ret = Math.min(ret, cnt + mySolution(clocks, switches, curSwitch + 1)); //cnt + mySolution 생각
            pushSwitch(clocks, switches[curSwitch]);
        }

        return ret;
    }

    private static boolean checkClocks(int[] clocks)
    {
        for(int clock : clocks)
        {
            if(clock != 12)
                return false;
        }
        return true;
    }
    private static void pushSwitch(int[] clocks, int[] curSwitch)
    {
        for(int i = 0; i < curSwitch.length; i++)
        {
            if(curSwitch[i] == 1)
            {
                clocks[i] += 3;
                if(clocks[i] == 15)
                {
                    clocks[i] = 3;
                }
            }
        }
    }
}
