@echo off   % �رջ���%
git pull origin master
rmdir /Q /S public
hugo -D
git add .
git commit -m "auto commit"
git push

