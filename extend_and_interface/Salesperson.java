package extend_and_interface;

/**
 * Created by Better on 2017/7/4.
 */

public class Salesperson extends Employee implements ChangeSalary{
    private double unit = 0;
    private double unitPercent = 0.1;

    public Salesperson(String name, double salary, double unit) {
        super(name, salary);
        this.unit = unit;
    }

    public Salesperson(double salary, double unit){
        super(salary);
        this.unit = unit;
    }

    public Salesperson(){

    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public double getUnit(){
        return unit;
    }

    public void setUnitPercent(double unitPercent) {
        this.unitPercent = unitPercent;
    }

    @Override
    public double getSalary(){
        return super.getSalary() + unitPercent * unit;
    }

    @Override
    public String getDescription() {
        return "Salesperson " + getName() + " with salary of " + getSalary();
    }

    @Override
    public void addSalary(double salary) {
        this.salary += salary;
    }

    @Override
    public void reduceSalary(double salary){
        this.salary -= salary;
    }

    @Override
    public void raiseSalary(double percent) {
        salary = salary * (1 + percent / 100);
    }
}
