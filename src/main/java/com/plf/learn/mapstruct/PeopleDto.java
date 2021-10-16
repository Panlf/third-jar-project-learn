package com.plf.learn.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author panlf
 * @date 2021/10/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleDto {
    private Integer rid;
    private String rname;
    private String birth;
    private String address;

}
