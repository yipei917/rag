## 工程编译，调试的顺序

所有module都依赖于core模块，先要将core安装到maven的本地仓库，才能编译运行其他模块。方法如下：

1. 首先将oomall下的pom.xml文件中除·<module>core</module>·以外的module注释掉，<br>
2. 在maven的中跑install phase，将core和oomall安装到maven的本地仓库<br>
3. 将oomall下的pom.xml文件中注释掉的部分修改回来<br>
4. 编译打包其他部分<br>
5. 以后修改了core的代码，只需要单独install core到maven本地仓库，无需重复上述步骤<br>

## rag部分

1 安装Milvus向量数据库

```
wget https://github.com/milvus-io/milvus/releases/download/v2.2.2/milvus-standalone-docker-compose.yml -O docker-compose.yml`
sudo docker-compose up -d`
```

下载链接：https://github.com/milvus-io/milvus/releases/download/v2.2.2/milvus-standalone-docker-compose.yml

2 运行MilvusTestsTests，创建表结构

3 运行ChatGPTTest

4 Milvus向量数据库可视化软件：https://github.com/zilliztech/attu/releases/download/v2.2.2/attu-Setup-2.2.2.exe
