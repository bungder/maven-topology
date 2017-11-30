[![Build Status](https://travis-ci.org/bungder/maven-topology.svg?branch=master)](https://travis-ci.org/bungder/maven-topology)
[![codecov](https://codecov.io/gh/bungder/maven-topology/branch/master/graph/badge.svg)](https://codecov.io/gh/bungder/maven-topology)

# Topological Sorting Tools For Maven Nodes

# 1. Introduction

Since microservice is getting popular, the dependencies among the projects is also more complicated. It is inevitable to handle these dependencies if you want to adopt [CI(Continuous Integration)](https://en.wikipedia.org/wiki/Continuous_integration)

This util does only on thing: enter a batch of project names and output the result of the topological sorting.

It supports these features:

1. Dependency information could be pulled from GitLab automatically.
2. You can use regular expression to configure which tags you want to handle, and ordering rule is also configurable. After applying regular expression to sift and using ordering rule to sort, the first tag would be the version we used to analyze dependencies. 
3. The results of the analysis are subdivided into modules.
4. You can configure regular expression to sift maven nodes you need.

## 2. Environment Requirement

1. JDK 8+
2. git command and xargs command are installed

## 3. Usage

### 3.1 Implement ModuleParentNameResolver
Sometimes granularity of packaging is subdivided into module. It means maven node may not match a GitLab repository when analyzing dependency relations. Then rule which defines how to find out a GitLab repository by using a module name is needed.

This rule varies from people to people. So you may need to define your rule when using this util:

Implement interface `pub.gordon.dg.maven.ModuleParentNameResolver`, then use full classname of that implementation class to cover content of file `src/main/resources/META-INF/service/pub.gordon.dg.maven.ModuleParentNameResolver`.

### 3.2 Parameters or Configuration File

See [src/main/resources/usage.txt](src/main/resources/usage.txt)

### 3.3 Packaging

(Package as a runnable jar)

```bash
mvn assembly:assembly -Dmaven.test.skip=true
```

### 3.4 Run

See [src/main/resources/usage.txt](src/main/resources/usage.txt)

## LICENSE

[MIT](LICENSE)

---------------------------


# Maven 节点的拓扑排序工具

## 1. 简介

如今微服务比较流行，项目之间的依赖关系也比较复杂，如果要实施[CI](https://en.wikipedia.org/wiki/Continuous_integration)，处理项目之间的依赖关系是一道避不开的槛。

这个工具的用途只有一个：输入一批项目名字，输出拓扑排序的结果。

虽然用途单一，但是具备以下的特性：

1. 依赖关系的信息可以自动从GitLab上拉取项目取得
2. 可以灵活配置tag的规则，基于目标tag版本的代码来分析依赖关系，避开了多分支的问题
3. 可以自动探测子模块的依赖关系，最后输出的结果细分到模块
4. 可以使用正则表达式过滤出需要排序的maven节点

## 2. 环境要求

1. JDK 8+
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

（打包成可运行的jar包）

```bash
mvn assembly:assembly -Dmaven.test.skip=true
```

### 3.4 运行

参见[src/main/resources/usage.txt](src/main/resources/usage.txt)

## 许可证

[MIT](LICENSE)
