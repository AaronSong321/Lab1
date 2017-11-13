package com.arron;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class white {

	@Test
	public void test() {
		//两个都不在图结构中
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("Neither not nor here exists.", l.queryBridgeWords("not", "here"));
	}
	@Test
	public void test1() {
		//src不在图结构中
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The word not does not exist.", l.queryBridgeWords("not", "you"));
	}
	@Test
	public void test2() {
		//dst不在图结构中
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The word no does not exist.", l.queryBridgeWords("no", "you"));
	}
	@Test
	public void test3() {
		//src和dst都在图结构中 但是没有桥接词
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("There are no bridge between are and you.", l.queryBridgeWords("are", "you"));
	}
	@Test
	public void test4() {
		//src和dst都在图结构中 但是只有一个桥接词
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The bridge word between who and you is are.", l.queryBridgeWords("who", "you"));
	}
	@Test
	public void test5() {
		//src和dst都在图结构中 但是有多个桥接词
		ListDG l = new ListDG("i dont know who are you and who knows you");
		Assert.assertEquals("The bridge word between who and you are are, knows.", l.queryBridgeWords("who", "you"));
	}
}
