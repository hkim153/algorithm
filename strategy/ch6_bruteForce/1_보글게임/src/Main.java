import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <a href="https://algospot.com/judge/problem/read/BOGGLE">링크</a>
 * <p>
 * 문제
 * 보글(Boggle) 게임은 그림 (a)와 같은 5x5 크기의 알파벳 격자인
 * 게임판의 한 글자에서 시작해서 펜을 움직이면서 만나는 글자를 그 순서대로 나열하여 만들어지는 영어 단어를 찾아내는 게임입니다.
 * 펜은 상하좌우, 혹은 대각선으로 인접한 칸으로 이동할 수 있으며 글자를 건너뛸 수는 없습니다.
 * 지나간 글자를 다시 지나가는 것은 가능하지만, 펜을 이동하지않고 같은 글자를 여러번 쓸 수는 없습니다.
 * <p>
 * 예를 들어 그림의 (b), (c), (d)는 각각 (a)의 격자에서 PRETTY, GIRL, REPEAT을 찾아낸 결과를 보여줍니다.
 * <p>
 * 보글 게임판과 알고 있는 단어들의 목록이 주어질 때, 보글 게임판에서 각 단어를 찾을 수 있는지 여부를 출력하는 프로그램을 작성하세요.
 * <p>
 * 주의: 알고리즘 문제 해결 전략 6장을 읽고 이 문제를 푸시려는 분들은 주의하세요.
 * 6장의 예제 코드는 이 문제를 풀기에는 너무 느립니다. 6장의 뒷부분과 8장을 참조하세요.
 * <p>
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C(C <= 50)가 주어집니다.
 * 각 테스트 케이스의 첫 줄에는 각 5줄에 5글자로 보글 게임판이 주어집니다.
 * 게임판의 모든 칸은 알파벳 대문자입니다.
 * 그 다음 줄에는 우리가 알고 있는 단어의 수 N(1 <= N <= 10)이 주어집니다.
 * 그 후 N줄에는 한 줄에 하나씩 우리가 알고 있는 단어들이 주어집니다.
 * 각 단어는 알파벳 대문자 1글자 이상 10글자 이하로 구성됩니다.
 * <p>
 * 출력
 * 각 테스트 케이스마다 N줄을 출력합니다.
 * 각 줄에는 알고 있는 단어를 입력에 주어진 순서대로 출력하고, 해당 단어를 찾을 수 있을 경우 YES, 아닐 경우 NO를 출력합니다.
 */
/*
예제 입력
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

예제 출력
PRETTY YES
GIRL YES
REPEAT YES
KARA NO
PANDORA NO
GIAZAPX YES

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

    private static boolean inRange(int sy, int sx)
    {
        return sy < 5 && sx < 5 && sy >= 0 && sx >= 0;
    }
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
                boolean found = solution(word);
                System.out.println(word + " " + (found ? "YES" : "NO"));
            }
        }
    }

    private static boolean solution(String word)
    {
        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 5; x++)
            {
                int[][][] memoization = new int[5][5][word.length()];
                if(mySolution(y, x, word, 0, memoization))
                    return true;

                /*if(bookSolution(y, x, word))
                    return true;*/
            }
        }
        return false;
    }

    private static boolean mySolution(int sy, int sx, String word, int offset, int[][][] memoization)
    {
        //제한
        if(!inRange(sy, sx))
        {
            return false;
        }
        //memoization
        if(memoization[sy][sx][offset] != 0)
        {
            return memoization[sy][sx][offset] == 1;
        }
        //base case
        if(word.length() - 1 == offset)
        {
            if(map[sy][sx] == word.charAt(offset))
            {
                memoization[sy][sx][offset] = 1;
                return true;
            }
            else
            {
                memoization[sy][sx][offset] = -1;
                return false;
            }
        }

        //현재 case
        if(map[sy][sx] != word.charAt(offset))
        {
            memoization[sy][sx][offset] = -1;
            return false;
        }

        boolean nextCorrect = false;
        for(int i = 0; i < 8; i++)
        {
            int dy = sy + dir[i][0];
            int dx = sx + dir[i][1];
            if(mySolution(dy, dx, word, offset + 1, memoization))
            {
                memoization[sy][sx][offset] = 1;
                return true;
            }
            else
            {
                memoization[sy][sx][offset] = -1;
            }
        }

        return false;
    }

    private static boolean bookSolution(int y, int x, String word)
    {
        //기저 사례 1: 시작 위치가 범위 밖이면 무조건 실패
        if(!inRange(y, x)) return false;
        //기저 사례 2: 첫글자가 일치하지 않으면 실패
        if(map[y][x] != word.charAt(0)) return false;
        //기저 사례 3: 단어 길이가 1이면 성공
        if(word.length() == 1) return true;
        //인접한 여덟칸을 검사한다.
        for(int direction = 0; direction < 8; direction++)
        {
            int ny = y + dir[direction][0];
            int nx = x + dir[direction][1];
            if(bookSolution(ny, nx, word.substring(1)))
                return true;
        }
        return false;
    }
}
