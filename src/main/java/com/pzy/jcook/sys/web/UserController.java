package com.pzy.jcook.sys.web;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pzy.jcook.dto.json.FailedResponse;
import com.pzy.jcook.dto.json.SuccessResponse;
import com.pzy.jcook.sys.entity.Role;
import com.pzy.jcook.sys.entity.User;
import com.pzy.jcook.sys.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends AbstractBaseCURDController<User,Long>  {
	
	@Override
	public UserService getBaseService() {
		return (UserService)super.getBaseService();
	}
	
	@Override
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("roles", this.getBaseService().findAllRoles());
		return this.getBasePath()+"/index";
	}
	@RequestMapping(value = "changepw", method = RequestMethod.GET)
	public String changepw(Model model) {
		model.addAttribute("user", (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser"));
		return this.getBasePath()+"/changepw";
	}
	
	@RequestMapping(value = "/changepw", method = RequestMethod.POST)
	public String changepw(Model model,String newpw,String oldpw) {
		Long userid = ((User)SecurityUtils.getSubject().getSession().getAttribute("currentUser")).getId();
		User user = this.getBaseService().find(userid);
		if(!user.getPassword().equals( DigestUtils.md5Hex(oldpw))){
			model.addAttribute("response",new FailedResponse("原始密码不正确"));
		}else{
			user.setPassword( DigestUtils.md5Hex(newpw));
			this.getBaseService().save(user);
			model.addAttribute("response",new SuccessResponse());
		}
		
		return this.getBasePath()+"/changepw";
	}
	@Override
	String getBasePath() {
		return "user";
	}
	
	@ModelAttribute
	public User preget(@RequestParam(required=false) Long id,@RequestParam(required=false) String role) {
		User user = new User();
		if (id!=null){
			user = this.getBaseService().find(id);
		}else{
			user.setPassword( DigestUtils.md5Hex(User.DEFAULT_PASSWORD));
		}
		if(StringUtils.isNotBlank(role)){
			String[] ids = role.split(",");
			Set<Role> roles = new HashSet<Role>();
			for(int i=0;i<ids.length;i++){
				roles.add(this.getBaseService().findRole(Long.valueOf(ids[i])));
			}
			user.setRoles(roles);
		}
		return user;
	}
}
