package com.tennis.atp_matches_stats.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.List;

@Service
public class DataSavingServiceImpl implements DataSavingService {
    private PlayerService playerService;
    private MatchService matchService;
    private MatchRepository matchRepository;
    private TournamentRepository tournamentRepository;
    private PlayerRepository playerRepository;

    public DataSavingServiceImpl(MatchRepository matchRepository, @Lazy MatchService matchService,TournamentRepository tournamentRepository, @Lazy PlayerService playerService,
                                 PlayerRepository playerRepository){
        this.playerService = playerService;
        this.matchService = matchService;
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void parseCsvToMatchs(String fileName) throws IOException, CsvException {

        URL fileUrl = new File(fileName).toURI().toURL();
        CSVReader csvReader = new CSVReader(new FileReader(fileUrl.getFile()));
        List<String[]> records = csvReader.readAll();

        for (String[] dataLine : records) {
            Match match = new Match();
            match.setId(Long.valueOf(dataLine[0].replace("-", "") + dataLine[6]));
            Tournament tournament = new Tournament();
            Player winner = new Player();
            Player loser = new Player();
            tournament.setId(Long.valueOf(dataLine[0].replace("-", "")));
            tournament.setName(dataLine[1]);
            tournament.setSurface(dataLine[2]);
            tournament.setDrawSize(dataLine[3]);
            tournament.setLevel(dataLine[4]);
            tournament.setDate(dataLine[5]);
            match.setTournament(tournament);
            match.setMatchNumber(dataLine[0] + dataLine[6]);
            winner.setId(Long.valueOf(dataLine[7]));
            winner.setSeed(dataLine[8]);
            winner.setEntry(dataLine[9]);
            winner.setName(dataLine[10]);
            winner.setHand(dataLine[11]);
            winner.setHeight(dataLine[12]);
            winner.setNationality(dataLine[13]);
            winner.setAge(dataLine[14]);
            winner.setWonMatchs(matchService.findAllWonMatchsByPlayerId(winner.getId()));
            winner.setLostMatchs(matchService.findAllLostMatchsByPlayerId(winner.getId()));
            match.setWinner(winner);
            loser.setId(Long.valueOf(dataLine[15]));
            loser.setSeed(dataLine[16]);
            loser.setEntry(dataLine[17]);
            loser.setName(dataLine[18]);
            loser.setHand(dataLine[19]);
            loser.setHeight(dataLine[20]);
            loser.setNationality(dataLine[21]);
            loser.setAge(dataLine[22]);
            loser.setWonMatchs(matchService.findAllWonMatchsByPlayerId(loser.getId()));
            loser.setLostMatchs(matchService.findAllLostMatchsByPlayerId(loser.getId()));
            match.setLoser(loser);
            match.setScore(dataLine[23]);
            match.setBestOf(dataLine[24]);
            match.setTour(dataLine[25]);
            match.setMinutes(dataLine[26]);
            match.setWinnerAces(dataLine[27]);
            match.setWinnerDoubleFaults(dataLine[28]);
            match.setWinnerServicePoints(dataLine[29]);
            match.setWinnerFirstServeIn(dataLine[30]);
            match.setWinnerFirstServeWon(dataLine[31]);
            match.setWinnerSecondServiceWon(dataLine[32]);
            match.setWinnerServiceGames(dataLine[33]);
            match.setWinnerSavedBreakPoints(dataLine[34]);
            match.setWinnerFacedBreakPoints(dataLine[35]);
            match.setLoserAces(dataLine[36]);
            match.setLoserDoubleFaults(dataLine[37]);
            match.setLoserServicePoints(dataLine[38]);
            match.setLoserFirstServeIn(dataLine[39]);
            match.setLoserFirstServeWon(dataLine[40]);
            match.setLoserSecondServiceWon(dataLine[41]);
            match.setLoserServiceGames(dataLine[42]);
            match.setLoserSavedBreakPoints(dataLine[43]);
            match.setLoserFacedBreakPoints(dataLine[44]);
            match.setWinnerRank(dataLine[45]);
            match.setWinnerRankPoints(dataLine[46]);
            match.setLoserRank(dataLine[47]);
            match.setLoserRankPoints(dataLine[48]);


            //       if (!findingPlayer(winner) && !findingPlayer(loser) && !findingTournament(tournament)) {
            matchRepository.save(match);
            //     }
        }
    }


    boolean findingPlayer(Player player) {
        for (Player p : playerRepository.findAll()) {
            if (p.getId().equals(player.getId())) {
                return true;
            }
        }
        return false;
    }

    boolean findingTournament(Tournament tournament) {
        for (Tournament t : tournamentRepository.findAll()) {
            if (t.getId().equals(tournament.getId())) {
                return true;
            }
        }
        return false;
    }

}