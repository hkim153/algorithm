import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <a href="https://algospot.com/judge/problem/read/BOARDCOVER">링크</a>
 * <p>
 * 문제
 * 03.png
 * <p>
 * H*W 크기의 게임판이 있습니다.
 * 게임판은 검은 칸과 흰 칸으로 구성된 격자 모양을 하고 있는데 이 중 모든 흰 칸을 3칸짜리 L자 모양의 블록으로 덮고 싶습니다.
 * 이 때 블록들은 자유롭게 회전해서 놓을 수 있지만, 서로 겹치거나, 검은 칸을 덮거나, 게임판 밖으로 나가서는 안 됩니다.
 * 위 그림은 한 게임판과 이를 덮는 방법을 보여줍니다.
 * <p>
 * 게임판이 주어질 때 이를 덮는 방법의 수를 계산하는 프로그램을 작성하세요.
 * <p>
 * 입력
 * 력의 첫 줄에는 테스트 케이스의 수 C (C <= 30) 가 주어집니다.
 * 각 테스트 케이스의 첫 줄에는 2개의 정수 H, W (1 <= H,W <= 20) 가 주어집니다.
 * 다음 H 줄에 각 W 글자로 게임판의 모양이 주어집니다. # 은 검은 칸, . 는 흰 칸을 나타냅니다.
 * 입력에 주어지는 게임판에 있는 흰 칸의 수는 50 을 넘지 않습니다.
 * <p>
 * 출력
 * 한 줄에 하나씩 흰 칸을 모두 덮는 방법의 수를 출력합니다.
 */
/*
예제 입력
3
3 7
#.....#
#.....#
##...##
3 7
#.....#
#.....#
##..###
8 10
##########
#........#
#........#
#........#
#........#
#........#
#........#
##########

예제 출력
0
2
1514
 */
public class Main
{
    private static final int[][][] TYPE =
    {
        {{0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {0, 1}, {1, 1}},
        {{0, 0}, {1, 0}, {1, 1}},
        {{0, 0}, {1, 0}, {1, -1}}
    };
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        while(testCase-- > 0)
        {
            StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
            int ylen = Integer.parseInt(stringTokenizer.nextToken());
            int xlen = Integer.parseInt(stringTokenizer.nextToken());
            char[][] map = new char[ylen][xlen];
            int empty = 0;

            for(int y = 0; y < ylen; y++)
            {
                String line = br.readLine();
                for(int x = 0; x < xlen; x++)
                {
                    char ch = line.charAt(x);
                    if(ch == '.')
                        empty++;
                    map[y][x] = ch;
                }
            }
            System.out.println(mySolution(empty, map));
        }
    }

    //책 solution이랑 같다.
    private static int mySolution(int empty, char[][] map)
    {
        if(empty % 3 != 0)
            return 0;

        return mySolution(map);
    }

    private static int mySolution(char[][] map)
    {
        int cy = -1;
        int cx = -1;
        Loop:
        for(int y = 0; y < map.length; y++)
        {
            for(int x = 0; x < map[y].length; x++)
            {
                if(map[y][x] == '.')
                {
                    cy = y;
                    cx = x;
                    break Loop;
                }
            }
        }

        if(cy == -1)
            return 1;
        int ret = 0;
        for(int type = 0; type < 4; type++)
        {
            if(check(cy, cx, type, map))
            {
                set(cy, cx, type, map, '#');
                ret += mySolution(map);
                set(cy, cx, type, map, '.');
            }
        }
        return ret;
    }

    private static boolean check(int cy, int cx, int type, char[][] map)
    {
        for(int i = 0; i < 3; i++)
        {
            int ny = cy + TYPE[type][i][0];
            int nx = cx + TYPE[type][i][1];
            if(ny < 0 || ny >= map.length || nx < 0 || nx >= map[ny].length)
                return false;
            else if(map[ny][nx] != '.')
                return false;
        }

        return true;
    }

    private static void set(int cy, int cx, int type, char[][] map, char set)
    {
        for(int i = 0; i < 3; i++)
        {
            int ny = cy + TYPE[type][i][0];
            int nx = cx + TYPE[type][i][1];
            map[ny][nx] = set;
        }
    }
}
