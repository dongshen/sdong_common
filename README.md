# 1. sdong_common
My common module

# 2. build
* mvn package 
* mvn clean assembly:assembly -Pdist
* mvn clean install -Pdist

# 3. LocUtil
## 3.1. 运行
```
java -jar .\target\locutil.jar .\input\loc\example\
```

## 3.2. LocUtil 介绍
* Source lines of code
引自[wiki](http://en.wikipedia.org/wiki/Source_lines_of_code)  
源代码行（SLOC或LOC）是一种软件度量标准，用于通过计算程序源代码文本中的行数来衡量软件程序的大小。SLOC通常用于预测开发程序所需的工作量，并在软件生产完成后估算编程的生产率或工作量。

* 測量方法
SLOC度量有两种主要类型：
    * 物理SLOC（LOC）: 最常见定义是程序源代码文本中包含注释行的行数。
    * 逻辑SLOC（LLOC）: 逻辑LOC尝试测量“语句”的数量，它们的特定定义与特定的计算机语言相关（对于类似于C的编程语言，一种简单的逻辑LOC度量是语句终止分号的数量）。

这里使用的方法主要指物理LOC.
  
* 开源LOC tools

| 名称               | 网址                                         | 开发语言 | license    | 最后更新时间 | 特性                                                               | 比对 |
| ------------------ | -------------------------------------------- | -------- | ---------- | ------------ | ------------------------------------------------------------------ | ---- |
| cloc               | https://github.com/AlDanial/cloc             | perl     | GPL v2     | 2020-05-18   | 正则表达式                                                         | √    |
| scc                | https://github.com/boyter/scc/               | go       | MIT        | 2020-03-03   | 字节状态机                                                         | √    |
| loc                | https://github.com/cgag/loc                  | rust     | MIT        | 2017-10-15   | contains code from Tokei by Aaronepower and ripgrep by BurntSushi. | √    |
| gocloc             | https://github.com/hhatto/gocloc             | go       | MIT        | 2020-04-02   | inspired by tokei,不支持windows                                    |
| tokei              | https://github.com/Aaronepower/tokei         | rust     | APACHE 2.0 | 2020-06-24   | 字节状态机                                                         | √    |
| Ohcount            | https://github.com/blackducksoftware/ohcount | ruby     | GPL 2.0    | 2020-02-12   | 不支持windows,Ragel(状态机)                                        |      |
| sclc               | https://code.google.com/archive/p/sclc/      | perl     | GPL        | 2010-08-09   |                                                                    |
| SLOCCount          | https://dwheeler.com/sloccount/              | perl     | GPL        | 2004-08-02   |                                                                    |
| Sonar              | http://www.sonarsource.org/                  | java     | GPL 3.0    | 2020-04      |                                                                    |
| ployglot           | https://github.com/vmchale/polyglot          | ATS      | BSD 3      | 2020-01-11   | 不支持windows                                                      |      |
| locCount           | https://gitlab.com/esr/loccount              | go       | BSD 2      |              | SLOCCount by go                                                    |
| Unified Code Count | http://csse.usc.edu/ucc_new/wordpress/       | C++      | 2015-06-09 | USC-CSSE     |                                                                    |


* 参考
  * [Sloc Cloc and Code - What happened on the way to faster Cloc](https://boyter.org/posts/sloc-cloc-code/)
  * [Polyglot Is the Fastest Code-counting Tool Available](http://blog.vmchale.com/article/polyglot-comparisons)
  * [Differences in the Deﬁnition and Calculation of the LOC Metric in Free Tools](http://inf.u-szeged.hu/~beszedes/research/SED-TR2014-001-LOC.pdf)

## 3.3. 精确度比较
  | 期望值\工具     | testcase | cloc 1.86 | scc 2.12.0 | tokei 12.0.4 | loc 0.4.1 |
  | --------------- | -------- | --------- | ---------- | ------------ | --------- |
  | blank Line      | 50       | 50(√)     | 50(√)      | 50(√)        | 50(√)     |
  | comment line    | 47       | 57(×)     | 47(√)      | 60(×)        | 41(×)     |
  | comment in Line | 37       |           |            |
  | Line of Code    | 46       | 36(×)     | 46(√)      | 60(×)        | 52(×)     |
  | row line        | 143      |           | 143(√)     | 143(√)       | 143(√)    |
  | use time        |          | 0.03s     |

## 3.4. 效率比较
### 3.4.1. 比较代码
  * unit test
  * Juliet 1.3 C
  * Juliet 1.3 Java

### 3.4.2. LocUtil
* unit test
```
Use: 95 msec.
| File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|----------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|C         |              2|            107|            109|             51|           8503|            204|            420|
|Xml       |              1|              0|              2|              1|            480|             12|             14|
|Python    |              1|              2|              7|              0|            339|              5|             14|
|Java      |              1|              5|              9|              0|            392|              7|             21|
|Properties|              1|              3|             14|              0|            866|              3|             20| 
```

* Juliet 1.3 C
```
Use: 18566 msec.
| File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|----------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|Others    |            352|              0|              0|              0|        1371997|              0|              0|
|Python    |              4|            158|            665|              2|          45383|            473|           1296|
|Xml       |              1|              0|              0|              0|       15721107|         362895|         362895|
|C         |         105735|        2173512|        2909034|         549607|      432944027|        9114463|       14197009|
```

* Juliet 1.3 Java
```
Use: 9393 msec.
| File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|----------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|Java      |          46475|         830870|        1449096|          78627|      256307981|        5143739|        7423705|
|C         |              8|             36|             75|              9|           5714|             84|            195|
|Xml       |            290|           2180|           1713|            142|       16657999|         319783|         323676|
|Properties|              2|              1|             13|              0|            837|              7|             21|
|Others    |             76|              0|              0|              0|        1999633|              0|              0|
|Python    |              4|            132|            526|              1|          32732|            360|           1018|
```

### 3.4.3. cloc 1.92 2021.12.06
* unit test
```
       6 text files.
       6 unique files.
       0 files ignored.

github.com/AlDanial/cloc v 1.92  T=0.02 s (293.9 files/s, 23950.7 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
C                                2            108            118            194
XML                              1              0              2             12
Java                             1              5              9              7
Python                           1              2              5              7
Properties                       1              3             14              3
-------------------------------------------------------------------------------
SUM:                             6            118            148            223
-------------------------------------------------------------------------------
```

* Juliet 1.3 C
```
  106092 text files.
  106011 unique files.
      81 files ignored.

github.com/AlDanial/cloc v 1.92  T=147.83 s (717.1 files/s, 97681.7 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
C                            54486         773618        1635084        4861805
C++                          46670        1036593        1202118        3774373
C/C++ Header                  4500         317647          70568         389843
XML                              1              0              0         362895
make                           153           3200            459           7773
DOS Batch                      197            593            789           1188
Python                           4            276            318            702
-------------------------------------------------------------------------------
SUM:                        106011        2131927        2909336        9398579
-------------------------------------------------------------------------------
```

* Juliet 1.3 Java
```
   46797 text files.
   46563 unique files.
     292 files ignored.

github.com/AlDanial/cloc v 1.92  T=105.59 s (441.0 files/s, 73233.5 lines/s)
------------------------------------------------------------------------------------
Language                          files          blank        comment           code
------------------------------------------------------------------------------------
Java                              46475         830879        1449087        5143739
XML                                  68            197            196         306719
Python                                4            211            310            497
Ant                                   4             41             27            265
MSBuild script                        1              0              0            249
C++                                   4             18             49             48
C/C++ Header                          4             18             26             36
Visual Studio Solution                1              1              1             18
Properties                            2              1             13              7
------------------------------------------------------------------------------------
SUM:                              46563         831366        1449709        5451578
------------------------------------------------------------------------------------
```


### 3.4.4. Scc 3.00 2021.02.23
* Unit test
```
Start time:  0:10:46.54
───────────────────────────────────────────────────────────────────────────────
Language                 Files     Lines   Blanks  Comments     Code Complexity
───────────────────────────────────────────────────────────────────────────────
C                            2       420      107       109      204         45
Java                         1        21        5         9        7          0
Properties File              1        20        3        14        3          0
Python                       1        14        1         2       11          0
XML                          1        14        0         2       12          0
───────────────────────────────────────────────────────────────────────────────
Total                        6       489      116       136      237         45
───────────────────────────────────────────────────────────────────────────────
Estimated Cost to Develop (organic) $5,957
Estimated Schedule Effort (organic) 1.963111 months
Estimated People Required (organic) 0.269620
───────────────────────────────────────────────────────────────────────────────
Processed 10580 bytes, 0.011 megabytes (SI)
───────────────────────────────────────────────────────────────────────────────

End time:  0:10:46.62
```

* Juliet 1.3 C
```
start time: 22:20:45.79
───────────────────────────────────────────────────────────────────────────────
Language                 Files     Lines   Blanks  Comments     Code Complexity
───────────────────────────────────────────────────────────────────────────────
C                        54486   7270507   773618   1635084  4861805     485357
C++                      46749   6148444  1029757   1164812  3953875     177248
C Header                  4500    778058   315747     70468   391843          0
Batch                      197      2570      593         0     1977          0
Makefile                   153     11432     3200       459     7773          0
Python                       4      1296       84       108     1104         50
Plain Text                   1        32        4         0       28          0
XML                          1    362895        0         0   362895          0
───────────────────────────────────────────────────────────────────────────────
Total                   106091  14575234  2123003   2870931  9581300     662655
───────────────────────────────────────────────────────────────────────────────
Estimated Cost to Develop (organic) $409,346,410
Estimated Schedule Effort (organic) 135.209944 months
Estimated People Required (organic) 268.966549
───────────────────────────────────────────────────────────────────────────────
Processed 449200468 bytes, 449.200 megabytes (SI)
───────────────────────────────────────────────────────────────────────────────

End time: 22:20:48.56
```

* Juliet 1.3 Java
``` 
Start time: 22:49:51.75
───────────────────────────────────────────────────────────────────────────────
Language                 Files     Lines   Blanks  Comments     Code Complexity
───────────────────────────────────────────────────────────────────────────────
Java                     46475   7423705   826189   1444350  5153166     493950
XML                        289    323670     2180      1713   319777          0
Plain Text                   8      1497      282         0     1215          0
C Header                     4        80       18        26       36          0
C++                          4       115       18        49       48          1
Python                       4      1018       77       116      825         45
Properties File              2        21        1        13        7          0
gitignore                    1         7        0         0        7          0
───────────────────────────────────────────────────────────────────────────────
Total                    46787   7750113   828765   1446267  5475081     493996
───────────────────────────────────────────────────────────────────────────────
Estimated Cost to Develop (organic) $227,460,192
Estimated Schedule Effort (organic) 108.152885 months
Estimated People Required (organic) 186.845743
───────────────────────────────────────────────────────────────────────────────
Processed 273084031 bytes, 273.084 megabytes (SI)
───────────────────────────────────────────────────────────────────────────────

End time: 22:49:53.63
```

