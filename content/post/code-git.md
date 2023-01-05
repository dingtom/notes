---
        # 文章标题
        title: "code-git"
        # 分类
        categories: 
            - code
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
---





# Git

**Git和其他版本控制系统如SVN的一个不同之处就是有暂存区的概念。 Git管理的是修改，而不是文件**

![](https://i.loli.net/2021/06/23/fPtkTSNQoch6sd1.png)

工作区（Working Directory） 

  - 就是你在电脑里能看到的目录，比如我的learngit文件夹就是一个**工作区**： 

版本库（Repository） 

  - 工作区有一个隐藏目录.git，这个不算工作区，而是Git的**版本库**。 
  - Git的版本库里存了很多东西，其中最重要的就是称为**stage（或者叫index）的暂存区**，
    还有Git为我们自动创建的**第一个分支master，以及指向master的一个指针叫HEAD** 



第一步是用git add把文件添加进去，实际上就是把文件修改添加到暂存区

第二步是用git commit提交更改，实际上就是**把暂存区的所有内容提交到当前分支**

# 安装Git 

Linux

```  shell
sudo apt-get install git
```

Windows

 [下载安装程序](https://git-scm.com/downloads)，（网速慢的同学请移步[国内镜像]((https://npm.taobao.org/mirrors/git-for-windows/)
)）
安装完成后，在开始菜单里找到“Git”->“Git Bash”，蹦出一个类似命令行窗口的东西，就说明Git安装成功！ 

 **！！！如果你使用Windows系统，为了避免遇到各种莫名其妙的问题，请确保目录名（包括父目录）不包含中文。**




# 查看配置信息

```shell
git config --list --global
git config -e [--global]  # 编辑Git配置文件
```

# 自报家门

```shell
 git config --global user.name "dingtom" 
 git config --global user.email "2524370217@qq.com" 
```

# 创建一个版本库 
```shell
git init xx
# 第一步是用git add把文件添加进去，实际上就是把文件修改添加到暂存区
git add xx/ git add .
# 第二步是用git commit提交更改，实际上就是**把暂存区的所有内容提交到当前分支**
git commit -m "xx"
```

# 提交
```shell
# 提交工作区自上次commit之后的变化，直接到仓库区
git commit -a  
# 使用一次新的commit，替代上一次提交， 如果代码没有任何新变化，则用来改写上一次commit的提交信息
git commit --amend -m [message]  
# 重做上一次commit，并包括指定文件的新变化
git commit --amend [file1] [file2] ...  
```

# 工作区的状态
```shell
git status
# 告诉你有文件被修改过，

git diff
# 未commit前可看修改内容。
```

# 在版本的历史之间穿梭
```shell
git log
# 可以查看提交历史，以便确定要回退到哪个版本。
# 退出查看按q

git reflog
# 查看命令历史，以便确定要回到未来的哪个版本

git log 
--graph 
--oneline  # 简洁的观看历史
--abbrev-commit 
-n4 # 查看最近的4次提交，所有分支
--all # 查看所有分支版本信息

git reset --hard HEAD^  
# HEAD指向的版本就是当前版本。上一个版本就是HEAD^，上上一个版本就是HEAD^^，当然往上100个版本写100个^比较容易数不过来，所以写成HEAD~100。 

git reset --hard commit_id


# 文件回退到某一版本
git checkout 版本号 文件名
```

# 让工作区的文件恢复为暂存区

```shell
# 未add 撤销工作区修改
git restore <chaned_file>
# 撤销git add，保留工作区修改
git restore --staged <chaned_file>
# 撤销git add和工作区修改
git checkout HEAD <chaned_file>
# 撤销git commit，保留git add和工作区修改
git reset --soft HEAD~1
# 撤销git commit和git add，保留工作区修改
git reset HEAD~1
# 撤销git commit,git add和工作区修改
git reset --hard HEAD~1

#和git reset不同，git revert 本质上是增加一个 反相的commit。revert只能前进不许后退，
#在公有分支上 只能前进 不许后退，私有分支上可以使用reset
#不过同步时要使用 git push -f
git revert HEAD~1


#从工作目录中移除没有track的文件.-d表示同时移除目录,-f表示force
git clean -df
```





# 删除文件

```shell
# 删错了，因为版本库里还有   
git checkout -- filename 

# 当我们需要删除暂存区或分支上的文件, 同时工作区也不需要这个文件了
git rm 

# 当我们需要删除暂存区或分支上的文件, 但本地又需要使用, 只是不希望这个文件被版本控制, 可以使用
git rm --cached
```




#  本地仓库推送到远程仓库
```shell
#创建SSH Key：   
ssh-keygen -t rsa -C "2524370217@qq.com"
##在用户主目录里找到.ssh目录，里面有id_rsa和id_rsa.pub两个文件，这两个就是SSH Key的秘钥对，id_rsa是私钥，不能泄露出去，id_rsa.pub是公钥，可以放心地告诉任何人。   

#登陆GitHub，打开“Account settings”，“SSH Keys”页面：
#然后，点“Add SSH Key”，填上任意Title，在Key文本框里粘id_rsa.pub文件的内容：   
#首先，登陆GitHub，然后，在右上角找到“Create a new repo”按钮，创建一个新的仓库：  

#在本地关联的就是我的远程库：
git remote add origin git@github.com:git名/库名.git   

#把本地库的master分支内容推送到远程库上：
git push -u origin "master"

#由于远程库是空的，我们第一次推送master分支时，加上了-u参数，Git不但会把本地的master分支内容推送的远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。

#从现在起，只要本地作了提交，同步到远程仓库通过命令：
git push  
```

当你从远程仓库克隆时，实际上Git自动把本地的master分支和远程的master分支对应起来了，并且，远程仓库的默认名称是origin。      

```shell
# 取消本地目录下关联的远程库：
git remote remove origin

# 查看远程库的信息，
git remote -v

#推送时，要指定本地分支，这样，Git就会把该分支推送到远程库对应的远程分支上
git push origin master       
新分支推送到远程
git push -u origin dev        

多人协作的工作模式通常是这样
现在我们的小伙伴要在dev分支上做开发，就必须把远程的origin的dev分支到本地来，于是可以使用命令创建本地dev分支：
git checkout –b dev origin/dev

推送： 
git push  origin dev        
如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；
如果合并有冲突，则解决冲突，并在本地提交； 没有冲突或者解决掉冲突后，再用push推送就能成功！ 

如果git pull提示no tracking information，则说明本地分支和远程分支的链接关系没有创建，用命令
git branch --set-upstream-to=origin/dev dev
```




# 分支管理

```shell
查看分支：
git branch    
创建分支：
git branch 分支名  
切换分支：
git checkout 分支名   
创建+切换分支：
git checkout -b 分支名  

合并某分支到当前分支：
git merge --no-ff -m "merge with no-ff" dev
git merge 分支名   

删除分支：
git branch -d 分支名     
丢弃一个没有被合并过的分支，强行删除。
git branch -D 分支名 
删除远程分支: 
git push origin --delete [branchname]

```


## 无法自动合并分支
必须首先解决冲突。解决冲突后，再提交，合并完成。    

解决冲突就是把Git合并失败的文件手动编辑为我们希望的内容，再提交。    

   用```git log --graph```命令可以看到分支合并图。    

# 把当前工作现场“储藏”起来

等以后恢复现场后继续工作

```shell
git stash
# 查看刚才的工作现场
git stash list

# 工作现场还在，Git把stash内容存在某个地方了，但是需要恢复一下，有两个办法：   
git stash apply
# 恢复后，stash内容并不删除，你需要用git stash drop来删除； 
git stash pop
# 恢复的同时把stash内容也删了

# 你可以多次stash，恢复的时候，先用git stash list查看，然后恢复指定的stash，
git stash apply stash@{0} 
```

# rebase

把本地未push的分叉提交历史整理成直线；

```shell
git rebase -i commit_id    
```


- rebase之前需要经master分支拉到最新

- 执行git rebase master，有冲突就解决冲突，解决后直接git add . 再git - rebase --continue即可


# 打标签
```shell
切换到需要打标签的分支上
git tag <name>

查看所有标签
git tag

默认标签是打在最新提交的commit上的。有时候，如果忘了找到历史提交的commit id，然后打上就可以了：
git tag v0.9 f52c633

注意，标签不是按时间顺序列出，而是按字母排序的。查看标签信息
git show <tagname>

创建带有说明的标签
git tag -a v0.1 -m "version 0.1 released" 1094adb

注意：标签总是和某个commit挂钩。如果这个commit既出现在master分支，又出现在dev分支，那么在这两个分支上都可以看到这个标签。

删除：
git tag -d v0.1

推送某个标签到远程，使用命令
git push origin <tagname>

或者，一次性推送全部尚未推送到远程的本地标签
git push origin --tags

如果标签已经推送到远程，要删除远程标签就麻烦一点，先从本地删除：
git tag -d v0.9
然后，从远程删除。删除命令也是push，但是格式如下：
git push origin :refs/tags/v0.9
要看看是否真的从远程库删除了标签，可以登陆GitHub查看。
```
# 忽略文件

.gitignore只能忽略那些原来没有被track的文件，如果某些文件已经被纳入了版本管理中，则修改.gitignore是无效的。所以一定要养成在项目开始就创建.gitignore文件的习惯。

```shell
touch .gitignore
# 所有配置文件可以直接在线浏览：https://github.com/github/gitignore
```

## 忽略已提交文件

```shell
# 先把项目备份，以防万一。
git rm --cached app.iml      #从版本库中rm 文件，working dicrectory中仍然保留，
在.gitignore中添加要忽略的文件
#如果要删除目录下所有文件包括子目录中的 git rm -r --cached directory_name


#文件被.gitignore忽略了：可以用-f强制添加到Git：
git add -f App.class


#或者你发现，可能是.gitignore写得有问题，需要找出来到底哪个规则写错了，可以用git check-ignore命令检查：
git check-ignore -v App.class 

#.gitignore:3:*.class App.class
#Git会告诉我们，.gitignore的第3行规则忽略了该文件，于是我们就可以知道应该修订哪个规则。


#glob模式匹配示例
logs/
#忽略当前路径下的logs目录，包含logs下的所有子目录和文件
/logs.txt
#忽略根目录下的logs.txt文件
*.class
#忽略所有后缀为.class的文件
!/classes/a.class
#不忽略classes目录下的a.class文件
tmp/*.txt
#只忽略tmp目录下的.txt文件
**/foo
#可以忽略/foo, a/foo, a/b/foo等
```

# 命令简写
很多人都用co表示checkout，ci表示commit，br表示branch：

```shell
git config --global alias.co checkout
git config --global alias.ci commit
git config --global alias.br branch
#--global参数是全局参数，也就是这些命令在这台电脑的所有Git仓库下都有用。

#命令git reset HEAD file可以把暂存区的修改撤销掉（unstage），重新放回工作区。既然是一个unstage操作，就可以配置一个unstage别名
git config --global alias.unstage 'reset HEAD'

git config --global alias.lg "log --color --graph --abbrev-commit"

#而当前用户的Git配置文件放在用户主目录下的一个隐藏文件.gitconfig中：
cat .gitconfig
#配置别名也可以直接修改这个文件，如果改错了，可以删掉文件重新通过命令配置。
```




# 搭建一台Git服务器
推荐用Ubuntu或Debian，这样，通过几条简单的apt命令就可以完成安装。假设你已经有sudo权限的用户账号，下面，正式开始安装。
第一步，安装git：
​````sudo apt-get install git```
第二步，创建一个git用户，用来运行git服务：
​```sudo adduser git```
第三步，创建证书登录：
收集所有需要登录的用户的公钥，就是他们自己的id_rsa.pub文件，把所有公钥导入到/home/git/.ssh/authorized_keys文件里，一行一个。
第四步，初始化Git仓库：
先选定一个目录作为Git仓库，假定是/srv/sample.git，在/srv目录下输入命令：
​```sudo git init --bare sample.git```
Git就会创建一个裸仓库，裸仓库没有工作区，因为服务器上的Git仓库纯粹是为了共享，所以不让用户直接登录到服务器上去改工作区，并且服务器上的Git仓库通常都以.git结尾。然后，把owner改为git：
​```sudo chown -R git:git sample.git```
第五步，禁用shell登录：
出于安全考虑，第二步创建的git用户不允许登录shell，这可以通过编辑/etc/passwd文件完成。找到类似下面的一行：
git:x:1001:1001:,,,:/home/git:/bin/bash
改为：
git:x:1001:1001:,,,:/home/git:/usr/bin/git-shell
这样，git用户可以正常通过ssh使用git，但无法登录shell，因为我们为git用户指定的git-shell每次一登录就自动退出。
第六步，克隆远程仓库：
现在，可以通过git clone命令克隆远程仓库了，在各自的电脑上运行：
​```git clone git@server:/srv/sample.git Cloning into 'sample'... warning: You appear to have cloned an empty repository.```
剩下的推送就简单了。

管理公钥

如果团队很小，把每个人的公钥收集起来放到服务器的/home/git/.ssh/authorized_keys文件里就是可行的。如果团队有几百号人，就没法这么玩了，这时，可以用[Gitosis](https://github.com/res0nat0r/gitosis)来管理公钥。

这里我们不介绍怎么玩[Gitosis](https://github.com/res0nat0r/gitosis)了，几百号人的团队基本都在500强了，相信找个高水平的Linux管理员问题不大。

管理权限

有很多不但视源代码如生命，而且视员工为窃贼的公司，会在版本控制系统里设置一套完善的权限控制，每个人是否有读写权限会精确到每个分支甚至每个目录下。因为Git是为Linux源代码托管而开发的，所以Git也继承了开源社区的精神，不支持权限控制。不过，因为Git支持钩子（hook），所以，可以在服务器端编写一系列脚本来控制提交等操作，达到权限控制的目的。[Gitolite](https://github.com/sitaramc/gitolite)就是这个工具。

这里我们也不介绍[Gitolite](https://github.com/sitaramc/gitolite)了，不要把有限的生命浪费到权限斗争中。

小结

搭建Git服务器非常简单，通常10分钟即可完成；

要方便管理公钥，用[Gitosis](https://github.com/sitaramc/gitolite)；

要像SVN那样变态地控制权限，用[Gitolite](https://github.com/sitaramc/gitolite)

       如何参与一个开源项目呢？比如人气极高的bootstrap项目，这是一个非常强大的CSS框架，你可以访问它的项目主页https://github.com/twbs/bootstrap，点“Fork”就在自己的账号下克隆了一个bootstrap仓库，然后，从自己的账号下clone：

git clone git@github.com:michaelliao/bootstrap.git

一定要从自己的账号下clone仓库，这样你才能推送修改。如果从bootstrap的作者的仓库地址git@github.com:twbs/bootstrap.git克隆，因为没有权限，你将不能推送修改。

 如果你想修复bootstrap的一个bug，或者新增一个功能，立刻就可以开始干活，干完后，往自己的仓库推送。

如果你希望bootstrap的官方库能接受你的修改，你就可以在GitHub上发起一个pull request。当然，对方是否接受你的pull request就不一定了。

    如果你没能力修改bootstrap，但又想要试一把pull request，那就Fork一下我的仓库：https://github.com/michaelliao/learngit，创建一个your-github-id.txt的文本文件，写点自己学习Git的心得，然后推送一个pull request给我，我会视心情而定是否接受。    

    使用码云和使用GitHub类似，我们在码云上注册账号并登录后，需要先上传自己的SSH公钥。选择右上角用户头像 -> 菜单“修改资料”，然后选择“SSH公钥”，填写一个便于识别的标题，然后把用户主目录下的.ssh/id_rsa.pub文件的内容粘贴进去：  如果我们已经有了一个本地的git仓库（例如，一个名为learngit的本地库），如何把它关联到码云的远程库上呢？ 首先，我们在码云上创建一个新的项目，选择右上角用户头像 -> 菜单“控制面板”，然后点击“创建项目”： 项目名称最好与本地库保持一致：

然后，我们在本地库上使用命令git remote add把它和码云的远程库关联：git remote add origin git@gitee.com:liaoxuefeng/learngit.git

之后，就可以正常地用git push和git pull推送了！

如果在使用命令git remote add时报错：git remote add origin git@gitee.com:liaoxuefeng/learngit.git fatal: remote origin already exists. 

这说明本地库已经关联了一个名叫origin的远程库，此时，可以先用git remote -v查看远程库信息：

git remote -v 可以看到，本地库已经关联了origin的远程库，并且，该远程库指向GitHub。

我们可以删除已有的GitHub远程库：git remote rm origin

再关联码云的远程库（注意路径中需要填写正确的用户名）：git remote add origin git@gitee.com:liaoxuefeng/learngit.git

现在可以看到，origin已经被关联到码云的远程库了。通过git push命令就可以把本地库推送到Gitee上。

因为git本身是分布式版本控制系统，可以同步到另外一个远程库，当然也可以同步到另外两个远程库。

使用多个远程库时，我们要注意，git给远程库起的默认名称是origin，如果有多个远程库，我们需要用不同的名称来标识不同的远程库。

仍然以learngit本地库为例，我们先删除已关联的名为origin的远程库：git remote rm origin

然后，先关联GitHub的远程库：git remote add github git@github.com:michaelliao/learngit.git

注意，远程库的名称叫github，不叫origin了。

接着，再关联码云的远程库：git remote add gitee git@gitee.com:liaoxuefeng/learngit.git

同样注意，远程库的名称叫gitee，不叫origin。

现在，我们用git remote -v查看远程库信息，可以看到两个远程库：

如果要推送到GitHub，使用命令：git push github master

如果要推送到码云，使用命令：git push gitee master

码云也同样提供了Pull request功能，可以让其他小伙伴参与到开源项目中来。你可以通过Fork我的仓库：[https://gitee.com/liaoxuefeng/learngit](https://gitee.com/liaoxuefeng/learngit)，创建一个your-gitee-id.txt的文本文件，

写点自己学习Git的心得，然后推送一个pull request给我，这个仓库会在码云和GitHub做双向同步。

     git错误:unable to auto-detect email address

找到工程目录 (Project) 的.git文件夹，打开之后找到config文件，在最后边加上一句话

[user]

email=your email

name=your name


#显示当前的git配置 git config --list

#编辑git配置文件 git config -e [--global]

#提交暂存区的指定文件到仓库区 git commit [file] -m”message”

#提交工作区自上次commit之后的变化,直接到仓库区 git commit -a

#提交时显示所有diff信息 git commit -v

#使用一次新的commit,代替上一次提交如果代码没有变化则用来改写上一次commit的提交信息 git commit --amend -m”message”

#重做上一次commit,包括指定文件的新变化 git commit --amend [file]

#显示所有本地分支 git branch

#列出所有远程分支 git branch -r

#列出所有本地分支和远程分支 git branch -a

#新建一个分支,但依然停留就在当前分支 git branch [branch-name]

#新建一个分支,与指定的远程分支建立追踪关系 git branch --track [branch] [remote-branch]

#删除分支 git branch -d [branch-name]

#删除远程分支 git push origin --delete  [branch]

               Git branch -dr [remote/branch]

#新建一个分支并切换到该分支 git checkout -b [branch]

#切换到指定分支并更新工作区 git checkout [branch-name]

#切换到上一个分支git checkout -

#在现有分支与指定分支之间建立追踪关系 git branch --set-upstream [branch] [remote-branch]

#合并制定分支到当前分支 git merge [branch]

#衍合指定分支到当前分支 git rebase [branch]

#选择一个commit,合并进当前分支 git cheery-pick [commit]

#列出所有本地标签 git tag

#基于最新提交创建标签 git tag  [tagname]

#删除标签 git tag -d [tagname]

#删除远程标签 git push origin :refs/tags/tagname

#查看标签信息 git show [tagname]

#提交指定标签 git push [remote] [tag]

#提交所有标签 git push [remote] --tags

#新建一个分支,指向某个标签 git checkout -b [branchname] [tagname]

#显示有变更的文件 git status

#显示当前分支的版本历史 git log

#显示commit历史,以及每次commit发生变更的文件 git log --stat

#根据关键词搜索提交历史 git log -s keyword

#显示某个提交后的所有变动,每个提交占据一行 git log tagname HEAD --pretty=format:%s

#显示某个提交之后的所有变动,其提交说明必须符合搜索条件 git log tagname HEAD --grep feature

#显示某个文件的版本历史,包括文件改名 git log --follow filename

                                     Git whatchanged filename

#显示指定文件相关的每一次diff  git log -p filename

# 显示过去5次提交 git log -5 --pretty --oneline

#显示所有提交过的用户,按提交次数排序 git shortlog -sn

#显示指定文件是什么人在什么时间修改过 git blame filename

#显示暂存区和工作区的差异 git diff

#显示暂存区和上一个提交的差异 git diff --cached filename

#显示两次提交之间的差异 git diff firstbranch secondbranch

#显示工作区与当前分支最新提交之间的差异 git diff HEAD

#显示今天你写了多少行代码 git diff --shortstat “@{0 day ago}”

#显示某次提交的元数据和内容变化 git show commitid

#显示某次提交发生变化的文件 git show --name-only commitid

#显示某次提交时,某个文件的内容 git show commitid:filename

#显示当前分支的最近几次提交 git reflog

#下载远程仓库的所有变动 git fetch [remote]

#取回远程仓库的变化,并与本地分支合并 git pull [remote] [branch]

#显示所有远程仓库 git remote -v

#显示某个远程仓库的信息 git remote show [remote]

#增加一个新的远程仓库并命名 git remote add [shortname] [url]

#上传本地指定分支到远程仓库 git push [remote] [branch]

#强行推送当前分支到远程仓库,即使有冲突 git push [remote] --force

#推送所有分支到远程仓库 git push [remote] --all

#删除远程分支或标签 git push [remote] :branchname/tagname

#上传所有标签 git push --tags

#撤销工作目录中所有未提交文件的修改内容 git reset --hard HEAD

#撤销指定的未提交文件的修改内容 git checkout HEAD filename

#撤销指定的提交 git revert commitid

#退回到一天之前的版本 git log --before=”1 days”

#恢复暂存区的指定文件到工作区 git checkout filename

#恢复某个提交的指定文件到暂存区和工作区 git checkout commitid filename

#恢复暂存区的所有文件到工作区 git checkout .

#重置暂存区的指定文件,与上一次提交保持一致,但工作区不变 git reset filename

#重置暂存区与工作区,与上一次提交保持一致 git reset --hard

#重置当前分支的指针为指定提交,同时重置暂存区,但工作区不变 git reset commitid

#重置当前分支的HEAD为指定提交,同时重置暂存区和工作区,与指定提交一致 git reset --hard commitid

#重置当前HEAD 为指定提交,但保持暂存区和工作区不变 git reset --keep commitid

#新建一个提交用来撤销指定提交后者的所有变化都将被前者抵消,并且应用到当前分支 git revert commitid

#生成一个可共发布的压缩包 git archive

# 修改提交注释
git commit -v --amend

