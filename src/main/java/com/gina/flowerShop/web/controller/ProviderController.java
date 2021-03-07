package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.repository.RoleRepository;
import com.gina.flowerShop.service.ProviderService;
import com.gina.flowerShop.web.dto.ProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ProviderController {
    private ProviderService providerService;

    private RoleRepository roleRepository;
    @Autowired
    public ProviderController(ProviderService providerService, RoleRepository roleRepository) {
        this.providerService = providerService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin/registration")
    public String showRegistrationFormProvider(Model model) {
        ProviderDto provider = new ProviderDto();
        model.addAttribute("roleList", roleRepository.findFirst2ByOrderByIdRole());
        model.addAttribute("provider", provider);
        return "admin-registration";
    }

    @PostMapping("/admin/registration")
    public String registerProviderAccount(@Valid @ModelAttribute("provider") ProviderDto providerDto, BindingResult result,
                                          RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()){

            return "admin-registration";

        }
        if(providerService.countByUserName(providerDto.getUsername()) == 1l){
            redirectAttributes.addFlashAttribute("message", providerDto.getUsername()+" already have account");
           
            return "redirect:/admin/providers/list";
        }

       /* Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role("ROLE_ADMIN"));
        roleSet.add(new Role("ROLE_STAFF"));

        providerDto.setRoles(roleSet);
        Set<ProviderDto> userSet = new HashSet<>();
        userSet.add(providerDto);*/

        providerService.save(providerDto);
        model.addAttribute("provider", providerDto);
        return "redirect:/admin/registration?success";
    }

    @GetMapping("/admin/providers/list")
    public String displayProvidersList(Model model) {
        model.addAttribute("providers", providerService.findAll());

        return "admin-providers-list";
    }

    @GetMapping("/admin/delete/provider/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        ProviderDto providerDto = providerService.findById(id).get();
        redirectAttributes.addFlashAttribute("message","Utilizatorul "+providerDto.getFullName()+ " a fost sters!");
        providerService.delete(id);

        return "redirect:/admin/providers/list";
    }

    @GetMapping("/admin/update/form/{id}")
    public String displayUpdateFormProvider(@PathVariable("id") Long id, Model model){
        final Optional<ProviderDto> optionalProviderDto = providerService.findById(id);
        if (optionalProviderDto.isPresent()) {
            final ProviderDto providerDto = optionalProviderDto.get();
            List<Role> roleList = roleRepository.findFirst2ByOrderByIdRole();
            model.addAttribute("roleList", roleList);
            model.addAttribute("providers", providerService.findAll());
            model.addAttribute("provider", providerDto);
        } else {
            new IllegalArgumentException("Invalid user Id:" + id);
        }

        return "admin-update-provider";
    }

    @PostMapping("/admin/update/provider/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("provider") ProviderDto providerDto,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-update-provider";
        }

        providerService.save(providerDto);
        redirectAttributes.addFlashAttribute("message", "Utilizatorul "+providerDto.getFullName()+" a fost editat cu succes");

        return "redirect:/admin/providers/list";
    }
}
