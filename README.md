[![Build Status](https://travis-ci.org/bungder/maven-topology.svg?branch=master)](https://travis-ci.org/bungder/maven-topology)
[![codecov](https://codecov.io/gh/bungder/maven-topology/branch/master/graph/badge.svg)](https://codecov.io/gh/bungder/maven-topology)
# Maven 节点的拓扑排序工具

## 1. 简介

如今微服务比较流行，项目之间的依赖关系也比较复杂，如果要实施CI，处理项目之间的依赖关系是一道避不开的槛。

这个工具的用途只有一个：输入一批项目名字，输出拓扑排序的结果。

虽然用途单一，但是具备以下的特性：

1. 依赖关系的信息可以自动从GitLab上拉取项目取得
2. 可以灵活配置tag的规则，基于目标tag版本的代码来分析依赖关系，避开了多分支的问题
3. 可以自动探测子模块的依赖关系，最后输出的结果细分到模块
4. 可以使用正则表达式过滤出需要排序的maven节点

## 2. 环境要求

1. JRE 8+
2. 系统安装了git命令以及xargs命令

## 3. 用法

### 3.1 自定义ModuleParentNameResolver

有时候maven打包的粒度是module，那么在依赖分析的过程中，maven节点不能直接匹配到一个GitLab的仓库，这时候需要使用module的名字去找到所在的GitLab仓库。

这种规则因人而异，因此需要具体的使用者去定义。

实现`pub.gordon.dg.maven.ModuleParentNameResolver`接口，然后将实现类的路径覆盖到
`src/main/resources/META-INF/service/pub.gordon.dg.maven.ModuleParentNameResolver`文件中即可。

### 3.2 参数 or 配置文件

参见[src/main/resources/usage.txt](src/main/resources/usage.txt)

### 3.3 打包

```bash
mvn assembly:assembly -Dmaven.test.skip=true
```

### 3.4 运行

参见[src/main/resources/usage.txt](src/main/resources/usage.txt)

## LICENSE

[MIT](LICENSE)
