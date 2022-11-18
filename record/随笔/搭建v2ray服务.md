# hax申请

[IPv6 to IPv4 - Hax.co.id](https://hax.co.id/ipv6-to-ipv4/)

有效期7天，提前3天申请延期

# 安装xui

```
bash <(curl -Ls https://raw.githubusercontent.com/vaxilu/x-ui/master/install.sh)
```

设置账号、密码、端口54321

# 端口映射ipv6-ipv4

3066->54321

3067->443

# 登录

 91.134.238.134:3066/xui/

添加入站规则：端口443

# BBR加速

```
wget -N --no-check-certificate "https://raw.githubusercontent.com/chiakge/Linux-NetSpeed/master/tcp.sh" && chmod +x tcp.sh && ./tcp.sh
```

选择2，安装BBRplus,重启vps

重启后再次运行 ./tcp.sh ，选择7，启动BBRplus；

再次运行 ./tcp.sh ，选择10，优化系统并再次重启；

# 添加伪装

1.[谷歌登录，申请域名](https://www.freenom.com/zh/index.html?lang=zh)

2.services->mydomains->manage domain->management tools->nameservers

![quicker_252fb086-fc84-458b-9846-5d5fcf0025a4.png](https://s2.loli.net/2022/03/27/IP3V5EN4CGRvyOW.png)

3.在[CF](https://dash.cloudflare.com/19f3e4f6a808840d62de6918f4c85f22/ilovelearnai.ml/dns)中DNS中，给域名添加AAAA记录为IPv6地址；

4.在SSL/TLS中，选择“完全”，然后申请客户端证书；

![quicker_5514ad83-80ee-451d-bc1b-39a12b83ca26.png](https://s2.loli.net/2022/03/25/EZCe8U3WqH7LYPh.png)

![quicker_e39e0148-15b7-4eae-b1d7-c5ea80e34d06.png](https://s2.loli.net/2022/03/25/ewBv1c6x8jn5msk.png)

![quicker_56808014-c3f5-450c-82d7-b25a2465cd76.png](https://s2.loli.net/2022/03/25/6nBTf5G1pCD4jUm.png)

5.在Xray面板中，修改入站规则如图：用域名和443端口登录

![quicker_54a42c2d-4358-4474-be22-097457f7e025.png](https://s2.loli.net/2022/03/25/k9X2zHP8vSd7pEI.png)







install.sh

```
#!/bin/bash

red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
plain='\033[0m'

cur_dir=$(pwd)

# check root
[[ $EUID -ne 0 ]] && echo -e "${red}错误：${plain} 必须使用root用户运行此脚本！\n" && exit 1

# check os
if [[ -f /etc/redhat-release ]]; then
    release="centos"
elif cat /etc/issue | grep -Eqi "debian"; then
    release="debian"
elif cat /etc/issue | grep -Eqi "ubuntu"; then
    release="ubuntu"
elif cat /etc/issue | grep -Eqi "centos|red hat|redhat"; then
    release="centos"
elif cat /proc/version | grep -Eqi "debian"; then
    release="debian"
elif cat /proc/version | grep -Eqi "ubuntu"; then
    release="ubuntu"
elif cat /proc/version | grep -Eqi "centos|red hat|redhat"; then
    release="centos"
else
    echo -e "${red}未检测到系统版本，请联系脚本作者！${plain}\n" && exit 1
fi

arch=$(arch)

if [[ $arch == "x86_64" || $arch == "x64" || $arch == "amd64" ]]; then
    arch="amd64"
elif [[ $arch == "aarch64" || $arch == "arm64" ]]; then
    arch="arm64"
else
    arch="amd64"
    echo -e "${red}检测架构失败，使用默认架构: ${arch}${plain}"
fi

echo "架构: ${arch}"

if [ $(getconf WORD_BIT) != '32' ] && [ $(getconf LONG_BIT) != '64' ]; then
    echo "本软件不支持 32 位系统(x86)，请使用 64 位系统(x86_64)，如果检测有误，请联系作者"
    exit -1
fi

os_version=""

# os version
if [[ -f /etc/os-release ]]; then
    os_version=$(awk -F'[= ."]' '/VERSION_ID/{print $3}' /etc/os-release)
fi
if [[ -z "$os_version" && -f /etc/lsb-release ]]; then
    os_version=$(awk -F'[= ."]+' '/DISTRIB_RELEASE/{print $2}' /etc/lsb-release)
fi

if [[ x"${release}" == x"centos" ]]; then
    if [[ ${os_version} -le 6 ]]; then
        echo -e "${red}请使用 CentOS 7 或更高版本的系统！${plain}\n" && exit 1
    fi
elif [[ x"${release}" == x"ubuntu" ]]; then
    if [[ ${os_version} -lt 16 ]]; then
        echo -e "${red}请使用 Ubuntu 16 或更高版本的系统！${plain}\n" && exit 1
    fi
elif [[ x"${release}" == x"debian" ]]; then
    if [[ ${os_version} -lt 8 ]]; then
        echo -e "${red}请使用 Debian 8 或更高版本的系统！${plain}\n" && exit 1
    fi
fi

install_base() {
    if [[ x"${release}" == x"centos" ]]; then
        yum install wget curl tar -y
    else
        apt install wget curl tar -y
    fi
}

#This function will be called when user installed x-ui out of sercurity
config_after_install() {
    echo -e "${yellow}出于安全考虑，安装完成后需要强制修改端口与账户密码${plain}"
    read -p "请设置您的账户名:" config_account
    echo -e "${yellow}您的账户名将设定为:${config_account}${plain}"
    read -p "请设置您的账户密码:" config_password
    echo -e "${yellow}您的账户密码将设定为:${config_password}${plain}"
    read -p "请设置面板访问端口:" config_port
    echo -e "${yellow}您的面板访问端口将设定为:${config_port}${plain}"
    read -p "确认设定完成？[y/n]": config_confirm
    if [[ x"${config_confirm}" == x"y" || x"${config_confirm}" == x"Y" ]]; then
        echo -e "${yellow}确认设定,设定中${plain}"
        /usr/local/x-ui/x-ui setting -username ${config_account} -password ${config_password}
        echo -e "${yellow}账户密码设定完成${plain}"
        /usr/local/x-ui/x-ui setting -port ${config_port}
        echo -e "${yellow}面板端口设定完成${plain}"
    else
        echo -e "${red}已取消,所有设置项均为默认设置,请及时修改${plain}"
    fi
}

install_x-ui() {
    systemctl stop x-ui
    cd /usr/local/

    if [ $# == 0 ]; then
        last_version=$(curl -Ls "https://api.github.com/repos/vaxilu/x-ui/releases/latest" | grep '"tag_name":' | sed -E 's/.*"([^"]+)".*/\1/')
        if [[ ! -n "$last_version" ]]; then
            echo -e "${red}检测 x-ui 版本失败，可能是超出 Github API 限制，请稍后再试，或手动指定 x-ui 版本安装${plain}"
            exit 1
        fi
        echo -e "检测到 x-ui 最新版本：${last_version}，开始安装"
        wget -N --no-check-certificate -O /usr/local/x-ui-linux-${arch}.tar.gz https://github.com/vaxilu/x-ui/releases/download/${last_version}/x-ui-linux-${arch}.tar.gz
        if [[ $? -ne 0 ]]; then
            echo -e "${red}下载 x-ui 失败，请确保你的服务器能够下载 Github 的文件${plain}"
            exit 1
        fi
    else
        last_version=$1
        url="https://github.com/vaxilu/x-ui/releases/download/${last_version}/x-ui-linux-${arch}.tar.gz"
        echo -e "开始安装 x-ui v$1"
        wget -N --no-check-certificate -O /usr/local/x-ui-linux-${arch}.tar.gz ${url}
        if [[ $? -ne 0 ]]; then
            echo -e "${red}下载 x-ui v$1 失败，请确保此版本存在${plain}"
            exit 1
        fi
    fi

    if [[ -e /usr/local/x-ui/ ]]; then
        rm /usr/local/x-ui/ -rf
    fi

    tar zxvf x-ui-linux-${arch}.tar.gz
    rm x-ui-linux-${arch}.tar.gz -f
    cd x-ui
    chmod +x x-ui bin/xray-linux-${arch}
    cp -f x-ui.service /etc/systemd/system/
    wget --no-check-certificate -O /usr/bin/x-ui https://raw.githubusercontent.com/vaxilu/x-ui/main/x-ui.sh
    chmod +x /usr/local/x-ui/x-ui.sh
    chmod +x /usr/bin/x-ui
    config_after_install
    #echo -e "如果是全新安装，默认网页端口为 ${green}54321${plain}，用户名和密码默认都是 ${green}admin${plain}"
    #echo -e "请自行确保此端口没有被其他程序占用，${yellow}并且确保 54321 端口已放行${plain}"
    #    echo -e "若想将 54321 修改为其它端口，输入 x-ui 命令进行修改，同样也要确保你修改的端口也是放行的"
    #echo -e ""
    #echo -e "如果是更新面板，则按你之前的方式访问面板"
    #echo -e ""
    systemctl daemon-reload
    systemctl enable x-ui
    systemctl start x-ui
    echo -e "${green}x-ui v${last_version}${plain} 安装完成，面板已启动，"
    echo -e ""
    echo -e "x-ui 管理脚本使用方法: "
    echo -e "----------------------------------------------"
    echo -e "x-ui              - 显示管理菜单 (功能更多)"
    echo -e "x-ui start        - 启动 x-ui 面板"
    echo -e "x-ui stop         - 停止 x-ui 面板"
    echo -e "x-ui restart      - 重启 x-ui 面板"
    echo -e "x-ui status       - 查看 x-ui 状态"
    echo -e "x-ui enable       - 设置 x-ui 开机自启"
    echo -e "x-ui disable      - 取消 x-ui 开机自启"
    echo -e "x-ui log          - 查看 x-ui 日志"
    echo -e "x-ui v2-ui        - 迁移本机器的 v2-ui 账号数据至 x-ui"
    echo -e "x-ui update       - 更新 x-ui 面板"
    echo -e "x-ui install      - 安装 x-ui 面板"
    echo -e "x-ui uninstall    - 卸载 x-ui 面板"
    echo -e "----------------------------------------------"
}

echo -e "${green}开始安装${plain}"
install_base
install_x-ui $1
```

tcp.sh

```
#!/usr/bin/env bash
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

#=================================================
#	System Required: CentOS 6/7,Debian 8/9,Ubuntu 16+
#	Description: BBR+BBR魔改版+BBRplus+Lotserver
#	Version: 1.4.0
#	Author: 千影,cx9208
#	Blog: https://www.939.me/
#   推荐使用5.5以上内核直接开启的bbr速度最佳
#=================================================

sh_ver="1.4.0"
github="raw.githubusercontent.com/chiakge/Linux-NetSpeed/master"

Green_font_prefix="\033[32m" && Red_font_prefix="\033[31m" && Green_background_prefix="\033[42;37m" && Red_background_prefix="\033[41;37m" && Font_color_suffix="\033[0m"
Info="${Green_font_prefix}[信息]${Font_color_suffix}"
Error="${Red_font_prefix}[错误]${Font_color_suffix}"
Tip="${Green_font_prefix}[注意]${Font_color_suffix}"

#安装BBR内核
installbbr(){
	kernel_version="4.11.8"
	if [[ "${release}" == "centos" ]]; then
		rpm --import http://${github}/bbr/${release}/RPM-GPG-KEY-elrepo.org
		yum install -y http://${github}/bbr/${release}/${version}/${bit}/kernel-ml-${kernel_version}.rpm
		yum remove -y kernel-headers
		yum install -y http://${github}/bbr/${release}/${version}/${bit}/kernel-ml-headers-${kernel_version}.rpm
		yum install -y http://${github}/bbr/${release}/${version}/${bit}/kernel-ml-devel-${kernel_version}.rpm
	elif [[ "${release}" == "debian" || "${release}" == "ubuntu" ]]; then
		mkdir bbr && cd bbr
		wget http://security.debian.org/debian-security/pool/updates/main/o/openssl/libssl1.1_1.1.1d-0+deb10u2_amd64.deb
		wget -N --no-check-certificate http://${github}/bbr/debian-ubuntu/linux-headers-${kernel_version}-all.deb
		wget -N --no-check-certificate http://${github}/bbr/debian-ubuntu/${bit}/linux-headers-${kernel_version}.deb
		wget -N --no-check-certificate http://${github}/bbr/debian-ubuntu/${bit}/linux-image-${kernel_version}.deb
	
		dpkg -i libssl1.1_1.1.1d-0+deb10u2_amd64.deb
		dpkg -i linux-headers-${kernel_version}-all.deb
		dpkg -i linux-headers-${kernel_version}.deb
		dpkg -i linux-image-${kernel_version}.deb
		cd .. && rm -rf bbr
	fi
	detele_kernel
	BBR_grub
	echo -e "${Tip} 重启VPS后，请重新运行脚本开启${Red_font_prefix}BBR/BBR魔改版${Font_color_suffix}"
	stty erase '^H' && read -p "需要重启VPS后，才能开启BBR/BBR魔改版，是否现在重启 ? [Y/n] :" yn
	[ -z "${yn}" ] && yn="y"
	if [[ $yn == [Yy] ]]; then
		echo -e "${Info} VPS 重启中..."
		reboot
	fi
}

#安装BBRplus内核
installbbrplus(){
	kernel_version="4.14.129-bbrplus"
	if [[ "${release}" == "centos" ]]; then
		wget -N --no-check-certificate https://${github}/bbrplus/${release}/${version}/kernel-${kernel_version}.rpm
		yum install -y kernel-${kernel_version}.rpm
		rm -f kernel-${kernel_version}.rpm
		kernel_version="4.14.129_bbrplus" #fix a bug
	elif [[ "${release}" == "debian" || "${release}" == "ubuntu" ]]; then
		mkdir bbrplus && cd bbrplus
		wget -N --no-check-certificate http://${github}/bbrplus/debian-ubuntu/${bit}/linux-headers-${kernel_version}.deb
		wget -N --no-check-certificate http://${github}/bbrplus/debian-ubuntu/${bit}/linux-image-${kernel_version}.deb
		dpkg -i linux-headers-${kernel_version}.deb
		dpkg -i linux-image-${kernel_version}.deb
		cd .. && rm -rf bbrplus
	fi
	detele_kernel
	BBR_grub
	echo -e "${Tip} 重启VPS后，请重新运行脚本开启${Red_font_prefix}BBRplus${Font_color_suffix}"
	stty erase '^H' && read -p "需要重启VPS后，才能开启BBRplus，是否现在重启 ? [Y/n] :" yn
	[ -z "${yn}" ] && yn="y"
	if [[ $yn == [Yy] ]]; then
		echo -e "${Info} VPS 重启中..."
		reboot
	fi
}

#安装Lotserver内核
installlot(){
	if [[ "${release}" == "centos" ]]; then
		rpm --import http://${github}/lotserver/${release}/RPM-GPG-KEY-elrepo.org
		yum remove -y kernel-firmware
		yum install -y http://${github}/lotserver/${release}/${version}/${bit}/kernel-firmware-${kernel_version}.rpm
		yum install -y http://${github}/lotserver/${release}/${version}/${bit}/kernel-${kernel_version}.rpm
		yum remove -y kernel-headers
		yum install -y http://${github}/lotserver/${release}/${version}/${bit}/kernel-headers-${kernel_version}.rpm
		yum install -y http://${github}/lotserver/${release}/${version}/${bit}/kernel-devel-${kernel_version}.rpm
	elif [[ "${release}" == "ubuntu" ]]; then
		bash <(wget --no-check-certificate -qO- "http://${github}/Debian_Kernel.sh")
	elif [[ "${release}" == "debian" ]]; then
		bash <(wget --no-check-certificate -qO- "http://${github}/Debian_Kernel.sh")
	fi
	detele_kernel
	BBR_grub
	echo -e "${Tip} 重启VPS后，请重新运行脚本开启${Red_font_prefix}Lotserver${Font_color_suffix}"
	stty erase '^H' && read -p "需要重启VPS后，才能开启Lotserver，是否现在重启 ? [Y/n] :" yn
	[ -z "${yn}" ] && yn="y"
	if [[ $yn == [Yy] ]]; then
		echo -e "${Info} VPS 重启中..."
		reboot
	fi
}

#启用BBR
startbbr(){
	remove_all
	if [[ `echo ${kernel_version} | awk -F'.' '{print $1}'` -ge "5" ]]; then
		echo "net.core.default_qdisc=cake" >> /etc/sysctl.conf
		echo "net.ipv4.tcp_congestion_control=bbr" >> /etc/sysctl.conf
	else
		echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
		echo "net.ipv4.tcp_congestion_control=bbr" >> /etc/sysctl.conf
	fi
	sysctl -p
	echo -e "${Info}BBR启动成功！"
}

#启用BBRplus
startbbrplus(){
	remove_all
	echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
	echo "net.ipv4.tcp_congestion_control=bbrplus" >> /etc/sysctl.conf
	sysctl -p
	echo -e "${Info}BBRplus启动成功！"
}

#编译并启用BBR魔改
startbbrmod(){
	remove_all
	if [[ "${release}" == "centos" ]]; then
		yum install -y make gcc
		mkdir bbrmod && cd bbrmod
		wget -N --no-check-certificate http://${github}/bbr/tcp_tsunami.c
		echo "obj-m:=tcp_tsunami.o" > Makefile
		make -C /lib/modules/$(uname -r)/build M=`pwd` modules CC=/usr/bin/gcc
		chmod +x ./tcp_tsunami.ko
		cp -rf ./tcp_tsunami.ko /lib/modules/$(uname -r)/kernel/net/ipv4
		insmod tcp_tsunami.ko
		depmod -a
	else
		apt-get update
		if [[ "${release}" == "ubuntu" && "${version}" = "14" ]]; then
			apt-get -y install build-essential
			apt-get -y install software-properties-common
			add-apt-repository ppa:ubuntu-toolchain-r/test -y
			apt-get update
		fi
		apt-get -y install make gcc
		mkdir bbrmod && cd bbrmod
		wget -N --no-check-certificate http://${github}/bbr/tcp_tsunami.c
		echo "obj-m:=tcp_tsunami.o" > Makefile
		ln -s /usr/bin/gcc /usr/bin/gcc-4.9
		make -C /lib/modules/$(uname -r)/build M=`pwd` modules CC=/usr/bin/gcc-4.9
		install tcp_tsunami.ko /lib/modules/$(uname -r)/kernel
		cp -rf ./tcp_tsunami.ko /lib/modules/$(uname -r)/kernel/net/ipv4
		depmod -a
	fi
	

	echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
	echo "net.ipv4.tcp_congestion_control=tsunami" >> /etc/sysctl.conf
	sysctl -p
    cd .. && rm -rf bbrmod
	echo -e "${Info}魔改版BBR启动成功！"
}

#编译并启用BBR魔改
startbbrmod_nanqinlang(){
	remove_all
	if [[ "${release}" == "centos" ]]; then
		yum install -y make gcc
		mkdir bbrmod && cd bbrmod
		wget -N --no-check-certificate https://raw.githubusercontent.com/chiakge/Linux-NetSpeed/master/bbr/centos/tcp_nanqinlang.c
		echo "obj-m := tcp_nanqinlang.o" > Makefile
		make -C /lib/modules/$(uname -r)/build M=`pwd` modules CC=/usr/bin/gcc
		chmod +x ./tcp_nanqinlang.ko
		cp -rf ./tcp_nanqinlang.ko /lib/modules/$(uname -r)/kernel/net/ipv4
		insmod tcp_nanqinlang.ko
		depmod -a
	else
		apt-get update
		if [[ "${release}" == "ubuntu" && "${version}" = "14" ]]; then
			apt-get -y install build-essential
			apt-get -y install software-properties-common
			add-apt-repository ppa:ubuntu-toolchain-r/test -y
			apt-get update
		fi
		apt-get -y install make gcc-4.9
		mkdir bbrmod && cd bbrmod
		wget -N --no-check-certificate https://raw.githubusercontent.com/chiakge/Linux-NetSpeed/master/bbr/tcp_nanqinlang.c
		echo "obj-m := tcp_nanqinlang.o" > Makefile
		make -C /lib/modules/$(uname -r)/build M=`pwd` modules CC=/usr/bin/gcc-4.9
		install tcp_nanqinlang.ko /lib/modules/$(uname -r)/kernel
		cp -rf ./tcp_nanqinlang.ko /lib/modules/$(uname -r)/kernel/net/ipv4
		depmod -a
	fi
	

	echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
	echo "net.ipv4.tcp_congestion_control=nanqinlang" >> /etc/sysctl.conf
	sysctl -p
	echo -e "${Info}魔改版BBR启动成功！"
}

#启用Lotserver
startlotserver(){
	remove_all
	if [[ "${release}" == "centos" ]]; then
		yum install ethtool
	else
		apt-get update
		apt-get install ethtool
	fi
	bash <(wget --no-check-certificate -qO- https://raw.githubusercontent.com/chiakge/lotServer/master/Install.sh) install
	sed -i '/advinacc/d' /appex/etc/config
	sed -i '/maxmode/d' /appex/etc/config
	echo -e "advinacc=\"1\"
maxmode=\"1\"">>/appex/etc/config
	/appex/bin/lotServer.sh restart
	start_menu
}

#卸载全部加速
remove_all(){
	rm -rf bbrmod
	sed -i '/net.core.default_qdisc/d' /etc/sysctl.conf
  sed -i '/net.ipv4.tcp_congestion_control/d' /etc/sysctl.conf
  sed -i '/fs.file-max/d' /etc/sysctl.conf
	sed -i '/net.core.rmem_default/d' /etc/sysctl.conf
	sed -i '/net.core.wmem_default/d' /etc/sysctl.conf
	sed -i '/net.core.somaxconn/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_syncookies/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_tw_reuse/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_tw_recycle/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_fin_timeout/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_keepalive_time/d' /etc/sysctl.conf
	sed -i '/net.ipv4.ip_local_port_range/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_max_tw_buckets/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_rmem/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_wmem/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_mtu_probing/d' /etc/sysctl.conf
	sed -i '/net.ipv4.ip_forward/d' /etc/sysctl.conf
	sed -i '/fs.inotify.max_user_instances/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_syncookies/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_fin_timeout/d' /etc/sysctl.conf
	sed -i '/net.ipv4.route.gc_timeout/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_synack_retries/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_syn_retries/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_timestamps/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_max_orphans/d' /etc/sysctl.conf
	sed -i '/net.core.rmem_max/d' /etc/sysctl.conf
	sed -i '/net.core.wmem_max/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_max_syn_backlog/d' /etc/sysctl.conf
	sed -i '/net.core.netdev_max_backlog/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_slow_start_after_idle/d' /etc/sysctl.conf
	sed -i '/net.ipv4.ip_forward/d' /etc/sysctl.conf
	if [[ -e /appex/bin/lotServer.sh ]]; then
		bash <(wget --no-check-certificate -qO- https://github.com/MoeClub/lotServer/raw/master/Install.sh) uninstall
	fi
	clear
	echo -e "${Info}:清除加速完成。"
	sleep 1s
}

#优化系统配置
optimizing_system(){
	sed -i '/fs.file-max/d' /etc/sysctl.conf
	sed -i '/fs.inotify.max_user_instances/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_tw_reuse/d' /etc/sysctl.conf
	sed -i '/net.ipv4.ip_local_port_range/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_rmem/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_wmem/d' /etc/sysctl.conf
	sed -i '/net.core.somaxconn/d' /etc/sysctl.conf
	sed -i '/net.core.rmem_max/d' /etc/sysctl.conf
	sed -i '/net.core.wmem_max/d' /etc/sysctl.conf
	sed -i '/net.core.wmem_default/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_max_tw_buckets/d' /etc/sysctl.conf
	sed -i '/net.ipv4.tcp_max_syn_backlog/d' /etc/sysctl.conf
	sed -i '/net.core.netdev_max_backlog/d' /etc/sysctl.conf
 	sed -i '/net.ipv4.tcp_slow_start_after_idle/d' /etc/sysctl.conf
	sed -i '/net.ipv4.ip_forward/d' /etc/sysctl.conf
	echo "fs.file-max = 1000000
fs.inotify.max_user_instances = 8192
net.ipv4.tcp_tw_reuse = 1
net.ipv4.ip_local_port_range = 1024 65535
net.ipv4.tcp_rmem = 16384 262144 8388608
net.ipv4.tcp_wmem = 32768 524288 16777216
net.core.somaxconn = 8192
net.core.rmem_max = 16777216
net.core.wmem_max = 16777216
net.core.wmem_default = 2097152
net.ipv4.tcp_max_tw_buckets = 5000
net.ipv4.tcp_max_syn_backlog = 10240
net.core.netdev_max_backlog = 10240
net.ipv4.tcp_slow_start_after_idle = 0
# forward ipv4
net.ipv4.ip_forward = 1">>/etc/sysctl.conf
	sysctl -p
	echo "*               soft    nofile           1000000
*               hard    nofile          1000000">/etc/security/limits.conf
	echo "ulimit -SHn 1000000">>/etc/profile
	read -p "需要重启VPS后，才能生效系统优化配置，是否现在重启 ? [Y/n] :" yn
	[ -z "${yn}" ] && yn="y"
	if [[ $yn == [Yy] ]]; then
		echo -e "${Info} VPS 重启中..."
		reboot
	fi
}
#更新脚本
Update_Shell(){
	echo -e "当前版本为 [ ${sh_ver} ]，开始检测最新版本..."
	sh_new_ver=$(wget --no-check-certificate -qO- "http://${github}/tcp.sh"|grep 'sh_ver="'|awk -F "=" '{print $NF}'|sed 's/\"//g'|head -1)
	[[ -z ${sh_new_ver} ]] && echo -e "${Error} 检测最新版本失败 !" && start_menu
	if [[ ${sh_new_ver} != ${sh_ver} ]]; then
		echo -e "发现新版本[ ${sh_new_ver} ]，是否更新？[Y/n]"
		read -p "(默认: y):" yn
		[[ -z "${yn}" ]] && yn="y"
		if [[ ${yn} == [Yy] ]]; then
			wget -N --no-check-certificate http://${github}/tcp.sh && chmod +x tcp.sh
			echo -e "脚本已更新为最新版本[ ${sh_new_ver} ] !"
		else
			echo && echo "	已取消..." && echo
		fi
	else
		echo -e "当前已是最新版本[ ${sh_new_ver} ] !"
		sleep 5s
	fi
}

#开始菜单
start_menu(){
clear
echo && echo -e " TCP加速 一键安装管理脚本 ${Red_font_prefix}[v${sh_ver}]${Font_color_suffix}
  -- 就是爱生活 | 94ish.me --
  
 ${Green_font_prefix}0.${Font_color_suffix} 升级脚本
————————————内核管理————————————
 ${Green_font_prefix}1.${Font_color_suffix} 安装 BBR/BBR魔改版内核
 ${Green_font_prefix}2.${Font_color_suffix} 安装 BBRplus版内核 
 ${Green_font_prefix}3.${Font_color_suffix} 安装 Lotserver(锐速)内核
————————————加速管理————————————
 ${Green_font_prefix}4.${Font_color_suffix} 使用BBR加速
 ${Green_font_prefix}5.${Font_color_suffix} 使用BBR魔改版加速
 ${Green_font_prefix}6.${Font_color_suffix} 使用暴力BBR魔改版加速(不支持部分系统)
 ${Green_font_prefix}7.${Font_color_suffix} 使用BBRplus版加速
 ${Green_font_prefix}8.${Font_color_suffix} 使用Lotserver(锐速)加速
————————————杂项管理————————————
 ${Green_font_prefix}9.${Font_color_suffix} 卸载全部加速
 ${Green_font_prefix}10.${Font_color_suffix} 系统配置优化
 ${Green_font_prefix}11.${Font_color_suffix} 退出脚本
————————————————————————————————" && echo

	check_status
	if [[ ${kernel_status} == "noinstall" ]]; then
		echo -e " 当前状态: ${Green_font_prefix}未安装${Font_color_suffix} 加速内核 ${Red_font_prefix}请先安装内核${Font_color_suffix}"
	else
		echo -e " 当前状态: ${Green_font_prefix}已安装${Font_color_suffix} ${_font_prefix}${kernel_status}${Font_color_suffix} 加速内核 , ${Green_font_prefix}${run_status}${Font_color_suffix}"
		
	fi
echo
read -p " 请输入数字 [0-11]:" num
case "$num" in
	0)
	Update_Shell
	;;
	1)
	check_sys_bbr
	;;
	2)
	check_sys_bbrplus
	;;
	3)
	check_sys_Lotsever
	;;
	4)
	startbbr
	;;
	5)
	startbbrmod
	;;
	6)
	startbbrmod_nanqinlang
	;;
	7)
	startbbrplus
	;;
	8)
	startlotserver
	;;
	9)
	remove_all
	;;
	10)
	optimizing_system
	;;
	11)
	exit 1
	;;
	*)
	clear
	echo -e "${Error}:请输入正确数字 [0-11]"
	sleep 5s
	start_menu
	;;
esac
}
#############内核管理组件#############

#删除多余内核
detele_kernel(){
	if [[ "${release}" == "centos" ]]; then
		rpm_total=`rpm -qa | grep kernel | grep -v "${kernel_version}" | grep -v "noarch" | wc -l`
		if [ "${rpm_total}" > "1" ]; then
			echo -e "检测到 ${rpm_total} 个其余内核，开始卸载..."
			for((integer = 1; integer <= ${rpm_total}; integer++)); do
				rpm_del=`rpm -qa | grep kernel | grep -v "${kernel_version}" | grep -v "noarch" | head -${integer}`
				echo -e "开始卸载 ${rpm_del} 内核..."
				rpm --nodeps -e ${rpm_del}
				echo -e "卸载 ${rpm_del} 内核卸载完成，继续..."
			done
			echo --nodeps -e "内核卸载完毕，继续..."
		else
			echo -e " 检测到 内核 数量不正确，请检查 !" && exit 1
		fi
	elif [[ "${release}" == "debian" || "${release}" == "ubuntu" ]]; then
		deb_total=`dpkg -l | grep linux-image | awk '{print $2}' | grep -v "${kernel_version}" | wc -l`
		if [ "${deb_total}" > "1" ]; then
			echo -e "检测到 ${deb_total} 个其余内核，开始卸载..."
			for((integer = 1; integer <= ${deb_total}; integer++)); do
				deb_del=`dpkg -l|grep linux-image | awk '{print $2}' | grep -v "${kernel_version}" | head -${integer}`
				echo -e "开始卸载 ${deb_del} 内核..."
				apt-get purge -y ${deb_del}
				echo -e "卸载 ${deb_del} 内核卸载完成，继续..."
			done
			echo -e "内核卸载完毕，继续..."
		else
			echo -e " 检测到 内核 数量不正确，请检查 !" && exit 1
		fi
	fi
}

#更新引导
BBR_grub(){
	if [[ "${release}" == "centos" ]]; then
        if [[ ${version} = "6" ]]; then
            if [ ! -f "/boot/grub/grub.conf" ]; then
                echo -e "${Error} /boot/grub/grub.conf 找不到，请检查."
                exit 1
            fi
            sed -i 's/^default=.*/default=0/g' /boot/grub/grub.conf
        elif [[ ${version} = "7" ]]; then
            if [ ! -f "/boot/grub2/grub.cfg" ]; then
                echo -e "${Error} /boot/grub2/grub.cfg 找不到，请检查."
                exit 1
            fi
            grub2-set-default 0
        fi
    elif [[ "${release}" == "debian" || "${release}" == "ubuntu" ]]; then
        /usr/sbin/update-grub
    fi
}

#############内核管理组件#############



#############系统检测组件#############

#检查系统
check_sys(){
	if [[ -f /etc/redhat-release ]]; then
		release="centos"
	elif cat /etc/issue | grep -q -E -i "debian"; then
		release="debian"
	elif cat /etc/issue | grep -q -E -i "ubuntu"; then
		release="ubuntu"
	elif cat /etc/issue | grep -q -E -i "centos|red hat|redhat"; then
		release="centos"
	elif cat /proc/version | grep -q -E -i "debian"; then
		release="debian"
	elif cat /proc/version | grep -q -E -i "ubuntu"; then
		release="ubuntu"
	elif cat /proc/version | grep -q -E -i "centos|red hat|redhat"; then
		release="centos"
    fi
}

#检查Linux版本
check_version(){
	if [[ -s /etc/redhat-release ]]; then
		version=`grep -oE  "[0-9.]+" /etc/redhat-release | cut -d . -f 1`
	else
		version=`grep -oE  "[0-9.]+" /etc/issue | cut -d . -f 1`
	fi
	bit=`uname -m`
	if [[ ${bit} = "x86_64" ]]; then
		bit="x64"
	else
		bit="x32"
	fi
}

#检查安装bbr的系统要求
check_sys_bbr(){
	check_version
	if [[ "${release}" == "centos" ]]; then
		if [[ ${version} -ge "6" ]]; then
			installbbr
		else
			echo -e "${Error} BBR内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "debian" ]]; then
		if [[ ${version} -ge "8" ]]; then
			installbbr
		else
			echo -e "${Error} BBR内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "ubuntu" ]]; then
		if [[ ${version} -ge "14" ]]; then
			installbbr
		else
			echo -e "${Error} BBR内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	else
		echo -e "${Error} BBR内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
	fi
}

check_sys_bbrplus(){
	check_version
	if [[ "${release}" == "centos" ]]; then
		if [[ ${version} -ge "6" ]]; then
			installbbrplus
		else
			echo -e "${Error} BBRplus内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "debian" ]]; then
		if [[ ${version} -ge "8" ]]; then
			installbbrplus
		else
			echo -e "${Error} BBRplus内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "ubuntu" ]]; then
		if [[ ${version} -ge "14" ]]; then
			installbbrplus
		else
			echo -e "${Error} BBRplus内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	else
		echo -e "${Error} BBRplus内核不支持当前系统 ${release} ${version} ${bit} !" && exit 1
	fi
}


#检查安装Lotsever的系统要求
check_sys_Lotsever(){
	check_version
	if [[ "${release}" == "centos" ]]; then
		if [[ ${version} == "6" ]]; then
			kernel_version="2.6.32-504"
			installlot
		elif [[ ${version} == "7" ]]; then
			yum -y install net-tools
			kernel_version="3.10.0-327"
			installlot
		else
			echo -e "${Error} Lotsever不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "debian" ]]; then
		if [[ ${version} = "7" || ${version} = "8" ]]; then
			if [[ ${bit} == "x64" ]]; then
				kernel_version="3.16.0-4"
				installlot
			elif [[ ${bit} == "x32" ]]; then
				kernel_version="3.2.0-4"
				installlot
			fi
		elif [[ ${version} = "9" ]]; then
			if [[ ${bit} == "x64" ]]; then
				kernel_version="4.9.0-4"
				installlot
			fi
		else
			echo -e "${Error} Lotsever不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	elif [[ "${release}" == "ubuntu" ]]; then
		if [[ ${version} -ge "12" ]]; then
			if [[ ${bit} == "x64" ]]; then
				kernel_version="4.4.0-47"
				installlot
			elif [[ ${bit} == "x32" ]]; then
				kernel_version="3.13.0-29"
				installlot
			fi
		else
			echo -e "${Error} Lotsever不支持当前系统 ${release} ${version} ${bit} !" && exit 1
		fi
	else
		echo -e "${Error} Lotsever不支持当前系统 ${release} ${version} ${bit} !" && exit 1
	fi
}

check_status(){
	kernel_version=`uname -r | awk -F "-" '{print $1}'`
	kernel_version_full=`uname -r`
	if [[ ${kernel_version_full} = "4.14.129-bbrplus" ]]; then
		kernel_status="BBRplus"
	elif [[ ${kernel_version} = "3.10.0" || ${kernel_version} = "3.16.0" || ${kernel_version} = "3.2.0" || ${kernel_version} = "4.4.0" || ${kernel_version} = "3.13.0"  || ${kernel_version} = "2.6.32" || ${kernel_version} = "4.9.0" ]]; then
		kernel_status="Lotserver"
	elif [[ `echo ${kernel_version} | awk -F'.' '{print $1}'` == "4" ]] && [[ `echo ${kernel_version} | awk -F'.' '{print $2}'` -ge 9 ]] || [[ `echo ${kernel_version} | awk -F'.' '{print $1}'` -ge "5" ]]; then
		kernel_status="BBR"
	else 
		kernel_status="noinstall"
	fi

	if [[ ${kernel_status} == "Lotserver" ]]; then
		if [[ -e /appex/bin/lotServer.sh ]]; then
			run_status=`bash /appex/bin/lotServer.sh status | grep "LotServer" | awk  '{print $3}'`
			if [[ ${run_status} = "running!" ]]; then
				run_status="启动成功"
			else 
				run_status="启动失败"
			fi
		else 
			run_status="未安装加速模块"
		fi
	elif [[ ${kernel_status} == "BBR" ]]; then
		run_status=`grep "net.ipv4.tcp_congestion_control" /etc/sysctl.conf | awk -F "=" '{print $2}'`
		if [[ ${run_status} == "bbr" ]]; then
			run_status=`lsmod | grep "bbr" | awk '{print $1}'`
			if [[ ${run_status} == "tcp_bbr" ]]; then
				run_status="BBR启动成功"
			else 
				run_status="BBR启动失败"
			fi
		elif [[ ${run_status} == "tsunami" ]]; then
			run_status=`lsmod | grep "tsunami" | awk '{print $1}'`
			if [[ ${run_status} == "tcp_tsunami" ]]; then
				run_status="BBR魔改版启动成功"
			else 
				run_status="BBR魔改版启动失败"
			fi
		elif [[ ${run_status} == "nanqinlang" ]]; then
			run_status=`lsmod | grep "nanqinlang" | awk '{print $1}'`
			if [[ ${run_status} == "tcp_nanqinlang" ]]; then
				run_status="暴力BBR魔改版启动成功"
			else 
				run_status="暴力BBR魔改版启动失败"
			fi
		else 
			run_status="未安装加速模块"
		fi
	elif [[ ${kernel_status} == "BBRplus" ]]; then
		run_status=`grep "net.ipv4.tcp_congestion_control" /etc/sysctl.conf | awk -F "=" '{print $2}'`
		if [[ ${run_status} == "bbrplus" ]]; then
			run_status=`lsmod | grep "bbrplus" | awk '{print $1}'`
			if [[ ${run_status} == "tcp_bbrplus" ]]; then
				run_status="BBRplus启动成功"
			else 
				run_status="BBRplus启动失败"
			fi
		else 
			run_status="未安装加速模块"
		fi
	fi
}

#############系统检测组件#############
check_sys
check_version
[[ ${release} != "debian" ]] && [[ ${release} != "ubuntu" ]] && [[ ${release} != "centos" ]] && echo -e "${Error} 本脚本不支持当前系统 ${release} !" && exit 1
start_menu


```

