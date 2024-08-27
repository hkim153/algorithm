import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 */
/*
3
2 1
0 1
4 6
0 1 1 2 2 3 3 0 0 2 1 3
6 10
0 1 0 2 1 2 1 3 1 4 2 3 2 4 3 4 3 5 4 5
*/
public class Main
{
    public static int[][] map;
    public static int answer;
    public static int[] friends;

    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        for(int i = 0; i < testCase; i++)
        {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numOfFriends = Integer.parseInt(st.nextToken());
            friends = new int[numOfFriends];
            int count = Integer.parseInt(st.nextToken());
            map = new int[numOfFriends][numOfFriends];
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < count; j++)
            {
                int f1 = Integer.parseInt(st.nextToken());
                int f2 = Integer.parseInt(st.nextToken());
                map[f1][f2] = 1;
                map[f2][f1] = 1;
            }
            solve(numOfFriends, numOfFriends);
            //
            System.out.println(answer);

            answer = 0;
            map = null;
            friends = null;
        }
    }

    public static void solve(int numOfFriends, int count)
    {
        if(count == 0)
        {
            answer++;
            return;
        }
        int currentFriend = 0;
        for(int i = 0; i < numOfFriends; i++)
        {
            if(friends[i] == 0)
            {
                friends[i] = 1;
                currentFriend = i;
                break;
            }
        }
        for(int i = 0; i < numOfFriends; i++)
        {
            if(map[currentFriend][i] == 1 && friends[i] == 0)
            {
                friends[i] = 1;
                solve(numOfFriends, count - 2);
                friends[i] = 0;
            }
        }
        friends[currentFriend] = 0;
    }
}