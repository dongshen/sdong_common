Tips

- [1. Tesseract-OCR](#1-tesseract-ocr)
  - [1.1. 安装(目前安装版本:v5.0.0-alpha.20201127)](#11-安装目前安装版本v500-alpha20201127)
  - [1.2. 训练](#12-训练)
- [2. Web Scraper](#2-web-scraper)
- [3. Excel tips](#3-excel-tips)
  - [3.1. Get cell web link](#31-get-cell-web-link)
  - [3.2. 填充合并单元格](#32-填充合并单元格)
  - [3.3. 6.3 得到特殊字符的最后一个值](#33-63-得到特殊字符的最后一个值)
- [4. markdown Reference](#4-markdown-reference)
- [5. 希腊字母在数学或物理中代表的意思](#5-希腊字母在数学或物理中代表的意思)
- [6. Vscode extension](#6-vscode-extension)
- [7. edge tips](#7-edge-tips)
- [8. GOOGLE 人机验证(RECAPTCHA)无法显示解决方案](#8-google-人机验证recaptcha无法显示解决方案)
- [9. Git](#9-git)
- [Reg](#reg)

# 1. Tesseract-OCR
主要用于图片的文字提取.

## 1.1. 安装(目前安装版本:v5.0.0-alpha.20201127)
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

## 1.2. 训练
1. 下载安装jTessBoxEditor，https://sourceforge.net/projects/vietocr/files/jTessBoxEditor/

Ref
* [](https://blog.csdn.net/limingblogs/article/details/104062955)

# 2. Web Scraper
* [Web Scraper](https://www.webscraper.io/) 


# 3. Excel tips
## 3.1. Get cell web link
* 按Alt+F11进入宏操作界面，选择插入-->模块，输入以下代码;
```
Sub getLink()
For Each cell In Range("A1:A10")
  cell.Offset(0, 1) = cell.Hyperlinks(1).Address
Next

End Sub
```
* F5运行宏.

## 3.2. 填充合并单元格
1. 取消合并居中
2. 空值定位
  * 选中目标列，在Excel的开始菜单栏下，找到“查找和选择”联级菜单，点击“定位条件(s)”子菜单;
  * 定位条件选择“空值“这一项，点击确定。
3. 批量填充空值
  * 敲击键盘上的等于号，输入公式后同时按下：ctrl+enter.

## 3.3. 6.3 得到特殊字符的最后一个值
例如得到目录里的文件： 
`
=RIGHT(E2,LEN(E2)-SEARCH("$",SUBSTITUTE(E2,"/","$",LEN(E2)-LEN(SUBSTITUTE(E2,"/","")))))
`

# 4. markdown Reference
* [markdown中公式编辑教程](https://www.jianshu.com/p/25f0139637b7)
* [katex](https://katex.org/docs/supported.html)

# 5. 希腊字母在数学或物理中代表的意思
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

# 6. Vscode extension
* http://asciiflow.com
* https://www.kite.com/welcome/?id=702879


# 7. edge tips
* edge://flags/#enable-parallel-downloading

# 8. GOOGLE 人机验证(RECAPTCHA)无法显示解决方案
* Header editor

https://blog.csdn.net/qq_42729058/article/details/116915982

首先在此处下载我写好的配置: GitHub Release

在“本地文件”点击“导入”，导入刚才你下载的配置文件。

方法2: 导入在线配置

在下载规则中，填入下面的地址(任选其一，推荐使用 GitHub 版本):

(GitHub，推荐) https://azurezeng.github.io/static/HE-GoogleRedirect.json
(本站服务器) https://www.azurezeng.com/static/HE-GoogleRedirect.json

# 9. Git
* 永久删除不用的大文件
git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch -r input/Juliet_Test_Suite_v1.3_for_Java' --prune-empty --tag-name-filter cat -- --all
git push origin --all --force

# Reg
