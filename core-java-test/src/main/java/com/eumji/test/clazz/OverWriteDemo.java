package com.eumji.test.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 父类和子类继承关系
 * 方法重载和复写的调用结果验证
 */
public class OverWriteDemo {

    public static void main(String[] args) {
        Father father = new ChildChild();
        Father child = new Child();
        father.hello();
        child.hello();

        father.d1(father);
        father.d1(child);

    }
}

class Father{
    private static Logger logger = LoggerFactory.getLogger(Father.class);

    public void hello(){
        logger.info("hello father");
    }

    public void d1(Father father){
        logger.info("father d1 doing:{}",father);
    }
    public void d1(Child child){
        //ignore
        logger.info("child d1 doing:{}",child);
    }

    public void d1(ChildChild child){
        logger.info("childchild d1 doing:{}",child);
    }

}

class Child extends Father{
    private static Logger logger = LoggerFactory.getLogger(Child.class);

    @Override
    public void hello(){
        logger.info("hello Child");
    }
}


class ChildChild extends Child{
    private static Logger logger = LoggerFactory.getLogger(ChildChild.class);

    @Override
    public void hello(){
        logger.info("child child");
    }
}
