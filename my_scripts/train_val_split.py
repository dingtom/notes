import random
import os

path = 'notices\images'
files = [os.path.join(path, i) for i in os.listdir(path) if i.endswith('png')]
random.shuffle(files)
t = int(len(files)*0.8)
trains = files[:t]
vals = files[t:]
with open('train.txt', 'w') as f:
    f.write('\n'.join(trains))
with open('val.txt', 'w') as f:
    f.write('\n'.join(vals))