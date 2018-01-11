# idea提交代码到bintray步骤
## 一.准备远程空间
### 1.登录[bintray](https://bintray.com/)注册一个帐号，并获取Api Key
### 2.创建一个组织
### 3.创建一个仓库Repository，类型选择Maven，开源协议尽量选择Apache-2.0

## 二.项目中配置
### 1.在project下build.gradle中添加如下：
    dependencies{}中加入
     classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:latest.integration' （1.5）
     classpath "com.github.dcendents:android-maven-gradle-plugin:latest.integration"（1.7.3）

### 2.在本地文件local.properties中添加用户名和appkey
	bintray.apikey=***********************
	bintray.user=name

### 3.在需要上传的module中的build.gradle最后添加如下代码：

	//TODO 每次发布修改部分
	def repertoyAllName = 'com.gxut.core'   ////最终引用形式，如compile 'com.gxut.ui.baseurl:1.0.0'，中的com.gxut.ui部分
	def versionCode = '1.0'                 // 发布版本号
	def versionName = 'v1.0'                // 发布版本名字
	def repertoyName = 'core'               //仓库名称
	def projectName = 'NetWork'             //项目名称
	def organizationName = 'gxut'           //组织名称
	def desction = 'okhttp'                 //备注


    apply plugin: 'com.jfrog.bintray'
    apply plugin: 'com.github.dcendents.android-maven'

	def siteUrl = 'https://github.com/Bitliker/Repertory'   // 项目的主页
	def gitUrl = 'https://github.com/Bitliker/Repertory.git'   // Git仓库的url
	Properties properties = new Properties()
	properties.load(project.rootProject.file('local.properties').newDataInputStream())
	version = versionName
	group = repertoyAllName

	bintray {
    user = properties.getProperty("bintray.user")
    key = properties.getProperty("bintray.apikey")
    pkg {
        repo = repertoyName//自己创建的仓库名字
        name = projectName//上传到JCenter的名字,最终引用的名字
        websiteUrl = siteUrl
        vcsUrl = gitUrl
        licenses = ['Apache-2.0']//不能随便写，只能是仓库创建时选择的license type
        userOrg = organizationName //自己创建的organization名称
        publish = true // 是否是公开项目，公开别人可以引用

        version {
            name = versionCode
            desc = desction//描述，自己定义
            released = new Date()
            vcsTag = versionName
            attributes = ['gradle-plugin': 'com.use.less:com.use.less.gradle:gradle-useless-plugin']
        }
    }
    configurations = ['archives']
	}

	install {
    repositories.mavenInstaller {
        // This generates POM.xml with proper parameters
        pom {
            project {
                packaging 'jar'
                // Add your description here
                name 'Leon Android'
                description 'leon open library.'
                url siteUrl
                // Set your license
                licenses {
                    license {
                        name 'Apache-2.0' //和之前自己定义的协议一致
                        url 'https://raw.githubusercontent.com/minggo620/Pluto-Android/master/LICENSE'
                    	}
                	}
                developers {
                    developer {
                        id 'Bitliker'        //填写bintray或者github的用户名
                        name 'gongpengming'         //姓名
                        email 'gongpengming@aliyun.com'//邮箱
                    		}
                		}
                scm {
                    connection gitUrl
                    developerConnection gitUrl
                    url siteUrl
                	}
            	}
        	}
    	}
	}

	task sourcesJar(type: Jar) {
    from android.sourceSets.main.java.srcDirs
    classifier = 'sources'
		}
	task javadoc(type: Javadoc) {
    failOnError false //必须添加以免出错
    source = android.sourceSets.main.java.srcDirs
    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
	}
	task javadocJar(type: Jar, dependsOn: javadoc) {
    classifier = 'javadoc'
    from javadoc.destinationDir
	}
	artifacts {
    archives javadocJar
    archives sourcesJar
	}
	javadoc {
    options {
        //如果你的项目里面有中文注释的话，必须将格式设置为UTF-8，不然会出现乱码
        encoding "UTF-8"
        charSet 'UTF-8'
        author true
        version true
        links "http://docs.oracle.com/javase/8/docs/api"
   		}
	}

### 4.在idea 的Terminal里面窗口中输入
	gradlew install
	gradlew bintrayUpload

### 5.等待上传结束就可以了

### 6.常见错误处理
#### 6.1.'gradlew' 不是xxx的命令
> 在我的电脑=》属性=》高级系统设置=》添加GRADLE_HOME，指定本地的Gradle的版本，需和项目指定的版本一致，方法和设置JAVA_HOME方式一样





