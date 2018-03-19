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
import java.sql.Statement;
import java.util.ArrayList;
import model.OrderDetail;

/**
 *
 * @author Sandman
 */
public class OrderDetailsController {
    public static boolean addOrderDetails(ArrayList<OrderDetail> orderDetailList)throws ClassNotFoundException, SQLException{
		for(OrderDetail ob : orderDetailList){
			boolean isUpdated=OrderDetailsController.addOrderDetails(ob);
			if(!isUpdated){
				return false;
			}
		}
		return true;
	}
	public static boolean addOrderDetails(OrderDetail orderDetail)throws ClassNotFoundException, SQLException{
		Connection conn=DBConnection.getDBConnection().getConnection();
		String SQL="Insert into OrderDetail Values(?,?,?,?)";
		PreparedStatement stm=conn.prepareStatement(SQL);
		stm.setObject(1,orderDetail.getOrderId());
		stm.setObject(2,orderDetail.getItemCode());
		stm.setObject(3,orderDetail.getQty());
		stm.setObject(4,orderDetail.getUnitPrice());
		return stm.executeUpdate()>0;
	}
    public static ArrayList<OrderDetail> getAllOrderDetail(String orderId) throws ClassNotFoundException, SQLException{
        Connection conn=DBConnection.getDBConnection().getConnection();
        String query="SELECT * FROM orderdetail WHERE orderId='"+orderId+"'";
        Statement stm=conn.createStatement();
        ResultSet rst=stm.executeQuery(query);
        ArrayList<OrderDetail> orderDetailList=new ArrayList<>();
        while(rst.next()){
            OrderDetail od=new OrderDetail(rst.getString("orderId"), rst.getString("ItemCode"), rst.getInt("qty"),rst.getDouble("unitPrice"));
            orderDetailList.add(od);
        }
        return orderDetailList;
                
    }
}
