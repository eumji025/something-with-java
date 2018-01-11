package com.eumji.test.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 此类是测试stream的filter,map,reduce功能
 * 实例是 获取所有不为空的元素,获取年龄并求和的过程
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-01-11
 * @time: 22:18
 */
public class ArrayListStreamDemo {

    private static Logger logger = LoggerFactory.getLogger(ArrayListStreamDemo.class);
    private static Integer apply(Integer integer, Integer integer2) {
        return integer + integer2;
    }

    static class Person{
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


//    public static boolean isEmpty(Person person){
//        if (person.name == null || "".equals(person.name)){
//            return true;
//        }
//        return false;
//    }

    public static void main(String[] args) {

        List<Person> people = new ArrayList();
        people.add(new Person("zhangsan",12));
        people.add(null);
        people.add(new Person("lisi",13));

        Integer sum = people.stream().filter(Objects::nonNull).map(Person::getAge).reduce(ArrayListStreamDemo::apply).orElse(0);
        logger.info("the total is : {}",sum);

    }
}
