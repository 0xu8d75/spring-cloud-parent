package com.u8d75.user;

import com.u8d75.core.mybatis.Where;
import com.u8d75.user.domain.User;
import com.u8d75.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {Application.class})
class CrudTest {

    @Autowired
    private UserServiceImpl userService;

    Long id = 1L;
    List<Long> ids = List.of(1L, 2L);
    Long deleteId = 3L;

    @Test
    void findById() {
        assertNotNull(userService.findById(id));
    }

    @Test
    void findByIds() {
        assertEquals(2, userService.findByIds(ids).size());
    }

    @Test
    void findByParams() {
        assertNotNull(userService.findByParams(new Where<User>().eq("id", 1L)));
        assertNotNull(userService.findByParams(new Where<User>().neq("id", 1L)));
        assertNotNull(userService.findByParams(new Where<User>().in("id", List.of(1L, 2L))));
        assertNotNull(userService.findByParams(new Where<User>().nin("id", List.of(1L, 2L))));
        assertNotNull(userService.findByParams(new Where<User>().bt("id", 1L, 2L)));
        assertNotNull(userService.findByParams(new Where<User>().gt("id", 1L)));
        assertNotNull(userService.findByParams(new Where<User>().gte("id", 1L)));
        assertNotNull(userService.findByParams(new Where<User>().lt("id", 2L)));
        assertNotNull(userService.findByParams(new Where<User>().lte("id", 2L)));
        assertNotNull(userService.findByParams(new Where<User>().eq("username", "admin")));
        assertNotNull(userService.findByParams(new Where<User>().eq("id", 1L).eq("username", "admin")));
        assertNotNull(userService.findByParams(new Where<User>().eq("id", 1L).eq("username", "admin").orderByDesc("id").orderByAsc("username")));
    }

    @Test
    void findListByParams() {
        assertEquals(2, userService.findListByParams(new Where<User>().in("id", ids)).size());
    }

    @Test
    void countByParams() {
        assertEquals(2L, userService.countByParams(new Where<User>().in("id", ids)));
    }

    @Test
    void deleteById() {
        assertEquals(1, userService.deleteById(deleteId));
    }

    @Test
    void updateById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setCreateTime(1L);
        user.setUpdateTime(2L);
        user.setOperator(3L);
        user.setOperatorName("admin");
        assertEquals(1, userService.updateById(user));
    }

    @Test
    void updateByIdSelective() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setCreateTime(1L);
        user.setUpdateTime(2L);
        user.setOperator(3L);
        user.setOperatorName(null);
        assertEquals(1, userService.updateByIdSelective(user));
    }

    @Test
    void insert() {
        User user = new User();
        user.setUsername("insert");
        user.setCreateTime(1L);
        user.setUpdateTime(2L);
        user.setOperator(3L);
        user.setOperatorName(null);
        assertEquals(1, userService.insert(user));
    }

    @Test
    void insertBatch() {
        List<User> list = new ArrayList<>();
        {
            User user = new User();
            user.setUsername("insertBatch");
            user.setCreateTime(1L);
            user.setUpdateTime(2L);
            user.setOperator(3L);
            user.setOperatorName("insertBatch");
            list.add(user);
        }
        {
            User user = new User();
            user.setUsername("insertBatch2");
            user.setCreateTime(1L);
            user.setUpdateTime(2L);
            user.setOperator(3L);
            user.setOperatorName("insertBatch2");
            list.add(user);
        }
        assertEquals(2, userService.insertBatch(list));
    }


}
