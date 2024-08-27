import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
입력:
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

답:
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
        for(int i = 0; i < testCase; i++)
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
            System.out.println(possibleCovering(ylen, xlen, empty, map));
        }
    }

    private static int possibleCovering(int y, int x, int empty, char[][] map)
    {
        if(empty % 3 != 0)
            return 0;

        return cover(map);
    }

    private static int cover(char[][] map)
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
                ret += cover(map);
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
