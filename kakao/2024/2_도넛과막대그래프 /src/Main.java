/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/258711
 * 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프들이 있습니다.
 * 이 그래프들은 1개 이상의 정점과, 정점들을 연결하는 단방향 간선으로 이루어져 있습니다.
 * <p>
 * 크기가 n인 도넛 모양 그래프는 n개의 정점과 n개의 간선이 있습니다.
 * 도넛 모양 그래프의 아무 한 정점에서 출발해 이용한 적 없는 간선을 계속 따라가면 나머지 n-1개의
 * 정점들을 한 번씩 방문한 뒤 원래 출발했던 정점으로 돌아오게 됩니다. 도넛 모양 그래프의 형태는 다음과 같습니다.
 * 제목 없는 다이어그램.drawio.png
 * <p>
 * 크기가 n인 막대 모양 그래프는 n개의 정점과 n-1개의 간선이 있습니다.
 * 막대 모양 그래프는 임의의 한 정점에서 출발해 간선을 계속 따라가면 나머지 n-1개의 정점을 한 번씩 방문하게 되는 정점이 단 하나 존재합니다.
 * 막대 모양 그래프의 형태는 다음과 같습니다.
 * 도넛과막대2.png
 * <p>
 * 크기가 n인 8자 모양 그래프는 2n+1개의 정점과 2n+2개의 간선이 있습니다.
 * 8자 모양 그래프는 크기가 동일한 2개의 도넛 모양 그래프에서 정점을 하나씩 골라 결합시킨 형태의 그래프입니다.
 * 8자 모양 그래프의 형태는 다음과 같습니다.
 * 8자그래프.drawio.png
 * <p>
 * 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프가 여러 개 있습니다. 이 그래프들과 무관한 정점을 하나 생성한 뒤,
 * 각 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프의 임의의 정점 하나로 향하는 간선들을 연결했습니다.
 * 그 후 각 정점에 서로 다른 번호를 매겼습니다.
 * 이때 당신은 그래프의 간선 정보가 주어지면 생성한 정점의 번호와 정점을 생성하기 전 도넛 모양 그래프의 수,
 * 막대 모양 그래프의 수, 8자 모양 그래프의 수를 구해야 합니다.
 * <p>
 * 그래프의 간선 정보를 담은 2차원 정수 배열 edges가 매개변수로 주어집니다.
 * 이때, 생성한 정점의 번호, 도넛 모양 그래프의 수, 막대 모양 그래프의 수, 8자 모양 그래프의 수를 순서대로
 * 1차원 정수 배열에 담아 return 하도록 solution 함수를 완성해 주세요.
 */

/**
 * 1 ≤ edges의 길이 ≤ 1,000,000
 * edges의 원소는 [a,b] 형태이며, a번 정점에서 b번 정점으로 향하는 간선이 있다는 것을 나타냅니다.
 * 문제의 조건에 맞는 그래프가 주어집니다.
 * 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프의 수의 합은 2이상입니다.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 입출력
 * [[2, 3], [4, 3], [1, 1], [2, 1]]	[2, 1, 1, 0]
 * [[4, 11], [1, 12], [8, 3], [12, 7], [4, 2], [7, 11], [4, 8], [9, 6], [10, 11], [6, 10], [3, 5], [11, 1], [5, 3], [11, 9], [3, 8]]	[4, 0, 1, 2]
 */
public class Main
{
    public static void main(String[] args)
    {
        Main main = new Main();

        int[][][] edges = new int[][][]
                {
                        {{2, 3}, {4, 3}, {1, 1}, {2, 1}},
                        {{4, 11}, {1, 12}, {8, 3}, {12, 7}, {4, 2}, {7, 11}, {4, 8}, {9, 6}, {10, 11}, {6, 10}, {3, 5}, {11, 1}, {5, 3}, {11, 9}, {3, 8}}
                };
        int testCase = 2;
        int cTest = 0;
        while(cTest < testCase)
        {
            int[] result = main.solution(edges[cTest]);
            System.out.println("[" + result[0] + ", " + result[1] + ", " + result[2] + ", " + result[3] + "]");
            cTest++;
        }
    }

    public int[] solution(int[][] edges) {
        int OUT = 0;
        int IN = 1;
        Map<Integer, int[]> edgeCount = new HashMap<>();

        //먼저 새로 생긴 점을 구한다. 세 그래프의 합이 2개 이상이라, 새로 생긴 점에서 나가는 간선은 2개 이상이고 들어오는 간선은 0이여야 한다.
        //그래서 모든 간선의 in and out count를 구해서 새로운 점을 구한다.
        for(int[] edge : edges)
        {
            int start = edge[OUT];
            edgeCount.computeIfAbsent(start, k -> new int[2])[OUT]++;
            int end = edge[IN];
            edgeCount.computeIfAbsent(end, k -> new int[2])[IN]++;
        }

        int newP = 0, newPOUT = 0, bar = 0, eight = 0;
        for(Map.Entry<Integer, int[]> entry : edgeCount.entrySet())
        {
            System.out.println("point= " + entry.getKey() + ", arrays= " + Arrays.toString(entry.getValue()));
            if(entry.getValue()[OUT] >= 2 && entry.getValue()[IN] == 0)
            {
                newP = entry.getKey();
                newPOUT = entry.getValue()[0];
            }
            else if(entry.getValue()[OUT] == 0 && entry.getValue()[IN] >= 1)
                bar++;
            else if(entry.getValue()[OUT] == 2 && entry.getValue()[IN] >= 2)
                eight++;
        }

        int donut = newPOUT - bar - eight;

        return new int[] {newP, donut, bar, eight};
    }
}
