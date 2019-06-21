package com.eumji.test.io;

import java.io.*;
import java.util.stream.Stream;

/**
 *
 *  基于Java7中的 try-catch-resource 特性和 之前版本的比较
 *
 *  总的来说就是方便和高效很多，关闭的东西都交给JVM自己去管理
 *
 *   hello.txt在resources中有，只要复制到d盘根目录即可
 *
 * @author eumji
 * @date 2018/3/14
 */
public class TryCatchResouceDemo {

    /**
     * Java6及之前版本的中的用法，读取文件的每一行并输出，需要我们显示的关闭资源
     */
    public static void readFile6(){
        BufferedReader bufferedReader = null;
        try {

            String path = TryCatchResouceDemo.class.getClassLoader().getResource("./").getPath();

            bufferedReader = new BufferedReader(new FileReader(new File(path+"/hello.txt")));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Java7及以后的用法，使用try-catch-resouce的形式，在try中声明要关闭的资源，这要就不需要我们显示的去关闭。
     * Stream<String> lines = bufferedReader.lines();
     lines.forEach(System.out::println);
     使用了Java8中的的stream和lambda
     */
    public static void readFile7(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("d:/hello.txt")))){
            Stream<String> lines = bufferedReader.lines();
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        readFile6();
        readFile7();
    }
}
