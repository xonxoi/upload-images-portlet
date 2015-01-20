package se.niteco.service;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import se.niteco.model.Employee;
import senselogic.sitevision.api.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public interface EmployeeService {
	
	public final String metadataName = "nitecoEmployee";
	
	public Type employeeType =  new TypeToken<LinkedList<Employee>>() {}.getType();
	
	public Gson gson = new Gson();
	
	/**
	 * @return List of employee
	 */
	public List<Employee> getListEmployee(Utils utils);
	
	/**
	 * @param newEmp
	 */
	public boolean insertEmployee(Utils utils, Employee newEmp) throws Exception;
	
	/**
	 * @param id
	 * @return employee
	 */
	public Employee getEmployee(Utils utils, String id);
	
	/**
	 * @param info
	 */
	public void updateEmployee(Utils utils, Employee info) throws Exception;
	
	/**
	 * @param id
	 */
	public void removeEmployee(Utils utils, String id) throws Exception;
}
