sudo update-grub



sudo chmod 777 /boot/grub/grub.cfg
sudo grub-mkconfig > /boot/grub/grub.cfg

sudo add-apt-repository ppa:danielrichter2007/grub-customizer
sudo apt-get update
sudo apt-get install grub-customizer
sudo update-grub
