@echo off   % �رջ���%
python toc.py
	    ::echo off��echo״̬����Ϊoff��ʾ�ر�������������(������������������)�Ļ���%
                    ::@�����þ��ǹرս�������һ������Ļ���%
                    ::C:\Users\liang\Desktop> ����echo off ����Ļ���%
title GITһ���ύ
color 3
echo ��ǰĿ¼�ǣ�%cd%
echo;

set /p declation=�����ύ��commit��Ϣ:
echo;


set /p shutdownTime=���ö������ػ�:
echo;


echo ��������֧��ȡԶ������֧��git pull origin master
git pull origin master
echo;

echo ��ʼ��ӱ����git add .
git add .
git commit -m "%declation%"
echo;


 
echo ���������ύ��Զ���Լ���֧��git push
git push
echo;
				
echo 2���Ӻ�ػ�
shutdown -s -t %shutdownTime%


