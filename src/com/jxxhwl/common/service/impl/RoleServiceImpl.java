package com.jxxhwl.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxhwl.common.dao.RoleDao;
import com.jxxhwl.common.entity.Authority;
import com.jxxhwl.common.entity.AuthorityMid;
import com.jxxhwl.common.entity.Role;
import com.jxxhwl.common.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	@Autowired RoleDao roleDao;
	/**
	 * 加载部门信息
	 */
	@Override
	public List<Role> loadRole() {
		List<Role> list = roleDao.findAll();
		return list;
	}
	/**
	 * 保存
	 */
	@Override
	public String save(Role role) {
		int i = roleDao.save(role);
		if(i > 0 ){
			return "保存成功";
		}
		return "保存失败";
	}
	/**
	 * 修改
	 */
	@Override
	public String edit(Role role) {
		int i = roleDao.update(role);
		if(i > 0 ){
			return "修改成功";
		}
		return "修改失败";
	}
	/**
	 * 删除
	 */
	@Override
	public String delete(Role role) {
		int i = roleDao.delete(role);
		if(i > 0 ){
			return "删除成功";
		}
		return "删除失败";
	}
	
	/**
	 * 根据角色id获取权限权限列表
	 */
	@Override
	public List<Authority> getAuth(long id) {
		List<AuthorityMid> list = roleDao.roleDao(id);
		List<Authority> authorityList = new ArrayList<Authority>();
		for (AuthorityMid a : list) {
			if(a.getFoo_id() == 0){
				Authority au = new Authority();
				au.setId(String.valueOf(a.getAccordion_id()));
				au.setText(a.getAccordion_name());
				au.setState("open");
				List<Authority> children = new ArrayList<Authority>();
				for(AuthorityMid aa : list){
					if(aa.getFoo_id() == a.getAccordion_id()){
						Authority auu = new Authority();
						auu.setId(String.valueOf(aa.getAccordion_id()));
						auu.setText(aa.getAccordion_name());
						if(aa.getRole_id() != 0 ){
							auu.setChecked(true);
						}else{
							auu.setChecked(false);
						}
						children.add(auu);
					}else{
						continue;
					}
				}
				au.setChildren(children);
				authorityList.add(au);
			}else {
				continue;
			}
		}
		
		return authorityList;
	}
	/**
	 * 提交权限
	 */
	@Override
	public String comfirmAuth(long id, String[] ids) {
		//delete 
		roleDao.deleteAuth(id);
		int i = 0 ;
		//查询父节点
		//TODO
		StringBuffer zb = new StringBuffer();
		for(String s: ids){
			zb.append("'").append(s).append("',");
		}
		String str = zb.toString();
		str = zb.substring(0, zb.length()-1);
		Set<String> set = roleDao.findFather(str);
		if(set.size() > 0 && set != null ){
			
			for(String s : ids ){
				set.add(s);
			}
			
			for (String s : set) {
				i += roleDao.saveAuth(id,s);
			}
		}else{
			for(String s : ids) {
				i += roleDao.saveAuth(id,s);
			}
		}
		if(i > 0) {
			return "权限保存成功";
		}
		return "权限保存失败";
	}
	/**
	 * 确认角色
	 */
	@Override
	public String updateRole(String login_id, String[] ids) {
		//delete
		roleDao.deleteRole(login_id);
		int i = 0;
		for(String s : ids) {
			i += roleDao.saveRole(login_id, s);
		}
		if(i > 0) {
			return "权限角色成功";
		}
		return "权限角色失败";
	}
}
