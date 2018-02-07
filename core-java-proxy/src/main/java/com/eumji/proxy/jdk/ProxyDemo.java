package com.eumji.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * @author eumji025
 * @date 2018/2/7
 */
public class ProxyDemo {

	interface Base{
		void hello(String name);
		void out();
	}

	static class LoginImpl implements Base{
		@Override
		public void hello(String name) {
			System.out.println("Hello "+name);
		}
		@Override
		public void out() {
			System.out.println("I'm out");
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
			before();
			Object invoke = method.invoke(originalObj, args);
			if (invoke != null) {
				result(invoke);
			}
			after();

			return invoke;
		}

		private void before() {
			System.out.println("方法执行之前");
		}
		private void after() {
			System.out.println("方法执行之后");
		}
		private void result(Object o) {
			o.toString();
		}

	}

	public static void main(String[] args) {
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		Base hello = (Base) new DynamicProxy().bind(new LoginImpl());
		hello.hello("zhangsan");
	}

}
