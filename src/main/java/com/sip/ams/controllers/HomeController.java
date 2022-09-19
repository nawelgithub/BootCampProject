package com.sip.ams.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.ams.entities.Candidat;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	
	

	@RequestMapping("/index")
	// @ResponseBody
	public String home() {
		// return "bienvenue au BootCamp";
		return "home/home";
	}

	
	
	
	@RequestMapping("/candidats")
	public String listCandidats(Model m) {
		Candidat c1 = new Candidat(1,"mariem","mariem@gmail.com","1111111111");
		Candidat c2 = new Candidat(1,"hannen","hannen@gmail.com","22222222222");
		Candidat c3 = new Candidat(1,"wiem","wiem@gmail.com","33333333333");
		
		Candidat tab[] = new Candidat[3];
		tab[0]=c1;
		tab[1]=c2;
		tab[2]=c3;
		
		m.addAttribute("tabview",tab);
		
		return "home/candidats";
	}
}
