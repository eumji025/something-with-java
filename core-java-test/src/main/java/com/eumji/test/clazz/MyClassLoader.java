package com.eumji.test.clazz;

import java.io.*;

/**
 * 自定义classLoader，具体描述参照ClassLoader doc文档描述的内容来进行
 * 这里最关键的地方在于重写findClass方法，并且需要调用defineClass来进行定义。最后如果要实例化则使用newInstance方法
 * @email eumji025@gmail.com
 * @author:EumJi
 * @date: 2019/4/21
 * @time: 12:54
 */
public class MyClassLoader extends ClassLoader {

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
            return defineClass(name,classData,0,classData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 根据名称获取class的字节数组
     * @param name
     * @return
     */
    private byte[] findClassData(String name) throws IOException {
        name = name.replace(".","/");
        if (!name.endsWith(fileSuffix)){
            name= name+fileSuffix;
        }
        String basePath = MyClassLoader.class.getClass().getResource("/").getPath()+"/";
        File file = new File(basePath+name);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] tmp = new byte[1024];
        int length = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((length = fileInputStream.read(tmp)) > -1){
            byteArrayOutputStream.write(tmp,0,length);
        }

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public String toString() {
        return "MyClassLoader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader myclassloader = new MyClassLoader("myclassloader");
        Class<?> aClass = myclassloader.loadClass("com.eumji.test.clazz.Hello");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());
    }

}
