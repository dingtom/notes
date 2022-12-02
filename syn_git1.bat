@echo off   % ¹Ø±Õ»ØÏÔ%
git pull origin master
hugo -D
git add .
git commit -m "auto commit"
git push

