// app名
const appName = '饿了么';
// 任务tab
const taskList = ['真香', '我的'];
// 需要点击的关键词
const watchList = ['签到']
const browseList = ['浏览', '去变美', '去搜索', '去观看', '去完成', '去逛逛']
const activateList = ['完成']
// 任务结束标志词
const watchEnd = '明日'
const browseEnd = '签到'

init();
function init() {
    start();
    // 进入活动中心
    // if (keyClick('真香') == 1) {
    //     customSleep(getRandom(2, 4));
    //     watchVideos();
    // }
    if (keyClick('我的') == 1) {
        customSleep(getRandom(2, 4));
        browseActivates();
    }

}

// 真香，看视频
function watchVideos() {
    if (classClick('android.widget.ImageView', 20, 2, 1, '真香签到') == 1) {
        return 1;
    }
    // while (1) {
    //     swipe(500,2000,500,500,1000)
    //     customSleep(getRandom(6,10));
    //     watchList.some(item => {
    //         log(item)
    //         if (keyClick(item) == 1) {
    //             customSleep(getRandom(2,4));
    //             return true;
    //         }
    //     })
    //     swipe(500,2000,500,500,1000)
    //     customSleep(getRandom(6,10));
    //     if (keyClick(watchEnd) == 1) break;
    // }
}

// 我的，去浏览
function browseActivates() {
    if (classClick('android.view.ViewGroup', 22, 1, 0, '赚吃货豆') == 1) {
        customSleep(getRandom(3, 4));

        while (1) {
            let flag = browseList.some(item => {
                if (keyClick(item) == 1) {
                    // 等一会看看没有浏览计时直接返回
                    customSleep(getRandom(4, 5));  
                    if (currentPackage() != 'me.ele') {
                        log("返回ele");
                        launchApp('饿了么');
                    }
                    if (!textContains('浏览').exists()) {
                        log("返回上层");
                        back();
                    } else {  // 有计时等待返回出现
                        while (!textContains('返回').exists()) {
                            customSleep(getRandom(2, 4));
                        } 
                        log("返回上层");
                        back();
                    }
                    return true;
                }
            })
            
            if (!flag) {
                if (textContains('领任务').exists()) {  // 循环一遍真的没任务了退出
                    log('没任务了');
                    break;
                } else {  // 当前不在任务页
                    back();
                    continue;
                }
            }
        }
    }

}

// -------------------------------------------------
// 启动饿了么
function start() {
    auto.waitFor()
    launchApp(appName);
    console.show();
    customSleep(getRandom(3, 4));
}
// 点击某词
function keyClick(key) {
    if (textContains(key).exists() || descContains(key).exists()) {
        const element = textContains(key).findOne() || descContains(key).findOne();
        if (element.clickable()) {
            element.click();
            log('clickable' + key);
            return 1;
        } else {
            let b = element.bounds()
            if (b) {
                click(b.centerX(), b.centerY());
                log('bounds' + key);
                return 1;
            } else {
                log(key + '无坐标');
                return -1;
            }
        }
    } else {
        return -1;
    }
}
// 无id、desc、text的点击
function classClick(cN, d, dO, iIP, desc) {
    let b = className(cN).depth(d).drawingOrder(dO).indexInParent(iIP).findOne().bounds();
    if (b) {
        click(b.centerX(), b.centerY());
        log('点击' + desc);
        return 1;
    } else {
        log(desc + '无坐标');
        return -1;
    }
}
//    体眠函数
function customSleep(time) {
    sleep(time * 1000);
}
// 产生[min， max）之间的值
function getRandom(min, max) {
    var value = (random() + 1) * min;
    return value > max ? max : value;
}


