命名实体识别其本质是一个序列标注问题，序列标注就是对给定文本中每一个字符打上标签。标签的格式可以分为BO，BIO和BIEO三种形式。对于数据集较少的情况，建议使用BO，如果有大量数据可以选用BIEO格式。


先列出来BIOES分别代表什么意思：
- B，即Begin，表示开始
- I，即Intermediate，表示中间
- E，即End，表示结尾
- S，即Single，表示单个字符
- O，即Other，表示其他，用于标记无关字符

将“小明在北京大学的燕园看了中国男篮的一场比赛”这句话，进行标注，结果就是：
[B-PER，E-PER，O, B-ORG，I-ORG，I-ORG，E-ORG，O，B-LOC，E-LOC，O，O，B-ORG，I-ORG，I-ORG，E-ORG，O，O，O，O]
