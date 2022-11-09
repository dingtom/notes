auto();  //确保打开无障碍
console.show();

setScreenMetrics(1080,1920);
launchApp("抖音极速版");
customSleep(getRandom(4));
while(true){
	swipe(500,1700,500,180,500);
	customSleep(getRandom(4));
	if(getRandom(10)>5){
        comment();
    }
}

function comment(){
	click(1023,1390);
    while(j++<3){
        swipe(500,1650,500,1000,500);
    	customSleep(getRandom(3));
    }
    click(500,1860);
    id("pt").findOne().setText("1111");
    customSleep(1);
    id("q4").findOne().click();
    customSleep(3);
    back();
    customSleep(5);

}

//    体眠函数
function customSleep(time){
	sleep(time*1000);
}
/// 产生[2， max）之间的值
function getRandom(max){
	 var value=random()*max;
	 return value>2?value:2;
}