# sdong_common
my own common module

# build
mvn package -Dmaven.test.skip=true

# LocUtil

* Source lines of code
引自[wiki](http://en.wikipedia.org/wiki/Source_lines_of_code)  
源代码行（SLOC或LOC）是一种软件度量标准，用于通过计算程序源代码文本中的行数来衡量软件程序的大小。SLOC通常用于预测开发程序所需的工作量，并在软件生产完成后估算编程的生产率或工作量。

* 測量方法
SLOC度量有两种主要类型：
    * 物理SLOC（LOC）: 最常见定义是程序源代码文本中包含注释行的行数。
    * 逻辑SLOC（LLOC）: 逻辑LOC尝试测量“语句”的数量，它们的特定定义与特定的计算机语言相关（对于类似于C的编程语言，一种简单的逻辑LOC度量是语句终止分号的数量）。

这里使用的方法主要指物理LOC.
  
* 开源LOC tools

|名称|网址|开发语言|license|最后更新时间|特性|比对|
|-|-|-|-|-|-|-|
|cloc|https://github.com/AlDanial/cloc|perl|GPL v2|2020-05-18|正则表达式|√|
|scc|https://github.com/boyter/scc/|go|MIT|2020-03-03|字节状态机|√|
|loc|https://github.com/cgag/loc|rust|MIT|2017-10-15| contains code from Tokei by Aaronepower and ripgrep by BurntSushi.|√|
|gocloc|https://github.com/hhatto/gocloc|go|MIT|2020-04-02|inspired by tokei,不支持windows|
|tokei|https://github.com/Aaronepower/tokei|rust|APACHE 2.0|2020-06-24|字节状态机|√|
|Ohcount|https://github.com/blackducksoftware/ohcount|ruby|GPL 2.0|2020-02-12|不支持windows,Ragel(状态机)||
|sclc|https://code.google.com/archive/p/sclc/|perl|GPL|2010-08-09||
|SLOCCount|https://dwheeler.com/sloccount/|perl|GPL|2004-08-02||
|Sonar|http://www.sonarsource.org/|java|GPL 3.0|2020-04||
|ployglot|https://github.com/vmchale/polyglot|ATS|BSD 3|2020-01-11|不支持windows||
|locCount|https://gitlab.com/esr/loccount|go|BSD 2||SLOCCount by go|
|Unified Code Count|http://csse.usc.edu/ucc_new/wordpress/|C++|2015-06-09|USC-CSSE||


* 参考
  * [Sloc Cloc and Code - What happened on the way to faster Cloc](https://boyter.org/posts/sloc-cloc-code/)
  * [Polyglot Is the Fastest Code-counting Tool Available](http://blog.vmchale.com/article/polyglot-comparisons)
  * [Differences in the Deﬁnition and Calculation of the LOC Metric in Free Tools](http://inf.u-szeged.hu/~beszedes/research/SED-TR2014-001-LOC.pdf)

* 精确度比较
  |期望值\工具|testcase|cloc 1.86|scc 2.12.0|tokei 12.0.4|loc 0.4.1|
  |-|-|-|-|-|-|
  |blank Line|50|50(√)|50(√)|50(√)|50(√)|
  |comment line|47|57(×)|47(√)|60(×)|41(×)|
  |comment in Line|37|||
  |Line of Code| 46|36(×)|46(√)|60(×)|52(×)|
  |row line|143||143(√)|143(√)|143(√)|
  |use time||0.03s|


# Tesseract-OCR
主要用于图片的文字提取.

## 安装
1. 下载安装Tesseract-OCR 安装，链接地址https://digi.bib.uni-mannheim.de/tesseract/
2. 中文的安装: Tesseract-OCR的安装目录要包含识别中文的字符集chi_sim.traineddata，可以在GitHub下载https://github.com/tesseract-ocr/tessdata
3. 查看版本 tesseract -v
4. 转换: tesseract test.png result -l chi_sim
   
## 训练
1. 下载安装jTessBoxEditor，https://sourceforge.net/projects/vietocr/files/jTessBoxEditor/

Ref
* [](https://blog.csdn.net/limingblogs/article/details/104062955)