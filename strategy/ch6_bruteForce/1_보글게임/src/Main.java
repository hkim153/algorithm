import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 5x5 격자 판에서 원하는 글자 찾기
 * 글자는 상화좌우 / 대각선으로 이어서 만들어질 수 있다.
 * 주어진 칸에서 시작해서 특정 단어를 찾을 수 있는지 확인하는 문제
 */
/*
입력
1
URLPM
XPRET
GIAET
XTNZY
XOQRS
6
PRETTY
GIRL
REPEAT
KARA
PANDORA
GIAZAPX

시간 초과 나는 인풋:
1
AAAAA
AAAAA
AAAAA
AAACC
AAACB
1
AAAAAAAAAB

동적 프로그래밍 써야 한다는데;;
*/

public class Main
{
    private static char[][] map = new char[5][5];

    private static int[][] dir = {{0, 1}, {1, 0}, {1, 1}, {-1, 0}, {0, -1}, {-1, -1}, {1, -1}, {-1, 1}};

    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        for(int i = 0; i < testCase; i++)
        {
            for(int y = 0; y < 5; y++)
            {
                String line = br.readLine();
                String value = line.trim(); //아나  ㅡㅡ
                for(int x = 0; x < 5; x++)
                {
                    map[y][x] = value.charAt(x);
                }
            }

            int wordCount = Integer.parseInt(br.readLine());
            for(int y = 0; y < wordCount; y++)
            {
                String word = br.readLine();
                boolean found = hasWord(word);
                System.out.println(word + " " + (found ? "YES" : "NO"));
            }
        }
    }

    private static boolean hasWord(String word)
    {
        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 5; x++)
            {
                if(hasWord(y, x, word, 0))
                    return true;
            }
        }
        return false;
    }

    private static boolean hasWord(int sy, int sx, String word, int offset)
    {
        if(word.length() == offset)
        {
            return true;
        }
        if(sy >= 5 || sx >= 5 || sy < 0 || sx < 0)
        {
            return false;
        }
        if(map[sy][sx] != word.charAt(offset))
            return false;

        for(int i = 0; i < 8; i++)
        {
            int dy = sy + dir[i][0];
            int dx = sx + dir[i][1];
            if(hasWord(dy, dx, word, offset + 1))
                return true;
        }
        return false;
    }
}
