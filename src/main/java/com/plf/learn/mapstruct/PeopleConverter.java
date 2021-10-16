package com.plf.learn.mapstruct;

import java.util.Date;

/**
 * @author panlf
 * @date 2021/10/16
 */
public class PeopleConverter {
    public static void main(String[] args) {
        People people = People.builder().id(1).name("张三").birthday(new Date()).age(1).build();
        System.out.println(people);
        PeopleDto peopleDto = PeopleMapper.INSTANCE.domainToDto(people);
        System.out.println(peopleDto);
    }
}
