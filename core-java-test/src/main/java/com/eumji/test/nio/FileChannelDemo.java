package com.eumji.test.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.UUID;

/**
 * @email eumji025@gmail.com
 * @author:EumJi
 * @date: 2019/3/10
 * @time: 11:00
 */
public class FileChannelDemo {
    private static final Logger logger = LoggerFactory.getLogger(FileChannelDemo.class);

    public static void main(String[] args) throws IOException {

        //String projectDir = System.getProperty("use.dir");

        //获取编译后的class根路径
        String path = FileChannelDemo.class.getClassLoader().getResource("./").getPath();

        File file = new File(path+"/hello.txt");

        //FileOutputStream对应的不可读  and FileIputStream对应的channel不可写
        FileChannel channel = new FileOutputStream(file).getChannel();

//        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
//        channel.read(readBuffer);
//        byte b;
//        do {
//            b = readBuffer.get();
//            System.out.print(b);
//        }while (b > 0);
        //readBuffer.get()

        long size = channel.size();
        long position = channel.position();

        //现有大小
        logger.info("file hello.txt size is :{} position is:{}",size,position);


        String s = UUID.randomUUID().toString();
        //写操作
        ByteBuffer uidBF = ByteBuffer.allocate(s.length());
        uidBF.put(s.getBytes());
        //翻转buffer
        uidBF.flip();
        //写入到管道
        channel.write(uidBF);
        //刷到磁盘
        channel.force(true);
        //关闭
        channel.close();

        //获取随机访问的file
        RandomAccessFile accessFile = new RandomAccessFile(path + "/hello.txt", "rw");
        //获取channel
        FileChannel accessFileChannel = accessFile.getChannel();
        //映射内存
        MappedByteBuffer buffer = accessFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, accessFileChannel.size());

        //读取
        byte[] bytes = new byte[(int) accessFileChannel.size()];
        buffer.get(bytes);


        //输出
        String value = new String(bytes);
        System.out.println(value);


    }
}
