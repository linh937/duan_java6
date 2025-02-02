package com.poly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.bean.Users;
import com.poly.service.CookieService;
import com.poly.service_bean.OrderDetailService;
import com.poly.service_bean.OrderService;



@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailSer;
	@Autowired
	CookieService cookieService;
	

	
	@RequestMapping("/order/checkout")
	public String CheckOut() {
		return "order/checkout";
	}

	@RequestMapping("/order/list")
	public String getList(Model m, HttpServletRequest request) {
	    // Lấy thông tin người dùng từ Cookie
	    String username = cookieService.getValue(request, "username");

	    if (username != null) {
	        // Giả sử orderService có phương thức findByUsername để tìm đơn hàng theo tên người dùng
	        m.addAttribute("orders", orderService.findByUsername(username));
	        return "home/order";
	    } else {
	        // Nếu không tìm thấy thông tin người dùng trong Cookie, chuyển hướng đến trang đăng nhập
	        return "redirect:/login";
	    }
	}


	@RequestMapping("/order/detail/{id}")
	public String getView(Model m, @PathVariable("id") Integer id) {
		m.addAttribute("order", orderService.findByID(id));
		m.addAttribute("detailOrder", orderDetailSer.findAllOrderDetail(id));
		return "home/homeDetail";
	}

}
