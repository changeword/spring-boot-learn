package com.example.demo;

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
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.demo.entity.Blog;
import com.example.demo.web.HelloController;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DemoApplicationTests {



	//@Test
	public void splitList() {
		List<String> A = new ArrayList<>();
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

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
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

}
