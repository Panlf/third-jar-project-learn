package com.plf.learn.orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaTutorial {

	public static void main(String[] args) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(User.class, Person.class)
                .field("id", "pId")
                .field("name", "pName")
                .byDefault()
                .register();

        User user = new User();
        user.setId(123);
        user.setName("小明");

        MapperFacade mapper = mapperFactory.getMapperFacade();

        Person p = mapper.map(user, Person.class);
        System.out.println(p.toString());
	}
}

class User {
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}

class Person {
	private Integer pId;
	private String pName;
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	@Override
	public String toString() {
		return "Person [pId=" + pId + ", pName=" + pName + "]";
	}
}
