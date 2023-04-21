package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.AvgStat;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Stat;
import com.tennis.atp_matches_stats.model.AvgStatDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.AvgStatRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import com.tennis.atp_matches_stats.util.WebUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AvgStatServiceImpl implements AvgStatService {

    private final AvgStatRepository avgStatRepository;
    private final PlayerRepository playerRepository;

    public AvgStatServiceImpl(final AvgStatRepository avgStatRepository,
            final PlayerRepository playerRepository) {
        this.avgStatRepository = avgStatRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public SimplePage<AvgStatDTO> findAll(final String filter, final Pageable pageable) {
        Page<AvgStat> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = avgStatRepository.findAllById(longFilter, pageable);
        } else {
            page = avgStatRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((avgStat) -> mapToDTO(avgStat, new AvgStatDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public AvgStatDTO get(final Long id) {
        return avgStatRepository.findById(id)
                .map(avgStat -> mapToDTO(avgStat, new AvgStatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final AvgStatDTO avgStatDTO) {
        final AvgStat avgStat = new AvgStat();
        mapToEntity(avgStatDTO, avgStat);
        return avgStatRepository.save(avgStat).getId();
    }

    @Override
    public void update(final Long id, final AvgStatDTO avgStatDTO) {
        final AvgStat avgStat = avgStatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(avgStatDTO, avgStat);
        avgStatRepository.save(avgStat);
    }

    @Override
    public void delete(final Long id) {
        avgStatRepository.deleteById(id);
    }

    private AvgStatDTO mapToDTO(final AvgStat avgStat, final AvgStatDTO avgStatDTO) {
        avgStatDTO.setId(avgStat.getId());
        avgStatDTO.setAvgAces(avgStat.getAvgAces());
        avgStatDTO.setAvgDoubleFaults(avgStat.getAvgDoubleFaults());
        avgStatDTO.setAvgServicePoints(avgStat.getAvgServicePoints());
        avgStatDTO.setAvgFirstServeIn(avgStat.getAvgFirstServeIn());
        avgStatDTO.setAvgFirstServeWon(avgStat.getAvgFirstServeWon());
        avgStatDTO.setAvgSecondServeWon(avgStat.getAvgSecondServeWon());
        avgStatDTO.setAvgServiceGames(avgStat.getAvgServiceGames());
        avgStatDTO.setAvgSavedBreakPoints(avgStat.getAvgSavedBreakPoints());
        avgStatDTO.setAvgFacedBreakPoints(avgStat.getAvgFacedBreakPoints());
        avgStatDTO.setPlayer(avgStat.getPlayer());
        return avgStatDTO;
    }

    private AvgStat mapToEntity(final AvgStatDTO avgStatDTO, final AvgStat avgStat) {
        avgStat.setAvgAces(avgStatDTO.getAvgAces());
        avgStat.setAvgDoubleFaults(avgStatDTO.getAvgDoubleFaults());
        avgStat.setAvgServicePoints(avgStatDTO.getAvgServicePoints());
        avgStat.setAvgFirstServeIn(avgStatDTO.getAvgFirstServeIn());
        avgStat.setAvgFirstServeWon(avgStatDTO.getAvgFirstServeWon());
        avgStat.setAvgSecondServeWon(avgStatDTO.getAvgSecondServeWon());
        avgStat.setAvgServiceGames(avgStatDTO.getAvgServiceGames());
        avgStat.setAvgSavedBreakPoints(avgStatDTO.getAvgSavedBreakPoints());
        avgStat.setAvgFacedBreakPoints(avgStatDTO.getAvgFacedBreakPoints());
        final Player player = avgStatDTO.getPlayer() == null ? null : playerRepository.findById(avgStatDTO.getPlayer().getId())
                .orElseThrow(() -> new NotFoundException("player not found"));
        avgStat.setPlayer(player);
        return avgStat;
    }

    @Override
    public String getReferencedWarning(final Long id) {
        final AvgStat avgStat = avgStatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Player avgStatPlayer = playerRepository.findFirstByAvgStat(avgStat);
        if (avgStatPlayer != null) {
            return WebUtils.getMessage("avgStat.player.avgStat.referenced", avgStatPlayer.getId());
        }
        return null;
    }

    private double calculateAverage(List <Long> marks) {
       
        DoubleSummaryStatistics iss = marks.stream().mapToDouble((a) -> a). summaryStatistics();

        return (double) Math.round(iss.getAverage()*10)/10;
      }
    

    @Override
    public AvgStat computeAvgStatsByPlayer(Player player) {

            AvgStat avgStat = new AvgStat();
            Set<Stat> stats = player.getStats();



            List<Long> acesList = new ArrayList<>();
            List<Long> doubleFaultsList = new ArrayList<>();
            List<Long> servicePointsList = new ArrayList<>();
            List<Long> firstServeInList = new ArrayList<>();
            List<Long> firstServeWonList = new ArrayList<>();
            List<Long> secondServeWonList = new ArrayList<>();
            List<Long> serviceGamesList = new ArrayList<>();
            List<Long> savedBreakPointsList = new ArrayList<>();
            List<Long> facedBreakPointsList = new ArrayList<>();

            
            for(Stat stat : stats){

                acesList.add(stat.getAces());
                doubleFaultsList.add(stat.getDoubleFaults());
                servicePointsList.add(stat.getServicePoints());
                firstServeInList.add(stat.getFirstServeIn());
                firstServeWonList.add(stat.getFirstServeWon());
                secondServeWonList.add(stat.getFirstServeWon());
                serviceGamesList.add(stat.getServiceGames());
                savedBreakPointsList.add(stat.getSavedBreakPoints());
                facedBreakPointsList.add(stat.getFacedBreakPoints());
            }
          
            double avgAces = calculateAverage(acesList);
            double avgDoubleFaults = calculateAverage(doubleFaultsList);
            double avgServicePoints = calculateAverage(servicePointsList);
            double avgFirstServeIn = calculateAverage(firstServeInList);
            double avgFirstServeWon = calculateAverage(firstServeWonList);
            double avgSecondServeWon = calculateAverage(secondServeWonList);
            double avgServiceGames = calculateAverage(serviceGamesList);
            double avgSavedBreakPoints = calculateAverage(savedBreakPointsList);
            double avgFacedBreakPoints = calculateAverage(facedBreakPointsList);

            avgStat.setId(player.getId());
            avgStat.setAvgAces(avgAces);
            avgStat.setAvgDoubleFaults(avgDoubleFaults);
            avgStat.setAvgServicePoints(avgServicePoints);
            avgStat.setAvgFirstServeIn(avgFirstServeIn);
            avgStat.setAvgFirstServeWon(avgFirstServeWon);
            avgStat.setAvgSecondServeWon(avgSecondServeWon);
            avgStat.setAvgServiceGames(avgServiceGames);
            avgStat.setAvgSavedBreakPoints(avgSavedBreakPoints);
            avgStat.setAvgFacedBreakPoints(avgFacedBreakPoints);
            avgStat.setPlayer(player == null ? null : playerRepository.findById(player.getId())
            .orElseThrow(() -> new NotFoundException("player not found")));

            return avgStat;
            
    }

}
