@echo off   % �رջ���%
git pull origin master
rm public -r
hugo -D
git add .
git commit -m "auto commit"
git push

