package com.jxxhwl.common.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jxxhwl.common.BaseAction;
import com.jxxhwl.common.entity.Department;
import com.jxxhwl.common.service.DepartmentService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction implements ModelDriven<Department> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Department department = new Department();
	@Override
	public Department getModel() {
		return department;
	}
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 *  加载部门信息
	 * @return
	 */
	public String loadDepartment(){
		List<Department> list = departmentService.loadDepartment();
		ActionContext.getContext().getValueStack().set("result", list);
		return SUCCESS;
	}
	/**
	 * 添加
	 * @return
	 */
	public String addInfo(){
		ok = departmentService.save(department);
		return OK;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String editInfo(){
		ok = departmentService.edit(department);
		return OK;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteInfo(){
		ok = departmentService.delete(department);
		return OK;
	}
	
	
	

}
