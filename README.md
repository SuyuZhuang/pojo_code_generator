# feature 功能

根据create table建表语句生成pojo对象


# usage 使用方式


在想要创建POJO对象文件的位置右键，选择
**[NEW] | [Create POJO From DDL]** 



```
create table emp(
    id int auto_increment,
    emp_name char(20) not null default '' comment '员工名称',
    birth datetime not null comment '出生日期',
    addr varchar(200) not null comment '地址',
    dept_id int not null default 0 comment '部门id',
    index idx_emp_name(emp_name),
    index idx_emp_name_birth(emp_name,birth),
    primary key(id)
) engine=innoDB default charset=utf
```

![img1.png](imgs%2Fimg1.png)

在弹框中粘贴建表语句，如下图，如果只是普通的POJO，在弹框的上方选择Simple POJO, 如果需要Mybatis-plus的DO文件，选择Mybatis-Plus POJO

![img2.png](imgs%2Fimg2.png)


![img3.png](imgs%2Fimg3.png)
