package se.niteco.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import org.springframework.stereotype.Service;

import se.niteco.model.Employee;
import senselogic.sitevision.api.Utils;
import senselogic.sitevision.api.context.PortletContextUtil;
import senselogic.sitevision.api.metadata.MetadataUtil;
import senselogic.sitevision.api.property.PropertyUtil;



@Service(value="employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	public List<Employee> getListEmployee(Utils utils){
		
		List<Employee> ret = new ArrayList<Employee>();
		
		//Get data
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		PropertyUtil propertyUtil = utils.getPropertyUtil(); 
		Node currentPage = ctxUtil.getCurrentPage();
		
		String employeeJSON = propertyUtil.getString(currentPage, EmployeeService.metadataName);
		
		//Return data
		if(employeeJSON != null && employeeJSON.length() > 0){
			ret = gson.fromJson(employeeJSON, EmployeeService.employeeType);
		}
		
		return ret;
	}
	
	public boolean insertEmployee(Utils utils, Employee newEmp) throws Exception{
		Employee exist = getEmployee(utils, newEmp.getEmployeeId());
		
		if( exist == null ){
			MetadataUtil dataUtil = utils.getMetadataUtil();			
			PropertyUtil propertyUtil = utils.getPropertyUtil(); 
			PortletContextUtil ctxUtil = utils.getPortletContextUtil();
			Node currentPage = ctxUtil.getCurrentPage();
			
			String employeeJSON = propertyUtil.getString(currentPage, EmployeeService.metadataName);	
			
			List<Employee> lst = gson.fromJson(employeeJSON, EmployeeService.employeeType);
			if(lst == null){
				lst = new ArrayList<Employee>();
			}
			
			lst.add(newEmp);
			dataUtil.setMetadataPropertyValue(currentPage, EmployeeService.metadataName, gson.toJson(lst));
			
			return true;
		}else{
			return false;
		}
		
	}
	
	public Employee getEmployee(Utils utils, String id){
		Employee ret = null;
		List<Employee> empData = new ArrayList<Employee>(); 
		
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		PropertyUtil propertyUtil = utils.getPropertyUtil(); 
		
		Node currentPage = ctxUtil.getCurrentPage();
		String employeeJSON = propertyUtil.getString(currentPage, EmployeeService.metadataName);
		
		if(employeeJSON != null && employeeJSON.length() > 0){
			empData = gson.fromJson(employeeJSON, EmployeeService.employeeType);
		}
		
		for(Employee emp : empData){
			if(id.equals(emp.getEmployeeId())){
				return emp;
			}
		}
		return ret;
	}
	
	public void updateEmployee(Utils utils, Employee info) throws Exception{
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		PropertyUtil propertyUtil = utils.getPropertyUtil();
		MetadataUtil dataUtil = utils.getMetadataUtil();
		
		Node currentPage = ctxUtil.getCurrentPage();
		String employeeJSON = propertyUtil.getString(currentPage, EmployeeService.metadataName);
		
		List<Employee> empData = gson.fromJson(employeeJSON, EmployeeService.employeeType);
		
		for(Employee emp : empData){
			if(emp.getEmployeeId().equals(info.getEmployeeId())){
				emp.setEmployeeName(info.getEmployeeName());
				emp.setDepartment(info.getDepartment());
				emp.setPosition(info.getPosition());
				emp.setSalary(info.getSalary());
				emp.setPicture(info.getPicture());
				break;
			}
		}
		
		dataUtil.setMetadataPropertyValue(currentPage, EmployeeService.metadataName, gson.toJson(empData));
	}
	
	public void removeEmployee(Utils utils, String id) throws Exception{
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		PropertyUtil propertyUtil = utils.getPropertyUtil();
		MetadataUtil dataUtil = utils.getMetadataUtil();
		
		Node currentPage = ctxUtil.getCurrentPage();
		String employeeJSON = propertyUtil.getString(currentPage, EmployeeService.metadataName);
		
		List<Employee> empData = gson.fromJson(employeeJSON, EmployeeService.employeeType);
		List<Employee> newList = new ArrayList<Employee>();
		
		for(Employee emp : empData){
			if(!emp.getEmployeeId().equals(id)){
				newList.add(emp);
			}
		}
		
		dataUtil.setMetadataPropertyValue(currentPage, EmployeeService.metadataName, gson.toJson(newList));
	}
}
