public class HunterXHunter{
    private String firstname, lastname, address;
    private int id, salary;

    public HunterXHunter(int id, String firstname, String lastname, int salary, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.id = id;
        this.salary = salary;
    }


    public String getFname() {
        return this.firstname;
    }

    public void setFname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return getFirstname() + " " + getLastname();
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getAnnualSalary() {
        return this.salary*12;
    }

    public int raiseSalary(int percent) {
        //e.g. if salary 100$ and percent is 20, it will increase to 120$
        setSalary(salary+(salary*percent/100));
        return getSalary();
    }

    public String toString() {
        return "Employee[id=" + getId() + ", name=" + getName() + ", salary=" + getSalary() + ", address=" + getAddress() + "]";
    }
}