# Repertory
# 基本的依赖库的封装保存 #

## 功能
> 主要用于对andorid技术方面贮备和封装上传jcenter

## modular
### simple
> library依赖库的简单例子

### baseutil
> 开发过程中积累和整理的一些工具类，BaseActivity和BaseFragment默认进度框和滑动退出等功能
**需要依赖 swipebacklayout**

### network
> 对于OkHttp的简单封装，统一调度使用，中间层实现对于子线程和主线程的转换，可以自动将请求转到子线程处理

### refreshlayout
> 对于下拉刷新和上拉加载控件的封装，支持扩展，以抽象方式实现基本功能并提供自定义扩展和两个简单例子

### commonui
> 多个自定义小控件的的集合

### swipebacklayout
> 滑动退出控件的依赖

### facedialog
> android弹框控件


## 引用
##### 在项目工程中build.gradle中的allprojects.repositories标签下添加仓库如 

	allprojects {
    repositories {
        jcenter()
        //BaseUtil、network
        maven { url 'https://dl.bintray.com/gxut/ui' }
        //refreshlayout、commonui、swipebacklayout
        maven { url 'https://dl.bintray.com/gxut/code' }
    	}
	}


## baseutil
####  maven引用方式
	<dependency>
		<groupId>com.gxut.core</groupId>
		<artifactId>baseutil</artifactId>
		<version>v1.1.0</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.core:baseutil:v1.1.0'

####  update
v1.0.6
> 移除等待进度条，将等待进度整合到了facedialog


#### updata v1.0.2
>添加DisplayUtil设置全屏和KeybordUtil键盘控制工具和RegexUtil正则的基本格式和运算工具


----------

### network
####  maven引用方式
	<dependency>
		<groupId>com.gxut.core</groupId>
		<artifactId>network</artifactId>
		<version>v1.0</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.core:network:v1.0'

----------

### refreshlayout
####  maven引用方式
	<dependency>
		<groupId>com.gxut.ui</groupId>
		<artifactId>refreshlayout</artifactId>
		<version>v0.8.1</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.ui:refreshlayout:v0.8.1'

#### updata
v0.8.1 :
> same bug

----------

### commonui
####  maven引用方式
	<dependency>
		<groupId>com.gxut.ui</groupId>
		<artifactId>commonui</artifactId>
		<version>v1.0.0</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.ui:commonui:v1.0.0'

----------

### swipebacklayout
####  maven引用方式
	<dependency>
		<groupId>com.gxut.ui</groupId>
		<artifactId>swipebacklayout</artifactId>
		<version>v1.0</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.ui:swipebacklayout:v1.0'

----------


----------

## facedialog
####  maven引用方式
	<dependency>
		<groupId>com.gxut.ui</groupId>
		<artifactId>facedialog</artifactId>
		<version>v0.9.7</version>
		<type>pom</type>
	</dependency>

####  gradle引用方式
	compile 'com.gxut.ui:facedialog:v0.9.7'

####  update
v0.9.7

	修复列表弹框出现的数据显示错误和返回不正确的问题
v0.7.0

	修复列表弹框出现的数据显示错误和返回不正确的问题

v0.6.0

	更新版本，提供提示框、等待框、列表选择框

----------

 
