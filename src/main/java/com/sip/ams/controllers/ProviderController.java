package com.sip.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sip.ams.entities.Article;
//import org.springframework.web.bind.annotation.ResponseBody;
import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/provider/")
public class ProviderController {

	public static String providerDirectory = System.getProperty("user.dir")
			+ "/src/main/resources/static/images/providers";

	private final ProviderRepository providerRepository;

	@Autowired
	public ProviderController(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listProviders(Model model, @RequestParam(name="page",defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="3") int size,
			@RequestParam(name="keyword",defaultValue="")String mc) {

		// model.addAttribute("providers", providerRepository.findAll());
		// si la base de donnee est vide, dans ce cas le retour de cette instruction est
		// le vide
		// or on a besoin de null pour afficher No Providers yet! donc il faut utiliser
		// une list

		//List<Provider> lp = (List<Provider>) providerRepository.findAll();
		
		Page<Provider> lp = providerRepository.findByNameContains(mc, PageRequest.of(page, size));
		
		//if (lp.size() == 0)
		if (lp.getSize() == 0)
			lp = null;
		model.addAttribute("providers", lp.getContent());// l'envoi de la liste 
		model.addAttribute("pages",new int[lp.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",mc);
		

		// return "Nombre de fournisseur = " + lp.size();
		return "provider/listProvider";
	}

	@GetMapping("add")
	public String showAddProviderForm(Model model) {
		Provider provider = new Provider();
		model.addAttribute("provider", provider);
		return "provider/addProvider";
	}

	@PostMapping("add")
	public String addProvider(@Valid Provider provider, BindingResult result,
			@RequestParam("files") MultipartFile[] files) {
		if (result.hasErrors()) {
			return "provider/addProvider";
		}
		// upload image
		StringBuilder fileName = new StringBuilder();
		MultipartFile file = files[0];
		Path fileNameAndPath = Paths.get(providerDirectory, file.getOriginalFilename());
		fileName.append(file.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		provider.setLogo(fileName.toString());
		// fin upload image
		providerRepository.save(provider);
		return "redirect:list";
	}

	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") long id) {

		Provider provider = providerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalidprovider Id:" + id));

		System.out.println("suite du programme...");

		providerRepository.delete(provider);

		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
		Provider provider = providerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalidprovider Id:" + id));

		model.addAttribute("provider", provider);

		return "provider/updateProvider";
	}

	/*
	 * @PostMapping("update") public String updateProvider(@Valid Provider provider,
	 * BindingResult result, @RequestParam("files") MultipartFile[] files) {
	 * 
	 * if (result.hasErrors()) { return "redirect:list"; }
	 * 
	 * // upload image StringBuilder fileName = new StringBuilder(); MultipartFile
	 * file = files[0]; Path fileNameAndPath = Paths.get(providerDirectory,
	 * file.getOriginalFilename()); fileName.append(file.getOriginalFilename()); try
	 * { Files.write(fileNameAndPath, file.getBytes()); } catch (IOException e) {
	 * e.printStackTrace(); } provider.setLogo(fileName.toString()); // fin upload
	 * image
	 * 
	 * providerRepository.save(provider); return "redirect:list";
	 * 
	 * }
	 */
	
	
	@PostMapping("update/{id}")
	public String updateProvider(@PathVariable("id") long id, @Valid Provider provider, BindingResult result,  Model model, @RequestParam("files") MultipartFile[] files) {

		if (result.hasErrors()) {
			return "redirect:../list";
		}
		
		// upload image
		StringBuilder fileName = new StringBuilder();
		MultipartFile file = files[0];
		Path fileNameAndPath = Paths.get(providerDirectory, file.getOriginalFilename());
		fileName.append(file.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		provider.setLogo(fileName.toString());
		// fin upload image

		providerRepository.save(provider);
		return "redirect:../list";

	}
	
	 @GetMapping("show/{id}")  
	 public String showProvider(@PathVariable("id") long id, Model model) {   
		 Provider provider = providerRepository.findById(id)
				    .orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));   
		 List<Article> articles = providerRepository.findArticlesByProvider(id);   
		 for (Article a : articles)    
		System.out.println("Article = " + a.getLabel());      
		 model.addAttribute("articles", articles);   
		 model.addAttribute("provider", provider);   
	    return "provider/showProvider";  }
	 
}