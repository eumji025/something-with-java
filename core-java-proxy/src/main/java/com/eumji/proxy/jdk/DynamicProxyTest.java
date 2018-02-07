package com.eumji.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

	interface Demo
	{
		void print();
	}

	static class DemoImpl implements Demo{

		@Override
		public void print() {
			// TODO Auto-generated method stub
			System.out.println("我是目标方法!");
		}

	}

	static class DynamicProxy implements InvocationHandler{

		Object originalObj;

		Object bind(Object originalObj) {
			this.originalObj = originalObj;
			return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
		}
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO Auto-generated method stub
			System.out.println("welcome");
			return method.invoke(originalObj, args);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		Demo hello = (Demo) new DynamicProxy().bind(new DemoImpl());
		hello.print();

	}

}
