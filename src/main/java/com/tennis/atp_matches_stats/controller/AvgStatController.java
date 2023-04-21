package com.tennis.atp_matches_stats.controller;

import com.tennis.atp_matches_stats.model.AvgStatDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.service.AvgStatService;
import com.tennis.atp_matches_stats.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/avgStats")
public class AvgStatController {

    private final AvgStatService avgStatService;

    public AvgStatController(final AvgStatService avgStatService) {
        this.avgStatService = avgStatService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final SimplePage<AvgStatDTO> avgStats = avgStatService.findAll(filter, pageable);
        model.addAttribute("avgStats", avgStats);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(avgStats));
        return "avgStat/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("avgStat") final AvgStatDTO avgStatDTO) {
        return "avgStat/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("avgStat") @Valid final AvgStatDTO avgStatDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "avgStat/add";
        }
        avgStatService.create(avgStatDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("avgStat.create.success"));
        return "redirect:/avgStats";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("avgStat", avgStatService.get(id));
        return "avgStat/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("avgStat") @Valid final AvgStatDTO avgStatDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "avgStat/edit";
        }
        avgStatService.update(id, avgStatDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("avgStat.update.success"));
        return "redirect:/avgStats";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = avgStatService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            avgStatService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("avgStat.delete.success"));
        }
        return "redirect:/avgStats";
    }

}
