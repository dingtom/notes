```
3.标题、作者和注释 
\documentclass{article} 
   \author{My Name} 
   \title{The Title} 
\begin{document} 
   \maketitle 
   hello, world % This is comment 
\end{document}
4.章节和段落 
\documentclass{article} 
   \title{Hello World} 
\begin{document} 
   \maketitle 
   \section{Hello China} China is in East Asia. 
     \subsection{Hello Beijing} Beijing is the capital of China. 
       \subsubsection{Hello Dongcheng District} 
         \paragraph{Tian'anmen Square}is in the center of Beijing 
           \subparagraph{Chairman Mao} is in the center of Tian'anmen Square 
       \subsection{Hello Guangzhou} 
         \paragraph{Sun Yat-sen University} is the best university in Guangzhou. 
\end{document} 
5.加入目录 
\documentclass{article} 
\begin{document} 
   \tableofcontents 
   \section{Hello China} China is in East Asia. 
     \subsection{Hello Beijing} Beijing is the capital of China. 
       \subsubsection{Hello Dongcheng District} 
         \paragraph{Hello Tian'anmen Square}is in the center of Beijing 
           \subparagraph{Hello Chairman Mao} is in the center of Tian'anmen Square 
\end{document} 
6.换行
\documentclass{article} 
\begin{document} 
   Beijing is 
   the capital 
   of China. 

  Washington is 

   the capital 

   of America. 

   Amsterdam is \\ the capital \\ 
   of the Netherlands. 
\end{document}
7.数学公式 
\documentclass{article} 
   \usepackage{amsmath} 
   \usepackage{amssymb} 
\begin{document} 
8.插入图片 
\documentclass{article} 
   \usepackage{graphicx} 
\begin{document} 
   \includegraphics[width=4.00in,height=3.00in]{figure1.jpg} 
\end{document}
9.带标题图，子图
\documentclass{article}
\usepackage{amsmath}
\usepackage{amssymb}
\usepackage{graphicx, subfig}
\usepackage{caption}
\begin{document}

	\begin{figure}[!htbp]
		\centering
		\includegraphics[width = .8\textwidth]{image1.jpg}
		\caption{example of one image} \label{one-img}
	\end{figure}

	\begin{figure}[!htbp]
		\centering
		\subfloat[first sub-image]{
			\includegraphics[width = .45\textwidth]{image1.jpg}
			\label{sub1}
		}
		\qquad
		\subfloat[second sub-image]{
			\includegraphics[width = .45\textwidth]{image2.jpg}
			\label{sub2}
		}
		\caption{combined image}\label{img-together}
	\end{figure} 
	
	\begin{equation}\label{abcde}
	a+b+c+d+e=f
	\end{equation}
	
\end{document}
10.制作参考文献

建立一个新文档，把以下内容复制进入文档中，保存，保存文件名为references.bib，保存类型为UTF-8。这个文档专门用来存放参考文献的信息。

@article{rivero2001resistance,
title={Resistance to cold and heat stress: accumulation of phenolic compounds in tomato and watermelon plants},
author={Rivero, Rosa M and Ruiz, Juan M and Garc{\i}a, Pablo C and L{\'o}pez-Lefebre, Luis R and S{\'a}nchez, Esteban and Romero, Luis},
journal={Plant Science},
volume={160},
number={2},
pages={315--321},
year={2001},
publisher={Elsevier}
}

@article{gostout1992clinical,
title={The clinical and endoscopic spectrum of the watermelon stomach},
author={Gostout, Christopher J and Viggiano, Thomas R and Ahlquist, David A and Wang, Kenneth K and Larson, Mark V and Balm, Rita},
journal={Journal of clinical gastroenterology},
volume={15},
number={3},
pages={256--263},
year={1992},
publisher={LWW}
}

建立一个新文档，把以下内容复制进入文档中，保存在同一个文件夹里，保存类型为UTF-8。

\documentclass{article}
\usepackage[numbers]{natbib}
\begin{document}
          One reference about watermelon \cite{gostout1992clinical}        
          Another reference about watermelon \cite{rivero2001resistance}        
          \bibliographystyle{plain}        
          \bibliography{references}        
        \end{document}

```
#公式
![image.png](https://upload-images.jianshu.io/upload_images/18339009-2ec4b273f4a7248a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
    Greek Letters $\eta$ and $\mu$ 
	
    
	Fraction $\frac{a}{b}$ 
	

	Power $a^b$ 
	

	Subscript $a_b$ 
	

	Derivate $\frac{\partial y}{\partial t} $ 
	

	Vector $\vec{n}$ 
	

	Bold $\mathbf{n}$ 
	

	To time differential $\dot{F}$ 
	
	Matrix (lcr here means left, center or right for each column) 
	$\[ 
	\left[ 
	\begin{array}{lcr} 
	a1 & b22 & c333 \\ 
	d444 & e555555 & f6 
	\end{array} 
	\right] 
	\]$
	
	Equations(here \& is the symbol for aligning different rows) 
	$\begin{align} 
	a+b&=c\\ 
	d&=e+f+g 
	\end{align}$
	
	$\[ 
	\left\{ 
	\begin{aligned} 
	&a+b=c\\ 
	&d=e+f+g 
	\end{aligned} 
	\right. 
	\]$
```


