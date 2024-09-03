import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 문제
 * 와일드카드는 다양한 운영체제에서 파일 이름의 일부만으로 파일 이름을 지정하는 방법이다.
 * 와일드카드 문자열은 일반적인 파일명과 같지만, * 나 ? 와 같은 특수 문자를 포함한다.
 * <p>
 * 와일드카드 문자열을 앞에서 한 글자씩 파일명과 비교해서, 모든 글자가 일치했을 때 해당 와일드카드 문자열이 파일명과 매치된다고 하자.
 * 단, 와일드카드 문자열에 포함된 ? 는 어떤 글자와 비교해도 일치한다고 가정하며, * 는 0 글자 이상의 어떤 문자열에도 일치한다고 본다.
 * <p>
 * 예를 들어 와일드 카드 he?p 는 파일명 help 에도, heap 에도 매치되지만, helpp 에는 매치되지 않는다.
 * 와일드 카드 *p* 는 파일명 help 에도, papa 에도 매치되지만, hello 에는 매치되지 않는다.
 * <p>
 * 와일드카드 문자열과 함께 파일명의 집합이 주어질 때, 그 중 매치되는 파일명들을 찾아내는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 수 C (1 <= C <= 10) 가 주어진다.
 * 각 테스트 케이스의 첫 줄에는 와일드카드 문자열 W 가 주어지며, 그 다음 줄에는 파일명의 수 N (1 <= N <= 50) 이 주어진다.
 * 그 후 N 줄에 하나씩 각 파일명이 주어진다. 파일명은 공백 없이 알파벳 대소문자와 숫자만으로 이루어져 있으며,
 * 와일드카드는 그 외에 * 와 ? 를 가질 수 있다. 모든 문자열의 길이는 1 이상 100 이하이다.
 * <p>
 * 출력
 * 각 테스트 케이스마다 주어진 와일드카드에 매치되는 파일들의 이름을 한 줄에 하나씩 아스키 코드 순서(숫자, 대문자, 소문자 순)대로 출력한다.
 */

/*
2
he?p
3
help
heap
helpp
*p*
3
help
papa
hello

답:
heap
help
help
papa

6
he?p
3
help
heap
helpp
p
3
help
papa
hello
bb
1
babbbc
t*l?*o*r?ng*s
3
thelordoftherings
tlorngs
tllorrngs
******a
2
aaaaaaaaaab
abcdea
a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a
2
ahfjsdhfjkdshfkjdshfkdsaojajfjaljaflkajflkdsaljflkaflsaffasfa54656454aafasfsdafsaaaaaaaaaaaaaaaaaaaa
aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb

 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        while(testCase-- > 0)
        {
            String wildCard = br.readLine();
            int fileCount = Integer.parseInt(br.readLine());
            ArrayList<String> ans = new ArrayList<>();
            for(int i = 0; i < fileCount; i++)
            {
                String file = br.readLine();
                int[][] memoization = new int[wildCard.length() + 100][file.length() + 100]; //넉넉히 잡는다
                if(mySolve(0, 0, wildCard, file, memoization))
                    ans.add(file);
            }

            Collections.sort(ans);
            for(int k = 0; k < ans.size(); k++)
            {
                System.out.println(ans.get(k));
            }
        }
    }

    //책 답안지
    private static boolean solve(int wo, int fo, String wildCard, String file, int[][] memoization)
    {
        if(memoization[wo][fo] != 0)
        {
            return memoization[wo][fo] == 1;
        }

        if(wo < wildCard.length() && fo < file.length() &&
              (wildCard.charAt(wo) == '?' || wildCard.charAt(wo) == file.charAt(fo)))
        {
            if(solve(wo + 1, fo + 1, wildCard, file, memoization))
            {
                memoization[wo][fo] = 1;
                return true;
            }
        }

        if(wo == wildCard.length())
        {
            if(fo == file.length())
            {
                memoization[wo][fo] = 1;
                return true;
            }
            else
            {
                memoization[wo][fo] = -1;
                return false;
            }
        }

        if(wildCard.charAt(wo) == '*')
        {
            if(solve(wo + 1, fo, wildCard, file, memoization) || (fo < file.length() && solve(wo, fo + 1, wildCard, file, memoization)))
            {
                memoization[wo][fo] = 1;
                return true;
            }
        }

        memoization[wo][fo] = -1;
        return false;
    }

    //맞음
    private static boolean mySolve(int wo, int fo, String wildCard, String file, int[][] memoization)
    {
        if(fo == file.length())
        {
            if(wo == wildCard.length())
            {
                return true;
            }
            else if(wo < file.length())
            {
                if(wildCard.charAt(wo) == '*')
                    return solve(wo + 1, fo, wildCard, file, memoization);
                else
                    return false;
            }
            return false;
        }
        else if(wo == wildCard.length())
        {
            return false;
        }

        if(memoization[wo][fo] == -1)
        {
            return false;
        }

        if(wildCard.charAt(wo) == '*')
        {
            if(wo + 1 < wildCard.length() && (wildCard.charAt(wo + 1) == file.charAt(fo) || wildCard.charAt(wo + 1) == '*'))
                return solve(wo + 1, fo, wildCard, file, memoization) || solve(wo, fo + 1, wildCard, file, memoization);
            else
                return solve(wo, fo + 1, wildCard, file, memoization);
        }
        else if(wildCard.charAt(wo) == '?' || wildCard.charAt(wo) == file.charAt(fo))
        {
            return solve(wo + 1, fo + 1, wildCard, file, memoization);
        }

        memoization[wo][fo] = -1;
        return false;
    }
}
