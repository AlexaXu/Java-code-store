package extend_and_interface;

abstract public class Employee {
	private String name;
	protected double salary;
	private static int idNext = 1;
	private int id = idNext;

	public Employee(String name, double salary){
		this.name = name;
		this.salary = salary;
		idNext++;
	}

	public Employee(double salary){
		this("Employee NO." + idNext, salary);
	}

	public Employee(){
		
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getSalary(){
		return salary;
	}

	public void setSalary(double salary){
		this.salary = salary;
	}

	public int getId(){
		return id;
	}

	public abstract String getDescription();

}
