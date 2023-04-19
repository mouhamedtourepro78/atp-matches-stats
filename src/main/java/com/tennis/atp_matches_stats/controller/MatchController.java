package com.tennis.atp_matches_stats.controller;

import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.model.MatchDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import com.tennis.atp_matches_stats.service.MatchService;
import com.tennis.atp_matches_stats.util.CustomCollectors;
import com.tennis.atp_matches_stats.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/matchs")
public class MatchController {

    private final MatchService matchService;
    private final TournamentRepository tournamentRepository;
    private final PlayerRepository playerRepository;

    public MatchController(final MatchService matchService,
            final TournamentRepository tournamentRepository,
            final PlayerRepository playerRepository) {
        this.matchService = matchService;
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("tournamentValues", tournamentRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Tournament::getId, Tournament::getName)));
        model.addAttribute("winnerValues", playerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Player::getId, Player::getSeed)));
        model.addAttribute("loserValues", playerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Player::getId, Player::getSeed)));
    }

    @GetMapping
    public String list(@RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final SimplePage<MatchDTO> matchs = matchService.findAll(filter, pageable);
        model.addAttribute("matchs", matchs);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(matchs));
        return "match/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("match") final MatchDTO matchDTO) {
        return "match/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("match") @Valid final MatchDTO matchDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "match/add";
        }
        matchService.create(matchDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("match.create.success"));
        return "redirect:/matchs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("match", matchService.get(id));
        return "match/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("match") @Valid final MatchDTO matchDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "match/edit";
        }
        matchService.update(id, matchDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("match.update.success"));
        return "redirect:/matchs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        matchService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("match.delete.success"));
        return "redirect:/matchs";
    }

}
