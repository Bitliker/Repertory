# 提交步骤
## 一.准备远程空间
### 1.登录https://bintray.com/注册一个帐号，并获取Api Key
### 2.创建一个Maven仓库
### 3.创建一个package
## 二.项目中配置
### 1.在project下build.gradle中添加如下
dependencies{}中加入
·· classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:latest.integration'
·· classpath "com.github.dcendents:android-maven-gradle-plugin:latest.integration"


gradlew install
gradlew bintrayUpload

