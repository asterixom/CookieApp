package hibernate_util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeDAO {
	// Temporary method...later will be removed once call is made from service
	// class.
	public static void main(String[] args) {
              EmployeeDAO dao = new EmployeeDAO();
              EmployeeBean emp = dao.getEmployee(1);
              if(emp!=null){
                     System.out.println(emp.getfName()+" "+emp.getlName());
              }
              
            
            	  
            	  EmployeeBean test = new EmployeeBean("Moritz", "Gabriel", 600);
            	  Session sess = null;
            	  

           

             
       }

	/**
	 * Will get the employee id and will return from northwind.employees table
	 * 
	 * @param empId
	 * @return
	 */
	public EmployeeBean getEmployee(int empId) {
		Session sess = null;
		try {
			sess = HibernateUtil.getSession();
			return (EmployeeBean) sess.get(EmployeeBean.class, empId);
		} catch (HibernateException e) {
			e.printStackTrace();// Later remove this by appropriate logger
								// statement or throw custom exception
		}
		return null;
	}

	

	
}
