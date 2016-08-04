# SprintNBA

## NBA头条新闻、视频集锦、比赛直播（目前仅支持文字直播、球队及球员数据统计）、球队战绩排行、球员数据排名、虎扑论坛专区、球队及球员详细资料

## 声明

本项目的API均来自NBA中文官网与虎扑体育，纯练手之作，个人未从中获取任何利益，其所有内容均可在NBA中文官网与虎扑体育获取。数据的获取与共享可能会侵犯到NBA中文官网与虎扑体育的权益，若被告知需停止共享与使用，本人会立即删除整个项目。

## 项目

本项目采用 MaterialDesign + MVP + Retrofit2 开发。MVP分层写的可能不是很好，部分功能未完成，目前正在不断完善中，欢迎star ~ fork。

## 更新
###V1.0
初次版本，含NBA新闻、视频花絮、赛程及文字直播、技术统计、球队及球员数据排行、详细资料，虎扑论坛专区。

## 下载
APK本地下载：[SprintNBA-1.0.apk](https://raw.githubusercontent.com/smuyyh/SprintNBA/master/app/release/SprintNBA-1.0.apk)

百度手机助手：[SprintNBA](http://shouji.baidu.com/software/9748339.html)

91无线：[SprintNBA](http://apk.91.com/Soft/Android/com.yuyh.sprintnba-1.html)

安卓市场：[SprintNBA](http://apk.hiapk.com/appinfo/com.yuyh.sprintnba/1)

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/download.png?raw=true" width="200"/>

## TODO

*   [ ] 代码优化
*   [ ] MVP细节调整
*   [ ] 虎扑论坛部分功能暂未实现
*   [ ] 单支球队赛程
*   [ ] 优化相关视频播放。腾讯视频真实地址提取功能不稳定，导致部分视频不能播放。
*   [ ] 部分BUG修复

## 友情提示
由于反馈信息及崩溃信息上传至Bmob云数据库，并且该项目已在百度应用商店上线，为了避免其他fork&colne等的项目run起来的信息干扰到，所以bmob api key暂不提供，有需要的童鞋可自行到bmob后台申请一个，并根据字段建立反馈及崩溃信息这两个表。然后在Application配置
```java
BmobConfig config = new BmobConfig.Builder(this)
    .setApplicationId("")//设置appkey  bmob申请
    .build();
```

## 应用截图

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_news.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/news_detail.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/news_img.jpeg?raw=true" width="280"/>

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_video.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/video_play.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_schedule.jpeg?raw=true" width="280"/>

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/match_data.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/match_live.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_team_sort.jpeg?raw=true" width="280"/>

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_player_stats.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_hupu.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/thread_list.jpeg?raw=true" width="280"/>

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/thread_detail.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/home_other.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/team_detail.jpeg?raw=true" width="280"/>

<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/player_list.jpeg?raw=true" width="280"/>
<img src="https://github.com/smuyyh/SprintNBA/blob/master/screenshot/player_detail.jpeg?raw=true" width="280"/>