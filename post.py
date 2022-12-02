import sys
import os
import argparse
import time



headline_dic={'#':0,'##':1,'###':2,'####':3,'#####':4,'######':5}
suojin={0:-1,1:-1,2:-1,3:-1,4:-1,5:-1,6:-1}
def writefile(fp, str=None):
    with open(fp,'w') as f:
        f.write(str)

def detectHeadLines(f):
    '''detact headline and return inserted string.
    params:
        f: Markdown file
    '''
    f.seek(0)

    insert_str=""
    org_str=""

    last_status = -1
    c_status=-1

    headline_counter=0
    iscode=False
    for line in f.readlines():
        if(line[:3]=='```'):
            iscode= not iscode
            
        # fix code indent bug and fix other indentation bug. 2020/7/3
        if not iscode:
            temp_line=line.strip(' ')
        try: # 起始是代码
            ls=temp_line.split(' ')
        except: 
            return '去draft看'

        if len(ls)>1 and ls[0] in headline_dic.keys() and not iscode:
            headline_counter+=1
            c_status=headline_dic[ls[0]]
            # find first rank headline
            if last_status==-1 or c_status==0 or suojin[c_status]==0:
                # init suojin
                for key in suojin.keys():
                    suojin[key]=-1
                suojin[c_status]=0
            elif c_status>last_status:
                suojin[c_status]=suojin[last_status]+1
            
            # update headline text
            headtext=' '.join(ls[1:-1])
            if ls[-1][-1]=='\n':
                headtext+=(' '+ls[-1][:-1])
            else:
                headtext+=(' '+ls[-1])
            headid = '{}{}'.format('head',headline_counter)
            headline=ls[0]+' <span id=\"{}\"'.format(headid)+'>'+ headtext+'</span>'+'\n'
            org_str+=headline

            jump_str='- [{}](#{}{})'.format(headtext,'head',headline_counter)
            insert_str+=('\t'*suojin[c_status]+jump_str+'\n')
                    
            last_status=c_status
        else:
            org_str+=line
            
            
    return insert_str+org_str
 
    
if __name__=='__main__':

    # post_path = sys.argv[1]
    # if not os.path.exists(os.path.join('./blog', post_path)):
    #     os.makedirs(os.path.join('./blog', post_path))
        
    # filename = sys.argv[2]

    # #print(filename)
    # d_file = os.path.join('./draft/'+post_path, filename)
    # with open(d_file,'r',encoding='utf-8') as f:
    #     insert_str=detectHeadLines(f)
    # b_file = './blog/{}.md'.format(os.path.join(post_path, filename[:filename.find('.')]))
    # with open(b_file,'w',encoding='utf-8') as f:
    #     f.write(insert_str)
    # os.system('git status')
    # os.system('git add {}'.format(d_file))
    # os.system('git add {}'.format(b_file))
    
    # comment = sys.argv[3]
    # if comment == 0:
    #     os.system('git commit -m %date:~0,8%_%time:~0,8%__{}'.format(comment))
    # else:
    #     os.system('git commit -m %date:~0,8%_%time:~0,8%__modify__{}'.format(filename))
    # os.system('git push')
    # os.system('*'*20)
    # os.system('git status')
    parser = argparse.ArgumentParser()
    parser.add_argument('--draft_path', type=str, default='my_record/Linux', help='draft_path')
    opt = parser.parse_known_args()[0]

    draft_path = opt.draft_path
    post_path = 'content/post'

    insert_str = """---
            # 文章标题
            title: "title_name"
            # 分类
            categories: 
                - categories_name
            # 发表日期
            date: post_date+08:00

            # 标签
            #tags:
            # 文章内容摘要
            #description: "{{ .Name }}" 
            # 最后修改日期
            #lastmod: {{ .Date }}
            # 文章内容关键字
            #keywords: "{{replace .Name "-" ","}}"
            # 原文作者
            #author:
            # 原文链接
            #link:
            # 图片链接，用在open graph和twitter卡片上
            #imgs:
            # 在首页展开内容
            #expand: true
            # 外部链接地址，访问时直接跳转
            #extlink:
            # 在当前页面关闭评论功能
            #comment:
            # enable: false
            # 关闭当前页面目录功能
            # 注意：正常情况下文章中有H2-H4标题会自动生成目录，无需额外配置
            #toc: false
            # 绝对访问路径
            #url: "{{ lower .Name }}.html"
            # 开启文章置顶，数字越小越靠前
            #weight: 1
            #开启数学公式渲染，可选值： mathjax, katex
            #math: mathjax
            # 开启各种图渲染，如流程图、时序图、类图等
            #mermaid: true
    --- \n\n"""
    
    f_list = []
    for root, subdir, files in os.walk(draft_path):
        for f_name in files:
            f_list.append(os.path.join(root, f_name))
    count = 0
    for file in f_list:
        _, filename = os.path.split(file)
        category = filename.split('-')[0]
        name = os.path.splitext(filename)[0]

        post_date = time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime())

        insert_str.replace('title_name', name).replace('categories_name', category).replace('post_date', post_date)

        if not os.path.exists(post_path):
            os.makedirs(post_path)
        with open(os.path.join(draft_path, filename), 'r', encoding='utf-8') as f:
            content = f.read()
        with open(os.path.join(post_path, filename),'w',encoding='utf-8') as f:
            f.write(insert_str + content)
            count = count + 1
    print('post {} file, please commit changes'.format(count))
    os.system('hugo -D')
