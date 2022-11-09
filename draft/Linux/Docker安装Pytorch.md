```bash
docker pull ubuntu:16.04
docker run -it docker:16.04 
apt install net-tools        # ifconfig 
apt install iputils-ping
exit

docker start -i 2e01fd26138f
ping
exit

docker cp J:\软件\linux\Anaconda3-5.3.1-Linux-x86_64.sh 2e01fd26138f:\home\
docker cp 680b957:/home/datawhale_nlp/ J:/NEW/
//要不安装anaconda报错
apt-get install bzip2  
bash Anaconda3-5.3.1-Linux-x86_64.sh

apt-get install vim
vim ~/.bashrc
 //追加
export PATH=/home/lq/anaconda3/bin:$PATH    
source ~/.bashrc  

conda create -n tomding
source activate tomding

conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
conda config --set show_channel_urls yes

//删除清华源改回默认源
conda config --remove-key channels

pip install tensorflow==2.0.0 -i https://pypi.tuna.tsinghua.edu.cn/simple
conda install tensorflow
conda install pytorch torchvision torchaudio cpuonly -c pytorch

conda install ipython
conda install jupyter

exit
docker commit 2e01fd26138f ubuntu:test

docker run -it ubuntu:test

docker save ubuntu:test > J:my_docker.tar 
docker load < J:my_docker.tar
```