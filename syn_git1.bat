@echo off   % �رջ���%
git pull origin master
hugo -D
git add .
git commit -m "auto commit"
git push

