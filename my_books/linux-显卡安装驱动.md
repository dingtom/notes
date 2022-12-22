---
    # 文章标题
    title: "ml-gpu版pytorch安装"
    # 分类
    categories: 
        - ml
    # 发表日期
    date: 2022-12-01T19:59:47+08:00

---



驱动安装



```bash
#https://www.nvidia.cn/geforce/drivers/
#查看显卡型号
lspci | grep -i nvidia

#安装驱动
sudo bash NVIDIA-Linux-x86_64-455.23.04.run

#查看显卡信息
nvidia-smi
```

## 卸载显卡驱动重新安装



```shell
#命令行界面
Ctrl+Alt+F1
sudo apt-get --purge remove nvidia*
sudo apt autoremove
# To remove CUDA Toolkit:
sudo apt-get --purge remove "*cublas*" "cuda*"
# To remove NVIDIA Drivers:
sudo apt-get --purge remove "*nvidia*"
```



# 
