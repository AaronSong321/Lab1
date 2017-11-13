package com.arron;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class white {

	@Test
	public void test() {
		//����������ͼ�ṹ��
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("Neither not nor here exists.", l.queryBridgeWords("not", "here"));
	}
	@Test
	public void test1() {
		//src����ͼ�ṹ��
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The word not does not exist.", l.queryBridgeWords("not", "you"));
	}
	@Test
	public void test2() {
		//dst����ͼ�ṹ��
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The word no does not exist.", l.queryBridgeWords("no", "you"));
	}
	@Test
	public void test3() {
		//src��dst����ͼ�ṹ�� ����û���ŽӴ�
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("There are no bridge between are and you.", l.queryBridgeWords("are", "you"));
	}
	@Test
	public void test4() {
		//src��dst����ͼ�ṹ�� ����ֻ��һ���ŽӴ�
		ListDG l = new ListDG("i dont know who are you");
		Assert.assertEquals("The bridge word between who and you is are.", l.queryBridgeWords("who", "you"));
	}
	@Test
	public void test5() {
		//src��dst����ͼ�ṹ�� �����ж���ŽӴ�
		ListDG l = new ListDG("i dont know who are you and who knows you");
		Assert.assertEquals("The bridge word between who and you are are, knows.", l.queryBridgeWords("who", "you"));
	}
}
