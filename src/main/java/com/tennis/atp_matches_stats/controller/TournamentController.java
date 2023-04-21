package com.tennis.atp_matches_stats.controller;

import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.TournamentDTO;
import com.tennis.atp_matches_stats.service.TournamentService;
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
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(final TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final SimplePage<TournamentDTO> tournaments = tournamentService.findAll(filter, pageable);
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(tournaments));
        return "tournament/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("tournament") final TournamentDTO tournamentDTO) {
        return "tournament/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("tournament") @Valid final TournamentDTO tournamentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tournament/add";
        }
        tournamentService.create(tournamentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("tournament.create.success"));
        return "redirect:/tournaments";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("tournament", tournamentService.get(id));
        return "tournament/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("tournament") @Valid final TournamentDTO tournamentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tournament/edit";
        }
        tournamentService.update(id, tournamentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("tournament.update.success"));
        return "redirect:/tournaments";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = tournamentService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            tournamentService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("tournament.delete.success"));
        }
        return "redirect:/tournaments";
    }

}
