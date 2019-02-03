package com.sxw.code.acm.dp.maxsum_1003;

import java.util.Scanner;

/**
 * 第一步：设计，思考，规划(审题，对问题理解，概念使用，经验)
 * S1，审题，观察问题
 * 问题规模，
 * A(n)表示，长度为n的整型数组的，最大子序列
 * A(n+1)，长度为n+1的数组，最大子序列
 * 那么其实就是增加一个arr[n+1]，对之前A(n)结果的影响，
 * 分析
 * 那么可能，
 * A(n+1):
 * arr[n+1];
 * arr[n+1]+A(n)，因为“连续”其实就是：arr[n+1],arr[n].....，A(n)最后包含了arr[n]
 * A(n)，arr[n]，不在A(n)结果中
 * 3种的最大值，
 * 并且其实可以进一步发现，其实A(n)，最终结果序列，必然值以一个arr[i]结尾了，
 * 那A(n) = A(i)，其实就是之前的结果
 * 其实 A(n) = {s(1),s(2)...s(i)..s(n)}max
 * s(n)，以arr[n]结尾，包含arr[n]的最大子序列
 * 通过抽出来s(n)的概念，逻辑上就固定了，子序列(开始，结束)的结束index，瞬间就好做多了！
 * 不然，开始，结束都没定，真的不知道怎么去做
 * 那么从n=1去递推，
 * Description: 最大连续子序列求和
 * User: shixiangweii
 * Date: 2019-01-15
 * Time: 19:56
 * <p>
 * 一点感触：
 * 刚开始的时候，没有设计的概念，那个时候就是基本的代码关，比如算法很明确，就是练习C语言的语法
 * 这个时候的问题，其实大多不是很难，但是也其实有“设计，思考”的概念在里面，但是当时的自己感受不到，什么叫做思考，设计
 * 后期当语法差不多了，基本的结构都能写了，接下来，思考、审题、设计、分析，常用算法、概念的理解变的及其重要
 * 并且这时候，注重于设计，思路，具体的代码的实现技巧次之
 * <p>
 * 其实不光当初学acm，工作也是，
 * 起初的时候，自己纠结的是具体的语法，mybatis resultMap怎么用，传参怎么丢失了，让当时那样的自己去考虑设计什么的，根本不可能
 * 后来，自己各种代码写的多了，语法什么都比较稳定了，面对问题时候，可以脱离考虑具体的语法细节，可以开始纯粹考虑设计的事情
 * 但是，真正让自己有设计的概念的，还是壮哥，
 * 其实很奇怪，为什么当初的自己一直领悟不到“设计”呢？
 * 明明现在看来这么显而易见，那么重要的问题，为什么当时自己如此的纠结，甚至还把问题归结到自己，只要的语法不够多，自己的编程能力
 * 不够好，自己不懂源码，不懂多线程上呢？完完全全的思维误区
 * <p>
 * 现在，回过来看ACM，确实，这些东西对自己来说太难了，因为像这种题目，太抽象了，不要做应用，自己能用自己生活种的感知去理解，
 * 而这种什么数字，数组，连续性，DP，太学术化，太难以理解，要分析这种问题，真的很难
 * 而且本来自己的数学的熟悉，敏感度就不够，所以真的很不好弄
 * <p>
 * 所以算法这种的，真的只能说去熟悉常用的，数据结构，常用算法，才是自己的方向，这种DP什么的真的是难
 * 做什么app，问诊，派单，最起码自己都有一个感性的认识，都能用生活中的经验帮助自己去理解
 * 而这种DP递推方程，真的是太抽象了，太不知道怎么弄了
 *
 * 其实自己就是真的难以把思维提高到那个程度，去考虑，其实如果说能多例举出来多个例子，的其实oj上的题目就简单多了，
 * 其实就是因为，大量的实例，例子数据，能够很大很大的帮助自己去理解问题，去帮助自己补充思考是欠缺的东西
 *
 * 所以还是去做业务，做应用把？？
 *
 *
 * @author shixiangweii
 */
public class Main {
    /**
     * 第二步：具体编码实现（用具体某一种语言去具体实现，涉及一些具体的语法的技巧，函数，方法的使用）
     * 比如更好的优化，可以不用s[]数组吗，直接用一个int直接保存就可以，这就是编码级别的优化
     * 具体一种思想，用编码表现出来的时候，这时候也是有编码的技巧的
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int line = in.nextInt();
        int cnt = 1;
        while (line-- > 0) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            int max = a[0], start = 0, end = 0, i, sb = 0;
            // 可以省去一个数组
            int[] s = new int[n];
            s[0] = a[0];
            for (i = 1; i < n; i++) {
                // s[i - 1] + a[i] >= a[i]
                if (s[i - 1] >= 0) {
                    s[i] = s[i - 1] + a[i];
                } else {
                    s[i] = a[i];
                    // 这个坐标自己还是理解不到位，还是不对
                    sb = i;
                }
                if (s[i] > max) {
                    max = s[i];
                    end = i;
                    start = sb;
                }
            }
            System.out.println("Case " + cnt + ":");
            cnt++;
            System.out.println(max + " " + (start + 1) + " " + (end + 1));
            if (line != 0) {
                System.out.println();
            }
        }
        in.close();
    }
}