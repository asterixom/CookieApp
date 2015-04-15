package hibernate_util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class EmployeeDAO {
       //Temporary method...later will be removed once call is made from service class.
       public static void main(String[] args) {

              EmployeeDAO dao = new EmployeeDAO();
              EmployeeBean emp = dao.getEmployee(1);
              if(emp!=null){
                     System.out.println(emp.getfName()+" "+emp.getlName());
              }
              
              EmployeeBean employee = new EmployeeBean();
              employee.setEmpId(600);
              employee.setfName("Max");
              employee.setlName("Peter");
              
              dao.saveEmployee(employee);
             
             
       }
      
       /**
        * Will get the employee id and will return from northwind.employees table
        * @param empId
        * @return
        */
       
       
       
       public EmployeeBean getEmployee(int empId){
              Session sess = null;
              try{
                     sess = HibernateUtil.getSession();
                     return (EmployeeBean) sess.get(EmployeeBean.class, empId);
              }
              catch(HibernateException e){
                     e.printStackTrace();//Later remove this by appropriate logger statement or throw custom exception
              }
              return null;
       }
       
       public void saveEmployee(EmployeeBean emp){
    	   Session sess = null;
    	   try {
    		   sess = HibernateUtil.getSession();
    		   sess.beginTransaction();
    		   sess.save(emp);
    		   sess.getTransaction().commit();
			
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       }
       
      
       
    	  
}
