## 工程编译，调试的顺序

所有module都依赖于core模块，先要将core安装到maven的本地仓库，才能编译运行其他模块。方法如下：

1. 首先将oomall下的pom.xml文件中除·<module>core</module>·以外的module注释掉，<br>
2. 在maven的中跑install phase，将core和oomall安装到maven的本地仓库<br>
3. 将oomall下的pom.xml文件中注释掉的部分修改回来<br>
4. 编译打包其他部分<br>
5. 以后修改了core的代码，只需要单独install core到maven本地仓库，无需重复上述步骤<br>