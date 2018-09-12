# mqttServer

代码参考了码云项目iot-mqtt-server 做了一些更改，关闭了ssl连接认证，topic过滤等等.适用单机环境.单机稳定性还是挺好

集群，docker建议参考 iot-mqtt-server项目  https://gitee.com/recallcode/iot-mqtt-server/releases

除此之外 moquette和Apollo的broker 都是很好参考例子

#### 项目介绍
轻量级物联网MQTT服务器, 快速部署, 支持集群.

#### 软件架构说明
基于netty+springboot+ignite技术栈实现
1. 使用netty实现通信及协议解析
2. 使用springboot提供依赖注入及属性配置
3. 使用ignite实现存储, 分布式锁, 集群和集群间通信


#### 功能说明
1. 参考MQTT3.1.1规范实现
2. 完整的QoS服务质量等级实现
3. 遗嘱消息, 保留消息及消息分发重试
4. 心跳机制
5. 连接认证(强制开启,可以关闭)
5. SSL方式连接(默认不开启)
6. 主题过滤(标准协议)
7. websocket支持
8. 集群功能
9. tcp 8885  ws 9995 
#### 快速开始
- [下载已打包好的可运行的jar文件]
- 运行jar文件(如果需要修改配置项,application.yml进行修改)
- 连接端口:8885, websocket端口: 9995 websocket path: /mqtt
- 连接使用的用户名:admin
- 连接使用的密码: 43388B17EC0E328A475AF8D44EA866839A0819CE04CA8758BFE20EF89E57179B1564A5362340D604F4813D7EDA6E89163D7043E64D9C5AE95DA0B295A35F2959
