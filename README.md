除特殊说明的代码或者依赖包外，默认使用 Apache License 2.0 协议分发

模块介绍：各个模块基于J2SE(J2EE)测试，不保证在J2ME等嵌入式类型JVM上正常执行,需要JVM8u45+或者兼容版本

crux-core 基本操作包 仅包含对JDK的再封装 提供基本功能，在不需要更多功能的时候使用 同时也是所有其他crux项目的依赖

 - crux-extend 扩展包 包含了基本包和二次封装的第三方包 主要是apache commons 和guava等
 - crux-source 数据源处理工具
 - crux-search 数据查询工具 SQL format and Lucene format。
 - crux-cache 数据缓存工具 包括内嵌的缓存服务和redis memcached等缓存的接二次封装
 - crux-table 数据化表格工具，包括xls xlsx csv tsv以及html

DOCS 编写自定义工具集过程中的笔记和心得 建议文档类型：TXT DOC PDF 以及暂时不使用或不进行编译的配置和数据文件