# DIM
![image](https://user-images.githubusercontent.com/34570474/172418057-5335cd65-1cf4-4513-a0bb-5a98f53f94d9.png)

DIM-基于Electron，Netty和SpringBoot的分布式IM


# DIM-基于Electron，Netty和SpringBoot的分布式IM
> 先说下背景，因为作者最近在学Netty，想着把学到的运用一下，所以就有了这个项目

## 项目介绍
DIM的D指的是**distributed**，界面用的是Electron+Vue2搭建(为什么不是Vue3，因为主要方向还是后端，没有去研究Vue3，后续打算学个React，用React再写一个)，后端使用的是SpringBoot，Netty作为通信框架,Kafka作为不同服务器之间转发消息的桥梁，Zookeeper作为服务注册和管理的中心，Redis存储连接信息等。
DIM实现了消息确认机制，自动重连和心跳检测等功能

## 项目界面
界面的布局全部使用的**Flex**布局，组件库用的[Ant Design Vue](https://www.antdv.com/docs/vue/introduce-cn/)

### 登录界面

![image.png](https://img-blog.csdnimg.cn/img_convert/d02d3dee78bf866491ef37559d050c45.png)

### 主界面

![image.png](https://img-blog.csdnimg.cn/img_convert/76ffcd0dd303598c426deecf72d94ae2.png)

![image.png](https://img-blog.csdnimg.cn/img_convert/c723dcaae1ce3fec51431e6393edb76a.png)

## 项目结构,架构和流程图

项目主要分为两部分，dim-gateway和dim-core，dim-gateway的作用主要是处理各种请求，比如登录，获取离线消息，重连分配服务器等。dim-core的作用是处理消息，转发消息，心跳检测等。
### 项目架构

![dim-framework.jpeg](https://img-blog.csdnimg.cn/img_convert/8a9b911d6da4904712d19b7accff6dda.png)

### 登录流程图

![dim-login.png](https://img-blog.csdnimg.cn/img_convert/b248ae85431eaaf07578d81d01015df0.png)

### 服务注册流程图

![dim-core.png](https://img-blog.csdnimg.cn/img_convert/a95a9d3d2c80f44326dc331c4134d3fe.png)

### 发送消息流程图

![dim-send.png](https://img-blog.csdnimg.cn/img_convert/34a7e04b61f2cafbe10f15e5e3947c30.png)

## 主要功能介绍
### 服务注册
![image.png](https://img-blog.csdnimg.cn/img_convert/0815127512d561f31b9fde526c1ef08f.png)

每启动一个节点的时候，都会获取节点的IP地址，然后读取配置文件中的NettyPort(用来TCP连接)和HttpPort，跟着这三个属性生成一个标识，然后启动一个线程，用这个标识来注册到Zookeeper和创建对应的Topic，topic的副本数量跟你的Kafka集群有关，我的Kafka集群有三个broker，所以副本数量为3

### 登录

用户登录的时候，通过路由算法，获取集群中的一个服务器，目前路由算法支持轮询和随机。然后将服务器信息返回给前端

![image.png](https://img-blog.csdnimg.cn/img_convert/d078e49f4f450c1ede7420b70df3015d.png)


![image.png](https://img-blog.csdnimg.cn/img_convert/ae2b5c6543358c72e779ad89cdf6a588.png)

前端收到消息后，根据服务器信息与其建立连接,同时开启心跳检测等

![image.png](https://img-blog.csdnimg.cn/img_convert/704d82f72427b15c5670ca2d97747757.png)

### 发送消息,消息确认机制
本项目采用服务器转发的方式来实现发送消息，发送消息时服务器先在本地缓存查找接收者是否也连接的该台服务器，如果在就直接发送

![image.png](https://img-blog.csdnimg.cn/img_convert/0e581ff3bdad383eef5da5888d08883e.png)

否则就通过Kafka将消息转发到接收者所在的服务器，转发之前判断一下接收者是否在线，如果不在线就将消息持久化

![image.png](https://img-blog.csdnimg.cn/img_convert/8e00af9e6cc8855682dea62094c12089.png)


#### 消息确认机制
消息确认机制，是为了确保消息已经把接收者接收，避免消息丢失。该项目是简单的实现了该机制，后续有很多可以完善的地方，在发送消息后，会有一个定时任务来维护发送的消息是否有收到ACK，即消息确认，如果超过4秒还未收到，就会启动消息重传任务，重新发送消息，同样如果4秒后还未收到，就正式列为消息丢失

![image.png](https://img-blog.csdnimg.cn/img_convert/45daea12d566bc816ad08cff35f35fd7.png)

那如何确认对方收到消息呢？当收到其他人发来的消息时，会发送个ACK回给发消息的人

![image.png](https://img-blog.csdnimg.cn/img_convert/ebe8974f6a868c8f9db3a261dd088e14.png)

等待消息确认

![image.png](https://img-blog.csdnimg.cn/img_convert/7a96e3a617501b8bd4a894c14d1d4ae1.png)

消息发送失败

![image.png](https://img-blog.csdnimg.cn/img_convert/61cf76f7079872dbf340a64a2545775c.png)

### 心跳检测和自动重连

心跳检测用于判断客户端是否存活，自动重连用于当客户端连接的服务器宕机时，能够自动重连到其他可用的服务器

#### 心跳检测
服务端很简单，就是当发生读空闲事件时，判断是否已经超过设定超时时间，如果超过就下线客户端

![image.png](https://img-blog.csdnimg.cn/img_convert/d6c6f42c25f2df0f1e7347b8b616d5aa.png)

客户端的实现，看了下**IdleStateHandler**的大概实现原理，客户端按照其原理简单的实现了一个心跳检测，
在客户端中会有一个属性**idleWrite**来维护上一次发送消息是什么时候，每次发送消息时都会更新该属性

![image.png](https://img-blog.csdnimg.cn/img_convert/5d2afbac49c652af7a70d34efaab6b2a.png)

接着在客户端登录成功后，会启动一个定时任务，来检测上一次发送消息到现在是否已经超过限制，如果超过就向服务器发送心跳消息

![image.png](https://img-blog.csdnimg.cn/img_convert/db054f54b4282f8cb12bb79f681aa429.png)

服务器收到后会更新当前channel的**idleTime**

![image.png](https://img-blog.csdnimg.cn/img_convert/33c8ed88f79c566ff4c76730b73fc99f.png)

#### 自动重连
当服务器不可用的时候，客户端会发起重连，然后再重新初始化

![image.png](https://img-blog.csdnimg.cn/img_convert/95b2ffe2866b3ae27c4e62d1a3f090de.png)

### 最后
因为我的最初目的是熟悉下Netty的使用，所以做到这里已经差不多达到我当初的目的，当然后面会继续深入学习Netty，给该项目继续完善功能。同时该项目参考了 [crossoverJie] 的 [CIM](https://github.com/crossoverJie/cim)

**正在做和待做**

- [发送图片和文件],其实发送图片在我之前用BIO写的那个版本就已经实现了，但是实现方式不太满意，想想有没有更好的方式
- [群聊功能]，群聊功能在之前的BIO版本也是实现了的，这个功能也是正在写的
- ...
