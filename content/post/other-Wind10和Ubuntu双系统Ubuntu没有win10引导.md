---
        # 文章标题
        title: "other-Wind10和Ubuntu双系统Ubuntu没有win10引导"
        # 分类
        categories: 
            - other
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

sudo update-grub



sudo chmod 777 /boot/grub/grub.cfg
sudo grub-mkconfig > /boot/grub/grub.cfg

sudo add-apt-repository ppa:danielrichter2007/grub-customizer
sudo apt-get update
sudo apt-get install grub-customizer
sudo update-grub
