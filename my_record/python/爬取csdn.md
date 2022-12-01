```
#爬取csdn文章的JSON数据，并保存至mongodb数据库
# 数据库分为关系型数据库和非关系型数据库，关系型数据库需要通过建立表与表之间的关系来进行数据的存储和查询，比如一对一、一对多、多对多关系，表与表之间的关系比较紧密。而非关系型数据库中，表与表之间是不存在关联的，每一个表都是独立存储数据的。
# mongodb属于非关系型数据库，可以在表里直接存储字典，所以在保存数据的时候比较方便。

# 接口请求的规律：每请求一次接口，该接口都会返回一个值shown_offset，这个shown_offset的值会作为下一个接口请求的参数，所以这个参数的值可以从上一个数据接着返回新的数据。

# 在第一次请求https://www.csdn.net/nav/newarticles地址的时候，返回的网页源代码中默认展示了11条数据，同时又进行了一次接口的请求，接口又加载了10条数据。在返回的网页源代码中的ul标签中有一个属性shown-offset，那么这个属性的值就是作为了第一次请求接口的参数使用了。
import json, requests, re, pymongo
class CSDNSpider(object):
    def __init__(self):
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.12 Safari/537.36'
        }
        # 建立p.
        # ymongo的链接
        self.client = pymongo.MongoClient('localhost')
        self.db = self.client['csdn']

    def get_article_html(self):
        """
        请求CSDN最新文章页面
        :return:
        """
        article_url = 'https://www.csdn.net/nav/newarticles'
        # 模拟浏览器首次加载的整个过程。
        # 请求article_url -> 提取ul标签的shown-offset属性 -> 同时利用正则将Html中的数据提取出来 -> 利用shown-offset发起第一次接口请求。
        response = requests.get(article_url, headers=self.headers)
        shown_offset = re.findall(re.compile(r'<ul.*?id="feedlist_id" shown-offset="(.*?)">', re.S), response.text)[0]
        print(shown_offset)

        datas = re.findall(re.compile(r'<li class="clearfix" data-type="blog".*?<div class="title">.*?<a.*?>(.*?)</a>.*?<div class="summary oneline">(.*?)</div>', re.S), response.text)
        for title, info in datas:
            new_title = title.replace('\n', '').replace(' ', '')
            new_info = info.replace('\n', '').replace(' ', '')

            data_dict = {'title': new_title, 'info': new_info}
            # 数据库名称：'csdn'(需要提前创建)，表的名称：'article'(自动创建)
            self.db['article'].insert_one(data_dict)

        return shown_offset

    def get_json_data(self, shownoffset):
        api_url = 'https://www.csdn.net/api/articles?type=more&category=newarticles&shown_offset={}'.format(shownoffset)
        json_dict = json.loads(requests.get(api_url, headers=self.headers).text)
        articles = json_dict.get('articles', None)

        for item in articles:
            title = item['title']
            summary = item['summary']

            dic = {'title': title, 'info': summary}
            self.db['article'].insert_one(dic)

        shown_offset = json_dict.get('shown_offset', None)
        # 将shown_offset作为下一次接口请求的参数
        # 递归调用函数本身。
        if articles and len(articles) > 0 and shown_offset:
            self.get_json_data(shown_offset)

if __name__ == '__main__':
    csdn = CSDNSpider()
    # 这个shownoffset就是从源码中提取的值，用作第一次接口请求。
    shownoffset = csdn.get_article_html()
    csdn.get_json_data(shownoffset)
```
