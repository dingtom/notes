auto();  //确保打开无障碍
console.show();

// setScreenMetrics(1080,2400);
console.log('打开饿了么');
launchApp("饿了么");
customSleep(getRandom(2,4));

//———————————————————————————————————弹出红包
if(textContains('去使用').exists()){
    back();
    customSleep(getRandom(2,4));
}
//_____________________________________看视频
console.log('开始看视频啦');
// text('真香').findOne().click();
click(418,2197)

customSleep(getRandom(2,4));
console.log('点进全屏播放视频');
click(500,1500)
for(i=0;i<35;i++){ 
    customSleep(getRandom(10,30));
    log('已经看了', i, '个')
    swipe(300,1000,800,1000,100)
    if(textContains('明日').exists()){
        log('出现了明天再来，退出看视频了')
        back();
        break;
        customSleep(getRandom(2,4));
    }
    if(i==35){
        back();
        break;
    }
}

//---------------------------------------浏览任务
console.log('点击我的');
// text("我的").findOne().click();
click(950,2197)
customSleep(getRandom(2,4));
console.log("点击赚吃货豆")
click(300,1000)
//className('android.view.ViewGroup').depth(15).drawingOrder(1).findOne().click();
customSleep(getRandom(7, 8));
//console.log(text("赚吃货豆").exists());
//click("跳过广告")
swipe(300,1000,800,1000,100)
var kw = ["去浏览","去变美","去搜索","去观看","去完成","去逛逛"];
while(true){
    var flag=false  //
    log('开始找任务')
    for(i=0;i<kw.length;i++){ //！！！！！！！！！！！！！！数组从0开始
        log(i, kw[i])
        if(textContains(kw[i]).exists()){
            clickByTextName(kw[i]);
            customSleep(getRandom(2,4));
            flag=true //判断有没有执行，没有执行过说明任务完成
        }
    }
    log("列表跑完了flag:"+flag);

    if(!flag){
        log("任务完成返回桌面");
        customSleep(getRandom(2,4));
        home();
        break;
    }
}
function clickByTextName(textName){
    console.log("进入"+textName+"界面");
    textContains(textName).findOne().click();
    customSleep(getRandom(19,21));
    ifSign();
    backByFinish();
    // 关掉签到打开的弹窗
    var tc=['离开','收下','再来', '晚点']
    for(i=0;i<tc.length;i++){
        customSleep(getRandom(2,4));
        if(textContains(tc[i]).exists()){
            log('出现啦'+tc[i])
            textContains(tc[i]).findOne().click();
            customSleep(getRandom(1,2));
        }
    }
    customSleep(getRandom(2,4));
}
//    体眠函数
function customSleep(time){
	sleep(time*1000);
}
/// 产生[min， max）之间的值
function getRandom(min, max){
	 var value=(random()+1)*min;
	 return value>max?max:value;
}
// 判断是否需要返回
function backByFinish(){
    if(textContains("任务完成").exists() || textContains("全部完成啦").exists() ||
    descContains("任务完成").exists() || textContains("任务已完成").exists() ||
    descContains("继续退出").exists() || descContains("全部完成啦").exists() || 
    textContains("当面分享").exists() || textContains("点击返回").exists() || 
    textContains("请返回重试").exists() || textContains("继续逛逛吧").exists() ||
    textContains("了解Ta").exists()){
        log("     出现任务结束符");
        customSleep(getRandom(1,2));
    }else{
        customSleep(getRandom(5,8));
        log("    时间到");
    }
    log("返回上层");
    back();
    customSleep(getRandom(2, 3));
}
// 判断是否有签到
function ifSign(){
    var sg=['签到', '领取']
    var flag=false
    for(i=0;i<sg.length;i++){
        if(textContains(sg[i]).exists()){
            textContains(sg[i]).findOne().click();
            flag=true
        }
        if(descContains(sg[i]).exists()){
            descContains(sg[i]).findOne().click();
            flag=true
        }
    }
    if(flag){
        log("    去签到");
        customSleep(getRandom(3,4)); //弹窗出现的较晚
    }

}

