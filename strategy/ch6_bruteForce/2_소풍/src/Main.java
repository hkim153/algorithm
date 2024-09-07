import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <a href="https://algospot.com/judge/problem/read/PICNIC">링크</a>
 * <p>
 * 문제
 * 안드로메다 유치원 익스프레스반에서는 다음 주에 율동공원으로 소풍을 갑니다.
 * 원석 선생님은 소풍 때 학생들을 두 명씩 짝을 지어 행동하게 하려고 합니다.
 * 그런데 서로 친구가 아닌 학생들끼리 짝을 지어 주면 서로 싸우거나 같이 돌아다니지 않기 때문에, 항상 서로 친구인 학생들끼리만 짝을 지어 줘야 합니다.
 * <p>
 * 각 학생들의 쌍에 대해 이들이 서로 친구인지 여부가 주어질 때, 학생들을 짝지어줄 수 있는 방법의 수를 계산하는 프로그램을 작성하세요.
 * 짝이 되는 학생들이 일부만 다르더라도 다른 방법이라고 봅니다. 예를 들어 다음 두 가지 방법은 서로 다른 방법입니다.
 * <p>
 * (태연,제시카) (써니,티파니) (효연,유리)
 * (태연,제시카) (써니,유리) (효연,티파니)
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C (C <= 50) 가 주어집니다.
 * 각 테스트 케이스의 첫 줄에는 학생의 수 n (2 <= n <= 10) 과 친구 쌍의 수 m (0 <= m <= n*(n-1)/2) 이 주어집니다.
 * 그 다음 줄에 m 개의 정수 쌍으로 서로 친구인 두 학생의 번호가 주어집니다.
 * 번호는 모두 0 부터 n-1 사이의 정수이고, 같은 쌍은 입력에 두 번 주어지지 않습니다.
 * 학생들의 수는 짝수입니다.
 * <p>
 * 출력
 * 각 테스트 케이스마다 한 줄에 모든 학생을 친구끼리만 짝지어줄 수 있는 방법의 수를 출력합니다.
 */
/*
예제 입력
3
2 1
0 1
4 6
0 1 1 2 2 3 3 0 0 2 1 3
6 10
0 1 0 2 1 2 1 3 1 4 2 3 2 4 3 4 3 5 4 5

예제 출력
1
3
4
*/
public class Main
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        while(testCase-- > 0)
        {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numOfFriends = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            int[] friends = new int[count * 2];
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < count; j++)
            {
                int f1 = Integer.parseInt(st.nextToken());
                int f2 = Integer.parseInt(st.nextToken());
                friends[j * 2] = f1;
                friends[j * 2 + 1] = f2;
            }

            System.out.println(mySolution(numOfFriends, friends));
        }
    }

    private static int mySolution(int numOfFriends, int[] friends)
    {
        int[] selectedFriends = new int[numOfFriends];
        int[][] map = new int[numOfFriends][numOfFriends];
        for(int i = 0; i < friends.length/2; i++)
        {
            map[friends[i * 2]][friends[i * 2 + 1]] = 1;
            map[friends[i * 2 + 1]][friends[i * 2]] = 1;
        }

        return mySolution(selectedFriends, map);
    }

    private static int mySolution(int[] selectedFriends, int[][] map)
    {
        int curFriend = -1;
        for(int i = 0; i < selectedFriends.length; i++)
        {
            if(selectedFriends[i] == 0)
            {
                curFriend = i;
                break;
            }
        }

        if(curFriend == -1)
            return 1;

        selectedFriends[curFriend] = 1;

        int total = 0;
        for(int i = 0; i < selectedFriends.length; i++)
        {
            if(map[curFriend][i] == 1 && selectedFriends[i] == 0)
            {
               selectedFriends[i] = 1;
               total += mySolution(selectedFriends, map);
               selectedFriends[i] = 0;
            }
        }

        selectedFriends[curFriend] = 0; //이게 중요하네;;;
        return total;
    }

    private static int bookSolution(int numOfFriends, int[] friends)
    {
        boolean[] taken = new boolean[10]; //최대 친구 수 10명
        boolean[][] areFriends = new boolean[10][10];

        for(int i = 0; i < friends.length/2; i++)
        {
            areFriends[friends[i * 2]][friends[i * 2 + 1]] = true;
            areFriends[friends[i * 2 + 1]][friends[i * 2]] = true;
        }

        return bookSolution(taken, areFriends, numOfFriends);
    }

    private static int bookSolution(boolean[] taken, boolean[][] areFriends, int numOfFriends)
    {
        //남은 학생들 중 가장 번호가 빠른 학생을 찾는다.
        int firstFree = -1;
        for(int i = 0; i < numOfFriends; i++)
        {
            if(!taken[i])
            {
                firstFree = i;
                break;
            }
        }

        //기저 사례: 모든 학생이 짝을 찾았으면 한 가지 방법을 찾았으니 종료한다.
        if(firstFree == -1) return 1;

        int ret = 0;
        //이 학생과 짝지을 학생을 결정한다.
        for(int pairWith = firstFree; pairWith < numOfFriends; pairWith++)
        {
            if(!taken[pairWith] && areFriends[firstFree][pairWith])
            {
                taken[firstFree] = taken[pairWith] = true;
                ret += bookSolution(taken, areFriends, numOfFriends);
                taken[firstFree] = taken[pairWith] = false;
            }
        }

        return ret;
    }
}