## 配置
1. 用`git submodule update --init --recursive`安装子模块
2. `./gradlew run --args="test-resources/src/c-summary/test.js -nodejs"` 运行
   1. `test-resources/src/c-summary/test.js`是测试对象
   2. `-nodejs`开启nodejs模式。用来建模C摘要的Javascript代码需要放在`./c-summaries/`。可用`-nodejs-c-summary <c summary path>`来指定C摘要的目录。