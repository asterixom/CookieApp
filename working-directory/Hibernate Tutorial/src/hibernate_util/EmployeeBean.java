package hibernate_util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class EmployeeBean {
      
       @Id
       @Column(name="EmployeeID")
       private int empId;
      
       @Column(name="FirstName")
       private String fName;
      
       @Column(name="LastName")
       private String lName;
       
       public EmployeeBean(String fname, String lname, int empid) {
		// TODO Auto-generated constructor stub
    	   this.fName = fname;
    	   this.lName = lname;
    	   this.empId = empid;
	}

       public int getEmpId() {
              return empId;
       }

       public void setEmpId(int empId) {
              this.empId = empId;
       }

       public String getfName() {
              return fName;
       }

       public void setfName(String fName) {
              this.fName = fName;
       }

       public String getlName() {
              return lName;
       }

       public void setlName(String lName) {
              this.lName = lName;
       }
       
     
}