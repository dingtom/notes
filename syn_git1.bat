@echo off   % ¹Ø±Õ»ØÏÔ%
hugo -D
git pull origin master
git add .
git commit -m "auto commit"
git push

