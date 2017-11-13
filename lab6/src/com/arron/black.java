package com.arron;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;


public class black {
	@SuppressWarnings("deprecation")
	@Test
	public void test00() {
		//ֻ��һ��·��
		ListDG l = new ListDG("i dont know who she is and you dont know who the girl is the beautiful girl is susan");
		Assert.assertEquals(2, l.Dijkstra("beautiful","girl").length-1);
	}	
	
	@SuppressWarnings("deprecation")
	@Test
	public void test01() {
		//�ж�����·�����������·��ֻ��һ��
		ListDG l = new ListDG("i dont know who she is and you dont know who is the girl the beautiful girl is susan");
		Assert.assertEquals(3, l.Dijkstra("know","is").length-1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test02() {
		//�ж�����·�����������·���ж���
		ListDG l = new ListDG("i dont know who she is and you dont know who the girl the beautiful girl is susan");
		Assert.assertEquals(2, l.Dijkstra("girl","is").length-1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test03() {
		//word1 word2���ɴ�
		ListDG l = new ListDG("i dont know who she is and you dont know who the girl the beautiful girl is susan");
		Assert.assertEquals(104, l.Dijkstra("susan","girl").length-1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test04() {
		//word1 word2���ɴ�
		ListDG l = new ListDG("the girl is susan");
		Assert.assertEquals(100, l.Dijkstra("no","exist").length-1);
	}
	
}
