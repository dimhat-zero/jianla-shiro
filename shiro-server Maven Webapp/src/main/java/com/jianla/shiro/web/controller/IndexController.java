/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.web.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jianla.shiro.Constants;
import com.jianla.shiro.entity.Resource;
import com.jianla.shiro.entity.User;
import com.jianla.shiro.service.AuthorizationService;
import com.jianla.shiro.service.ResourceService;
import com.jianla.shiro.web.bind.annotation.CurrentUser;

/**
 * TODO
 * @author dimhat
 * @date 2015年12月4日 下午1:47:54
 * @version 1.0
 */
@Controller
public class IndexController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping("/")
	public String index(@CurrentUser User loginUser, Model model) {
		Set<String> permissions = authorizationService.findPermissions(Constants.SERVER_APP_KEY,
				loginUser.getUsername());
		List<Resource> menus = resourceService.findMenus(permissions);
		model.addAttribute("menus", menus);
		return "index";
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

}
