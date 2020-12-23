# netty-learn
netty 学习

## NIO 


### Selector Channel Buffer 的关系：  
1. channel 和 buffer 一对一
2. Selector 对应一个线程，并对应多个 channel， 多个 channel 注册到 一个 selector 上
3. 程序切换到哪个 channel 上是事件决定的，**event**  很重要
4. Selector 根据不同的事件，在各个 channel 上切换
5. buffer 就是一个内存块，底层是数组，和 channel 一一对应
6. 数据的读写是通过 buffer 来完成的。BIO 要么是分为输入流和输出流，不能双向， NIO的 buffer 双向，但需要 flip 方法进行切换。
7. channel 也是双向的，可以反映底层操作系统的情况，比如 ; linux , 底层操作系统就是双向的

### buffer
本质上就是一个可以读写数据的内存块，可以理解为容器对象（含数组），该对象提供了一组方法，可以轻松的使用内存块， buffer 对象内置了一些机制，能够跟踪记录缓冲区的状态变化， channel 提供从文件，网络读取数据的渠道，但是读写数据都是由 buffer 完成。  



