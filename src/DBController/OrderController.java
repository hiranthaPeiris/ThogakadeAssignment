/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBController;

import DBConnecction.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Orders;

/**
 *
 * @author Sandman
 */
public class OrderController {
    public static boolean addNewOrder(Orders orders)throws ClassNotFoundException, SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        String SQL="Insert into Orders Values(?,?,?)";
        PreparedStatement stm=conn.prepareStatement(SQL);
        stm.setObject(1,orders.getId());
        stm.setObject(2,orders.getDate());
        stm.setObject(3,orders.getCustomerId());
        try{
            conn.setAutoCommit(false);
            if(stm.executeUpdate()>0){
                if(OrderDetailsController.addOrderDetails(orders.getOrderDetailList())){
                    if(ItemController.updateStock(orders.getOrderDetailList())){
                        conn.commit();
                        return true;
		}
            }
	}
        conn.rollback();
	return false;
        }finally{
            conn.setAutoCommit(true);
        }
        
    }
    
    public static ArrayList<Orders> getAllOrders(String custId) throws ClassNotFoundException, SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        String query="SELECT * FROM orders WHERE customerId=?";
        PreparedStatement stm=conn.prepareStatement(query);
        stm.setObject(1, custId);
        ResultSet rst=stm.executeQuery();
        ArrayList<Orders> orderList=new ArrayList<>();
        while(rst.next()){
            Orders od=new Orders(rst.getString("id"), rst.getString("date"), rst.getString("customerId"));
            orderList.add(od);
        }
        return orderList;
        
    }
        
}
