/**
* 哈里哈里视频采集
* 采集对应视频的iframe页面
*/
var page = require('webpage').create();
var fs = require('fs');
//要打印的url地址
var address = 'https://www.halihali.me/v/fengyuzhou/0-1.html';
//存储文件路径和名称
var outputPng = './video/img.png';
var outputTxt = './video/video.html'

var webPage = require('webpage');
var page = webPage.create();

//设置长宽
page.viewportSize = {width: 1280 * 2, height: 1280 * 2};

page.open(address, function (status) {
    if (status !== 'success') {
        console.log('Unable to load the address!');
        phantom.exit();
    } else {
        page.switchToFrame('tv');
        console.log(page.frameContent);
        fs.write(outputTxt, page.frameContent, 'w');
        page.render(outputPng);
        phantom.exit();
    }
});

