package extend_and_interface;

/**
 * Created by Better on 2017/7/6.
 */
public interface ChangeSalary {
    double DEFAULTSALARY = 5000;

    public void addSalary(double salary);

    public void reduceSalary(double salary);

    public void raiseSalary(double percent);

}
