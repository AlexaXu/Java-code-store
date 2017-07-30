package extend_and_interface;

/**
 * Created by Better on 2017/7/4.
 */
public class Manager extends Employee implements ChangeSalary{

    private double bonus = 0;

    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    public Manager() {
    }

    public Manager(double salary, double bonus) {
        super(salary);
        this.bonus = bonus;
    }

    @Override
    public String getName() {
        return "Manager " + super.getName();
    }

    public double getBonus(){
        return bonus;
    }

    public void setBonus(double bonus){
        this.bonus = bonus;
    }

    @Override
    public double getSalary(){
        return bonus + super.getSalary();
    }

    @Override
    public String getDescription() {
        return getName() + " with salary of " + getSalary();
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
