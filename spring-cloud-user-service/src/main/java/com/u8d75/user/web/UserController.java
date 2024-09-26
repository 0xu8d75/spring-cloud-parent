package com.u8d75.user.web;

import com.github.pagehelper.PageInfo;
import com.u8d75.core.base.controller.BaseController;
import com.u8d75.core.base.result.BaseResult;
import com.u8d75.core.mybatis.Where;
import com.u8d75.core.token.GlobalTokenHolder;
import com.u8d75.user.domain.User;
import com.u8d75.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户")
@Log4j2
public class UserController extends BaseController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * login
     *
     * @param username username
     * @param password password
     * @return BaseResult<HashMap < String, Object>>
     */
    @PostMapping(value = "login")
    @Operation(summary = "登录")
    public BaseResult<HashMap<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        HashMap<String, Object> data = new HashMap<>();
        User user = userService.findByParams(new Where<User>().eq("username", username));
        if (null != user) {
            String accessToken = GlobalTokenHolder.getAccessToken(GlobalTokenHolder.DEFAULT_TOKEN_SECRET, user.getId(), user.getUsername());
            data.put("token", accessToken);
        }
        return BaseResult.ok(data);
    }

    /**
     * find
     *
     * @param id id
     * @return BaseResult<User>
     */
    @Operation(summary = "查找")
    @GetMapping("/find")
    public BaseResult<User> find(
            @RequestParam Long id
    ) {
        return BaseResult.ok(userService.findById(id));
    }

    /**
     * add
     *
     * @param username username
     * @return BaseResult<Serializable>
     */
    @Operation(summary = "添加")
    @GetMapping("/add")
    public BaseResult<Serializable> add(
            @RequestParam String username
    ) {
        User user = new User();
        user.setUsername(username);
        user.setCreateTime(System.currentTimeMillis());
        user.setOperator(1L);
        userService.insert(user);
        return BaseResult.ok();
    }

    /**
     * list
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @return BaseResult<PageInfo < User>>
     */
    @Operation(summary = "列表")
    @GetMapping("/list")
    public BaseResult<PageInfo<User>> list(
            @Parameter(description = "页码") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(required = false, defaultValue = "30") Integer pageSize
    ) {
        return BaseResult.ok(userService.findPage(pageNum, pageSize, new Where<>()));
    }

}
