package com.zxf.api.entity;
import lombok.Data;
@Data
public class UserEntity {
	
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public UserEntity(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", age=" + age + "]";
	}
	
	

}
