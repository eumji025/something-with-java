package com.eumji.test.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ServiceLoader;

/**
 * 自定义classLoader，具体描述参照ClassLoader doc文档描述的内容来进行
 * 这里最关键的地方在于重写findClass方法，并且需要调用defineClass来进行定义。最后如果要实例化则使用newInstance方法
 *
 *
 *
 *
 * 类加载的几个阶段
 *
 * 加载  -> 主要负责将任意方式的class文件加载到内存
 * 验证  -> 验证字节码的正确性，如java字节的顺序，常量是否能在常量池找到，类的继承是否正确等
 * 准备  -> 为类加载阶段的静态成员变量附初始值0，null这种
 * 解析  -> 解析主要把符号引用变成直接应用
 * 初始化-> 将初始化的变量赋值真正的值（a = 5，将5赋值给a）
 * 使用
 * 回收
 *
 *
 *
 * 双亲委派加载机制的实现:
 *
 * 在我们尝试类加载之前，会首先尝试将使用父加载器（网上递归），如果父加载器能加载则使用父加载器进行加载，如果不可能则让子加载器加载（向下递归）
 * 并且如果我们尝试重写一个JDK包存在的，将无法正常被加载的。
 *
 * 自定义类加载器建议重写findClass方法，主要是自定义寻找和class解析byte数组的过程，获取之后应该调用defineClass来加载类
 *
 * 如果不这么做将非常有可能让你无法正常的加载类
 *
 * 如果你这么做了，你所有想重写java包下的行为都会被阻止，在{@link java.lang.ClassLoader#preDefineClass}方法中都会抛出 SecurityException异常
 * ：Prohibited package name: java.util.concurrent.atomic
 *
 * 所以我们如果想要对字节码做混淆，就可以自定义类加载器进行还原成正常java字节码数组。
 *
 * 但是要提一下 如果我们希望通过spring加载类，那么这样做是不可行的，因为spring的扫描会不太一样。
 *
 *
 * 另外还有一个知识点就是父加载器无法访问子加载器加载的类，反过来是可以的。那么思考一下SPI是如何实现动态加载的
 * ServiceLoader是java.util包下的类，理应被extensionClassLoader加载，而这个类加载器本应该无法加载用户路径下的类
 * 这里可以参看{@link ServiceLoader#load(java.lang.Class)},JDK的后门，通过{@link Thread#getContextClassLoader()}来反向获取更下层的类加载器进行加载
 *
 *
 * @email eumji025@gmail.com
 * @author:EumJi
 * @date: 2019/4/21
 * @time: 12:54
 */
public class MyClassLoader extends ClassLoader {
    private static Logger logger = LoggerFactory.getLogger(MyClassLoader.class);

    private String classLoaderName;
    private String fileSuffix = ".class";

    public MyClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(String classLoaderName) {
        super(null);
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = findClassData(name);
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            logger.error("find data error:{}",e.getMessage());

        }
        return super.findClass(name);
    }

    /**
     * 根据名称获取class的字节数组
     *
     * @param name
     * @return
     */
    private byte[] findClassData(String name) throws IOException {
        name = name.replace(".", File.separator);
        if (!name.endsWith(fileSuffix)) {
            name = name + fileSuffix;
        }
        String basePath = MyClassLoader.class.getResource("/").getPath() + File.separator;
        File file = new File(basePath + name);
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
        ) {
            byte[] tmp = new byte[1024];
            int length = -1;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((length = fileInputStream.read(tmp)) > -1) {
                byteArrayOutputStream.write(tmp, 0, length);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }


    @Override
    public String toString() {
        return "MyClassLoader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader myclassloader = new MyClassLoader("myclassloader");
        Class<?> aClass = myclassloader.loadClass("java.util.concurrent.atomic.AtomicDemo");
        logger.info("{}",aClass);
        logger.info("{}",aClass.getClassLoader());
    }

}
