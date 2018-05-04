package com.example.demo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.Math.sqrt;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.demo.entity.Blog;
import com.example.demo.web.HelloController;
import redis.clients.jedis.Jedis;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DemoApplicationTests {



	@Test
	public void splitList() {
		List<String> A = new ArrayList<String>();
		A.add("1");A.add("2");A.add("3");A.add("4");A.add("5");A.add("6");A.add("7");A.add("8");A.add("9");
		List<String> list = new ArrayList<String>();
		List<List> lists = new ArrayList<List>();
		int index = 0;
		int listSize = 5;
		for(int i=0;i<A.size();i++){
			if((i+1)%listSize!=0){
				list.add(A.get(i));
				if((A.size()-listSize*index)<listSize){
					list.clear();
					list = A.subList(listSize*index,A.size());
					lists.add(index,list);
					break;
				}
			}else{
				list.add(A.get(i));
				lists.add(index,list);
				list = new ArrayList<String>();
				index++;
			}
		}

		System.out.print(lists);
	}

	private MockMvc mvc;

	//@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	//@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("LP,you are amazing")));
	}


	public Blog blogProperties;

	//@Test
	public  void getHello1(){
		Assert.assertEquals(blogProperties.getName(), "程序猿LP");
		Assert.assertEquals(blogProperties.getTitle(), "Spring Boot教程");
	}

	private Object copy(Object claxx,Object clazz) throws Exception{
		Field[] fields = claxx.getClass().getDeclaredFields();
		Object value = null;
		for(Field f : fields){
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(),claxx.getClass());
			Method wM  = pd.getWriteMethod();
			value = getValue(f.getName(),clazz);
			if(value!=null){
				wM.invoke(claxx,value);
			}
		}
		return claxx;
	}

	private Object getValue(String fieldName,Object clazz) throws Exception{
		Field[] fields = clazz.getClass().getDeclaredFields();
		for(Field f : fields){
			String name = f.getName();
			if(fieldName.equals(name)){
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method m = clazz.getClass().getMethod("get"+name);
				Object value =  m.invoke(clazz);
				if(value!= null){
					return value;
				}
			}
		}
		return null;
	}

	//@Test
	public void hash() throws Exception{
		HashMap hash = new HashMap<String,Object>();
		int i,j,e;
		int a;
		int I = 20;
		int R = 340;
		long time;
		for(i=1,a=I;i<I/2;i++,a--){
			for(j=(int) ( I-sqrt(I*I-(a-i)*(a-i)) );j>0;j--)
				System.out.printf(" ");
			for(e=1;e<=2*sqrt(I*I-(a-i)*(a-i));e++)
				System.out.printf("\3");
			for(j=(int) ( 2*( I-sqrt(I*I-(a-i)*(a-i)) ) );j>0;j--)
				System.out.printf(" ");
			for(e=1;e<=2*sqrt(I*I-(a-i)*(a-i));e++)
				System.out.printf("\3");
			System.out.printf("\n");
		}
		for(i=1;i<80;i++)
		{
			if(i==25){
				System.out.printf("XXX I LOVE YOU!I LOVE YOU!");
				i+=30;
			}
			System.out.printf("\3");
		}
		System.out.printf("\n");
		for(i=1;i<=R/2;i++){
			if(i%2 ==0 || i%3==0)continue;
			for(j=(int) ( R-sqrt(R*R-i*i) );j>0;j--)
				System.out.printf(" ");
			for(e=1;e<=2*( sqrt(R*R-i*i) - (R-2*I) );e++)
				System.out.printf("\3");
			System.out.printf("\n");
		}
		for(; ;)
		{
			System.out.printf("color a");
			Thread.sleep(1000);
			System.out.printf("color b");
			Thread.sleep(1000);
			System.out.printf("color c");
			Thread.sleep(1000);
			System.out.printf("color d");
			Thread.sleep(1000);
			System.out.printf("color e");
			Thread.sleep(1000);
		}

	}

	Jedis jedis;
	@Before
    public void before(){
	    jedis = new Jedis("47.98.115.186",6379);
    }
    @After
    public void after(){
	    jedis.close();
    }

   // @Test
    public void test() throws  InterruptedException{
	    jedis.set("name","LP");
	    String name = jedis.get("name");
	    System.out.println(name);
	    //查看有效期，-1表示持久化
	    Long t = jedis.ttl("name");
	    System.out.println(t);
	    //已存在的key设置过期时间
        jedis.expire("name",50);
        while(true){
            String name2 = jedis.get("name");
            System.out.println(name2);
            System.out.println("有效期"+jedis.ttl("name")+"秒");
            Thread.sleep(2000);
        }
    }


}
