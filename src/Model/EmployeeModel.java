/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author java-dev
 */
public class EmployeeModel {

    public EmployeeModel() {
    }
    
    int employeeId;
    String employeeNamePrefix, employeeFirstname, employeeLastname, employeePosition, employeeIdCardNumber, employeeAddress, employeeTel;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNamePrefix() {
        return employeeNamePrefix;
    }

    public void setEmployeeNamePrefix(String employeePrefix) {
        this.employeeNamePrefix = employeePrefix;
    }

    public String getEmployeeFirstname() {
        return employeeFirstname;
    }

    public void setEmployeeFirstname(String employeeFirstname) {
        this.employeeFirstname = employeeFirstname;
    }

    public String getEmployeeLastname() {
        return employeeLastname;
    }

    public void setEmployeeLastname(String employeeLastname) {
        this.employeeLastname = employeeLastname;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getEmployeeIdCardNumber() {
        return employeeIdCardNumber;
    }

    public void setEmployeeIdCardNumber(String employeeIdCardNumber) {
        this.employeeIdCardNumber = employeeIdCardNumber;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeTel() {
        return employeeTel;
    }

    public void setEmployeeTel(String employeeTel) {
        this.employeeTel = employeeTel;
    }
    
}
