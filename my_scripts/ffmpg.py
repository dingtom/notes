import os
import re
import argparse
from tqdm import tqdm
parser = argparse.ArgumentParser()
parser.add_argument('--i', type=str, default='', help='input_path')
parser.add_argument('--out', type=str, default='', help='output_path if '' set it with the value of input_path')
parser.add_argument('--old', type=str, default='', help='old_suffix')
parser.add_argument('--n', type=str, default='', help='new_suffix')
opt = parser.parse_known_args()[0]   

input_path, output_path, old_suffix, new_suffix = opt.i, opt.out, opt.old, opt.n
if output_path == '':
    output_path = input_path
# filepath：待处理视频的文件路径
print('要处理的文件夹是:', input_path)
files = [i for i in os.listdir(input_path) if i.endswith('vdat')]
out_files = os.listdir(output_path)

print("待处理的视频文件有{}个:".format(len((files))))
print("\n".join(files))

# 文件名去掉所有非中文字符
for i in files:
    old = os.path.join(input_path, i)
    j = ''.join(re.findall(r'[^\x00-\xff]', i)) + '.' + old_suffix
    new = os.path.join(input_path, j)
    try:
        os.rename(old, new)
    except FileExistsError:
        print('重新生成', i)
        # num = int(input("请输入您的幸运数字："))
        os.remove(new)
        os.rename(old, new)

    print(i, '被替换为', j, '\n')
files = [i for i in os.listdir(input_path) if i.endswith('vdat')]

for i in tqdm(files):
    new_name = i.replace(old_suffix, new_suffix)
    if new_name in out_files:
        print(new_name, "已存在，自动跳过\n")
        continue
    # 转MP3为wav
    # ffmpeg -i input.mp3 -acodec pcm_s16le -ac 2 -ar 44100 output.wav
    # 转m4a为wav
    # ffmpeg -i input.m4a -acodec pcm_s16le -ac 2 -ar 44100 output.wav
    # wav与PCM的相互转换
    # ffmpeg -i input.wav -f s16le -ar 44100 -acodec pcm_s16le output.raw
    # PCM转wav
    # ffmpeg -f s16le -ar 44100 -ac 2 -acodec pcm_s16le -i input.raw output.wav
    cmd = 'ffmpeg -i {} -codec copy {}'.format(os.path.join(input_path, i), os.path.join(output_path, new_name))
    os.system(cmd)	

print("\n任务完成！！！")
