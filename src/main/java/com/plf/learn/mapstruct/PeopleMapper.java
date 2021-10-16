package com.plf.learn.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author panlf
 * @date 2021/10/16
 */
@Mapper
public interface PeopleMapper {
    PeopleMapper INSTANCE = Mappers.getMapper(PeopleMapper.class);

    @Mapping(source = "id",target = "rid")
    @Mapping(source = "birthday",target = "birth",dateFormat = "yyyy-MM-dd")
    @Mapping(source = "name",target = "rname")
    PeopleDto domainToDto(People people);
}

