- [ 在jupyter中添加tensorflow核](#head1)
# <span id="head1"> 在jupyter中添加tensorflow核</span>
conda install  -n tensorflow ipykernel
python -m ipykernel install --user --name tensorflow --display-name tensorflow

conda install -n pytorch ipykernel
python -m ipykernel install --user --name pytorch --display-name pytorch

#　tensorflow环境中没有安装 jupyter
conda install ipython
conda install jupyter