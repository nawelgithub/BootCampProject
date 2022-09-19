package com.sip.ams.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sip.ams.entities.Candidat;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	static ArrayList<Candidat> lc;
	static {
		lc = new ArrayList<>();
		Candidat c1 = new Candidat(0, "mariem", "mariem@gmail.com", "1111111111");
		Candidat c2 = new Candidat(1, "hannen", "hannen@gmail.com", "22222222222");
		Candidat c3 = new Candidat(2, "wiem", "wiem@gmail.com", "33333333333");
		Candidat c4 = new Candidat(3, "ahmed", "ahmed@gmail.com", "33333333333");
		lc.add(c1);
		lc.add(c2);
		lc.add(c3);
		lc.add(c4);
	}

	@RequestMapping("/index")
	// @ResponseBody
	public String home() {
		// return "bienvenue au BootCamp";
		return "home/home";
	}

	@RequestMapping("/candidats")
	public String listCandidats(Model m) {

		// utiliser pour l'affichage
		// Candidat c1 = new Candidat(1, "mariem", "mariem@gmail.com", "1111111111");
		// Candidat c2 = new Candidat(2, "hannen", "hannen@gmail.com", "22222222222");
		// Candidat c3 = new Candidat(3, "wiem", "wiem@gmail.com", "33333333333");

		// Candidat tab[] = new Candidat[3];
		// tab[0] = c1;
		// tab[1] = c2;
		// tab[2] = c3;

		// m.addAttribute("tabview", tab);

		m.addAttribute("tabview", lc);
		return "home/candidats";
	}

	@GetMapping("/add")
	// @ResponseBody
	public String addCandidate() {
		return "home/add";
	}

	@PostMapping("/add")
	// @ResponseBody
	public String saveCandidate(@RequestParam("id") int id, @RequestParam("nom") String nom,
			@RequestParam("email") String email, @RequestParam("tel") String tel) {

		Candidat temp = new Candidat(id, nom, email, tel);
		lc.add(temp);
		// System.out.println(id+" "+nom+" "+" "+email+" "+tel);
		// return "info : "+id+" "+nom+" "+" "+email+" "+tel;
		return "redirect:candidats";
	}

	@GetMapping("show/{idC}")
	@ResponseBody
	public String show(@PathVariable("idC") int id) {

		// return"home/show";
		return "id: " + id;
	}

	@GetMapping("/delete/{idC}")
	public String delete(@PathVariable("idC") int id) {
		lc.remove(id);
		return "redirect:../candidats";
	}

}
