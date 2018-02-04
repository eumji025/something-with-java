package com.eumji.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * getConstructor 只能用于public方法
 * getConstructor 可以定义参数
 *
 * newInstance方法不能用于private方法
 * newInstance方法只能用于无参构造函数
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-2-4
 * @time: 下午7:04
 */
public class HelloModel {

    static class Model{
        String first;
        String second;
        String third;
        private Model(){

        }

        public Model(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }


        @Override
        public String toString() {
            return "Model{" +
                    "first='" + first + '\'' +
                    ", second='" + second + '\'' +
                    ", third='" + third + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //=========暴力通过反射的获取实例 构造方法为private时候
        Constructor<Model> constructors = Model.class.getDeclaredConstructor(null);
        //Constructor<Model> constructors = Model.class.getConstructor(null); //无法使用这个。这个是公有方法才可以使用的

        constructors.setAccessible(true);
        Model model = constructors.newInstance(null);
        System.out.println(model);

        //=======暴力通过反射的获取实例 构造函数不为private
        Model model2 = Model.class.newInstance();
        System.out.println(model2);
    }
}
