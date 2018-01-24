package com.dtc.base;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BeanCopierTest {

	private Parent parent;
	
	private Son son;
	@Before
	public void before() {
		parent=new Parent();
		parent.setName("Parent");
		
		son=new Son();
		son.setName("Son");
		son.setAge(10);
	}
	
	@Test
	public void test() {
		Son son1 = BeanCopier.copy(parent, Son.class);
		Assert.assertEquals(parent.getName(), son1.getName());
		
		Parent parent1=BeanCopier.copy(son, Parent.class);
		Assert.assertEquals(son.getName(), parent1.getName());
		
		Son son2=BeanCopier.copy(son, Son.class);
		Assert.assertEquals(son.getName(), son2.getName());
		Assert.assertEquals(son.getAge(), son2.getAge());
		
		Son son3=BeanCopier.copy(new Object[] {parent1, son},new Son());
		Assert.assertEquals(son.getName(), son3.getName());
		Assert.assertEquals(son.getAge(), son3.getAge());
		
		Son son4=BeanCopier.copy(new Parent[] {parent1, son},new Son(),s->{s.setAge(12); return s;});
		Assert.assertEquals(son.getName(), son4.getName());
		Assert.assertEquals(12, son4.getAge());
		
		Girl girl=BeanCopier.copy(son, Girl.class);
		Assert.assertEquals(son.getName(), girl.getName());
		Assert.assertNull(girl.getAge());//由于类型不一致，不能复制age
		
		
	}
	
	
	
	public static class Parent{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Son extends Parent{
		private int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		
	}
	
	public static class Girl extends Parent{
		private Integer age;

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}
		
	}
}
