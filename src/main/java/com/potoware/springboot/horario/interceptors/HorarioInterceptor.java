package com.potoware.springboot.horario.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class HorarioInterceptor implements HandlerInterceptor {
	@Value("${config.horario.apertura}")
	private Integer apertura;
	@Value("${config.horario.cierre}")
	private Integer cierre;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		int hora= calendar.get(Calendar.HOUR_OF_DAY);
		
		if(hora >= apertura && hora<cierre) {
			StringBuilder mensaje= new StringBuilder("Bienvenido al horario de atencion al cliente atendemos de ");
			mensaje.append(apertura)
			.append(" a ")
			.append(cierre);
			request.setAttribute("mensaje", mensaje.toString());
			return true;
		}
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String mensaje = (String) request.getAttribute("mensaje");
		modelAndView.addObject("mensaje",mensaje);
	}

	
}