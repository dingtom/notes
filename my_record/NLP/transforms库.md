```
# 有10个Transformer结构和30个预训练权重模型。
#           模型|                分词|                    预训练权重
MODELS = [(BertModel,       BertTokenizer,       'bert-base-uncased'),
          (OpenAIGPTModel,  OpenAIGPTTokenizer,  'openai-gpt'),
          (GPT2Model,       GPT2Tokenizer,       'gpt2'),
          (CTRLModel,       CTRLTokenizer,       'ctrl'),
          (TransfoXLModel,  TransfoXLTokenizer,  'transfo-xl-wt103'),
          (XLNetModel,      XLNetTokenizer,      'xlnet-base-cased'),
          (XLMModel,        XLMTokenizer,        'xlm-mlm-enfr-1024'),
          (DistilBertModel, DistilBertTokenizer, 'distilbert-base-cased'),
          (RobertaModel,    RobertaTokenizer,    'roberta-base'),
          (XLMRobertaModel, XLMRobertaTokenizer, 'xlm-roberta-base'),]
# 要使用TensorFlow 2.0版本的模型，只需在类名前面加上“TF”，例如。“TFRobertaModel”是TF2.0版本的PyTorch模型“RobertaModel”
```

```python
# 导入必要的库
import torch
from transformers import GPT2Tokenizer, GPT2LMHeadModel

# 加载预训练模型tokenizer (vocabulary)
tokenizer = GPT2Tokenizer.from_pretrained('gpt2')

text = "i want to eat a "
# 对文本输入进行编码
indexed_tokens = tokenizer.encode(text)  
# 在PyTorch张量中转换indexed_tokens
tokens_tensor = torch.tensor([indexed_tokens])
# 如果你有GPU，把所有东西都放在cuda上
tokens_tensor = tokens_tensor.to('cuda')
# 加载预训练模型 (weights)
model = GPT2LMHeadModel.from_pretrained('gpt2')
#将模型设置为evaluation模式，关闭DropOut模块
model.eval()
model.to('cuda')
# 预测所有的tokens
with torch.no_grad():
    outputs = model(tokens_tensor)
    # print(len(outputs), outputs[0].shape)  2 torch.Size([1, 6, 50257])
    predictions = outputs[0]
    
# 得到预测的单词
predicted_index = torch.argmax(predictions[0, -1, :]).item()
print(predicted_index)
# print(torch.argmax(predictions[0, -1, :]))  # tensor(3711, device='cuda:0')
predicted_text = tokenizer.decode(indexed_tokens + [predicted_index])
# 打印预测单词
print(predicted_text)
```

```python
import torch
from transformers import *

# transformer有一个统一的API


# 每个架构都提供了几个类，用于对下游任务进行调优，例如。
BERT_MODEL_CLASSES = [BertModel, BertForPreTraining, BertForMaskedLM, BertForNextSentencePrediction,
                      BertForSequenceClassification, BertForTokenClassification, BertForQuestionAnswering]

# 体系结构的所有类都可以从该体系结构的预训练权重开始
#注意，为微调添加的额外权重只在需要接受下游任务的训练时初始化

pretrained_weights = 'bert-base-uncased'
tokenizer = BertTokenizer.from_pretrained(pretrained_weights)
for model_class in BERT_MODEL_CLASSES:
    # 载入模型/分词器
    # 模型可以在每一层返回隐藏状态和带有注意力机制的权值
    model = model_class.from_pretrained(pretrained_weights,
                                        output_hidden_states=True,
                                        output_attentions=True)
    input_indexs = torch.tensor([tokenizer.encode("Let's see all hidden-states and attentions on this text")])
    all_hidden_states, all_attentions = model(input_ids)[-2:]

    #模型与Torchscript兼容
    model = model_class.from_pretrained(pretrained_weights, torchscript=True)
    traced_model = torch.jit.trace(model, (input_ids,))

    # 模型和分词的简单序列化
#     model.save_pretrained('./directory/to/save/')  # 保存
#     model = model_class.from_pretrained('./directory/to/save/')  # 重载
#     tokenizer.save_pretrained('./directory/to/save/')  # 保存
#     tokenizer = BertTokenizer.from_pretrained('./directory/to/save/')  # 重载
```











