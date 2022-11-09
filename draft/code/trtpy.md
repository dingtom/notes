pip install trtpy -U

trtpy set-key xxx

trtpy  get-env

trtpy  get-cpp-pkg opencv4.2

````
>trtpy --help

usage: __main__.py [-h]
                   {get-env,info,mnist-test,exec,list-templ,search-templ,get-templ,list-series,search-series,get-series,change-proj,series-detial,prep-vars,inv-vars,compile,tryload,env-source,local-cpp-pkg,get-cpp-pkg}
                   ...

positional arguments:
  {get-env,info,mnist-test,exec,list-templ,search-templ,get-templ,list-series,search-series,get-series,change-proj,series-detial,prep-vars,inv-vars,compile,tryload,env-source,local-cpp-pkg,get-cpp-pkg}
    get-env             download environment
    info                display support list
    mnist-test          test tensorrt with mnist
    exec                same to ./trtexec
    list-templ          display all code template
    search-templ        search all code template
    get-templ           fetch code template
    list-series         display all series template
    search-series       search all series template
    get-series          fetch series template
    change-proj         change series proj
    series-detial       change series proj
    prep-vars           replace local file variables
    inv-vars            replace local file value invert to variables
    compile             compile use trtpro
    tryload             try to load trtmodel file
    env-source          get trtpy environment
    local-cpp-pkg       display all installed local cpp package
    get-cpp-pkg         download cpp package

optional arguments:
  -h, --help            show this help message and exit
````



```
trtpy list-templ
trtpy get-templ cpp-trt-mnist
make run

为tensorRT Pro.项目做自动配置
下载ensorRT_Pro,并cd进目录，然后拉模版trtpy get-templ trtpro-makefile .
注意到当前目录，后面的点别少了。自动替换
然后make yolo -j64

学习系列
trtpy list-series


编译tensorRT的模型，拿mnist做例子
trtpy mnist-test
trtpy compile mnist --onnx-from-hub
加载trtmodel并打印信息
trtpy tryload mnist.trtmodel

trtpy的python接口
import trtpy.init default as trtpy
yolo = trtpy.Yolo("yolov5s.trtmodel")
其接口同tensorRT_Pro项目的python接口一致。不过仅保留了
和通用模型编译推理，移除了其他模型支持
```





卸载方式
1.去掉快捷指令vim ~/.bashrc中删除trtpy
2.pip uninstall trtpy-dev
3.删除缓存文件rm-f
/datav/software/anaconda3/lib/python3.9/site-packages/trtpy(注意这个路径是我电脑的)
4.删除缓存目录rm -rf ~/.cache/trtpy
5.删除模型缓存rm -rf ~/.trtpy