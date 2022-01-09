# 1. sdong_common
My common module

# 2. build
* mvn package 
* mvn clean compile assembly:assembly


# 3. LocUtil
## 3.1. 运行
```
java -jar .\target\sdong_common-1.1.1-jar-with-dependencies.jar .\input\loc\example\
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
Use: 72 msec.
|File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|---------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|Xml      |              1|              0|              2|              1|            480|             12|             14|
|C        |              2|            107|            109|             51|           8503|            204|            420|
|Java     |              1|              5|              9|              0|            392|              7|             21|
|Python   |              1|              2|              7|              0|            339|              5|             14|  
```

* Juliet 1.3 C
```
Use: 17306 msec.
|File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|---------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|Xml      |              1|              0|              0|              0|       15721107|         362895|         362895|
|C        |         105735|        2173512|        2909034|         549607|      432944027|        9114463|       14197009|
|Python   |              4|            158|            665|              2|          45383|            473|           1296|
|Others   |            352|              0|              0|              0|        1371997|              0|              0|
```

* Juliet 1.3 Java
Use: 10434 msec.
|File Type|          Files|    Blank Lines|       Comments|Comment in Line|      File Size|    Line Counts|Row Line Counts|
|---------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|
|Python   |              4|            132|            526|              1|          32732|            360|           1018|
|Xml      |            290|           2180|           1713|            142|       16657999|         319783|         323676|
|Others   |             76|              0|              0|              0|        1999633|              0|              0|
|Properties|              2|              0|              0|              0|            837|              0|              0|
|Java     |          46475|         830870|        1449096|          78627|      256307981|        5143739|        7423705|
|C        |              8|             36|             75|              9|           5714|             84|            195|

### 3.4.3. cloc 1.92 2021.12.06
* unit test
```
       5 text files.
       5 unique files.
       0 files ignored.

github.com/AlDanial/cloc v 1.92  T=0.02 s (302.7 files/s, 28393.5 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
*C                                2            108            118            194
XML                              1              0              2             12
Java                             1              5              9              7
Python                           1              2              5              7
-------------------------------------------------------------------------------
SUM:                             5            115            134            220
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
Start time: 22:29:25.94
───────────────────────────────────────────────────────────────────────────────
Language                 Files     Lines   Blanks  Comments     Code Complexity
───────────────────────────────────────────────────────────────────────────────
C                            2       420      107       109      204         45
Java                         1        21        5         9        7          0
*Python                       1        14        1         2       11          0
XML                          1        14        0         2       12          0
───────────────────────────────────────────────────────────────────────────────
Total                        5       469      113       122      234         45
───────────────────────────────────────────────────────────────────────────────
Estimated Cost to Develop (organic) $5,878
Estimated Schedule Effort (organic) 1.953158 months
Estimated People Required (organic) 0.267393
───────────────────────────────────────────────────────────────────────────────
Processed 9714 bytes, 0.010 megabytes (SI)
───────────────────────────────────────────────────────────────────────────────

End time: 22:29:26.00
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

# 4. Tesseract-OCR
主要用于图片的文字提取.

## 4.1. 安装(目前安装版本:v5.0.0-alpha.20201127)
1. 下载安装Tesseract-OCR 安装，链接地址https://digi.bib.uni-mannheim.de/tesseract/
2. 中文的安装: Tesseract-OCR的安装目录要包含识别中文的字符集chi_sim.traineddata，可以在GitHub下载https://github.com/tesseract-ocr/tessdata
3. 查看版本: tesseract -v
```
D:\temp>tesseract -v
tesseract v5.0.0-alpha.20201127
 leptonica-1.78.0
  libgif 5.1.4 : libjpeg 8d (libjpeg-turbo 1.5.3) : libpng 1.6.34 : libtiff 4.0.9 : zlib 1.2.11 : libwebp 0.6.1 : libopenjp2 2.3.0
 Found AVX2
 Found AVX
 Found FMA
 Found SSE
 Found libarchive 3.3.2 zlib/1.2.11 liblzma/5.2.3 bz2lib/1.0.6 liblz4/1.7.5
 Found libcurl/7.59.0 OpenSSL/1.0.2o (WinSSL) zlib/1.2.11 WinIDN libssh2/1.7.0 nghttp2/1.31.0
```
4. 检查安装语言: tesseract --list-langs 
```
D:\temp>tesseract --list-langs
List of available languages (6):
chi_sim
chi_sim_vert
chi_tra
chi_tra_vert
eng
osd
```

5. 转换: 
 
 * tesseract test.png result -l chi_sim
```
D:\temp>tesseract test.JPG result -l chi_sim
Tesseract Open Source OCR Engine v5.0.0-alpha.20201127 with Leptonica
```
 * tesseract test.png result -l eng   

## 4.2. 训练
1. 下载安装jTessBoxEditor，https://sourceforge.net/projects/vietocr/files/jTessBoxEditor/

Ref
* [](https://blog.csdn.net/limingblogs/article/details/104062955)

# 5. Web Scraper
* [Web Scraper](https://www.webscraper.io/) 


# 6. Excel tips
## 6.1. Get cell web link
* 按Alt+F11进入宏操作界面，选择插入-->模块，输入以下代码;
```
Sub getLink()
For Each cell In Range("A1:A10")
  cell.Offset(0, 1) = cell.Hyperlinks(1).Address
Next

End Sub
```
* F5运行宏.

## 6.2. 填充合并单元格
1. 取消合并居中
2. 空值定位
  * 选中目标列，在Excel的开始菜单栏下，找到“查找和选择”联级菜单，点击“定位条件(s)”子菜单;
  * 定位条件选择“空值“这一项，点击确定。
3. 批量填充空值
  * 敲击键盘上的等于号，输入公式后同时按下：ctrl+enter.

## 6.3. 6.3 得到特殊字符的最有一个值
例如得到目录里的文件： 
`
=RIGHT(E2,LEN(E2)-SEARCH("$",SUBSTITUTE(E2,"/","$",LEN(E2)-LEN(SUBSTITUTE(E2,"/","")))))
`

# 7. markdown Reference
* [markdown中公式编辑教程](https://www.jianshu.com/p/25f0139637b7)
* [katex](https://katex.org/docs/supported.html)

# 8. 希腊字母在数学或物理中代表的意思
| 序号 | 大写 | 小写 | 英文注音 | 国际音标注音 | 中文读音  | 意义                                               |
| ---- | ---- | ---- | -------- | ------------ | --------- | -------------------------------------------------- |
| 1    | Α    | α    | alpha    | a:lf         | 阿尔法bai | 角度；系数                                         |
| 2    | Β    | β    | beta     | bet          | 贝塔      | 磁通系数；角度；系数                               |
| 3    | Γ    | γ    | gamma    | ga:m         | 伽马      | 电导系数（小写）                                   |
| 4    | Δ    | δ    | delta    | delt         | 德尔塔    | 变动；；屈光度                                     |
| 5    | Ε    | ε    | epsilon  | ep`silon     | 伊普西龙  | 对数之基数                                         |
| 6    | Ζ    | ζ    | zeta     | zat          | 截塔      | 系数；方位角；阻抗；相对粘度；原子序数             |
| 7    | Η    | η    | eta      | eit          | 艾塔      | 磁滞系数；效率（小写）                             |
| 8    | Θ    | θ    | thet     | θit          | 西塔      | 温度；相位角                                       |
| 9    | Ι    | ι    | iot      | aiot         | 约塔      | 微小，一点儿                                       |
| 10   | Κ    | κ    | kappa    | kap          | 卡帕      | 介质常数                                           |
| 11   | ∧    | λ    | lambda   | lambd        | 兰布达    | 波长（小写）；体积                                 |
| 12   | Μ    | μ    | mu       | mju          | 缪        | 磁导系数微（千分之一）放大因数（小写）             |
| 13   | Ν    | ν    | nu       | nju          | 纽        | 磁阻系数                                           |
| 14   | Ξ    | ξ    | xi       | ksi          | 克西      | 随机变量                                           |
| 15   | Ο    | ο    | omicron  | omik`ron     | 奥密克戎  | 无穷小量：ο(x)                                     |
| 16   | ∏    | π    | pi       | pai          | 派        | 圆周率=圆周÷直径=3.14159 2653589793                |
| 17   | Ρ    | ρ    | rho      | rou          | 肉        | 电阻系数（小写）密度(小写)                         |
| 18   | ∑    | σ    | sigma    | `sigma       | 西格马    | 总和（大写），表面密度；跨导（小写）               |
| 19   | Τ    | τ    | tau      | tau          | 套        | 时间常数                                           |
| 20   | Υ    | υ    | upsilon  | jup`silon    | 宇普西龙  | 位移                                               |
| 21   | Φ    | φ    | phi      | fai          | 佛爱      | 磁通；黄金分割符号；空集（大写）；工程学中表示直径 |
| 22   | Χ    | χ    | chi      | phai         | 西        | 卡方分布；电感                                     |
| 23   | Ψ    | ψ    | psi      | psai         | 普西      | 角速；介质电通量（静电力线）；角                   |
| 24   | Ω    | ω    | omega    | o`miga       | 欧米伽    | 欧姆（大写）；角速（小写）；角                     |

# 9. Vscode extension
* http://asciiflow.com
* https://www.kite.com/welcome/?id=702879


# 10. edge tips
* edge://flags/#enable-parallel-downloading