package com.ideas2it;
import java.util.Scanner;


import com.ideas2it.training.model.LeaveRecords;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.Project;

import com.ideas2it.training.services.employee.impl.EmployeeServicesImpl;
import com.ideas2it.training.services.employee.EmployeeServices;

import com.ideas2it.training.services.project.ProjectServices;
import com.ideas2it.training.services.project.impl.ProjectServicesImpl;

import com.ideas2it.training.services.leaveRecords.LeaveServices;
import com.ideas2it.training.services.leaveRecords.impl.LeaveServicesImpl;

import com.ideas2it.training.utils.Validiator;

import com.ideas2it.training.constants.Gender;
import com.ideas2it.training.constants.EmployeeType;
import com.ideas2it.training.constants.LeaveType;


/**
 *
 * this MainController class is used to control the flow of data
 *  ----> it takes user inputs and pass it to respective service layers for further operation.
 *  ----> it gets results back from service layers, and those results are passed to view.
 *
 * @author : Akhter hussain dar
 * @version : 1.0
 * @since : 01-aug-2022
 */
public class MainController {

	private Scanner scanner = new Scanner(System.in);
	private Validiator validiator = new Validiator();
	private EmployeeServices employeeServices = new EmployeeServicesImpl();
	private LeaveServices LeaveServices = new LeaveServicesImpl();
	private ProjectServices ProjectServices = new ProjectServicesImpl();



	/**
	 * addEmployee method takes user inputs and passes an object of employee to its service layer
	 *  ---> it takes all valid inputs by calling getValid Methods.
	 *  ---> status of the operation will be printed whether done or not with paricular message.
	 */
//    void addEmployee() {
//        String employeeId = employeeServices.generateEmployeeId();
//        System.out.println("Employee id assigned : "+employeeId);
//        String employeeType = getValidEmployeeType().toString();
//		String name = getValidName();
//		String gender = getValidGender().toString();
//		System.out.println(gender);
//		String birthdate = getValidBirthdate();
//		String designation = getValidDesignation();
//		String contact = getValidContact();
//		String email = getValidEmail();
//		String probationTime = getValidProbationTime();
//        boolean status = employeeServices.addEmployee(new Employee(employeeId,employeeType, name,gender,birthdate,designation,contact,email,probationTime));
//		if (status)
//		    System.out.println("Employee Added to database successfully");
//		else
//		    System.out.println("error while adding employee in database, Try Again");
//	}

	public String addEmployee(HttpServletRequest req) {
		try {
			String employeeId = employeeServices.generateEmployeeId();
			System.out.println("Employee id assigned : "+employeeId);
			String employeeType = req.getParameter("employee_type");
			String name = req.getParameter("employee_name");
			String gender = req.getParameter("gender");
			System.out.println(gender);
			String birthdate = req.getParameter("birthdate");
			String designation = req.getParameter("designation");
			String contact = req.getParameter("contact");
			String email = req.getParameter("email");
			String probationTime = req.getParameter("probation");
			boolean status = employeeServices.addEmployee(new Employee(employeeId,employeeType, name,gender,birthdate,designation,contact,email,probationTime));
			if (status)
				return "Employee Added to database successfully";
			else
				return "error while adding employee in database, Try Again";
		} catch (HibernateException h) {
			return "exception in hibernation"+h;
		}
	}

	/**
	 * addLeaveRecords method takes user inputs and passes an object of LeaveRecords to its service layer
	 *  ---> it takes all valid inputs by calling getValid Methods.
	 *  ---> status of the operation will be printed whether done or not with paricular message.
	 */
	public String  addLeaveRecords(HttpServletRequest req) {
		final int MAX_LEAVES_ALLOWED = 10;
		boolean status = false;
		String leaveType;
		LeaveRecords leaveRecords = new LeaveRecords();
			String employeeId = req.getParameter("employee_id");
			Employee employee = employeeServices.getEmployee(employeeId);
			if (employee != null) {
				employeeId = employee.getEmployeeId();
				String fromDate;
				String toDate;
				int leavesCount = LeaveServices.getLeaveCount(employeeId);
				if (leavesCount < MAX_LEAVES_ALLOWED) {
					fromDate = req.getParameter("from_date");
					toDate = req.getParameter("to_date");
					if (validiator.isValidlLeaveDates(fromDate,toDate,leavesCount)) {
						leaveType = req.getParameter("leave_type");
						leaveRecords = new LeaveRecords(fromDate,toDate,leaveType);
						status = LeaveServices.addLeaveRecords(employee.getEmployeeId(),leaveRecords);
							if (status)
								return "Records Added to database successfully";
							else
								return "error while adding records in database, Try Again";
					} else {
						return "you have only "+(MAX_LEAVES_ALLOWED-leavesCount)+" left, please enter valid dates";
					}

				} else {
					return "Sorry you have no leaves left";
				}
			} else {
				return "employee with this id does not exist";
			}

	}

	/**
	 * addProject method takes user inputs and passes an object of Project to its service layer
	 *  ---> it takes all valid inputs by calling getValid Methods.
	 *  ---> status of the operation will be printed whether done or not with paricular message.
	 */
	public String addProject(HttpServletRequest req) {
		Project project;
			String projectManagerId = req.getParameter("manager_id");
			Employee manager = employeeServices.getEmployee(projectManagerId);
			if (manager != null) {

				String projectName = req.getParameter("project_name");
				String startDate = req.getParameter("start_date");
				String description = req.getParameter("description");
				String managerName = manager.getName();
				String clientName = req.getParameter("client");

				project = new Project(projectName,startDate,description,managerName,clientName);
			} else {
				return "manager with this id does not exist";
			}
		boolean status = ProjectServices.addProject(project);
		if (status)
			return "project Added to database successfully";
		else
			return "error while adding project in database, Try Again";
	}

	/**
	 *
	 * this method is responsible for assigning project to an Employee.
	 *
	 */

	void assignProjectToEmployee() {
		boolean status = false;
		while(true) {
			String userEmployeeId = getString("enter employee's ID");
			Employee employee = employeeServices.getEmployee(userEmployeeId);
			if (employee!=null) {
				List<Project> projects = ProjectServices.getProjects();
				viewProjects(projects);
				int projectId = getInt("enter project ID");
				Project project = ProjectServices.getProject(projectId);
				if(project != null) {
					System.out.println(employee.getEmployeeId());
					status = employeeServices.assignProject(employee.getEmployeeId(),project.getProjectId());
					if (status)
						System.out.println("project assigned");
					else
						System.out.println("project could not be assigned , try again");
					break;
				} else {
					System.out.println("not a valid project id");
					continue;
				}
			} else {
				System.out.println("not a valid Employee id");
				continue;
			}
		}
	}



	/**
	 *
	 * this method retrieves data of all employees.
	 *
	 */
	public List<Employee>  getEmployees() {
		List<Employee> employees = employeeServices.getEmployees();
		String results = "";
		if (employees.size() != 0) {
			return employees;
		} else {
			return null;
		}
	}


	/**
	 *
	 * this method retrieves data of particular Employee.
	 */
	public Employee getEmployee(String employeeId) {
		return employeeServices.getEmployee(employeeId);
	}
	/**
	 *
	 * this method retrieves data of particular employee with the leaves assosiated..
	 *
	 */

	public Employee getEmployeeLeaves(String employeeId) {
		System.out.println("1st");
		Employee employee = employeeServices.getEmployeeLeaves(employeeId);
		System.out.println("end");
		//System.out.println(employee.getLeaves());
		System.out.println("end2");
		return employee;
	}

	/**
	 *
	 * this method retrieves data of particular Employee with projects assigned.
	 *
	 */
	void getEmployeeProjects() {
		String employeeId = getString("enter employee id ");
		Employee employee = employeeServices.getEmployeeProjects(employeeId);
		if (employee != null) {
			viewEmployee(employee);
			viewProjects(employee.getProjects());
		} else {
			System.out.println("Employee is not assigned to any project");
		}
	}

	/**
	 *
	 * this method provides options for getting leave details from database.
	 *
	 */


	/**
	 *
	 * this method retrieves All Leaves in the database.
	 *
	 */
	public List<LeaveRecords> getAllLeaves() {
		List<LeaveRecords> leaves = LeaveServices.getLeaveRecords();
		return leaves;
	}

	/**
	 *
	 * this method retrieves all Leaves associated with the particular employee.
	 *
	 */
	void getLeavesOfEmployee() {
		final int MAX_LEAVES_ALLOWED = 10;
		String employeeId = getString("enter employee id");
		List<LeaveRecords> leaveRecords = LeaveServices.getLeaveRecord(employeeId.toUpperCase());
		if (leaveRecords.size() == 0) {
			System.out.println("No records for this employee ID in Leaves");
		} else {
			viewLeaveRecords(leaveRecords);
			int leaveCount = LeaveServices.getLeaveCount(employeeId);
			System.out.println("you have used "+ leaveCount+" leaves do far\n"
					+"you have "+(MAX_LEAVES_ALLOWED-leaveCount)+" leaves left");
		}
	}


	/**
	 *
	 * this method retrieves details of all projects present in the database.
	 *
	 */
	public List<Project> getAllProjects() {
		List<Project> projects = ProjectServices.getProjects();
		return projects;
	}

	/**
	 *
	 * this method retrieves details of Particular project.
	 *
	 */
	public Project getProjectByProjectId(int projectId) {
		Project project = ProjectServices.getProject(projectId);
		return project;
	}

	/**
	 *
	 * this method retrieves details of particular project with Employees assosiated with it.
	 *
	 */
	void getProjectEmployees() {
		int projectId = getInt("enter the project ID ");
		Project project = ProjectServices.getProjectEmployees(projectId);
		if(project != null) {
			viewProject(project);
			viewEmployees(project.getEmployees());
		} else {
			System.out.println("Employees are yet to be assigned to this project");
		}
	}

	/**
	 * updateDetails method takes user input for update field
	 * Based on that input , update method is callled to update that model.
	 */

	/**
	 * updateEmployee update fields of Employee.
	 * prints status if true , if not then reason is printed.
	 */

	public boolean updateEmployee(Employee employee) {
		boolean status = employeeServices.updateEmployee(employee);
		return status;
	}
	/**
	 * updateLeaveRecord leave type field only.
	 * prints status if true , if not then reason is printed.
	 */

	public boolean updateLeaveRecord(LeaveRecords leave) {
		boolean status = LeaveServices.updateLeaveRecord(leave);
		return status;
	}

	public LeaveRecords getLeaveByLeaveId(int leaveId) {
		return LeaveServices.getLeaveByLeaveId(leaveId);
	}

	/**
	 * updateProject() updates employeedId field only.
	 * prints status if true , if not then reason is printed.
	 */
	void updateProject() {
		String message = "";
		String employeeId = getString("Enter Employee Id");
		List<Project> projects = ProjectServices.getProjects();
		if (projects.size() > 0) {
			viewProjects(projects);
			for(Project project:projects) {
				int projectId = getInt("enter project Id");

				if (project.getProjectId() == projectId) {
					String newEmployeeId = getString("enter new Employee Id");
					Employee employee = employeeServices.getEmployee(newEmployeeId);
					if(employee != null) {
						project.setEmployeeId(employee.getEmployeeId());
						boolean status = ProjectServices.updateProject(project);
						if (status) {
							message = "updation completed";
							List<Project> updatedProject = new ArrayList<>();
							updatedProject.add(project);
							viewProjects(updatedProject);
							break;
						} else
							message = "updation failed due some reason! please try again";
					} else
						message = " new employee not registered";
				} else
					message = "invalid project id ";
			}
			System.out.println(message);
		} else {
			System.out.println("Employee Not Found! try with some other employee Id");
		}
	}

	public boolean updateProjectDetails(Project project) {
		boolean status = ProjectServices.updateProject(project);
		System.out.println(status);
		return status;
	}


	/**
	 * deleteEmployee() takes employeeId from user which is to be deleted.
	 * if employee id is valid, its object is passed to service layer for deletion.
	 */
	public boolean deleteEmployee(Employee employee) {
		System.out.println(employee);
		return employeeServices.deleteEmployee(employee);
	}

	public boolean deleteLeave(LeaveRecords leave) {
		System.out.println(leave);
		return LeaveServices.deleteLeaveRecord(leave);
	}

	public boolean deleteProject(Project project){
		return ProjectServices.deleteProject(project);
	}

//*************************************************************************************************************

	/**
	 * getValidName() take user input name and validates by calling utils.Validiator.
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid name.
	 */
	public String getValidName() {
		while (true) {
			String name = getString("Enter Name of an Employee");
			if (validiator.validiateName(name) == true)
				return name;
			else
				System.out.println("name entered is invalid");
		}
	}

	/**
	 * getValidBirthdate() take user input birthdate as String and validates by calling utils.Validiator.
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid bithdate .
	 */
	public String getValidBirthdate() {
		while (true) {
			String birthdate = getString(" enter birthdate in format YYYY-MM-DD : ");
			if (validiator.validiateBirthdate(birthdate))
				return birthdate;
			else{
				continue;
			}
		}
	}

	/**
	 * getValidDesignation() take user input Designation and validates by calling utils.Validiator.
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid Designation.
	 */
	public String getValidDesignation() {
		while (true) {
			String designation = getString(" enter designation  : ");

			if (validiator.validiateName(designation) == true)
				return designation;
			else
				System.out.println("name entered is invalid");
		}
	}

	/**
	 * getValidEmployeeType take user input Integer and validates by calling enum class EmployeeType.
	 * if valid then returns the value.
	 * @return : returns EmployeeType type value which is a valid enum.
	 */
	public EmployeeType getValidEmployeeType() {
		while(true) {
			try {
				System.out.println("enter Employee type: ");
				System.out.println(" 1.Trainer\n 2.Trainee ");
				int value = Integer.parseInt(scanner.nextLine());

				EmployeeType EType = EmployeeType.getEmployeeType(value);
				if ( EType != null)
					return EType;
				else
					System.out.println("enter valid option");
			} catch (Exception e) {
				System.out.println("enter integer type Value");

			}

		}
	}

	/**
	 * getValidGender() take user input Integer and validates by calling its enum class .
	 * if valid then returns the value.
	 * @return : returns Gender type value which is a valid enum.
	 */
	public Gender getValidGender() {
		while(true) {
			try {
				System.out.println("enter gender of enployee");
				System.out.println(" 1.MALE\n 2.FEMALE\n 3.OTHER");
				int value = Integer.parseInt(scanner.nextLine());
				Gender gender = Gender.getGender(value);
				if ( gender != null)
					return gender;
			} catch (Exception e) {
				System.out.println("enter integer type Value");
				continue;
			}

		}
	}

	/**
	 * getValidContact() take user input String and validates by calling validiator.
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid contact.
	 */
	public String getValidContact() {
		while (true) {
			System.out.print(" enter contact : ");
			String contact = scanner.nextLine();
			if (validiator.validiateContact(contact) == true)
				return contact;
			else
				System.out.println("contact entered is invalid");
			continue;
		}
	}

	/**
	 * getValidEmail() take user input String and validates by calling validiator.
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid Email.
	 */
	public String getValidEmail() {
		while (true) {
			System.out.print(" enter email : ");
			String email = scanner.nextLine();
			if (validiator.validiateEmail(email))
				return email;
			else
				System.out.println("email entered is invalid");
			continue;
		}
	}

	/**
	 * getValidProbationTime() take user input String and validates by calling validiator.
	 * if valid then returns the value.
	 * @return : returns String type value.
	 */
	public String getValidProbationTime() {
		while (true) {
			System.out.print(" enter probationTime  : ");
			String probationTime = scanner.nextLine();
			if (validiator.validiateName(probationTime) == true)
				return probationTime;
			else
				System.out.println("probationTime entered is invalid,");
			continue;
		}
	}

	/**
	 * getValidLeaveType() take user input Integer and validates by calling its enum class .
	 * if valid then returns the value.
	 * @return : returns LeaveType type value which is a valid enum.
	 */
	public LeaveType getValidLeaveType() {
		while(true) {
			try {
				System.out.println("enter gender of enployee");
				System.out.println(" 1.Casual\n 2.Sick\n 3.Medical");
				int value = Integer.parseInt(scanner.nextLine());
				LeaveType leaveType = LeaveType.getLeaveType(value);
				if ( leaveType != null)
					return leaveType;
			} catch (Exception e) {
				System.out.println("enter integer type Value");
				continue;
			}

		}
	}
	/**
	 * getValidDate() take user input String and validates by calling utils.Validiator .
	 * if valid then returns the value.
	 * @return : returns String type value which is a valid Date.
	 */
	public String getValidDate(String message) {
		while (true) {
			String dateStr = getString(message);
			if (validiator.isValidDate(dateStr)) {
				return dateStr;
			} else {
				System.out.println("enter valid date");
				continue;
			}
		}
	}


//**********************************************************************************************

	/* Trainee Menu */
	public void operationsMenu() {
		System.out.println("***************OPERATIONS*************** \n"
				+"1.ADD-------------------------------- \n"
				+"2.READ ------------------------------ \n"
				+"3.UPDATE ---------------------------- \n"
				+"4.REMOVE ---------------------------- \n"
				+"5.EXIT--------- --------------------- \n"
				+"************************************* \n"
				+"Please enter your input : ");
	}

	/* Read Menu */
	public void readMenu() {
		System.out.println("***********************************\n"
				+"1.EMPLOYEE \n"
				+"2.LEAVES \n"
				+"3.PROJECTS\n"
				+"4.PREVIOUS MENU\n"
				+"************************************* \n"
				+"Please enter your input : ");
	}

	/* read Employee Menu */
	public void readEmployeeMenu() {
		System.out.println("***********************************\n"
				+"1.ALL EMPLOYEE DETAILS \n"
				+"2.INDIVISUAL EMPLOYEE DETAILS \n"
				+"3.EMPLOYEE LEAVES \n"
				+"4.EMPLOYEE PROJECTS \n"
				+"5.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");
	}

	/* read Leaves Menu */
	public void readLeavesMenu() {

		System.out.println("***********************************\n"
				+"1.ALL lEAVES \n"
				+"2.LEAVE EMPLOYEE \n"
				+"3.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");

	}

	/* read Projects Menu */
	public void readProjectsMenu() {
		System.out.println("***********************************\n"
				+"1.ALL PROJECTS \n"
				+"2.INDIVISUAL PROJECT \n"
				+"3.PROJECT EMPLOYEES \n"
				+"4.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");

	}

	/* insert Menu */
	public void insertMenu() {
		System.out.println("************************************* \n"
				+"1.INSERT EMPLOYEE DETAILS \n"
				+"2.INSERT LEAVE RECORD \n"
				+"3.INSERT PROJECT DETAILS \n"
				+"4.ASSIGN PROJECT TO EMPLOYEE \n"
				+"5.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");
	}

	/* update menu */
	public void updateMenu() {
		System.out.println("***********************************\n"
				+"1.Employees \n"
				+"2.LeaveRecords \n"
				+"3.Projects\n"
				+"4.PREVIOUS MENU \n"
				+"************************************* \n"
				+"Please enter your input : ");
	}

	/* update field options */
	public void updateEmployeeMenu() {
		System.out.println("*************************************\n"
				+"\n1.EMPLOYEE TYPE"
				+"\n2.UPDATE NAME"
				+"\n3.UPDATE DIRTHDATE"
				+"\n4.UPDATE DESIGNATION"
				+"\n5.UPDATE CONTACT"
				+"\n6.UPDATE EMAIL"
				+"\n7.PROBATION TIME"
				+"\n8.SAVE UPDATES AND EXIT "
				+"\n8.EXIT WITHOUT SAVING "
				+"\n*************************************"
				+"\nENTER ANY OPTION");

	}

	/**
	 * this method takes String type input from user.
	 * @param : message, to display while taking input from user.
	 * @return : String value.
	 */
	public String getString(String message) {
		Scanner scannerString = new Scanner(System.in);
		System.out.println(message);
		String str = scannerString.nextLine();
		return str;
	}

	/**
	 * this method takes String type input from user.
	 * @return : String value.
	 */
	public String getString() {
		Scanner scannerString = new Scanner(System.in);
		String str = scannerString.nextLine();
		return str;
	}

	/**
	 * this method takes integer type input from user.
	 * @param : message, to display while taking input from user.
	 * @return : valid integer value.
	 */
	public int getInt(String message) {
		while(true) {
			try {
				Scanner scannerInt = new Scanner(System.in);
				System.out.println(message);
				int intData = scannerInt.nextInt();
				return intData;
			} catch (Exception e) {
				System.out.println("enter integer value");
			}
		}
	}

	public void viewEmployees(List<Employee> employees) {
		System.out.print("+----------+---------+-------------------------+--------+");
		System.out.print("------------+-----+----------------+");
		System.out.print("--------------+----------------------+--------------+\n");
		System.out.printf("| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER");
		System.out.printf(" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION");
		System.out.printf(" %-12s | %-20s |","CONTACT","EMAIL");
		System.out.printf(" %-12s | \n","PROBATION");
		System.out.print("+----------+---------+-------------------------+--------+");
		System.out.print("------------+-----+----------------+");
		System.out.print("--------------+----------------------+--------------+\n");
		for(Employee employee:employees) {
			System.out.printf("| %-8s ", employee.getEmployeeId());
			System.out.printf("| %-7s ", employee.getEmployeeType());
			System.out.printf("| %-23s ", employee.getName());
			System.out.printf("| %-6s ", employee.getGender());
			System.out.printf("| %-10s ", employee.getBirthdate());
			System.out.printf("| %-3s ", employee.getAge(employee.getBirthdate()));
			System.out.printf("| %-14s ", employee.getDesignation());
			System.out.printf("| %-12s ", employee.getContact());
			System.out.printf("| %-20s ", employee.getEmail());
			System.out.printf("| %-12s |", employee.getProbationTime());
			System.out.printf("\n");

		}
		System.out.print("+----------+---------+-------------------------+--------+");
		System.out.print("------------+-----+----------------+");
		System.out.print("--------------+----------------------+--------------+\n");
	}

	public void viewEmployee(Employee employee) {
		System.out.printf("+----------+---------+-------------------------+--------+"
				+"------------+-----+----------------+"
				+"--------------+----------------------+--------------+\n"
				+"| %-8s | %-7s | %-23s | %-6s |","ID","TYPE","NAME","GENDER"
				+" %-10s | %-3s | %-14s |","DOB","AGE","DESIGNATION"
				+" %-12s | %-20s |","CONTACT","EMAIL"
				+" %-12s | \n","PROBATION"
				+"+----------+---------+-------------------------+--------+"
				+"------------+-----+----------------+"
				+"--------------+----------------------+--------------+\n"

				+"| %-8s ", employee.getEmployeeId()
				+"| %-7s ", employee.getEmployeeType()
				+"| %-23s ", employee.getName()
				+"| %-6s ", employee.getGender()
				+"| %-10s ", employee.getBirthdate()
				+"| %-3s ", employee.getAge(employee.getBirthdate())
				+"| %-14s ", employee.getDesignation()
				+"| %-12s ", employee.getContact()
				+"| %-20s ", employee.getEmail()
				+"| %-12s |", employee.getProbationTime()
				+"\n"


				+"+----------+---------+-------------------------+--------+"
				+"------------+-----+----------------+"
				+"--------------+----------------------+--------------+\n");
	}



	public void viewLeaveRecords(List<LeaveRecords> leaveRecords) {
		System.out.print("+------+----------+------------+------------+--------------+\n");
		System.out.printf("| %-4s | %-8s | %-10s | %-10s | %-12s |\n","L_id","e_id","F_date","To_date","type");
		System.out.print("+------+----------+------------+------------+--------------+\n");
		for(LeaveRecords record: leaveRecords) {
			System.out.printf("| %-4s ", record.getLeaveId());
			System.out.printf("| %-8s ", record.getEmployeeId());
			System.out.printf("| %-10s ", record.getFromDate());
			System.out.printf("| %-10s ", record.getToDate());
			System.out.printf("| %-12s |", record.getLeaveType());
			System.out.printf("\n");
		}
		System.out.print("+------+----------+------------+------------+--------------+\n");
	}


	public void viewProjects(List<Project> projects) {
		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
		System.out.printf("| %-6s | %-20s | %-12s | %-26s | %-15s | %-15s |\n","p_id","name","start_date","description","manager","client");
		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
		for(Project record: projects) {
			System.out.printf("| %-6s ", record.getProjectId());
			System.out.printf("| %-20s ", record.getName());
			System.out.printf("| %-12s ", record.getStartDate());
			System.out.printf("| %-26s ", record.getDescription());
			System.out.printf("| %-15s ", record.getProjectManager());
			System.out.printf("| %-15s |", record.getClientName());
			System.out.printf("\n");
		}
		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
	}


	public void viewProject(Project project) {
		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
		System.out.printf("| %-6s | %-20s | %-12s | %-26s | %-15s | %-15s |\n","p_id","name","start_date","description","manager","client");
		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");

		System.out.printf("| %-6s ", project.getProjectId());
		System.out.printf("| %-20s ", project.getName());
		System.out.printf("| %-12s ", project.getStartDate());
		System.out.printf("| %-26s ", project.getDescription());
		System.out.printf("| %-15s ", project.getProjectManager());
		System.out.printf("| %-15s |", project.getClientName());
		System.out.printf("\n");

		System.out.print("+--------+----------------------+--------------+----------------------------+-----------------+-----------------+\n");
	}
}